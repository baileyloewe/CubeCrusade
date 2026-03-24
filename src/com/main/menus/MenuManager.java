package com.main.menus;

import com.main.*;
import java.awt.*;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;

public class MenuManager extends MouseAdapter {
    protected final Handler handler;
    protected final Mediator mediator;
    protected final Upgrade upgrade;
    protected final int centeredX = Game.WIDTH / 2;
    protected final int centeredY = Game.HEIGHT / 2 - 16;
    protected int mouseX, mouseY;
    protected boolean gameLive = false;

    public GameOverMenu gameOverMenu;
    public MainMenu mainMenu;
    public PauseMenu pauseMenu;
    public SettingsMenu settingsMenu;
    public ShopMenu shopMenu;

    public MenuManager(Mediator mediator) {
        this.mediator = mediator;
        this.handler = mediator.getHandler();
        this.upgrade = mediator.getUpgrade();
    }

    public void mousePressed(MouseEvent e) {
        mouseX = e.getX();
        mouseY = e.getY();
        switch (mediator.getGame().gameState) {
            case Menu -> mainMenu.interact(mouseX, mouseY);
            case Settings -> settingsMenu.interact(mouseX, mouseY);
            case Paused -> pauseMenu.interact(mouseX, mouseY);
            case Shop -> shopMenu.interact(mouseX, mouseY);
            case End -> gameOverMenu.interact(mouseX, mouseY);
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
        switch (mediator.getGame().gameState) {
            case Menu -> mainMenu.render(g);
            case Settings -> settingsMenu.render(g);
            case Paused -> pauseMenu.render(g);
            case Shop -> shopMenu.render(g);
            case End -> gameOverMenu.render(g);
        }
    }
}
