package com.main;

import java.awt.image.BufferedImage;

public class Sprite {
    private BufferedImage sprite;

    public Sprite(BufferedImage ss) {
        this.sprite = ss;
    }

    public BufferedImage grabSprite(int x, int y, int width, int height) {
        return sprite.getSubimage(x, y, width, height);
    }
}
