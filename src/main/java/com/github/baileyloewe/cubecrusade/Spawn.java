package com.github.baileyloewe.cubecrusade;

import com.github.baileyloewe.cubecrusade.entities.Player;
import com.github.baileyloewe.cubecrusade.entities.enemies.*;
import com.github.baileyloewe.cubecrusade.signals.GameSignals;

import java.awt.*;
import java.util.HashMap;
import java.util.Map;
import java.util.Random;

/**
 * Handles spawning enemies based on level
 */
public class Spawn {

    private final Random RNG = new Random();
    private final Map<Integer, EnemyType> spawnMap = new HashMap<>();
    private final Game game;
    private final GameHandler gameHandler;
    private final Player player;

    public Spawn(Game game, GameHandler gameHandler, Player player) {
        this.game = game;
        this.gameHandler = gameHandler;
        this.player = player;
        this.setSpawnMap();
        GameSignals.levelChanged.connect(this, () -> {
                    EnemyType enemy = spawnMap.get(game.level);
                    if (enemy != null) spawnEnemy(enemy);
                }
        );
    }

    public void spawnEnemy(EnemyType enemyType) {
        boolean spawned = false;
        do {
            // Create an x and y var that is within the game width and height
            float x = RNG.nextFloat(5, Game.WIDTH - 36);
            float y = RNG.nextFloat(5, Game.HEIGHT - 36);

            /*
             The rectangle's coordinates in the below line of code are determined as follows:
             This is done to set the x (top left corner) to 250 units back from where the player hit-box begins
             x = x - 250

             This is done to set the y (top left corner) to 250s units back from where the player hit-box begins
             modified to be the same ratio as the game's original width/height ratio (Game.HEIGHT = Game.WIDTH / 12 * 9)
             y = y - 250 / 12 * 9

             This is done to make the rectangle a total of 500 units wide and the + 32 accounts for the player being 32 units in width/
             width = 250 * 2 + 32

             This is done to make the rectangle a total of 500 units wide and the + 32 accounts for the player being 32 units in width/height/
             height = 250 / 12 * 9 * 2 + 32

            */
            Vector2D pos = player.getPositionComponent().position;
            int playerWidth = player.getSizeComponent().width;
            int playerHeight = player.getSizeComponent().height;
            Rectangle PlayerPos = new Rectangle((int) pos.x - 250, (int) pos.y - (250 / 12 * 9), 250 * 2 + playerWidth, 250 / 12 * 9 * 2 + playerHeight);
            Rectangle possibleEnemyPos = new Rectangle((int) x, (int) y, 16, 16);

            if (!PlayerPos.intersects(possibleEnemyPos)) {
                switch (enemyType) {
                    case BOSS -> {
                        gameHandler.clearEnemies();
                        BossEnemy.create(ID.BossEnemy);
                    }
                    case SMART -> SmartEnemy.create(ID.SmartEnemy, player, new Vector2D(x, y));
                    case FAST -> {
                        if (game.difficulty == Difficulty.EASY)
                            FastEnemy.create(ID.FastEnemy, new Vector2D(x, y), 3);
                        else FastEnemy.create(ID.FastEnemy, new Vector2D(x, y), 2);
                    }
                    case SLOW -> {
                        if (game.difficulty == Difficulty.EASY)
                            SlowEnemy.create(ID.SlowEnemy, new Vector2D(x, y));
                        else HardEnemy.create(ID.HardEnemy, new Vector2D(x, y));
                    }
                    default -> {
                    }
                }
                spawned = true;
            }
        } while (!spawned);
    }

    /**
     * Sets the spawn mapping
     */
    public void setSpawnMap() {
        for (int level = 0; level < 10000; level++) {
            if (level % 10 == 0) spawnMap.put(level, EnemyType.BOSS);
            else if (level % 8 == 0) spawnMap.put(level, EnemyType.SMART);
            else if (level % 5 == 0) spawnMap.put(level, EnemyType.FAST);
            else if (level % 2 == 0) spawnMap.put(level, EnemyType.SLOW);
        }
    }

    public enum EnemyType {
        SLOW, FAST, SMART, BOSS
    }

}
