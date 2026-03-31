package com.github.baileyloewe.cubecrusade.menus;

import com.github.baileyloewe.cubecrusade.*;
import com.github.baileyloewe.cubecrusade.entities.components.UpgradeComponent;

import java.awt.event.MouseEvent;

public abstract class Menu {
    protected MenuManager mm;
    protected Game game;
    protected GameHandler gameHandler;
    protected UpgradeComponent upgradeComponent;
    protected int centeredX, centeredY;

    public Menu(MenuManager mm) {
        this.mm = mm;
        this.gameHandler = mm.gameHandler;
        this.centeredX = mm.centeredX;
        this.centeredY = mm.centeredY;
    }


    public boolean mouseOverItem(MenuBoxItem menuBoxItem, int mouseX, int mouseY) {
        return (mouseX >= menuBoxItem.rect.x && mouseX <= menuBoxItem.rect.x + menuBoxItem.rect.width) && (mouseY >= menuBoxItem.rect.y && mouseY <= menuBoxItem.rect.y + menuBoxItem.rect.height);
    }
}