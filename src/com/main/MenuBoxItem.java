package com.main;

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

    public Rectangle getRect() {
        return rect;
    }

    public void setRect(Rectangle rect) {
        this.rect = rect;
    }

    public String getText() {
        return text;
    }

    public void setText(String text) {
        this.text = text;
    }
}
