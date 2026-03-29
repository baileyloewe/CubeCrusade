package com.github.baileyloewe.cubecrusade.menus;

import com.github.baileyloewe.cubecrusade.GraphicsUtil;
import com.github.baileyloewe.cubecrusade.ID;
import com.github.baileyloewe.cubecrusade.MenuBoxItem;
import com.github.baileyloewe.cubecrusade.Vector2D;
import com.github.baileyloewe.cubecrusade.entities.MenuParticle;
import com.github.baileyloewe.cubecrusade.signals.GameSignals;

import java.awt.*;

import static com.github.baileyloewe.cubecrusade.GraphicsUtil.drawRectAndString;

public class MainMenu extends Menu {
    private final MenuBoxItem menuMenuBox, menuStartBox, menuSettingsBox, menuExitBox;
    private int menuParticleCount;

    public MainMenu(MenuManager menuManager) {
        super(menuManager);
        menuMenuBox = new MenuBoxItem(centeredX - 75, centeredY - 150, 150, 70, "MENU");
        menuStartBox = new MenuBoxItem(centeredX - 55, centeredY - 32, 110, 44, "START");
        menuSettingsBox = new MenuBoxItem(centeredX - 80, centeredY + 63, 160, 44, "SETTINGS");
        menuExitBox = new MenuBoxItem(centeredX - 40, centeredY + 158, 80, 44, "EXIT");
    }

    public void render(Graphics g) {
        drawRectAndString(g, menuMenuBox, GraphicsUtil.Fonts.LARGE);
        drawRectAndString(g, menuStartBox, GraphicsUtil.Fonts.MEDIUM);
        drawRectAndString(g, menuSettingsBox, GraphicsUtil.Fonts.MEDIUM);
        drawRectAndString(g, menuExitBox, GraphicsUtil.Fonts.MEDIUM);
    }

    public void interact(int mouseX, int mouseY) {
        if (mouseOverItem(menuStartBox, mouseX, mouseY))
        {
            GameSignals.gameStarted.emit();
            menuParticleCount = 0;
        } else if (mouseOverItem(menuSettingsBox, mouseX, mouseY))
        {
            GameSignals.openSettings.emit();
        } else if (mouseOverItem(menuExitBox, mouseX, mouseY))
        {
            GameSignals.gameExited.emit();
        } else if (menuParticleCount < 25) {
                MenuParticle.create(ID.MenuParticle, new Vector2D(mouseX, mouseY));
                menuParticleCount++;
        }
    }
}

