package com.main;

import java.awt.*;
import java.util.concurrent.CopyOnWriteArrayList;

/**
 * This class handlers ticking and rendering all game objects
 */
public class Handler {

    CopyOnWriteArrayList<GameObject> GameObjectLinkedList = new CopyOnWriteArrayList<>();

    /**
     * Loops through the GameObjectLinkedList, ticking each item
     */
    public void tick() {
        for (GameObject tempObject : GameObjectLinkedList) {
            tempObject.tick();
        }
    }


    /**
     * Loops through the GameObjectLinkedList, ticking menu particles
     */
    public void tickMenu() {
        for (GameObject tempObject : GameObjectLinkedList) {
            if (tempObject.id == ID.MenuParticle) tempObject.tick();
        }
    }


    /**
     * Loops through the GameObjectLinkedList, rendering each item
     */
    public void render(Graphics g) {
        for (GameObject tempObject : GameObjectLinkedList) {
            tempObject.render(g);
        }
    }


    /**
     * Clears all non-player game objects (for now)
     */
    public void clearEnemies() {
        for (int i = 0; i < GameObjectLinkedList.size(); i++) {
            GameObject tempObject = GameObjectLinkedList.get(i);
            if (tempObject.id != ID.Player) {
                removeObject(tempObject);
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
