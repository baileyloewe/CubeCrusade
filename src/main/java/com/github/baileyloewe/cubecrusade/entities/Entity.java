package com.github.baileyloewe.cubecrusade.entities;

import com.github.baileyloewe.cubecrusade.GameHandler;
import com.github.baileyloewe.cubecrusade.ID;
import com.github.baileyloewe.cubecrusade.entities.components.DisplayComponent;
import com.github.baileyloewe.cubecrusade.entities.components.MovementComponent;
import com.github.baileyloewe.cubecrusade.entities.components.PositionComponent;
import com.github.baileyloewe.cubecrusade.entities.components.SizeComponent;

import java.awt.*;
import java.awt.image.BufferedImage;

/**
 Creates an abstract Entity object for use in the game
 */
public abstract class Entity
{
    protected PositionComponent positionComponent;
    protected MovementComponent movementComponent;
    protected SizeComponent sizeComponent;
    protected DisplayComponent displayComponent;
    protected ID id;
    protected GameHandler gameHandler;
    protected BufferedImage image;

    public Entity(ID id, GameHandler gameHandler, PositionComponent positionComponent, SizeComponent sizeComponent, DisplayComponent displayComponent)
    {
        this.positionComponent = positionComponent;
        this.sizeComponent = sizeComponent;
        this.displayComponent = displayComponent;
        this.id = id;
        this.gameHandler = gameHandler;
        this.gameHandler.addEntity(this);
    }

    public Entity(ID id, GameHandler gameHandler, PositionComponent positionComponent, SizeComponent sizeComponent, MovementComponent movementComponent, DisplayComponent displayComponent)
    {
        this.positionComponent = positionComponent;
        this.sizeComponent = sizeComponent;
        this.movementComponent = movementComponent;
        this.displayComponent = displayComponent;
        this.id = id;
        this.gameHandler = gameHandler;
        this.gameHandler.addEntity(this);
    }

    public Entity(GameHandler gameHandler)
    {
        gameHandler.addEntity(this);
    }

    public void tick() {
        movementComponent.tick();
    }
    public void render(Graphics g) {
        displayComponent.render(g);
    }
    public Rectangle getBounds() {
        return new Rectangle((int) positionComponent.xPos, (int) positionComponent.yPos, (int) sizeComponent.width, (int) sizeComponent.height);
    }
    public ID getID()
    {
        return id;
    }
    public void setID(ID id)
    {
        this.id = id;
    }

}
