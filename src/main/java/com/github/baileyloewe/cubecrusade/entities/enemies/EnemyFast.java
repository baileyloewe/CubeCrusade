package com.github.baileyloewe.cubecrusade.entities.enemies;

import com.github.baileyloewe.cubecrusade.GameHandler;
import com.github.baileyloewe.cubecrusade.ID;

import java.awt.*;

/**
 * Creates a FastEnemy that extends the Enemy class
 */
public class EnemyFast extends Enemy {

    /**
     * Creates a fast enemy object with an x-coordinate, y-coordinate, ID, and attaches it to the gameHandler
     *
     * @param x       sets x-coordinate
     * @param y       sets y-coordinate
     * @param id      sets ID
     * @param gameHandler sets/attaches to gameHandler
     */
    public EnemyFast(float x, float y, ID id, GameHandler gameHandler) {
        super(x, y, id, gameHandler);
        setWidth(32);
        setHeight(32);
        this.gameHandler = gameHandler;
        setColor(Color.CYAN);
        velocityX = velocityY = 2;
        velocityX = randomizeVelocityDirection();
        velocityY = randomizeVelocityDirection();
        setImage(128, 0, (int) width, (int) height);
    }

    public EnemyFast(float x, float y, ID id, GameHandler gameHandler, float velocityX, float velocityY) {
        super(x, y, id, gameHandler);
        setWidth(16);
        setHeight(16);
        this.gameHandler = gameHandler;
        setColor(Color.CYAN);
        this.velocityX = velocityX;
        this.velocityY = velocityY;
        this.velocityX = randomizeVelocityDirection();
        this.velocityY = randomizeVelocityDirection();
        setImage(64, 0, (int) width, (int) height);
    }
}
