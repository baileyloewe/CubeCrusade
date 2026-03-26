package com.main;

import com.main.menus.MenuManager;

public class Mediator {

    private Game game;
    private GameHandler gameHandler;
    private Player player;
    private Upgrade upgrade;
    private Star star;
    private HUD hud;
    private Spawn spawn;
    private KeyInput keyInput;
    private MenuManager menuManager;
    private AudioStream audioStream;
    private SpriteLoader spriteLoader;

    public Mediator() {}

    public Game getGame() {
        return game;
    }

    public void setGame(Game game) {
        this.game = game;
    }

    public GameHandler getGameHandler() {
        return gameHandler;
    }

    public void setGameHandler(GameHandler handler) {
        this.gameHandler = handler;
    }

    public Player getPlayer() {
        return player;
    }

    public void setPlayer(Player player) {
        this.player = player;
    }

    public Upgrade getUpgrade() {
        return upgrade;
    }

    public void setUpgrade(Upgrade upgrade) {
        this.upgrade = upgrade;
    }

    public Star getStar() {
        return star;
    }

    public void setStar(Star star) {
        this.star = star;
    }

    public HUD getHud() {
        return hud;
    }

    public void setHud(HUD hud) {
        this.hud = hud;
    }

    public Spawn getSpawn() {
        return spawn;
    }

    public void setSpawn(Spawn spawn) {
        this.spawn = spawn;
    }

    public KeyInput getKeyInput() {
        return keyInput;
    }

    public void setKeyInput(KeyInput keyInput) {
        this.keyInput = keyInput;
    }

    public MenuManager getMenuManager() {
        return menuManager;
    }

    public void setMenuManager(MenuManager menuManager) {
        this.menuManager = menuManager;
    }

    public AudioStream getAudioStream() {
        return audioStream;
    }

    public void setAudioStream(AudioStream audioStream) {
        this.audioStream = audioStream;
    }

    public SpriteLoader getSpriteLoader() {
        return spriteLoader;
    }

    public void setSpriteLoader(SpriteLoader spriteLoader) {
        this.spriteLoader = spriteLoader;
    }
}

