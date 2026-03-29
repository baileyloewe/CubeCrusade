package com.github.baileyloewe.cubecrusade.entities;

import com.github.baileyloewe.cubecrusade.ID;
import com.github.baileyloewe.cubecrusade.Vector2D;
import com.github.baileyloewe.cubecrusade.entities.components.DisplayComponent;
import com.github.baileyloewe.cubecrusade.entities.components.MovementComponent;
import com.github.baileyloewe.cubecrusade.entities.components.PositionComponent;
import com.github.baileyloewe.cubecrusade.entities.components.SizeComponent;
import com.github.baileyloewe.cubecrusade.entities.enemies.Enemy;

import java.awt.*;
import java.util.Random;

/**
 Creates a MenuParticle that extends the Enemy class
 */
public class MenuParticle extends Enemy {
    private static final Random RNG = new Random();

    public MenuParticle(ID id, PositionComponent positionComponent, SizeComponent sizeComponent, DisplayComponent displayComponent, MovementComponent movementComponent) {
        super(id,positionComponent, sizeComponent, displayComponent, movementComponent);
        movementComponent.xAxisCollision.connect(movementComponent::bounceX);
        movementComponent.yAxisCollision.connect(movementComponent::bounceY);
    }

    public static MenuParticle create(ID id, Vector2D position) {
        PositionComponent positionComponent = new PositionComponent(position);
        int dimension = RNG.nextInt(16,33);
        SizeComponent sizeComponent = new SizeComponent(dimension, dimension);
        Vector2D initialDirection = new Vector2D(
                RNG.nextBoolean() ? 1 : -1,
                RNG.nextBoolean() ? 1 : -1
        );
        MovementComponent movementComponent = new MovementComponent(positionComponent, sizeComponent, initialDirection, getRandomVelocity());
        DisplayComponent displayComponent = new DisplayComponent(positionComponent, sizeComponent, new Color(RNG.nextInt(255), RNG.nextInt(255), RNG.nextInt(255)));

        return new MenuParticle(id, positionComponent, sizeComponent, displayComponent, movementComponent);
    }

    private static int getRandomVelocity() {
        if (RNG.nextInt(1, 5) == 1) return RNG.nextInt(4,8); // 25 % chance to make a fast particle
        else return RNG.nextInt(1, 4); // 75 % chance to make a regular particle
    }
}
