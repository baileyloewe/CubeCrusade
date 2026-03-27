package com.github.baileyloewe.cubecrusade.entities.enemies;

import com.github.baileyloewe.cubecrusade.GameHandler;
import com.github.baileyloewe.cubecrusade.ID;

import java.awt.*;

/**
 * Creates a SlowEnemy that extends the Enemy class
 */
public class EnemySlow extends Enemy {

    /**
     * Creates a slow enemy object with an x-coordinate, y-coordinate, ID, attaches it to the gameHandler, and adds a player
     *
     * @param x       sets x-coordinate
     * @param y       sets y-coordinate
     * @param id      sets ID
     * @param gameHandler sets/attaches to gameHandler
     */
    public EnemySlow(float x, float y, ID id, GameHandler gameHandler) {
        super(x, y, id, gameHandler);
        this.gameHandler = gameHandler;
        setWidth(32);
        setHeight(32);
        setColor(Color.RED);
        velocityX = velocityY = 1;
        velocityX = randomizeVelocityDirection();
        velocityY = randomizeVelocityDirection();
        setImage(64, 0, (int) width, (int) height);
    }
}
