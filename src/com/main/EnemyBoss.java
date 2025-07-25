package com.main;

import java.awt.*;
import java.util.Random;

/**
 * Creates a BossEnemy that extends the Enemy class
 */
public class EnemyBoss extends Enemy {
    private final Random r;
    private int timer = 80;
    private int timer2 = 50;
    private long lifetimer;


    /**
     * Creates a boss enemy object with an ID and attaches it to the handler
     *
     * @param id      sets ID
     * @param handler sets/attaches to handler
     */
    public EnemyBoss(ID id, Handler handler) {
        super(Game.WIDTH / 2.f, -50, id, handler);
        setX(Game.WIDTH / 2.f - 32);
        setWidth(128);
        setHeight(128);
        velocityX = velocityY = 2;
        r = new Random();
        this.handler = handler;
        setColor(Color.RED);
        velocityY = 1;
        setImage(0, 128, (int) width, (int) height);
        lifetimer = System.currentTimeMillis() + 8000;

    }

    @Override
    public void tick() {
        if (System.currentTimeMillis() > lifetimer) {
            handler.clearEnemies();
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
                handler.addObject(new EnemyBossBullet((int) x + (getWidth() / 2) - 8, (int) y + (getHeight() / 2) - 8, ID.BossEnemyBullet, handler));
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

