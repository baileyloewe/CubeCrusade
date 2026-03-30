package com.github.baileyloewe.cubecrusade.entities.enemies;

import com.github.baileyloewe.cubecrusade.ID;
import com.github.baileyloewe.cubecrusade.Vector2D;
import com.github.baileyloewe.cubecrusade.entities.Entity;
import com.github.baileyloewe.cubecrusade.entities.components.DisplayComponent;
import com.github.baileyloewe.cubecrusade.entities.components.MovementComponent;
import com.github.baileyloewe.cubecrusade.entities.components.PositionComponent;
import com.github.baileyloewe.cubecrusade.entities.components.SizeComponent;

import java.util.Random;

public class FastEnemy extends Entity {
    private static final Random RNG = new Random();

    public FastEnemy(ID id, PositionComponent positionComponent, SizeComponent sizeComponent, MovementComponent movementComponent, DisplayComponent displayComponent) {
        super(id, positionComponent, sizeComponent, displayComponent, movementComponent);
        movementComponent.xAxisCollision.connect(movementComponent::bounceX);
        movementComponent.yAxisCollision.connect(movementComponent::bounceY);
    }

    public static FastEnemy create(ID id, Vector2D position, float maxSpeed) {
        PositionComponent positionComponent = new PositionComponent(position);
        SizeComponent sizeComponent = new SizeComponent(32, 32);
        Vector2D initialDirection = new Vector2D(
                RNG.nextBoolean() ? 1 : -1,
                RNG.nextBoolean() ? 1 : -1
        );
        MovementComponent movementComponent = new MovementComponent(positionComponent, sizeComponent, initialDirection, maxSpeed);
        DisplayComponent displayComponent = new DisplayComponent(positionComponent, sizeComponent, 128, 0);
        return new FastEnemy(id, positionComponent, sizeComponent, movementComponent, displayComponent);
    }
}
