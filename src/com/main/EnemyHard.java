package com.main;

import java.awt.*;

/**
 * Creates a SlowEnemy that extends the Enemy class
 */
public class EnemyHard extends Enemy {

    /**
     * Creates a slow enemy object with an x-coordinate, y-coordinate, ID, attaches it to the handler, and adds a player
     *
     * @param x       sets x-coordinate
     * @param y       sets y-coordinate
     * @param id      sets ID
     * @param handler sets/attaches to handler
     */
    public EnemyHard(float x, float y, ID id, Handler handler) {
        super(x, y, id, handler);
        this.handler = handler;
        setWidth(32);
        setHeight(32);
        setColor(Color.YELLOW);
        velocityX = velocityY = 1;
        velocityX = randomizeVelocityDirection();
        velocityY = randomizeVelocityDirection();
        setImage(96, 0, (int) width, (int) height);
    }

    /**
     * Increments X and Y positions based on velocity X and Y
     * and clamps position to game bounds
     */
    @Override
    public void tick() {
        setX(getX() + getVelocityX());
        setY(getY() + getVelocityY());

        clampPosition();
    }

    /**
     * Clamps the position of the object to the game bounds
     * This one also changes velocity when hitting a wall
     */
    @Override
    public void clampPosition() {
        if (getX() <= 0 || getX() >= Game.WIDTH - getWidth()) {
            if (getX() <= 0) setVelocityX(r.nextInt(1, 6) * 1);
            else setVelocityX(r.nextInt(1, 7) * -1);
        }

        if (getY() <= 0 || getY() >= Game.HEIGHT - getHeight()) {
            if (getY() <= 0) setVelocityY(r.nextInt(1, 6) * 1);
            else setVelocityY(r.nextInt(1, 7) * -1);

        }
    }
}
