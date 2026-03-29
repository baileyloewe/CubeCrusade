package com.github.baileyloewe.cubecrusade.entities;

import com.github.baileyloewe.cubecrusade.GameHandler;
import com.github.baileyloewe.cubecrusade.ID;
import com.github.baileyloewe.cubecrusade.entities.components.DisplayComponent;
import com.github.baileyloewe.cubecrusade.entities.components.MovementComponent;
import com.github.baileyloewe.cubecrusade.entities.components.PositionComponent;
import com.github.baileyloewe.cubecrusade.entities.components.SizeComponent;
import com.github.baileyloewe.cubecrusade.signals.GameSignals;

import java.awt.*;

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



    public Entity(ID id, PositionComponent positionComponent, SizeComponent sizeComponent, DisplayComponent displayComponent, MovementComponent movementComponent)
    {
        this.positionComponent = positionComponent;
        this.sizeComponent = sizeComponent;
        this.movementComponent = movementComponent;
        this.displayComponent = displayComponent;
        this.id = id;
        GameSignals.entityAdded.emit(this);
    }

    public Entity() {
    }

    public void tick() {
        movementComponent.tick();
    }

    public void render(Graphics g) {
        displayComponent.render(g);
    }
    public Rectangle getBounds() {
        return new Rectangle((int) positionComponent.position.x, (int) positionComponent.position.y, (int) sizeComponent.width, (int) sizeComponent.height);
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
