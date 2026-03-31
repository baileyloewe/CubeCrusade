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
    private final UpgradeComponent upgradeComponent;
    private final KeyInput keyInput;
    private float gold = 0;

    public Player(ID id, GameHandler gameHandler, PositionComponent positionComponent, SizeComponent sizeComponent, DisplayComponent displayComponent, MovementComponent movementComponent, KeyInput keyInput) {
        super(id, positionComponent, sizeComponent, displayComponent, movementComponent);
        this.gameHandler = gameHandler;
        this.keyInput = keyInput;
        healthComponent = new HealthComponent(100);
        upgradeComponent = new UpgradeComponent(this);
        connectSignals();
    }

    public static Player create(ID id, GameHandler gameHandler, Vector2D position, KeyInput keyInput) {
        PositionComponent positionComponent = new PositionComponent(position);
        SizeComponent sizeComponent = new SizeComponent(64, 64);
        MovementComponent movementComponent = new MovementComponent(positionComponent, sizeComponent, new Vector2D(0, 0), 2);
        DisplayComponent displayComponent = new DisplayComponent(positionComponent, sizeComponent, 0, 0);
        return new Player(id, gameHandler, positionComponent, sizeComponent, displayComponent, movementComponent, keyInput);
    }

    public void connectSignals() {
        healthComponent.died.connect(this, () -> GameSignals.playerDied.emit());

        upgradeComponent.baseHealthIncreased.connect(this, healthComponent::increaseMaxHealth);
        upgradeComponent.healthRefilled.connect(this, healthComponent::fullHeal);
        upgradeComponent.speedIncreased.connect(this, (val) -> movementComponent.maxSpeed += val);

        movementComponent.xAxisCollision.connect(this, () -> positionComponent.clamp(sizeComponent));
        movementComponent.yAxisCollision.connect(this, () -> positionComponent.clamp(sizeComponent));
    }

    public void tick() {
        if (damageTimeout && (System.nanoTime() > timerEnd)) {
            damageTimeout = false;
        }
        collision();
        updateDirection();
        movementComponent.tick();
        this.gold += .1f;
    }

    private void updateDirection() {
        movementComponent.direction.x = keyInput.dPressed - keyInput.aPressed;
        movementComponent.direction.y = keyInput.sPressed - keyInput.wPressed;
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

    public float getGold() {
        return gold;
    }

    public void removeGold(float gold) {
        this.gold -= gold;
    }

    public PositionComponent getPositionComponent() {
        return positionComponent;
    }

    public SizeComponent getSizeComponent() {
        return sizeComponent;
    }

    public UpgradeComponent getUpgradeComponent() {
        return upgradeComponent;
    }

}
