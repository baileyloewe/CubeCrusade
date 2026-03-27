package com.github.baileyloewe.cubecrusade.entities;

import com.github.baileyloewe.cubecrusade.GameHandler;
import com.github.baileyloewe.cubecrusade.ID;
import com.github.baileyloewe.cubecrusade.entities.components.DisplayComponent;
import com.github.baileyloewe.cubecrusade.entities.components.MovementComponent;
import com.github.baileyloewe.cubecrusade.entities.components.PositionComponent;
import com.github.baileyloewe.cubecrusade.entities.components.SizeComponent;

import java.awt.*;
import java.util.Random;

/**
 Creates a EnemyMenuParticle that extends the Enemy class
 */
public class MenuParticle extends Entity {

    private static final Random r = new Random();

    public MenuParticle(ID id, GameHandler gameHandler, PositionComponent positionComponent, SizeComponent sizeComponent, MovementComponent movementComponent, DisplayComponent displayComponent) {
        super(id, gameHandler, positionComponent, sizeComponent, movementComponent, displayComponent);
    }

    public static MenuParticle create(ID id, GameHandler gameHandler, float xPos, float yPos) {
        PositionComponent positionComponent = new PositionComponent(xPos, yPos);
        int dimension = r.nextInt(16,33);
        SizeComponent sizeComponent = new SizeComponent(dimension, dimension);
        MovementComponent movementComponent = new MovementComponent(positionComponent, sizeComponent, getRandomVelocity(), getRandomVelocity(), getRandomVelocity());
        DisplayComponent displayComponent = new DisplayComponent(sizeComponent, positionComponent, new Color(r.nextInt(255), r.nextInt(255), r.nextInt(255)));

        return new MenuParticle(id, gameHandler, positionComponent, sizeComponent, movementComponent, displayComponent);
    }

    private static int getRandomVelocity() {
        if ((r.nextInt(1,2) == 1)) {
            if (r.nextInt(2) == 1) return r.nextInt(1,8) * -1;
            else return r.nextInt(1, 5);
        }
        else {
            if (r.nextInt(2) == 1) return r.nextInt(1,8) * -1;
            else return r.nextInt(1,2);
        }
    }

}
