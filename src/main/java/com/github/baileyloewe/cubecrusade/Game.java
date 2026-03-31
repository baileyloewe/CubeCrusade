package com.github.baileyloewe.cubecrusade;

import com.github.baileyloewe.cubecrusade.entities.MenuParticle;
import com.github.baileyloewe.cubecrusade.entities.Player;
import com.github.baileyloewe.cubecrusade.entities.Stars;
import com.github.baileyloewe.cubecrusade.menus.MenuManager;
import com.github.baileyloewe.cubecrusade.signals.GameSignals;

import java.awt.*;
import java.awt.image.BufferStrategy;
import java.awt.image.BufferedImage;
import java.io.Serial;

import static com.github.baileyloewe.cubecrusade.Spawn.EnemyType.SLOW;

public class Game extends Canvas implements Runnable {

    @Serial
    private static final long serialVersionUID = 1550691097823471818L;
    public static final int WIDTH = 1224, HEIGHT = WIDTH / 12 * 9;
    public static BufferedImage spriteSheet;
    private final GameHandler gameHandler;
    private final AudioStream audioStream;
    private final KeyInput keyInput;
    private final Stars stars;
    private final MenuManager menuManager;
    private Spawn spawn;
    private HUD hud;
    private final LevelManager levelManager;
    public int level = 0;

    private Thread thread;

    public GameState gameState = GameState.MAINMENU;
    public Difficulty difficulty = Difficulty.EASY;

    public boolean gameActive = false;
    public boolean running = false;


    public Game() {
        gameHandler = new GameHandler();
        audioStream = new AudioStream(this);
        spriteSheet = new SpriteLoader(this).loadImage("/Sprites.png");

        menuManager = new MenuManager(this, gameHandler, audioStream, null);
        addMouseListener(menuManager);
        keyInput = new KeyInput(this);
        addKeyListener(keyInput);

        stars = new Stars();
        levelManager = new LevelManager(this);

        audioStream.startAudioStream();

        MenuParticle.create(ID.MenuParticle, new Vector2D((float) Game.WIDTH / 2, (float) Game.HEIGHT / 2));
        new Window(WIDTH, HEIGHT, "Cube Crusade", this);

        GameSignals.gameQuit.connect(this, () -> {
            gameState = GameState.MAINMENU;
            gameActive = false;
        });
        GameSignals.gameExited.connect(this, this::exitGame);
        GameSignals.gameStarted.connect(this, this::startGame);
        GameSignals.openPauseMenu.connect(this, () -> gameState = GameState.PAUSED);
        GameSignals.gameResumed.connect(this, () -> gameState = GameState.GAME);
        GameSignals.openSettings.connect(this, () -> gameState = GameState.SETTINGS);
        GameSignals.openShop.connect(this, () -> gameState = GameState.SHOP);
        GameSignals.playerDied.connect(this, () -> {
            keyInput.resetStates();
            gameState = GameState.END;
        });
    }

    public void startGame() {
        Player player = Player.create(ID.Player, gameHandler, new Vector2D(Game.WIDTH / 2.f - 32, Game.HEIGHT / 2.f - 32), keyInput);
        spawn = new Spawn(this, gameHandler, player);
        hud = new HUD(this, player);
        menuManager.initGameMenus(player);
        spawn.spawnEnemy(SLOW);
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
        this.audioStream.closeAudioStream();
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
                hud.tick();
                levelManager.tick();
                gameHandler.tick();
            }
            case MAINMENU, SETTINGS, END -> gameHandler.tickMenuParticles();
            case PAUSED, SHOP -> keyInput.resetStates();
        }
    }

    private void render() {
        // BufferStrategy and Graphics *must* be re-created every frame
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
                stars.render(g);
                gameHandler.render(g);
                hud.render(g);
            }
            case MAINMENU, END, SETTINGS, SHOP -> {
                stars.render(g);
                gameHandler.render(g);
                menuManager.render(g);
            }
            case PAUSED -> {
                stars.render(g);
                gameHandler.render(g);
                hud.render(g);
                menuManager.render(g);
            }
            default -> {
            }
        }

        g.dispose();
        bs.show();
    }


}
