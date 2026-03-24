package com.main;

import java.awt.*;
import java.util.Random;

/**
 * Creates an abstract Enemy class that extends the GameObject class
 */
public abstract class Enemy extends GameObject {

    protected final Random r;
    protected Color color;

    /**
     * Creates an enemy object with an x-coordinate, y-coordinate, ID, and attaches it to the handler
     *
     * @param x       sets x-coordinate
     * @param y       sets y-coordinate
     * @param id      sets ID
     * @param handler sets/attaches to handler
     */
    public Enemy(float x, float y, ID id, Handler handler) {
        super(x, y, id, handler);
        this.r = new Random();
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

    @Override
    public void render(Graphics g) {
        g.drawImage(image, (int) x, (int) y, null);
    }

    public float randomizeVelocityDirection() {
        if (r.nextInt(2) == 1) {
            return -velocityX;
        } else return velocityY;
    }

    public void setImage(int x, int y, int width, int height) {
        Sprite sprite = new Sprite(Game.spriteSheet);
        image = sprite.grabSprite(x, y, width, height);
    }

    public Rectangle getBounds() {
        return new Rectangle((int) getX(), (int) getY(), (int) getWidth(), (int) getHeight());
    }

    public Color getColor() {
        return color;
    }

    public void setColor(Color color) {
        this.color = color;
    }


}
