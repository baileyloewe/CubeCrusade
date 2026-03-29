package com.github.baileyloewe.cubecrusade;

import com.github.baileyloewe.cubecrusade.entities.enemies.*;

import java.awt.*;
import java.util.HashMap;
import java.util.Map;
import java.util.Random;

/**
 * Handles spawning enemies based on level/score
 */
public class Spawn {
    private final ServiceLocator serviceLocator;
    private final Random RNG = new Random();
    private final Map<Integer, EnemyType> spawnMap = new HashMap<>();


    public Spawn(ServiceLocator serviceLocator) {
        this.serviceLocator = serviceLocator;
        this.setSpawnMap();
    }

    /**
     * Checks score, sets level based on score, and spawns enemies depending on the level
     */
    public void tick() {
        if (serviceLocator.getUpgrade().getScore() % 1000 == 0) {
            serviceLocator.getUpgrade().setLevel(serviceLocator.getUpgrade().getLevel() + 1);

            if (spawnMap.get(serviceLocator.getUpgrade().getLevel()) != null)
                spawnEnemy(spawnMap.get(serviceLocator.getUpgrade().getLevel()));
        }
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
            Rectangle PlayerPos = new Rectangle((int) serviceLocator.getPlayer().getXPos() - 250, (int) serviceLocator.getPlayer().getYPos() - (250 / 12 * 9), 250 * 2 + 32, 250 / 12 * 9 * 2 + 32);
            Rectangle possibleEnemyPos = new Rectangle((int) x, (int) y, 16, 16);

            if (!PlayerPos.intersects(possibleEnemyPos)) {
                switch (enemyType) {
                    case BOSS -> {
                        serviceLocator.getGameHandler().clearEnemies();
                        BossEnemy.create(ID.BossEnemy);
                    }
                    case SMART -> SmartEnemy.create(ID.SmartEnemy,  serviceLocator.getPlayer(), new Vector2D(x, y));
                    case FAST -> {
                        if (serviceLocator.getGame().difficulty == Difficulty.EASY)
                            FastEnemy.create(ID.FastEnemy, new Vector2D(x, y), 3);
                        else FastEnemy.create(ID.FastEnemy, new Vector2D(x, y), 2);
                    }
                    case SLOW -> {
                        if (serviceLocator.getGame().difficulty == Difficulty.EASY)
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
