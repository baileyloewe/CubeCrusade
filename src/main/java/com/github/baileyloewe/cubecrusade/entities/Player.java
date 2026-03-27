package com.github.baileyloewe.cubecrusade.entities;

import com.github.baileyloewe.cubecrusade.*;
import com.github.baileyloewe.cubecrusade.signals.GameSignals;

import java.awt.*;
import java.awt.image.BufferedImage;

public class Player extends Entity {
    private final GameHandler gameHandler;
    private final float width;
    private final float height;
    private final BufferedImage playerImage;
    private boolean damageTimeout = false;
    private long timerEnd;
    private int health = 100;
    private int maxHealth = 100;

    public Player(float x, float y, ID id, ServiceLocator serviceLocator) {
        super(x, y, id, serviceLocator.getGameHandler());
        this.gameHandler = serviceLocator.getGameHandler();
        width = 64;
        height = 64;
        Sprite sprite = new Sprite(Game.spriteSheet);
        playerImage = sprite.grabSprite(0, 0, (int) width, (int) height);
        GameSignals.GameExited.connect(this::reset);
        GameSignals.baseHealthIncreased.connect(this::onBaseHealthIncreased);
        GameSignals.healthRefilled.connect(() -> this.health = maxHealth);
    }

    @Override
    public void tick() {
        if (damageTimeout && (System.currentTimeMillis() > timerEnd)) {
            damageTimeout = false;
        }
        x += velocityX;
        y += velocityY;

        x = Game.clamp(x, 0, Game.WIDTH - 64);
        y = Game.clamp(y, 0, Game.HEIGHT - 64);

        collision();

        if (health <= 0) {
            GameSignals.PlayerDied.emit();
        }
    }

    private void collision() {
        for (Entity entity : gameHandler.getEntities()) {
            if (!(entity.getID() == ID.Player) && !(entity.getID() == ID.MenuParticle)) {
                // Collision check
                if (getBounds().intersects(entity.getBounds()) && !damageTimeout) {
                    GameSignals.healthLost.emit(-1);
                    health -= 1;
                    damageTimeout = true;
                    timerEnd = System.currentTimeMillis() + 8;
                }
            }
        }
    }

    public int getHealth() {
        return health;
    }

    public int getMaxHealth() {
        return maxHealth;
    }

    public void reset() {
        health = 100;
        maxHealth = 100;
        setVelocityX(0);
        setVelocityY(0);
    }

    public void onBaseHealthIncreased(int amount) {
        health += amount;
        maxHealth += amount;
    }

    @Override
    public void render(Graphics g) {
        g.drawImage(playerImage, (int) x, (int) y, null);
    }
}
