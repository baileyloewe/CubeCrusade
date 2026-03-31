package com.github.baileyloewe.cubecrusade;

import com.github.baileyloewe.cubecrusade.entities.*;
import com.github.baileyloewe.cubecrusade.entities.enemies.HardEnemy;
import com.github.baileyloewe.cubecrusade.menus.MenuManager;
import com.github.baileyloewe.cubecrusade.signals.GameSignals;

import java.awt.*;
import java.awt.image.BufferStrategy;
import java.awt.image.BufferedImage;
import java.io.Serial;
import java.util.Random;

import static com.github.baileyloewe.cubecrusade.Spawn.EnemyType.SLOW;

public class Game extends Canvas implements Runnable {

    /**
     *
     */
    @Serial
    private static final long serialVersionUID = 1550691097823471818L;
    private Thread thread;
    private boolean running = false;
    private final ServiceLocator serviceLocator;

    public static final int WIDTH = 1224, HEIGHT = WIDTH / 12 * 9;
    public static BufferedImage spriteSheet;

    public GameState gameState = GameState.MENU;
    public boolean gameActive = false;
    public Difficulty difficulty = Difficulty.EASY;

    public Game() {
        Random RNG = new Random();
        serviceLocator = new ServiceLocator();
        serviceLocator.setGame(this);
        serviceLocator.setGameHandler(new GameHandler());
        serviceLocator.setUpgrade(new Upgrade());
        serviceLocator.setHud(new HUD(serviceLocator));
        serviceLocator.setSpawn(new Spawn(serviceLocator));
        serviceLocator.setStar(new Stars());
        serviceLocator.setKeyInput(new KeyInput(serviceLocator));
        serviceLocator.setAudioStream(new AudioStream(this));
        serviceLocator.setMenuManager(new MenuManager(serviceLocator));
        serviceLocator.setSpriteLoader(new SpriteLoader(serviceLocator));
        new Window(WIDTH, HEIGHT, "Cube Crusade", this);
        this.addKeyListener(serviceLocator.getKeyInput());
        this.addMouseListener(serviceLocator.getMenuManager());

        MenuParticle.create(ID.MenuParticle, new Vector2D(RNG.nextInt(Game.WIDTH - 16), RNG.nextInt(Game.HEIGHT - 16)));

        serviceLocator.getAudioStream().startAudioStream();
        spriteSheet = serviceLocator.getSpriteLoader().loadImage("/Sprites.png");

        GameSignals.gameQuit.connect(() -> {this.gameState = GameState.MENU; this.gameActive = false;});
        GameSignals.gameExited.connect(this::exitGame);
        GameSignals.gameStarted.connect(this::onGameStarted);
        GameSignals.openPauseMenu.connect(() -> this.gameState = GameState.PAUSED);
        GameSignals.gameResumed.connect(() -> this.gameState = GameState.GAME);
        GameSignals.openSettings.connect(() -> this.gameState = GameState.SETTINGS);
        GameSignals.openShop.connect(() -> this.gameState = GameState.SHOP);
        GameSignals.playerDied.connect(() -> {
            serviceLocator.getKeyInput().resetStates();
            gameState = GameState.END;
        });
    }

    public void onGameStarted() {
        serviceLocator.getGameHandler().clearAll();
        sleepThread(500);
        Player player = Player.create(ID.Player, serviceLocator.getGameHandler(), new Vector2D(Game.WIDTH / 2.f - 32, Game.HEIGHT / 2.f - 32), serviceLocator.getKeyInput());
        serviceLocator.setPlayer(player);
        if (difficulty == Difficulty.EASY) serviceLocator.getSpawn().spawnEnemy(SLOW);
        else HardEnemy.create(ID.HardEnemy, new Vector2D(1, 1));
        gameState = GameState.GAME;
        gameActive = true;
    }

    public static void main(String[] args) {
        new Game();
    }

    /**
     * Used to close audio streams before exiting the game
     */
    public void exitGame() {
        serviceLocator.getAudioStream().closeAudioStream();
        System.exit(0);
    }

    public synchronized void start() {
        thread = new Thread(this);
        thread.start();
        running = true;
    }

    public synchronized void stop() {
        try {
            thread.join();
            running = false;
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void run() {
        this.requestFocus();
        long lastTime = System.nanoTime();
        double tickRate = 128.0;
        double nanosecondsPerTick = 1000000000 / tickRate;
        double delta = 0;
        while (running) {
            long now = System.nanoTime();
            delta += (now - lastTime) / nanosecondsPerTick;
            while (delta >= 1) {
                tick();
                delta--;
            }
            lastTime = System.nanoTime();
            if (running) {
                render();
            }
        }
        stop();
    }

    private void tick() {
        switch (gameState) {
            case GAME -> {
                serviceLocator.getHud().tick();
                serviceLocator.getSpawn().tick();
                serviceLocator.getGameHandler().tick();
            }
            case MENU, SETTINGS, END -> serviceLocator.getGameHandler().tickMenuParticles();
            case PAUSED, SHOP -> serviceLocator.getKeyInput().resetStates();
        }
    }

    private void render() {
        BufferStrategy bs = this.getBufferStrategy();
        if (bs == null) {
            this.createBufferStrategy(3);
            return;
        }
        Graphics g = bs.getDrawGraphics();

        g.setColor(Color.BLACK);
        g.fillRect(0, 0, WIDTH, HEIGHT);

        switch (gameState) {
            case GAME -> {
                serviceLocator.getStar().render(g);
                serviceLocator.getGameHandler().render(g);
                serviceLocator.getHud().render(g);
            }
            case MENU, END, SETTINGS, SHOP -> {
                serviceLocator.getStar().render(g);
                serviceLocator.getGameHandler().render(g);
                serviceLocator.getMenuManager().render(g);
            }
            case PAUSED -> {
                serviceLocator.getStar().render(g);
                serviceLocator.getGameHandler().render(g);
                serviceLocator.getHud().render(g);
                serviceLocator.getMenuManager().render(g);
            }
            default -> {
            }
        }

        g.dispose();
        bs.show();
    }


}
