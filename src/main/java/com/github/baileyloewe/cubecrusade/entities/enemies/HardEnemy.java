package com.github.baileyloewe.cubecrusade.entities.enemies;

import com.github.baileyloewe.cubecrusade.ID;
import com.github.baileyloewe.cubecrusade.Vector2D;
import com.github.baileyloewe.cubecrusade.entities.components.DisplayComponent;
import com.github.baileyloewe.cubecrusade.entities.components.MovementComponent;
import com.github.baileyloewe.cubecrusade.entities.components.PositionComponent;
import com.github.baileyloewe.cubecrusade.entities.components.SizeComponent;

import java.util.Random;


public class HardEnemy extends Enemy {
    private static final Random RNG = new Random();

    public HardEnemy(ID id, PositionComponent positionComponent, SizeComponent sizeComponent, DisplayComponent displayComponent, MovementComponent movementComponent) {
        super(id, positionComponent, sizeComponent, displayComponent, movementComponent);
        movementComponent.xAxisCollision.connect(() -> { movementComponent.maxSpeed *= (int) (Math.random() * 6); movementComponent.bounceX(); });
        movementComponent.yAxisCollision.connect(() -> { movementComponent.maxSpeed *= (int) (Math.random() * 6); movementComponent.bounceY(); });
    }

    public static HardEnemy create(ID id, Vector2D position) {
        PositionComponent positionComponent = new PositionComponent(position);
        SizeComponent sizeComponent = new SizeComponent(32, 32);

        Vector2D initialDirection = new Vector2D(
                RNG.nextBoolean() ? 1 : -1,
                RNG.nextBoolean() ? 1 : -1
        );
        float initialMaxSpeed = RNG.nextInt(1, 7);
        MovementComponent movementComponent = new MovementComponent(positionComponent, sizeComponent, initialDirection, initialMaxSpeed);
        DisplayComponent displayComponent = new DisplayComponent(positionComponent, sizeComponent, 96, 0);
        return new HardEnemy(id, positionComponent, sizeComponent, displayComponent, movementComponent);
    }
}
