package com.github.baileyloewe.cubecrusade.entities.enemies;

import com.github.baileyloewe.cubecrusade.*;

import java.awt.*;
import java.util.Random;

/**
 * Creates a BossEnemy that extends the Enemy class
 */
public class EnemyBoss extends Enemy {
    private final Random r;
    private int timer = 80;
    private int timer2 = 50;
    private final long lifespan;

    /**
     * Creates a boss enemy object with an ID and attaches it to the gameHandler
     *
     * @param id      sets ID
     * @param gameHandler sets/attaches to gameHandler
     */
    public EnemyBoss(ID id, GameHandler gameHandler) {
        super(Game.WIDTH / 2.f, -50, id, gameHandler);
        setX(Game.WIDTH / 2.f - 32);
        setWidth(128);
        setHeight(128);
        velocityX = velocityY = 2;
        r = new Random();
        this.gameHandler = gameHandler;
        setColor(Color.RED);
        velocityY = 1;
        setImage(0, 128, (int) width, (int) height);
        lifespan = System.currentTimeMillis() + 8000;
    }

    @Override
    public void tick() {
        if (System.currentTimeMillis() > lifespan) {
            gameHandler.clearEnemies();
        }
        setX(getX() + getVelocityX());
        setY(getY() + getVelocityY());

        if (timer <= 0) {
            setVelocityY(0);
            timer2--;
        } else {
            timer--;
        }
        if (timer2 <= 0) {
            if (getVelocityX() == 0) setVelocityX(2);
            int spawn = r.nextInt(10);
            if (spawn == 0) {
                gameHandler.addEntity(new EnemyBossBullet((int) x + (getWidth() / 2) - 8, (int) y + (getHeight() / 2) - 8, ID.BossEnemyBullet, gameHandler));
            }
        }

        if (getX() <= 100 || getX() >= Game.WIDTH - getWidth() - 50) {
            setVelocityX(getVelocityX() * -1);
        }
        if (getY() <= -200 || getY() >= Game.HEIGHT - getHeight() + 150) {
            setVelocityY(getVelocityY() * -1);
        }
    }
}

