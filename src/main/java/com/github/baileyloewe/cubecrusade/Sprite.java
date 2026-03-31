package com.github.baileyloewe.cubecrusade;

import java.awt.image.BufferedImage;

public class Sprite {
    private final BufferedImage sprite;

    public Sprite(BufferedImage spritesheet) {
        this.sprite = spritesheet;
    }

    public BufferedImage grabSprite(int x, int y, int width, int height) {
        return sprite.getSubimage(x, y, width, height);
    }
}
