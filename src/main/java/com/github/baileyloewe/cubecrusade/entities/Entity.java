package com.github.baileyloewe.cubecrusade.entities;

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
    protected float xPos, yPos, width, height;
    protected ID id;
    protected GameHandler gameHandler;
    protected BufferedImage image;

    public Entity(float xPos, float yPos, float width, float height, ID id, GameHandler gameHandler)
    {
        this.xPos = xPos;
        this.yPos = yPos;
        this.width = width;
        this.height = height;
        this.id = id;
        this.gameHandler = gameHandler;
        this.gameHandler.addEntity(this);
    }

    public Entity(ServiceLocator serviceLocator)
    {
        gameHandler = serviceLocator.getGameHandler();
        gameHandler.addEntity(this);
    }

    public abstract void tick();
    public abstract void render(Graphics g);
    public Rectangle getBounds() {
        return new Rectangle((int) xPos, (int) yPos, (int) width, (int) height);
    }
    public float getXPos()
    {
        return xPos;
    }
    public void setXPos(float xPos)
    {
        this.xPos = xPos;
    }
    public float getYPos()
    {
        return yPos;
    }
    public void setYPos(float yPos)
    {
        this.yPos = yPos;
    }
    public ID getID()
    {
        return id;
    }
    public void setID(ID id)
    {
        this.id = id;
    }
    public float getHeight()
    {
        return height;
    }
    public void setHeight(float height)
    {
        this.height = height;
    }
    public float getWidth()
    {
        return width;
    }
    public void setWidth(float width)
    {
        this.width = width;
    }
}
