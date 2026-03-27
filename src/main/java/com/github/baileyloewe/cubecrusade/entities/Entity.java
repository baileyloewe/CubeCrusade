package com.github.baileyloewe.cubecrusade.entities;

import com.github.baileyloewe.cubecrusade.Game;
import com.github.baileyloewe.cubecrusade.GameHandler;
import com.github.baileyloewe.cubecrusade.ID;
import com.github.baileyloewe.cubecrusade.ServiceLocator;

import java.awt.*;
import java.awt.image.BufferedImage;

/**
 Creates an abstract Entity object for use in the game
 */
public abstract class Entity
{
    protected float x, y, width, height;
    protected ID id;
    protected float velocityX, velocityY;
    protected GameHandler gameHandler;
    protected BufferedImage image;

    /**
     Creates a game object with an x-coordinate, y-coordinate, ID, and attaches it to the gameHandler
     @param x          sets x-coordinate
     @param y          sets y-coordinate
     @param id         sets ID
     @param gameHandler   sets/attaches to gameHandler
     */
    public Entity(float x, float y, ID id, GameHandler gameHandler)
    {
        this.x = x;
        this.y = y;
        this.id = id;
        this.gameHandler = gameHandler;
        this.gameHandler.addEntity(this);
    }

    public Entity(ServiceLocator serviceLocator)
    {
        gameHandler = serviceLocator.getGameHandler();
        gameHandler.addEntity(this);
    }

    /**
     Clamps the position of the object to the game bounds
     */
    public void clampPosition() {
        if (getX() <= 0 || getX() >= Game.WIDTH - getWidth()) {
            setVelocityX(getVelocityX() * -1);
        }

        if (getY() <= 0 || getY() >= Game.HEIGHT - getHeight()) {
            setVelocityY(getVelocityY() * -1);
        }
    }

     /**
      Normalizes a speed based on velocityX and velocityY to a target speed
      @param velocityX    sets object's x velocity
      @param velocityY    sets object's y velocity
      @param targetSpeed  the total speed you want the velocityX and velocityY to add to; the diagonal vector speed
      */
    public float[] normalizeSpeed(float velocityX, float velocityY, float targetSpeed)
    {
        if (velocityX != 0 && velocityY != 0)
        {
            float magnitude = (float) Math.sqrt(velocityX * velocityX + velocityY * velocityY);

            velocityX = (velocityX / magnitude) * Math.abs(targetSpeed);
            velocityY = (velocityY / magnitude) * Math.abs(targetSpeed);

            return new float[]{velocityX, velocityY};
        }
        return new float[]{velocityX, velocityY};
    }

    public abstract void tick();
    public abstract void render(Graphics g);
    public Rectangle getBounds() {
        return new Rectangle((int) x, (int) y, (int) width, (int) height);
    }

    /**
     Sets x value, this is the x-position on the coordinate plane
     @param x sets object's x pos
     */
    public void setX(float x)
    {
        this.x = x;
    }

    /**
     Sets y value, this is the y-position on the coordinate plane
     @param y sets object's y pos
     */
    public void setY(float y)
    {
        this.y = y;
    }


    /**
     Returns x value, this is the x-position on the coordinate plane
     @return float
     */
    public float getX()
    {
        return x;
    }


    /**
     Returns y value, this is the y-position on the coordinate plane
     @return float
     */
    public float getY()
    {
        return y;
    }


    /**
     Sets the ID of the object
     @param id sets object's id
     */
    public void setID(ID id)
    {
        this.id = id;
    }


    /**
     Returns the ID of the object
     @return ID id
     */
    public ID getID()
    {
        return id;
    }

    /**
     Sets the x velocity value, which is used to increment the y-position on the coordinate plane
     @param velocityX sets object's x velocity
     */
    public void setVelocityX(float velocityX)
    {
        this.velocityX = velocityX;
    }

    /**
     Returns x velocity value, which is used to increment the y-position on the coordinate plane
     @return float
     */
    public float getVelocityX()
    {
        return velocityX;
    }

    /**
     Sets the y velocity value, which is used to increment the y-position on the coordinate plane
     @param velocityY sets object's y velocity
     */
    public void setVelocityY(float velocityY)
    {
        this.velocityY = velocityY;
    }

    /**
     Returns y velocity value, which is used to increment the y-position on the coordinate plane
     @return float
     */
    public float getVelocityY()
    {
        return velocityY;
    }

    /**
     Returns the height value
     @return height
     */
    public float getHeight()
    {
        return height;
    }

    /**
     Sets the height value
     @param height sets object's height (y coordinate plane)
     */
    public void setHeight(float height)
    {
        this.height = height;
    }

    /**
     Returns the width value
     @return float
     */
    public float getWidth()
    {
        return width;
    }

    /**
     Sets the width value
     @param width sets object's width (x coordinate plane)
     */
    public void setWidth(float width)
    {
        this.width = width;
    }

    public ID getId() {
        return id;
    }
}
