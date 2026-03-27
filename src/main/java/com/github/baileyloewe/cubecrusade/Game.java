package com.github.baileyloewe.cubecrusade;

import com.github.baileyloewe.cubecrusade.entities.Player;
import com.github.baileyloewe.cubecrusade.entities.enemies.EnemyHard;
import com.github.baileyloewe.cubecrusade.entities.enemies.EnemySlow;
import com.github.baileyloewe.cubecrusade.entities.enemies.MenuParticle;
import com.github.baileyloewe.cubecrusade.menus.MenuManager;
import com.github.baileyloewe.cubecrusade.signals.GameSignals;

import java.awt.*;
import java.awt.image.BufferStrategy;
import java.awt.image.BufferedImage;
import java.io.Serial;
import java.util.Random;

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
        Random r = new Random();
        serviceLocator = new ServiceLocator();
        serviceLocator.setGame(this);
        serviceLocator.setGameHandler(new GameHandler());
        serviceLocator.setUpgrade(new Upgrade());
        serviceLocator.setHud(new HUD(serviceLocator));
        serviceLocator.setSpawn(new Spawn(serviceLocator));
        serviceLocator.setStar(new Star(serviceLocator));
        serviceLocator.setKeyInput(new KeyInput(serviceLocator));
        serviceLocator.setAudioStream(new AudioStream(this));
        serviceLocator.setMenuManager(new MenuManager(serviceLocator));
        serviceLocator.setSpriteLoader(new SpriteLoader(serviceLocator));
        new Window(WIDTH, HEIGHT, "Cube Crusade", this);
        this.addKeyListener(serviceLocator.getKeyInput());
        this.addMouseListener(serviceLocator.getMenuManager());

        new MenuParticle(r.nextInt(Game.WIDTH - 16), r.nextInt(Game.HEIGHT - 16), ID.MenuParticle, serviceLocator.getGameHandler());

        serviceLocator.getAudioStream().startAudioStream();
        spriteSheet = serviceLocator.getSpriteLoader().loadImage("/Sprites.png");

        GameSignals.GameQuit.connect(() -> {this.gameState = GameState.MENU; this.gameActive = false;});
        GameSignals.GameExited.connect(this::exitGame);
        GameSignals.GameStarted.connect(this::onGameStarted);
        GameSignals.OpenPauseMenu.connect(() -> this.gameState = GameState.PAUSED);
        GameSignals.GameResumed.connect(() -> this.gameState = GameState.GAME);
        GameSignals.OpenSettings.connect(() -> this.gameState = GameState.SETTINGS);
        GameSignals.OpenShop.connect(() -> this.gameState = GameState.SHOP);
        GameSignals.PlayerDied.connect(() -> {
            serviceLocator.getKeyInput().resetStates();
            gameState = GameState.END;
        });
    }

    public void onGameStarted() {
        serviceLocator.getGameHandler().clearAll();
        sleepThread(500);
        serviceLocator.setPlayer(new Player(Game.WIDTH / 2.f - 32, Game.HEIGHT / 2.f - 32, ID.Player, serviceLocator));
        if (difficulty == Difficulty.EASY) new EnemySlow(1, 1, ID.SlowEnemy, serviceLocator.getGameHandler());
        else new EnemyHard(1, 1, ID.HardEnemy, serviceLocator.getGameHandler());
        gameState = GameState.GAME;
        gameActive = true;
    }

    /**
     * Limits the value, val, to a min and max
     *
     * @param val value you are passing in to be clamped
     * @param min value you want val to stay above
     * @param max value you want val to stay below
     * @return float
     */
    public static float clamp(float val, float min, float max) {
        if (val >= max) return max;
        else return Math.max(val, min);
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

    public void sleepThread(int time) {
        try {
            Thread.sleep(time);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
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
        double amountOfTicks = 128.0;
        double ns = 1000000000 / amountOfTicks;
        double delta = 0;
        long timer = System.currentTimeMillis();
        while (running) {
            long now = System.nanoTime();
            delta += (now - lastTime) / ns;
            lastTime = now;
            while (delta >= 1) {
                tick();
                delta--;
            }

            if (running) {
                render();
            }

            if (System.currentTimeMillis() - timer > 1000) {
                timer += 1000;
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
