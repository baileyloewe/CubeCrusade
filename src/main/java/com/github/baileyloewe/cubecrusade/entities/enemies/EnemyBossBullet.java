package com.github.baileyloewe.cubecrusade.entities.enemies;

import com.github.baileyloewe.cubecrusade.Game;
import com.github.baileyloewe.cubecrusade.GameHandler;
import com.github.baileyloewe.cubecrusade.ID;

import java.awt.*;
import java.util.Random;

/**
 * Creates a BossEnemyBullet that extends the Enemy class
 */
public class EnemyBossBullet extends Enemy {

    /**
     * Creates a boss enemy bullet object with an x-coordinate, y-coordinate, ID, and attaches it to the gameHandler
     *
     * @param x       sets x-coordinate
     * @param y       sets y-coordinate
     * @param id      sets ID
     * @param gameHandler sets/attaches to gameHandler
     */
    public EnemyBossBullet(float x, float y, ID id, GameHandler gameHandler) {
        super(x, y, id, gameHandler);
        Random r = new Random();
        setWidth(32);
        setHeight(32);
        setColor(Color.RED);
        this.gameHandler = gameHandler;
        velocityX = r.nextInt(-2, 2);
        velocityY = 2;
        int[] rowCol = getImagePos();
        setImage(rowCol[0], rowCol[1], (int) width, (int) height);
    }

    @Override
    public void tick() {
        setxPos(getxPos() + getVelocityX());
        setyPos(getyPos() + getVelocityY());

        if (getyPos() >= Game.HEIGHT) {
            gameHandler.removeEntity(this);
        }
    }

    public int[] getImagePos() {
        int randomNum = r.nextInt(1, 9);
        return switch (randomNum) {
            case 1 -> new int[]{192, 0};
            case 2 -> new int[]{224, 0};
            case 3 -> new int[]{64, 32};
            case 4 -> new int[]{96, 32};
            case 5 -> new int[]{128, 32};
            case 6 -> new int[]{160, 32};
            case 7 -> new int[]{192, 32};
            case 8 -> new int[]{224, 32};
            default -> null;
        };
    }
}
