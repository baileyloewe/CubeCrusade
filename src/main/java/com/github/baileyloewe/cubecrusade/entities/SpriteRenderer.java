package com.github.baileyloewe.cubecrusade.entities;

import java.awt.*;
import java.awt.image.BufferedImage;

public class SpriteRenderer {
    private final BufferedImage image;
    private int xPos;
    private int yPos;

    public SpriteRenderer(BufferedImage image, int xPos, int yPos) {
        this.image = image;
        this.xPos = xPos;
        this.yPos = yPos;
    }

    public void render(Graphics g) {
        g.drawImage(image, (int) xPos, (int) yPos, null);
    }
}