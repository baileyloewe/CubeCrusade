package com.main.menus;

import com.main.*;
import com.main.signals.GameSignals;

import java.awt.*;

import static com.main.GraphicsUtil.Fonts;
import static com.main.GraphicsUtil.drawRectAndString;

public class PauseMenu extends Menu {
    private final MenuBoxItem pausedBox, resumeBox, settingsBox, mainMenuBox;

    public PauseMenu(MenuManager menuManager) {
        super(menuManager);
        pausedBox = new MenuBoxItem(centeredX - 100, centeredY - 210, 200, 70, "PAUSED");
        resumeBox = new MenuBoxItem(centeredX - 60, centeredY - 92, 120, 44, "RESUME");
        settingsBox = new MenuBoxItem(centeredX - 80, centeredY + 3, 160, 44, "SETTINGS");
        mainMenuBox = new MenuBoxItem(centeredX - 90, centeredY + 98, 180, 44, "MAIN MENU");
    }

    public void render(Graphics g) {
        drawRectAndString(g, pausedBox, Fonts.LARGE);
        drawRectAndString(g, resumeBox, Fonts.MEDIUM);
        drawRectAndString(g, settingsBox, Fonts.MEDIUM);
        drawRectAndString(g, mainMenuBox, Fonts.MEDIUM);
    }

    public void interact(int mouseX, int mouseY) {
        if (mouseOverItem(resumeBox, mouseX, mouseY)) {
            GameSignals.GameResumed.emit();
        } else if (mouseOverItem(settingsBox, mouseX, mouseY)) {
            GameSignals.OpenSettings.emit();
        } else if (mouseOverItem(mainMenuBox, mouseX, mouseY)) {
            GameSignals.GameQuit.emit();
        }
    }
}
