package com.main;

import java.awt.*;

/**
 * Creates a FastEnemy that extends the Enemy class
 */
public class EnemyFast extends Enemy {

    /**
     * Creates a fast enemy object with an x-coordinate, y-coordinate, ID, and attaches it to the handler
     *
     * @param x       sets x-coordinate
     * @param y       sets y-coordinate
     * @param id      sets ID
     * @param handler sets/attaches to handler
     */
    public EnemyFast(float x, float y, ID id, Handler handler) {
        super(x, y, id, handler);
        setWidth(32);
        setHeight(32);
        this.handler = handler;
        setColor(Color.CYAN);
        velocityX = velocityY = 2;
        velocityX = randomizeVelocityDirection();
        velocityY = randomizeVelocityDirection();
        setImage(128, 0, (int) width, (int) height);
    }

    public EnemyFast(float x, float y, ID id, Handler handler, float velocityX, float velocityY) {
        super(x, y, id, handler);
        setWidth(16);
        setHeight(16);
        this.handler = handler;
        setColor(Color.CYAN);
        this.velocityX = velocityX;
        this.velocityY = velocityY;
        this.velocityX = randomizeVelocityDirection();
        this.velocityY = randomizeVelocityDirection();
        setImage(64, 0, (int) width, (int) height);
    }
}
