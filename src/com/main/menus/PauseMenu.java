package com.main.menus;

import com.main.*;
import java.awt.*;
import static com.main.GraphicsUtil.*;

public class PauseMenu extends Menu {
    private final MenuBoxItem pausedBox, resumeBox, settingsBox, mainMenuBox, exitGameBox;

    public PauseMenu(MenuManager menuManager) {
        super(menuManager);
        pausedBox = new MenuBoxItem(centeredX - 100, centeredY - 210, 200, 70, "PAUSED");
        resumeBox = new MenuBoxItem(centeredX - 60, centeredY - 92, 120, 44, "RESUME");
        settingsBox = new MenuBoxItem(centeredX - 80, centeredY + 3, 160, 44, "SETTINGS");
        mainMenuBox = new MenuBoxItem(centeredX - 90, centeredY + 98, 180, 44, "MAIN MENU");
        exitGameBox = new MenuBoxItem(centeredX - 90, centeredY + 193, 180, 44, "EXIT GAME");
    }

    public void render(Graphics g) {
        drawRectAndString(g, pausedBox, Fonts.LARGE);
        drawRectAndString(g, resumeBox, Fonts.MEDIUM);
        drawRectAndString(g, settingsBox, Fonts.MEDIUM);
        drawRectAndString(g, mainMenuBox, Fonts.MEDIUM);
        drawRectAndString(g, exitGameBox, Fonts.MEDIUM);
    }

    public void interact(int mouseX, int mouseY) {
        if (mouseOverItem(resumeBox, mouseX, mouseY)) {
            mediator.getGame().gameState = Game.STATE.Game;
        } else if (mouseOverItem(settingsBox, mouseX, mouseY)) {
            mediator.getGame().gameState = Game.STATE.Settings;
        } else if (mouseOverItem(exitGameBox, mouseX, mouseY)) {
            mediator.getGame().exitGame();
        } else if (mouseOverItem(mainMenuBox, mouseX, mouseY)) {
            mm.gameLive = false;
            handler.clearAll();
            mediator.getGame().gameState = Game.STATE.Menu;
            togglePauseMusic();
            new MenuParticle(mouseX, mouseY, ID.MenuParticle, handler);
        }
    }
}
