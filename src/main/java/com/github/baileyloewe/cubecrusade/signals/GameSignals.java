package com.github.baileyloewe.cubecrusade.signals;
import com.github.baileyloewe.cubecrusade.signals.Signals.Signal;
import com.github.baileyloewe.cubecrusade.signals.Signals.EventSignal;

public class GameSignals {
    // Audio
    public static Signal<Integer> AudioAdjusted = new Signal<>();
    public static EventSignal MuteToggled = new EventSignal();

    // Game
    public static EventSignal GameStarted = new EventSignal();
    public static EventSignal GameQuit = new EventSignal();
    public static EventSignal GameExited = new EventSignal();
    public static EventSignal GameResumed = new EventSignal();

    // Player
    public static EventSignal PlayerDied = new EventSignal();

    // Menu
    public static EventSignal OpenPauseMenu = new EventSignal();
    public static EventSignal OpenSettings = new EventSignal();
    public static EventSignal OpenShop = new EventSignal();

    // Upgrades
    public static EventSignal HealthUpgradePurchased = new EventSignal();
    public static EventSignal SpeedUpgradePurchased = new EventSignal();
    public static EventSignal HealthRefillPurchased = new EventSignal();

    // Health
    public static Signal<Integer> healthLost = new Signal<>();
    public static Signal<Integer> baseHealthIncreased = new Signal<>();
    public static EventSignal healthRefilled = new EventSignal();
}
