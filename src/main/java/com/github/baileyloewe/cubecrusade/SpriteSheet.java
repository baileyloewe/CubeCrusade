package com.github.baileyloewe.cubecrusade;

import java.awt.image.BufferedImage;

public class SpriteSheet {
    private final BufferedImage spriteSheet;

    public SpriteSheet(BufferedImage spritesheet) {
        this.spriteSheet = spritesheet;
    }

    public BufferedImage grabSprite(int x, int y, int width, int height) {
        return spriteSheet.getSubimage(x, y, width, height);
    }
}
