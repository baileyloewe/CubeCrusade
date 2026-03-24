package com.main;

public class Mediator {



    private Game game;
    private Handler handler;
    private Player player;
    private Upgrade upgrade;
    private Star star;
    private HUD hud;
    private Spawn spawn;
    private KeyInput keyInput;
    private StateHandler stateHandler;
    private AudioStream gameAudio;
    private AudioStream menuAudio;


    private SpriteLoader spriteLoader;


    public Mediator() {

    }

    public Game getGame() {
        return game;
    }

    public void setGame(Game game) {
        this.game = game;
    }

    public Handler getHandler() {
        return handler;
    }

    public void setHandler(Handler handler) {
        this.handler = handler;
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

    public StateHandler getStateHandler() {
        return stateHandler;
    }

    public void setStateHandler(StateHandler stateHandler) {
        this.stateHandler = stateHandler;
    }

    public AudioStream getGameAudio() {
        return gameAudio;
    }

    public void setGameAudio(AudioStream gameAudio) {
        this.gameAudio = gameAudio;
    }

    public AudioStream getMenuAudio() {
        return menuAudio;
    }

    public void setMenuAudio(AudioStream menuAudio) {
        this.menuAudio = menuAudio;
    }

    public SpriteLoader getSpriteLoader() {
        return spriteLoader;
    }

    public void setSpriteLoader(SpriteLoader spriteLoader) {
        this.spriteLoader = spriteLoader;
    }


}
