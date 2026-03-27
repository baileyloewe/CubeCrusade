package com.github.baileyloewe.cubecrusade.entities;

import com.github.baileyloewe.cubecrusade.GameHandler;
import com.github.baileyloewe.cubecrusade.ID;
import com.github.baileyloewe.cubecrusade.entities.components.DisplayComponent;
import com.github.baileyloewe.cubecrusade.entities.components.MovementComponent;
import com.github.baileyloewe.cubecrusade.entities.components.PositionComponent;
import com.github.baileyloewe.cubecrusade.entities.components.SizeComponent;


public class EnemyHard extends Enemy {
    private static final int initialXVelocity = Math.random() < .5 ? 1 : -1;
    private static final int initialYVelocity = Math.random() < .5 ? 1 : -1;

    public EnemyHard(ID id, GameHandler gameHandler, PositionComponent positionComponent, SizeComponent sizeComponent, MovementComponent movementComponent, DisplayComponent displayComponent) {
        super(id, gameHandler, positionComponent, sizeComponent, movementComponent, displayComponent);
        movementComponent.xAxisCollision.connect(() -> movementComponent.xVelocity *= (int) (Math.random() * 6));
        movementComponent.yAxisCollision.connect(() -> movementComponent.yVelocity *= (int) (Math.random() * 6));
    }

    public static EnemyHard create(ID id, GameHandler gameHandler, float xPos, float yPos) {
        PositionComponent positionComponent = new PositionComponent(xPos, yPos);
        SizeComponent sizeComponent = new SizeComponent(32, 32);
        MovementComponent movementComponent = new MovementComponent(positionComponent, sizeComponent, initialXVelocity, initialYVelocity, 1);
        DisplayComponent displayComponent = new DisplayComponent(sizeComponent, 96, 0);
        return new EnemyHard(id, gameHandler, positionComponent, sizeComponent, movementComponent, displayComponent);
    }

    @Override
    public void tick() {
    }
}
