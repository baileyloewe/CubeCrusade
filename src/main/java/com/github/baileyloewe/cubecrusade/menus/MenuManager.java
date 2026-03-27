package com.github.baileyloewe.cubecrusade.menus;

import com.github.baileyloewe.cubecrusade.*;

import java.awt.*;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;

public class MenuManager extends MouseAdapter {
    protected final GameHandler gameHandler;
    protected final ServiceLocator serviceLocator;
    protected final Upgrade upgrade;
    protected final int centeredX = Game.WIDTH / 2;
    protected final int centeredY = Game.HEIGHT / 2 - 16;
    protected int mouseX, mouseY;

    public GameOverMenu gameOverMenu;
    public MainMenu mainMenu;
    public PauseMenu pauseMenu;
    public SettingsMenu settingsMenu;
    public ShopMenu shopMenu;

    public MenuManager(ServiceLocator serviceLocator) {
        this.serviceLocator = serviceLocator;
        this.gameHandler = serviceLocator.getGameHandler();
        this.upgrade = serviceLocator.getUpgrade();
        this.init();
    }

    public void mousePressed(MouseEvent e) {
        mouseX = e.getX();
        mouseY = e.getY();
        switch (serviceLocator.getGame().gameState) {
            case MENU -> mainMenu.interact(mouseX, mouseY);
            case SETTINGS -> settingsMenu.interact(mouseX, mouseY);
            case PAUSED -> pauseMenu.interact(mouseX, mouseY);
            case SHOP -> shopMenu.interact(mouseX, mouseY);
            case END -> gameOverMenu.interact(mouseX, mouseY);
            default -> {
            }
        }
    }

    public void init() {
        this.gameOverMenu = new GameOverMenu(this);
        this.mainMenu = new MainMenu(this);
        this.pauseMenu = new PauseMenu(this);
        this.settingsMenu = new SettingsMenu(this);
        this.shopMenu = new ShopMenu(this);
    }

    public void render(Graphics g) {
        g.setColor(Color.WHITE);
        switch (serviceLocator.getGame().gameState) {
            case MENU -> mainMenu.render(g);
            case SETTINGS -> settingsMenu.render(g);
            case PAUSED -> pauseMenu.render(g);
            case SHOP -> shopMenu.render(g);
            case END -> gameOverMenu.render(g);
        }
    }
}
