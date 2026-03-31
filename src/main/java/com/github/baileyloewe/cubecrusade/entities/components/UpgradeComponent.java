package com.github.baileyloewe.cubecrusade.entities.components;

import com.github.baileyloewe.cubecrusade.entities.Player;
import com.github.baileyloewe.cubecrusade.signals.GameSignals;
import com.github.baileyloewe.cubecrusade.signals.Signals.EventSignal;
import com.github.baileyloewe.cubecrusade.signals.Signals.Signal;

public class UpgradeComponent {
    private final Player player;
    private int healthUpgradeCount = 1;
    private int speedUpgradeCount = 1;
    private int healthRefillCount = 1;
    public final Signal<Integer> baseHealthIncreased = new Signal<>();
    public final EventSignal healthRefilled = new EventSignal();
    public final Signal<Float> speedIncreased = new Signal<>();

    public UpgradeComponent(Player player) {
        this.player = player;
        GameSignals.healthUpgradePurchased.connect(this, this::buyHealthUpgrade);
        GameSignals.speedUpgradePurchased.connect(this, this::buySpeedUpgrade);
        GameSignals.healthRefillPurchased.connect(this, this::buyHealthRefill);
        GameSignals.gameQuit.connect(this, this::disconnectSignals);
    }

    public void disconnectSignals() {
        GameSignals.healthUpgradePurchased.disconnect(this);
        GameSignals.speedUpgradePurchased.disconnect(this);
        GameSignals.healthRefillPurchased.disconnect(this);
        GameSignals.gameQuit.disconnect(this);
    }

    public int getCostOfNextHealthUpgrade() {
        return 100 * (healthUpgradeCount + 1);
    }

    public void buyHealthUpgrade() {
        if (player.getGold() > getCostOfNextHealthUpgrade()) {
            player.removeGold(getCostOfNextHealthUpgrade());
            healthUpgradeCount++;
            baseHealthIncreased.emit(25);
        }
    }

    public int getCostOfNextSpeedUpgrade() {
        return 100 * (speedUpgradeCount + 1);
    }

    public void buySpeedUpgrade() {
        if (player.getGold() > getCostOfNextSpeedUpgrade()) {
            player.removeGold(getCostOfNextSpeedUpgrade());
            speedUpgradeCount += 1;
            speedIncreased.emit(speedUpgradeCount * .1f);
        }
    }

    public int getCostOfNextHealthRefill() {
        return 100 * (healthRefillCount + 1);
    }

    public void buyHealthRefill() {
        if (player.getGold() > getCostOfNextHealthRefill()) {
            player.removeGold(getCostOfNextHealthRefill());
            healthRefillCount += 1;
            healthRefilled.emit();
        }
    }
}
