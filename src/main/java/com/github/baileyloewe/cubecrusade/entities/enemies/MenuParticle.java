package com.github.baileyloewe.cubecrusade.entities.enemies;

import com.github.baileyloewe.cubecrusade.GameHandler;
import com.github.baileyloewe.cubecrusade.ID;

import java.awt.*;
import java.util.Random;

/**
 Creates a EnemyMenuParticle that extends the Enemy class
 */
public class MenuParticle extends Enemy {

    private final Random r = new Random();

    /**
     Creates a fast enemy object with an x-coordinate, y-coordinate, ID, and attaches it to the gameHandler
     @param x          sets x-coordinate
     @param y          sets y-coordinate
     @param id         sets ID
     @param gameHandler    sets/attaches to gameHandler
     */
    public MenuParticle(float x, float y, ID id, GameHandler gameHandler) {
        super(x, y, id, gameHandler);
        setWidth(r.nextInt(16,32));
        setHeight(getWidth());
        setVelocityX(setRandomVelocity());
        setVelocityY(setRandomVelocity());
        this.gameHandler = gameHandler;
        color = new Color(r.nextInt(255), r.nextInt(255), r.nextInt(255));
        setColor(color);
    }

    /**
     Sets a random velocity
     between -5 and 5 excluding 0
     */
    private int setRandomVelocity() {
        if ((r.nextInt(1,2) == 1)) {
            if (r.nextInt(2) == 1) return r.nextInt(1,8) * -1;
            else return r.nextInt(1, 5);
        }
        else {
            if (r.nextInt(2) == 1) return r.nextInt(1,8) * -1;
            else return r.nextInt(1,2);
        }
    }

    @Override
    public void render(Graphics g) {
        g.setColor(color);
        g.fillRect((int) xPos,(int) yPos, (int) width, (int) height);
        g.setColor(Color.black);
        g.drawRect((int) xPos,(int) yPos, (int) width - 1, (int) height - 1);
    }
}
