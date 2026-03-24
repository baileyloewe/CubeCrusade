package com.main.menus;

import com.main.*;
import java.awt.*;
import static com.main.GraphicsUtil.*;

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
        handler.clearAll();
        drawRectAndString(g, gameOverBox, Fonts.XLARGE);
        g.setFont(Fonts.SMALL.getFont());
        g.drawString("You lost with a score of " + upgrade.getScore() + "\n on level " + upgrade.getLevel(), centeredX - 180, centeredY);
        drawRectAndString(g, mainMenuBox, Fonts.MEDIUM);
    }

}
