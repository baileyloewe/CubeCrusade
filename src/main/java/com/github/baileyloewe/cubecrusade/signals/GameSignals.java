package com.github.baileyloewe.cubecrusade.signals;

import com.github.baileyloewe.cubecrusade.entities.Entity;
import com.github.baileyloewe.cubecrusade.signals.Signals.EventSignal;
import com.github.baileyloewe.cubecrusade.signals.Signals.Signal;

public class GameSignals {
    // Audio
    public static Signal<Integer> audioAdjusted = new Signal<>();
    public static EventSignal muteToggled = new EventSignal();

    // Game
    public static EventSignal gameStarted = new EventSignal();
    public static EventSignal gameQuit = new EventSignal();
    public static EventSignal gameExited = new EventSignal();
    public static EventSignal gameResumed = new EventSignal();

    // Entities
    public static Signal<Entity> entityAdded = new Signal<Entity>();
    public static Signal<Entity> entityRemoved= new Signal<Entity>();
    public static EventSignal clearEnemies = new EventSignal();

    // Player
    public static EventSignal playerDied = new EventSignal();

    // Menu
    public static EventSignal openPauseMenu = new EventSignal();
    public static EventSignal openSettings = new EventSignal();
    public static EventSignal openShop = new EventSignal();

    // Upgrades
    public static EventSignal healthUpgradePurchased = new EventSignal();
    public static EventSignal speedUpgradePurchased = new EventSignal();
    public static EventSignal healthRefillPurchased = new EventSignal();

    // Health
    public static Signal<Integer> baseHealthIncreased = new Signal<>();
    public static EventSignal healthRefilled = new EventSignal();
}
