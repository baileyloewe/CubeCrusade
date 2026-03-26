package com.github.baileyloewe.cubecrusade.menus;

import com.github.baileyloewe.cubecrusade.GameHandler;
import com.github.baileyloewe.cubecrusade.Mediator;
import com.github.baileyloewe.cubecrusade.MenuBoxItem;
import com.github.baileyloewe.cubecrusade.Upgrade;

public abstract class Menu {
    protected MenuManager mm;
    protected Mediator mediator;
    protected GameHandler handler;
    protected Upgrade upgrade;
    protected int centeredX, centeredY;

    public Menu(MenuManager mm) {
        this.mm = mm;
        this.mediator = mm.mediator;
        this.handler = mm.handler;
        this.upgrade = mm.upgrade;
        this.centeredX = mm.centeredX;
        this.centeredY = mm.centeredY;
    }

    public boolean mouseOverItem(MenuBoxItem menuBoxItem, int mouseX, int mouseY) {
        return (mouseX >= menuBoxItem.rect.x && mouseX <= menuBoxItem.rect.x + menuBoxItem.rect.width) && (mouseY >= menuBoxItem.rect.y && mouseY <= menuBoxItem.rect.y + menuBoxItem.rect.height);
    }
}