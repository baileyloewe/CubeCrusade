package com.github.baileyloewe.cubecrusade;

import com.github.baileyloewe.cubecrusade.entities.Entity;
import com.github.baileyloewe.cubecrusade.signals.GameSignals;

import java.awt.*;
import java.util.concurrent.CopyOnWriteArraySet;

/**
 * This class handles ticking, rendering, and adding/removing entities to the game
 */
public class GameHandler {
    private final CopyOnWriteArraySet<Entity> entities = new CopyOnWriteArraySet<>();

    public GameHandler() {
        GameSignals.gameStarted.connect(this::clearAll);
        GameSignals.gameQuit.connect(this::clearAll);
        GameSignals.entityAdded.connect(this::addEntity);
        GameSignals.entityRemoved.connect(this::removeEntity);
        GameSignals.clearEnemies.connect(this::clearEnemies);
    }

    public void tick() {
        for (Entity entity : entities) {
            entity.tick();
        }
    }

    public void tickMenuParticles() {
        for (Entity entity : entities) {
            if (entity.getID() == ID.MenuParticle) entity.tick();
        }
    }

    public void render(Graphics g) {
        for (Entity entity : entities) {
            entity.render(g);
        }
    }

    public void clearEnemies() {
        entities.removeIf(entity -> entity.getID() != ID.Player);
    }

    public void clearAll() {
        entities.clear();
    }

    public void addEntity(Entity entity) {
        entities.add(entity);
    }

    public void removeEntity(Entity entity) {
        entities.remove(entity);
    }

    public CopyOnWriteArraySet<Entity> getEntities() {
        return entities;
    }
}
