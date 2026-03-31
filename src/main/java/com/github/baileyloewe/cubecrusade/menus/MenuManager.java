package com.github.baileyloewe.cubecrusade.menus;

import com.github.baileyloewe.cubecrusade.AudioStream;
import com.github.baileyloewe.cubecrusade.Game;
import com.github.baileyloewe.cubecrusade.GameHandler;
import com.github.baileyloewe.cubecrusade.entities.Player;

import java.awt.*;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;

public class MenuManager extends MouseAdapter {
    protected final Game game;
    protected final GameHandler gameHandler;
    protected final int centeredX = Game.WIDTH / 2;
    protected final int centeredY = Game.HEIGHT / 2 - 16;
    protected int mouseX, mouseY;

    public GameOverMenu gameOverMenu;
    public MainMenu mainMenu;
    public PauseMenu pauseMenu;
    public SettingsMenu settingsMenu;
    public ShopMenu shopMenu;

    public MenuManager(Game game, GameHandler gameHandler, AudioStream audioStream, Player player) {
        this.game = game;
        this.gameHandler = gameHandler;
        this.mainMenu = new MainMenu(this);
        this.settingsMenu = new SettingsMenu(this, game, audioStream);
    }

    public void initGameMenus(Player player) {
        this.gameOverMenu = new GameOverMenu(this, game);
        this.pauseMenu = new PauseMenu(this);
        this.shopMenu = new ShopMenu(this, player);
    }

    public void mousePressed(MouseEvent e) {
        mouseX = e.getX();
        mouseY = e.getY();
        switch (game.gameState) {
            case MAINMENU -> mainMenu.interact(mouseX, mouseY);
            case SETTINGS -> settingsMenu.interact(mouseX, mouseY);
            case PAUSED -> pauseMenu.interact(mouseX, mouseY);
            case SHOP -> shopMenu.interact(mouseX, mouseY);
            case END -> gameOverMenu.interact(mouseX, mouseY);
            default -> {
            }
        }
    }

    public void render(Graphics g) {
        g.setColor(Color.WHITE);
        switch (game.gameState) {
            case MAINMENU -> mainMenu.render(g);
            case SETTINGS -> settingsMenu.render(g);
            case PAUSED -> pauseMenu.render(g);
            case SHOP -> shopMenu.render(g);
            case END -> gameOverMenu.render(g);
        }
    }
}
