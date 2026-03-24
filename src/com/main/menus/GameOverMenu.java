package com.main.menus;

import com.main.*;

import java.awt.*;

public class GameOverMenu extends Menu {
    private final MenuBoxItem mainMenuBox, gameOverBox;

    public GameOverMenu(MenuManager menuManager) {
        super(menuManager);
        gameOverBox = new MenuBoxItem(centeredX - 175, centeredY - 170, 350, 70, "GAME OVER");
        mainMenuBox = new MenuBoxItem(centeredX - 90, centeredY + 163, 180, 44, "MAIN MENU");
    }


    public void interact(int mouseX, int mouseY) {
        if (mouseOverItem(mainMenuBox, mouseX, mouseY)) {
            handler.clearAll();
            mm.gameLive = false;
            mediator.getGame().gameState = Game.STATE.Menu;
            changeAudio();
            new MenuParticle(mouseX, mouseY, ID.MenuParticle, handler);
        }
    }

    public void render(Graphics g) {
        // Game over, allow returning to the main menu
        handler.clearAll(); // Clear all game objects from the handler
        GraphicsUtil.drawRectAndString(g, gameOverBox, GraphicsUtil.Fonts.XLARGE);
        // You lost
        g.setFont(GraphicsUtil.Fonts.SMALL.getFont());
        g.drawString("You lost with a score of " + upgrade.getScore() + "\n on level " + upgrade.getLevel(), centeredX - 180, centeredY);
        // Main menu box
        GraphicsUtil.drawRectAndString(g, mainMenuBox, GraphicsUtil.Fonts.MEDIUM);
    }

}
