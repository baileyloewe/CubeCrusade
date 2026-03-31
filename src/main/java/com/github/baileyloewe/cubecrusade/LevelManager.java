package com.github.baileyloewe.cubecrusade;

import com.github.baileyloewe.cubecrusade.signals.GameSignals;

public class LevelManager {
    ScheduleTask<Object> levelIncrement;
    private final Game game;

    public LevelManager(Game game) {
        this.game = game;
    }

    void start() {
        levelIncrement = new ScheduleTask<>(10, 0, () -> game.level++);
        GameSignals.spawnEnemy.emit();
    }
}
