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

    private static final int initialXVelocity = 2 * Math.random() < .5 ? 1 : -1;
    private static final int initialYVelocity = 2 * Math.random() < .5 ? 1 : -1;

    public EnemyFast(ID id, GameHandler gameHandler, PositionComponent positionComponent, SizeComponent sizeComponent, MovementComponent movementComponent, DisplayComponent displayComponent) {
        super(id, gameHandler, positionComponent, sizeComponent, movementComponent, displayComponent);
    }

    public static EnemyFast create(ID id, GameHandler gameHandler, float xPos, float yPos) {
        PositionComponent positionComponent = new PositionComponent(xPos, yPos);
        SizeComponent sizeComponent = new SizeComponent(32, 32);
        MovementComponent movementComponent = new MovementComponent(positionComponent, sizeComponent, initialXVelocity, initialYVelocity, 2);
        DisplayComponent displayComponent = new DisplayComponent(sizeComponent, 128, 0);
        return new EnemyFast(id, gameHandler, positionComponent, sizeComponent, movementComponent, displayComponent);
    }

//    public EnemyFast(ID id, GameHandler gameHandler, float x, float y) {
//        super(id, gameHandler, x, y, 16, 16, initialXVelocity, initialYVelocity, 2);
//        this.gameHandler = gameHandler;
//        displayComponent = new DisplayComponent(sizeComponent, 64, 0);
//    }
}
