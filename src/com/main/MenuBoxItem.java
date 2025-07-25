package com.main;

import java.awt.*;

public class MenuBoxItem {

    public Rectangle rect;
    public String s;

    public MenuBoxItem(int x, int y, int width, int height, String text) {
        this.rect = new Rectangle(x, y, width, height);
        this.s = text;
    }

    public MenuBoxItem(int x, int y, int width, int height) {
        this.rect = new Rectangle(x, y, width, height);
    }

    public Rectangle getRect() {
        return rect;
    }

    public void setRect(Rectangle rect) {
        this.rect = rect;
    }

    public String getS() {
        return s;
    }

    public void setS(String s) {
        this.s = s;
    }
}
