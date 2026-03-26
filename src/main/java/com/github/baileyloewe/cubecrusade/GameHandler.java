package com.github.baileyloewe.cubecrusade;

import com.github.baileyloewe.cubecrusade.signals.GameSignals;

import java.awt.*;
import java.util.concurrent.CopyOnWriteArrayList;

/**
 * This class handlers ticking and rendering all game objects
 */
public class GameHandler {

    CopyOnWriteArrayList<GameObject> GameObjectLinkedList = new CopyOnWriteArrayList<>();

    public GameHandler() {
        GameSignals.GameQuit.connect(this::clearAll);
    }

    /**
     * Loops through the GameObjectLinkedList, ticking each item
     */
    public void tick() {
        for (GameObject gameObject : GameObjectLinkedList) {
            gameObject.tick();
        }
    }


    /**
     * Loops through the GameObjectLinkedList, ticking menu particles
     */
    public void tickMenu() {
        for (GameObject gameObject : GameObjectLinkedList) {
            if (gameObject.id == ID.MenuParticle) gameObject.tick();
        }
    }


    /**
     * Loops through the GameObjectLinkedList, rendering each item
     */
    public void render(Graphics g) {
        for (GameObject gameObject : GameObjectLinkedList) {
            gameObject.render(g);
        }
    }


    /**
     * Clears all non-player game objects (for now)
     */
    public void clearEnemies() {
        for (int i = 0; i < GameObjectLinkedList.size(); i++) {
            GameObject gameObject = GameObjectLinkedList.get(i);
            if (gameObject.id != ID.Player) {
                removeObject(gameObject);
                i--;
            }
        }
    }


    /**
     * Clears all objects from the GameObjectLinkedList
     */
    public void clearAll() {
        GameObjectLinkedList.clear();
    }


    /**
     * Adds a GameObject (object) to the GameObjectLinkedList
     */
    public void addObject(GameObject object) {
        GameObjectLinkedList.add(object);
    }


    /**
     * Removes a GameObject (object) from the GameObjectLinkedList
     */
    public void removeObject(GameObject object) {
        GameObjectLinkedList.remove(object);
    }
}
