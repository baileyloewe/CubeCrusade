package com.github.baileyloewe.cubecrusade.entities;

import com.github.baileyloewe.cubecrusade.GameHandler;
import com.github.baileyloewe.cubecrusade.ID;
import com.github.baileyloewe.cubecrusade.entities.components.DisplayComponent;
import com.github.baileyloewe.cubecrusade.entities.components.MovementComponent;
import com.github.baileyloewe.cubecrusade.entities.components.PositionComponent;
import com.github.baileyloewe.cubecrusade.entities.components.SizeComponent;

/**
 * Creates an abstract Enemy class that extends the Entity class
 */
public abstract class Enemy extends Entity {
    public Enemy(ID id, GameHandler gameHandler, PositionComponent positionComponent, SizeComponent sizeComponent, MovementComponent movementComponent, DisplayComponent displayComponent) {
        super(id, gameHandler, positionComponent, sizeComponent, movementComponent, displayComponent);
        movementComponent.xAxisCollision.connect(this::bounceX);
        movementComponent.yAxisCollision.connect(this::bounceY);
    }

    public void bounceX() {
        movementComponent.direction.x *= -1;
    }

    public void bounceY() {
        movementComponent.direction.y *= - 1;
    }
}
