package com.main;

import java.awt.*;
import java.util.Random;

/**
 Creates a EnemyMenuParticle that extends the Enemy class
 */
public class EnemyMenuParticle extends Enemy
{

    private final Random r = new Random();


    /**
     Creates a fast enemy object with an x-coordinate, y-coordinate, ID, and attaches it to the handler
     @param x          sets x-coordinate
     @param y          sets y-coordinate
     @param id         sets ID
     @param handler    sets/attaches to handler
     */
    public EnemyMenuParticle(float x, float y, ID id, Handler handler)
    {
        super(x, y, id, handler);
        setWidth(r.nextInt(16,32));
        setHeight(getWidth());
        setVelocityX(setRandomVelocity());
        setVelocityY(setRandomVelocity());
        this.handler = handler;
        color = new Color(r.nextInt(255), r.nextInt(255), r.nextInt(255));
        setColor(color);
    }

    /**
     Sets a random velocity
     between -5 and 5 excluding 0
     */
    private int setRandomVelocity()
    {
        if ((r.nextInt(1,2) == 1))
        {
            if (r.nextInt(2) == 1) return r.nextInt(1,8) * -1;
            else return r.nextInt(1, 5);
        }
        else
        {
            if (r.nextInt(2) == 1) return r.nextInt(1,8) * -1;
            else return r.nextInt(1,2);
        }
    }

    @Override
    public void render(Graphics g) {
        g.setColor(color);
        g.fillRect((int) x,(int) y, (int) width, (int) height);
        g.setColor(Color.black);
        g.drawRect((int) x,(int) y, (int) width - 1, (int) height - 1);
    }
}
