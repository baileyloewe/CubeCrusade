package com.main;

import com.main.menus.MenuManager;

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

    public STATE gameState = STATE.Menu;
    public boolean difficulty = false;




    public Game() {
        Random r = new Random();
        mediator = new Mediator();
        mediator.setGame(this);
        mediator.setHandler(new Handler());
        mediator.setUpgrade(new Upgrade());
        mediator.setHud(new HUD(mediator));
        mediator.setSpawn(new Spawn(mediator));
        mediator.setStar(new Star(mediator));
        mediator.setKeyInput(new KeyInput(mediator));
        mediator.setMenuManager(new MenuManager(mediator));
        mediator.getMenuManager().init();
        mediator.setGameAudio(new AudioStream("/resources/Half-Mystery.wav", this));
        mediator.setMenuAudio(new AudioStream("/resources/Voxel Revolution.wav", this));
        mediator.setSpriteLoader(new SpriteLoader(mediator));
        new Window(WIDTH, HEIGHT, "Cube Crusade", this);
        this.addKeyListener(mediator.getKeyInput());
        this.addMouseListener(mediator.getMenuManager());

        new MenuParticle(r.nextInt(Game.WIDTH - 16), r.nextInt(Game.HEIGHT - 16), ID.MenuParticle, mediator.getHandler());

        mediator.getMenuAudio().playAudioStream();
        spriteSheet = mediator.getSpriteLoader().loadImage("/resources/Sprites.png");
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
        mediator.getGameAudio().stopAudioStream();
        mediator.getMenuAudio().stopAudioStream();
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
        //int frames = 0;
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
            //frames++;

            if (System.currentTimeMillis() - timer > 1000) {
                timer += 1000;
                //frames = 0;
            }
        }
        stop();
    }

    private void tick() {
        switch (gameState) {
            case Game -> {
                mediator.getHud().tick();
                mediator.getSpawn().tick();
                mediator.getHandler().tick();
                if (mediator.getUpgrade().getCurrentHealth() <= 0) {
                    mediator.getKeyInput().resetStates();
                    gameState = STATE.End;
                }
            }
            case Menu, Settings, End -> {
                mediator.getHandler().tickMenu();
            }
            case Paused, Shop -> {
                mediator.getKeyInput().resetStates();
            }
            default -> {
            }
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
                mediator.getHandler().render(g);
                mediator.getHud().render(g);
            }
            case Menu, End, Settings, Shop -> {
                mediator.getStar().render(g);
                mediator.getHandler().render(g);
                mediator.getMenuManager().render(g);
            }
            case Paused -> {
                mediator.getStar().render(g);
                mediator.getHandler().render(g);
                mediator.getHud().render(g);
                mediator.getMenuManager().render(g);
            }
            default -> {
            }
        }

        g.dispose();
        bs.show();
    }

    public enum STATE {
        Menu,
        Settings,
        Game,
        End,
        Paused,
        Shop,
    }

}
