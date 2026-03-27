package com.github.baileyloewe.cubecrusade;

import com.github.baileyloewe.cubecrusade.entities.Entity;
import com.github.baileyloewe.cubecrusade.signals.GameSignals;

import java.awt.*;
import java.util.concurrent.CopyOnWriteArrayList;

/**
 * This class handles ticking, rendering, and adding/removing entities to the game
 */
public class GameHandler {

    CopyOnWriteArrayList<Entity> entities = new CopyOnWriteArrayList<>();

    public GameHandler() {
        GameSignals.GameQuit.connect(this::clearAll);
    }

    public void tick() {
        for (Entity entity : entities) {
            entity.tick();
        }
    }

    public void tickMenuParticles() {
        for (Entity entity : entities) {
            if (entity.getId() == ID.MenuParticle) entity.tick();
        }
    }

    public void render(Graphics g) {
        for (Entity entity : entities) {
            entity.render(g);
        }
    }

    public void clearEnemies() {
        for (int i = 0; i < entities.size(); i++) {
            Entity entity = entities.get(i);
            if (entity.getID() != ID.Player) {
                removeEntity(entity);
                i--;
            }
        }
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

    public CopyOnWriteArrayList<Entity> getEntities() {
        return entities;
    }
}
