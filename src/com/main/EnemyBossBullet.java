package com.main;

import java.awt.*;
import java.util.Random;

/**
 * Creates a BossEnemyBullet that extends the Enemy class
 */
public class EnemyBossBullet extends Enemy {

    /**
     * Creates a boss enemy bullet object with an x-coordinate, y-coordinate, ID, and attaches it to the handler
     *
     * @param x       sets x-coordinate
     * @param y       sets y-coordinate
     * @param id      sets ID
     * @param handler sets/attaches to handler
     */
    public EnemyBossBullet(float x, float y, ID id, Handler handler) {
        super(x, y, id, handler);
        Random r = new Random();
        setWidth(32);
        setHeight(32);
        setColor(Color.RED);
        this.handler = handler;
        velocityX = r.nextInt(-2, 2);
        velocityY = 2;
        int[] rowCol = getImagePos();
        setImage(rowCol[0], rowCol[1], (int) width, (int) height);
    }

    @Override
    public void tick() {
        setX(getX() + getVelocityX());
        setY(getY() + getVelocityY());

        if (getY() >= Game.HEIGHT) {
            handler.removeObject(this);
        }
    }

    public int[] getImagePos() {
        int randomNum = r.nextInt(1, 9);
        switch (randomNum) {
            case 1:
                return new int[]{192, 0};
            case 2:
                return new int[]{224, 0};
            case 3:
                return new int[]{64, 32};
            case 4:
                return new int[]{96, 32};
            case 5:
                return new int[]{128, 32};
            case 6:
                return new int[]{160, 32};
            case 7:
                return new int[]{192, 32};
            case 8:
                return new int[]{224, 32};
        }
        return null;
    }
}
