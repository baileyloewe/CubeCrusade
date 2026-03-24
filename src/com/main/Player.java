package com.main;

import java.awt.*;
import java.awt.image.BufferedImage;

public class Player extends GameObject {
    private Mediator mediator;

    private final Handler handler;
    private final float width;
    private final float height;
    private final BufferedImage playerImage;
    private boolean damageTimeout = false;
    private long timerEnd;

    public Player(float x, float y, ID id, Handler handler, Mediator mediator) {
        super(x, y, id, handler);
        this.mediator = mediator;
        this.handler = mediator.getHandler();
        width = 64;
        height = 64;
        Sprite sprite = new Sprite(Game.spriteSheet);
        playerImage = sprite.grabSprite(0, 0, (int) width, (int) height);
    }

    @Override
    public void tick() {
        if (damageTimeout && (System.currentTimeMillis() > timerEnd)) {
            damageTimeout = false;
        }
        x += velocityX;
        y += velocityY;

        x = Game.clamp(x, 0, Game.WIDTH - 64);
        y = Game.clamp(y, 0, Game.HEIGHT - 64);

        collision();
    }

    private void collision() {
        for (GameObject tempObject : handler.GameObjectLinkedList) {
            if (!(tempObject.getID() == ID.Player) && !(tempObject.getID() == ID.MenuParticle)) {
                // Collision check
                if (getBounds().intersects(tempObject.getBounds()) && !damageTimeout) {
                    mediator.getUpgrade().setCurrentHealth(mediator.getUpgrade().getCurrentHealth() - 1);
                    damageTimeout = true;
                    timerEnd = System.currentTimeMillis() + 8;
                }
            }
        }
    }

    @Override
    public void render(Graphics g) {
        g.drawImage(playerImage, (int) x, (int) y, null);
    }

    @Override
    public Rectangle getBounds() {
        return new Rectangle((int) x, (int) y, (int) width, (int) height);
    }
}
