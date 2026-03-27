package com.github.baileyloewe.cubecrusade.entities;

import com.github.baileyloewe.cubecrusade.GameHandler;
import com.github.baileyloewe.cubecrusade.ID;
import com.github.baileyloewe.cubecrusade.entities.components.DisplayComponent;
import com.github.baileyloewe.cubecrusade.entities.components.MovementComponent;
import com.github.baileyloewe.cubecrusade.entities.components.PositionComponent;
import com.github.baileyloewe.cubecrusade.entities.components.SizeComponent;

/**
 * Creates a FastEnemy that extends the Enemy class
 */
public class EnemyFast extends Enemy {

    public EnemyFast(ID id, GameHandler gameHandler, PositionComponent positionComponent, SizeComponent sizeComponent, MovementComponent movementComponent, DisplayComponent displayComponent) {
        super(id, gameHandler, positionComponent, sizeComponent, movementComponent, displayComponent);
    }

    public static EnemyFast create(ID id, GameHandler gameHandler, float xPos, float yPos, float xVelocity, float yVelocity) {
        PositionComponent positionComponent = new PositionComponent(xPos, yPos);
        SizeComponent sizeComponent = new SizeComponent(32, 32);
        int initialXVelocity = xVelocity * Math.random() < .5 ? 1 : -1;
        int initialYVelocity = yVelocity * Math.random() < .5 ? 1 : -1;
        MovementComponent movementComponent = new MovementComponent(positionComponent, sizeComponent, initialXVelocity, initialYVelocity, 2);
        DisplayComponent displayComponent = new DisplayComponent(positionComponent, sizeComponent, 128, 0);
        return new EnemyFast(id, gameHandler, positionComponent, sizeComponent, movementComponent, displayComponent);
    }
}
