package com.github.baileyloewe.cubecrusade.entities;

import com.github.baileyloewe.cubecrusade.*;
import com.github.baileyloewe.cubecrusade.entities.components.*;
import com.github.baileyloewe.cubecrusade.signals.GameSignals;

import java.util.concurrent.TimeUnit;

public class Player extends Entity {
    private final GameHandler gameHandler;
    private boolean damageTimeout = false;
    private long timerEnd;
    private final HealthComponent healthComponent;
    private final KeyInput keyInput;

    public Player(ID id, GameHandler gameHandler, PositionComponent positionComponent, SizeComponent sizeComponent, DisplayComponent displayComponent, MovementComponent movementComponent, KeyInput keyInput) {
        super(id, positionComponent, sizeComponent, displayComponent, movementComponent);
        this.gameHandler = gameHandler;
        this.keyInput = keyInput;
        healthComponent = new HealthComponent(100);
        healthComponent.died.connect(() -> GameSignals.playerDied.emit());
        GameSignals.baseHealthIncreased.connect(this::onBaseHealthIncreased);
        GameSignals.healthRefilled.connect(healthComponent::fullHeal);
        movementComponent.xAxisCollision.connect(this::clamp);
        movementComponent.yAxisCollision.connect(this::clamp);
    }

    public static Player create(ID id, GameHandler gameHandler, Vector2D position, KeyInput keyInput) {
        PositionComponent positionComponent = new PositionComponent(position);
        SizeComponent sizeComponent = new SizeComponent(64, 64);
        MovementComponent movementComponent = new MovementComponent(positionComponent, sizeComponent, new Vector2D(0, 0), 2);
        DisplayComponent displayComponent = new DisplayComponent(positionComponent, sizeComponent, 0, 0);
        return new Player(id, gameHandler, positionComponent, sizeComponent, displayComponent, movementComponent, keyInput);
    }

    public void tick() {
        if (damageTimeout && (System.nanoTime() > timerEnd)) {
            damageTimeout = false;
        }
        collision();
        updateDirection();
        movementComponent.tick();
    }

    private void updateDirection() {
        movementComponent.direction.x = keyInput.dPressed - keyInput.aPressed;
        movementComponent.direction.y = keyInput.sPressed - keyInput.wPressed;
    }

    public void clamp() {
        positionComponent.position.x = Math.clamp(positionComponent.position.x, 0, Game.WIDTH - sizeComponent.width);
        positionComponent.position.y = Math.clamp(positionComponent.position.y, 0, Game.HEIGHT - sizeComponent.height);
    }

    public PositionComponent getPositionComponent() {
        return positionComponent;
    }

    private void collision() {
        for (Entity entity : gameHandler.getEntities()) {
            if (!(entity.getID() == ID.Player) && !(entity.getID() == ID.MenuParticle)) {
                if (this.getBounds().intersects(entity.getBounds()) && !damageTimeout) {
                    healthComponent.damage(1);
                    damageTimeout = true;
                    timerEnd = System.nanoTime() + TimeUnit.MILLISECONDS.toNanos(8);
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
        return positionComponent.position.x;
    }

    public float getYPos() {
        return positionComponent.position.y;
    }

}
