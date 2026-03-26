package com.main;

import com.main.enemies.EnemyHard;
import com.main.enemies.EnemySlow;
import com.main.menus.MenuManager;
import com.main.signals.GameSignals;

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
    private final Mediator mediator;

    public static final int WIDTH = 1224, HEIGHT = WIDTH / 12 * 9;
    public static BufferedImage spriteSheet;

    public GAMESTATE gameState = GAMESTATE.Menu;
    public boolean gameActive = false;
    public DIFFICULTY difficulty = DIFFICULTY.Easy;

    public Game() {
        Random r = new Random();
        mediator = new Mediator();
        mediator.setGame(this);
        mediator.setGameHandler(new GameHandler());
        mediator.setUpgrade(new Upgrade());
        mediator.setHud(new HUD(mediator));
        mediator.setSpawn(new Spawn(mediator));
        mediator.setStar(new Star(mediator));
        mediator.setKeyInput(new KeyInput(mediator));
        mediator.setAudioStream(new AudioStream(this));
        mediator.setMenuManager(new MenuManager(mediator));
        mediator.setSpriteLoader(new SpriteLoader(mediator));
        new Window(WIDTH, HEIGHT, "Cube Crusade", this);
        this.addKeyListener(mediator.getKeyInput());
        this.addMouseListener(mediator.getMenuManager());

        new MenuParticle(r.nextInt(Game.WIDTH - 16), r.nextInt(Game.HEIGHT - 16), ID.MenuParticle, mediator.getGameHandler());

        mediator.getAudioStream().startAudioStream();
        spriteSheet = mediator.getSpriteLoader().loadImage("/resources/Sprites.png");

        GameSignals.GameQuit.connect(() -> {this.gameState = GAMESTATE.Menu; this.gameActive = false;});
        GameSignals.GameExited.connect(this::exitGame);
        GameSignals.GameStarted.connect(this::onGameStarted);
        GameSignals.OpenPauseMenu.connect(() -> this.gameState = GAMESTATE.Paused);
        GameSignals.GameResumed.connect(() -> this.gameState = GAMESTATE.Game);
        GameSignals.OpenSettings.connect(() -> this.gameState = GAMESTATE.Settings);
        GameSignals.OpenShop.connect(() -> this.gameState = GAMESTATE.Shop);
    }

    public void onGameStarted() {
        mediator.getGameHandler().clearAll();
        sleepThread(500);
        mediator.setPlayer(new Player(Game.WIDTH / 2.f - 32, Game.HEIGHT / 2.f - 32, ID.Player, mediator));
        if (difficulty == DIFFICULTY.Easy) new EnemySlow(1, 1, ID.SlowEnemy, mediator.getGameHandler());
        else new EnemyHard(1, 1, ID.HardEnemy, mediator.getGameHandler());
        gameState = GAMESTATE.Game;
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
        mediator.getAudioStream().closeAudioStream();
        System.exit(1);
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
            case Game -> {
                mediator.getHud().tick();
                mediator.getSpawn().tick();
                mediator.getGameHandler().tick();
                if (mediator.getPlayer().getHealth() <= 0) {
                    mediator.getKeyInput().resetStates();
                    gameState = GAMESTATE.End;
                }
            }
            case Menu, Settings, End -> mediator.getGameHandler().tickMenu();
            case Paused, Shop -> mediator.getKeyInput().resetStates();
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
            case Game -> {
                mediator.getStar().render(g);
                mediator.getGameHandler().render(g);
                mediator.getHud().render(g);
            }
            case Menu, End, Settings, Shop -> {
                mediator.getStar().render(g);
                mediator.getGameHandler().render(g);
                mediator.getMenuManager().render(g);
            }
            case Paused -> {
                mediator.getStar().render(g);
                mediator.getGameHandler().render(g);
                mediator.getHud().render(g);
                mediator.getMenuManager().render(g);
            }
            default -> {
            }
        }

        g.dispose();
        bs.show();
    }

    public enum GAMESTATE {
        Menu,
        Settings,
        Game,
        End,
        Paused,
        Shop,
    }

    public enum DIFFICULTY {
        Easy,
        Hard,
    }

}
