package com.github.baileyloewe.cubecrusade.menus;

import com.github.baileyloewe.cubecrusade.*;

public abstract class Menu {
    protected MenuManager mm;
    protected ServiceLocator serviceLocator;
    protected GameHandler handler;
    protected Upgrade upgrade;
    protected int centeredX, centeredY;

    public Menu(MenuManager mm) {
        this.mm = mm;
        this.serviceLocator = mm.serviceLocator;
        this.handler = mm.handler;
        this.upgrade = mm.upgrade;
        this.centeredX = mm.centeredX;
        this.centeredY = mm.centeredY;
    }

    public boolean mouseOverItem(MenuBoxItem menuBoxItem, int mouseX, int mouseY) {
        return (mouseX >= menuBoxItem.rect.x && mouseX <= menuBoxItem.rect.x + menuBoxItem.rect.width) && (mouseY >= menuBoxItem.rect.y && mouseY <= menuBoxItem.rect.y + menuBoxItem.rect.height);
    }
}