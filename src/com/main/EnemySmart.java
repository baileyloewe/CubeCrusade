package com.main;

import java.awt.*;
import java.util.Random;

/**
 * Creates a SmartEnemy that extends the Enemy class
 */
public class EnemySmart extends Enemy {

    private final Player player;
    private final Random r;


    /**
     * Creates a smart enemy object with an x-coordinate, y-coordinate, ID, attaches it to the handler, and adds a player
     *
     * @param x       sets x-coordinate
     * @param y       sets y-coordinate
     * @param id      sets ID
     * @param handler sets/attaches to handler
     * @param player  sets the player
     */
    public EnemySmart(float x, float y, ID id, Handler handler, Player player) {
        super(x, y, id, handler);
        setWidth(32);
        setHeight(32);
        this.handler = handler;
        this.player = player;
        setColor(Color.GREEN);
        r = new Random();
        setImage(160, 0, (int) width, (int) height);
    }

    /**
     * Enables to the smart to track the player by pulling the player location
     * Calculates the different in position and the angle, then sets velocity based on these values
     * <p>
     * If out of bounds,
     */
    @Override
    public void tick() {
        setX(getX() + getVelocityX());
        setY(getY() + getVelocityY());

        float targetPlayerX = player.getX() + 8;
        float targetPlayerY = player.getY() + 8;
        float differenceX = getX() - targetPlayerX;
        float differenceY = getY() - targetPlayerY;
        float angle = (float) Math.atan2(differenceX, differenceY);

        setVelocityY((float) -Math.cos(angle) + r.nextFloat(-.25f, .25f));
        setVelocityX((float) -Math.sin(angle) + r.nextFloat(-.25f, .25f));

        clampPosition();
    }
}
