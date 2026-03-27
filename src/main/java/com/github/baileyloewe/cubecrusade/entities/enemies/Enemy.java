package com.github.baileyloewe.cubecrusade.entities.enemies;

import com.github.baileyloewe.cubecrusade.*;
import com.github.baileyloewe.cubecrusade.entities.Entity;
import com.github.baileyloewe.cubecrusade.entities.MoveableEntity;

import java.awt.*;
import java.util.Random;

/**
 * Creates an abstract Enemy class that extends the Entity class
 */
public abstract class Enemy extends MoveableEntity {

    protected final Random r;
    protected Color color;

    public Enemy(float xPos, float yPos, ID id, ServiceLocator serviceLocator, float xVelocity, float yVelocity, float speed) {
        super(xPos, yPos, width, height, id, serviceLocator.getGameHandler(), xVelocity, yVelocity, speed);
        this.r = new Random();
    }

    @Override
    public void tick() {
        super.tick();
    }

    @Override
    public void render(Graphics g) {
        g.drawImage(image, (int) xPos, (int) yPos, null);
    }

    public float randomizeVelocityDirection() {
        if (r.nextInt(2) == 1) {
            return -xVelocity;
        } else return yVelocity;
    }

    public void setImage(int x, int y, int width, int height) {
        Sprite sprite = new Sprite(Game.spriteSheet);
        image = sprite.grabSprite(x, y, width, height);
    }

    public Rectangle getBounds() {
        return new Rectangle((int) xPos, (int) yPos, (int) width, (int) height);
    }

    public Color getColor() {
        return color;
    }

    public void setColor(Color color) {
        this.color = color;
    }
}
