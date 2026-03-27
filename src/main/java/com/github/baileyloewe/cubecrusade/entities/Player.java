package com.github.baileyloewe.cubecrusade.entities;

import com.github.baileyloewe.cubecrusade.GameHandler;
import com.github.baileyloewe.cubecrusade.ID;
import com.github.baileyloewe.cubecrusade.entities.components.*;
import com.github.baileyloewe.cubecrusade.signals.GameSignals;

public class Player extends Entity {
    private final GameHandler gameHandler;
    private static final float width = 64;
    private static final float height = 64;
    private boolean damageTimeout = false;
    private long timerEnd;
    private final HealthComponent healthComponent;

    public Player(ID id, GameHandler gameHandler, PositionComponent positionComponent, SizeComponent sizeComponent, MovementComponent movementComponent, DisplayComponent displayComponent) {
        super(id, gameHandler, positionComponent, sizeComponent, movementComponent, displayComponent);

        this.gameHandler = gameHandler;

        healthComponent = new HealthComponent(100);
        healthComponent.died.connect(() -> GameSignals.playerDied.emit());
        GameSignals.baseHealthIncreased.connect(this::onBaseHealthIncreased);
        GameSignals.healthRefilled.connect(healthComponent::fullHeal);
    }

    public static Player create(ID id, GameHandler gameHandler, float xPos, float yPos) {
        PositionComponent positionComponent = new PositionComponent(xPos, yPos);
        SizeComponent sizeComponent = new SizeComponent(64, 64);
        MovementComponent movementComponent = new MovementComponent(positionComponent, sizeComponent,0, 0, 2);
        DisplayComponent displayComponent = new DisplayComponent(sizeComponent, 0, 0);
        return new Player(id, gameHandler, positionComponent, sizeComponent, movementComponent, displayComponent);
    }

    public void tick() {
        if (damageTimeout && (System.currentTimeMillis() > timerEnd)) {
            damageTimeout = false;
        }
        collision();
        movementComponent.tick();
    }

    private void collision() {
        for (Entity entity : gameHandler.getEntities()) {
            if (!(entity.getID() == ID.Player) && !(entity.getID() == ID.MenuParticle)) {
                // Collision check
                if (this.getBounds().intersects(entity.getBounds()) && !damageTimeout) {
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

    public float getXPos() {
        return positionComponent.xPos;
    }

    public float getYPos() {
        return positionComponent.yPos;
    }

    public void setXVelocity(float velocity) {
        movementComponent.xVelocity = velocity;
    }

    public void setYVelocity(float velocity) {
        movementComponent.yVelocity = velocity;
    }
}
