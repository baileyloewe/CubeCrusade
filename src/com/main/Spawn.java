package com.main;

import java.awt.*;
import java.util.HashMap;
import java.util.Map;
import java.util.Random;

/**
 * Handles spawning enemies based on level/score
 */
public class Spawn {
    private final Mediator mediator;
    private final Random r = new Random();
    private final Map<Integer, String> spawnMap = new HashMap<>();

    /**
     * Creates an instance of the spawn class
     */
    public Spawn(Mediator mediator) {
        this.mediator = mediator;
        this.setSpawnMap();
    }

    /**
     * Checks score, sets level based on score, and spawns enemies depending on the level
     */
    public void tick() {
        if (mediator.getUpgrade().getScore() % 1000 == 0) {
            mediator.getUpgrade().setLevel(mediator.getUpgrade().getLevel() + 1);

            if (spawnMap.get(mediator.getUpgrade().getLevel()) != null)
                spawnEnemy(spawnMap.get(mediator.getUpgrade().getLevel()));
        }
    }

    /**
     * Spawns an enemy away from the player and near the edges of the game space
     *
     * @param type this is a string such as "Slow", "Fast", "Smart", or "Boss"
     */
    public void spawnEnemy(String type) {
        boolean spawned = false;
        do {
            // Create an x and y var that is within the game width and height
            float x = r.nextFloat(5, Game.WIDTH - 36);
            float y = r.nextFloat(5, Game.HEIGHT - 36);

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
            Rectangle PlayerPos = new Rectangle((int) mediator.getPlayer().getX() - 250, (int) mediator.getPlayer().getY() - (250 / 12 * 9), 250 * 2 + 32, 250 / 12 * 9 * 2 + 32);
            Rectangle possibleEnemyPos = new Rectangle((int) x, (int) y, 16, 16);

            if (!PlayerPos.intersects(possibleEnemyPos)) {
                switch (type) {
                    case "Boss" -> {
                        mediator.getHandler().clearEnemies();
                        new EnemyBoss(ID.BossEnemy, mediator.getHandler());
                    }
                    case "Smart" -> new EnemySmart(x, y, ID.SmartEnemy, mediator.getHandler(), mediator.getPlayer());
                    case "Fast" -> {
                        if (!mediator.getGame().difficulty)
                            new EnemyFast(x, y, ID.FastEnemy, mediator.getHandler());
                        else new EnemyFast(x, y, ID.FastEnemy, mediator.getHandler(), 3.f, 3.f);
                    }
                    case "Slow" -> {
                        if (!mediator.getGame().difficulty)
                            new EnemySlow(x, y, ID.SlowEnemy, mediator.getHandler());
                        else new EnemyHard(x, y, ID.HardEnemy, mediator.getHandler());
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
            if (level % 10 == 0) spawnMap.put(level, "Boss");
            else if (level % 8 == 0) spawnMap.put(level, "Smart");
            else if (level % 5 == 0) spawnMap.put(level, "Fast");
            else if (level % 2 == 0) spawnMap.put(level, "Slow");
        }
    }

}
