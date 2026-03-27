package com.github.baileyloewe.cubecrusade.entities;

import java.awt.*;

public class RectRenderer {
    private final Color color;
    private int xPos;
    private int yPos;
    private final int width;
    private final int height;

    public RectRenderer(Color color, int width, int height) {
        this.color = color;
        this.width = width;
        this.height = height;
    }

    public void render(Graphics g) {
        g.setColor(color);
        g.fillRect(0, 0, width, height);
    }
}



