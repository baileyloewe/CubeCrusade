package com.github.baileyloewe.cubecrusade.entities;

import com.github.baileyloewe.cubecrusade.*;
import com.github.baileyloewe.cubecrusade.signals.GameSignals;

import java.awt.*;
import java.awt.image.BufferedImage;

public class Player extends MoveableEntity {
    private final GameHandler gameHandler;
    private static final float width = 64;
    private static final float height = 64;
    private final BufferedImage playerImage;
    private boolean damageTimeout = false;
    private long timerEnd;
    private final HealthComponent healthComponent;
    private final PositionComponent positionComponent;
    private final MovementComponent movementComponent;

    public Player(float xPos, float yPos, ID id, ServiceLocator serviceLocator, float xVelocity, float yVelocity, float speed) {
        super(xPos, yPos, width, height, id, serviceLocator.getGameHandler(), xVelocity, yVelocity, speed);
        this.gameHandler = serviceLocator.getGameHandler();

        Sprite sprite = new Sprite(Game.spriteSheet);
        playerImage = sprite.grabSprite(0, 0, (int) width, (int) height);
        healthComponent = new HealthComponent(100);
        healthComponent.died.connect(() -> GameSignals.playerDied.emit());
        positionComponent = new PositionComponent(0,0 );

        GameSignals.baseHealthIncreased.connect(this::onBaseHealthIncreased);
        GameSignals.healthRefilled.connect(() -> healthComponent.fullHeal());
    }

    @Override
    public void tick() {
        super.tick();
        if (damageTimeout && (System.currentTimeMillis() > timerEnd)) {
            damageTimeout = false;
        }

        collision();
    }

    private void collision() {
        for (Entity entity : gameHandler.getEntities()) {
            if (!(entity.getID() == ID.Player) && !(entity.getID() == ID.MenuParticle)) {
                // Collision check
                if (getBounds().intersects(entity.getBounds()) && !damageTimeout) {
                    healthComponent.damage(1);
                    damageTimeout = true;
                    timerEnd = System.currentTimeMillis() + 8;
                }
            }
        }
    }

    public int getHealth() {
        return healthComponent.currentHealth;
    }

    public int getMaxHealth() {
        return healthComponent.maxHealth;
    }

    public void onBaseHealthIncreased(int amount) {
        healthComponent.increaseMaxHealth(amount);
    }

    @Override
    public void render(Graphics g) {
        g.drawImage(playerImage, (int) xPos, (int) yPos, null);
    }
}
