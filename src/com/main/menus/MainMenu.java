package com.main.menus;

import com.main.*;
import com.main.enemies.EnemyHard;
import com.main.enemies.EnemySlow;
import java.awt.*;
import static com.main.GraphicsUtil.*;

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
        if (menuParticleCount < 25) {
            new MenuParticle(mouseX, mouseY, ID.MenuParticle, handler);
            menuParticleCount++;
        }
        if (mouseOverItem(menuStartBox, mouseX, mouseY))
        {
            togglePauseMusic();
            mm.gameLive = true;
            handler.clearAll();
            upgrade.initializeValues();
            menuParticleCount = 0;
            mediator.getGame().sleepThread(500);
            mediator.getGame().gameState = Game.STATE.Game;
            mediator.setPlayer(new Player(Game.WIDTH / 2.f - 32, Game.HEIGHT / 2.f - 32, ID.Player, handler, mediator));
            if (!mediator.getGame().difficulty) new EnemySlow(1, 1, ID.SlowEnemy, handler);
            else new EnemyHard(1, 1, ID.HardEnemy, handler);
        } else if (mouseOverItem(menuSettingsBox, mouseX, mouseY))
        {
            mediator.getGame().gameState = Game.STATE.Settings;
        } else if (mouseOverItem(menuExitBox, mouseX, mouseY))
        {
            mediator.getGame().exitGame();
        }
    }
}
