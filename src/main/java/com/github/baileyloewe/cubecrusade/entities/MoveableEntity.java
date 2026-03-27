package com.github.baileyloewe.cubecrusade.entities;

import com.github.baileyloewe.cubecrusade.Game;
import com.github.baileyloewe.cubecrusade.GameHandler;
import com.github.baileyloewe.cubecrusade.ID;

import java.awt.*;

public class MoveableEntity extends Entity {
    protected float xVelocity;
    protected float yVelocity;
    protected float speed;

    public MoveableEntity(float xPos, float yPos, float width, float height, ID id, GameHandler gameHandler, float xVelocity, float yVelocity, float speed) {
        super(xPos, yPos, width, height, id, gameHandler);
        this.xVelocity = xVelocity;
        this.yVelocity = yVelocity;
        this.speed = speed;
    }

    @Override
    public void tick() {
        updatePos();
        clampPosition();
    }

    @Override
    public void render(Graphics g) {

    }

    /**
     Clamps the position of the entity to the game bounds
     */
    public void clampPosition() {
        if (xPos <= 0 || xPos >= Game.WIDTH - getWidth()) {
            xVelocity *= -1;
        }

        if (yPos <= 0 || xPos >= Game.HEIGHT - getHeight()) {
            yVelocity *= - 1;
        }
    }

     /**
     Normalizes a speed based on velocityX and velocityY to the entity's current speed
     */
    public void normalizeSpeed()
    {
        if (xVelocity != 0 && yVelocity != 0)
        {
            float magnitude = (float) Math.sqrt(xVelocity * xVelocity + yVelocity * yVelocity);
            xVelocity = (xVelocity / magnitude) * Math.abs(speed);
            yVelocity = (yVelocity / magnitude) * Math.abs(speed);
        }
    }

    public void updatePos() {
        normalizeSpeed();
        xPos += xVelocity;
        yPos += yVelocity;
        clampPosition();
    }
}