package com.main;

import java.awt.*;

/**
 * Creates a SlowEnemy that extends the Enemy class
 */
public class EnemySlow extends Enemy {

    /**
     * Creates a slow enemy object with an x-coordinate, y-coordinate, ID, attaches it to the handler, and adds a player
     *
     * @param x       sets x-coordinate
     * @param y       sets y-coordinate
     * @param id      sets ID
     * @param handler sets/attaches to handler
     */
    public EnemySlow(float x, float y, ID id, Handler handler) {
        super(x, y, id, handler);
        this.handler = handler;
        setWidth(32);
        setHeight(32);
        setColor(Color.RED);
        velocityX = velocityY = 1;
        velocityX = randomizeVelocityDirection();
        velocityY = randomizeVelocityDirection();
        setImage(64, 0, (int) width, (int) height);
    }
}
