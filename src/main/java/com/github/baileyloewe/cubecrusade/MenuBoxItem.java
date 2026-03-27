package com.github.baileyloewe.cubecrusade;

import java.awt.*;

public class MenuBoxItem {

    public Rectangle rect;
    public String text;

    public MenuBoxItem(int x, int y, int width, int height, String text) {
        this.rect = new Rectangle(x, y, width, height);
        this.text = text;
    }

    public MenuBoxItem(int x, int y, int width, int height) {
        this.rect = new Rectangle(x, y, width, height);
    }
}
