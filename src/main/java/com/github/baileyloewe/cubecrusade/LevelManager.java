package com.github.baileyloewe.cubecrusade;

import com.github.baileyloewe.cubecrusade.signals.GameSignals;

import java.util.concurrent.TimeUnit;

public class LevelManager {
    private final Game game;
    private final long intervalNanoseconds = TimeUnit.SECONDS.toNanos(10L);
    private long timeLastTicked = 0L;

    public LevelManager(Game game) {
        this.game = game;
        GameSignals.gameStarted.connect(this::init);
        GameSignals.gameQuit.connect(this::init);
    }

    private void init() {
        setLevel(1);
        timeLastTicked = 0L;
    }

    public void tick() {
        long now = System.nanoTime();
        if (timeLastTicked == 0L) timeLastTicked = now;
        if (now > timeLastTicked + intervalNanoseconds) {
            setLevel(game.level + 1);
            timeLastTicked = now;
        }
    }

    private void setLevel(int level) {
        game.level = level;
        GameSignals.levelChanged.emit();
    }
}
