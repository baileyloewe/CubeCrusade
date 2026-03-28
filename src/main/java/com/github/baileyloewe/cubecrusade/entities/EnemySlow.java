package com.github.baileyloewe.cubecrusade.entities;

import com.github.baileyloewe.cubecrusade.GameHandler;
import com.github.baileyloewe.cubecrusade.ID;
import com.github.baileyloewe.cubecrusade.entities.components.DisplayComponent;
import com.github.baileyloewe.cubecrusade.entities.components.MovementComponent;
import com.github.baileyloewe.cubecrusade.entities.components.PositionComponent;
import com.github.baileyloewe.cubecrusade.entities.components.SizeComponent;

public class EnemySlow extends Enemy {
    private static final int initialXVelocity = Math.random() < .5 ? 1 : -1;
    private static final int initialYVelocity = Math.random() < .5 ? 1 : -1;

    public EnemySlow(ID id, GameHandler gameHandler, PositionComponent positionComponent, SizeComponent sizeComponent, MovementComponent movementComponent, DisplayComponent displayComponent) {
        super(id, gameHandler, positionComponent, sizeComponent, movementComponent, displayComponent);
    }

    public static EnemySlow create(ID id, GameHandler gameHandler, float xPos, float yPos) {
        PositionComponent positionComponent = new PositionComponent(xPos, yPos);
        SizeComponent sizeComponent = new SizeComponent(32, 32);
        MovementComponent movementComponent = new MovementComponent(positionComponent, sizeComponent, initialXVelocity, initialYVelocity, 1);
        DisplayComponent displayComponent = new DisplayComponent(positionComponent, sizeComponent, 96, 0);
        return new EnemySlow(id, gameHandler, positionComponent, sizeComponent, movementComponent, displayComponent);
    }
}
