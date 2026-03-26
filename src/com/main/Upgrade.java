package com.main;

import com.main.signals.GameSignals;

public class Upgrade {

    private int healthUpgradeCount = 1;
    private int speedUpgradeCount = 1;
    private int healthRefillCount = 1;
    private int score;
    private int level;
    private final float baseSpeed = 2;
    private float currentSpeed;
    private float playerCurrency;

    public Upgrade() {
        GameSignals.GameStarted.connect(this::initializeValues);
        GameSignals.HealthUpgradePurchased.connect(this::buyHealthUpgrade);
        GameSignals.SpeedUpgradePurchased.connect(this::buySpeedUpgrade);
        GameSignals.HealthRefillPurchased.connect(this::buyHealthRefill);
    }

    public int getHealthUpgradeCount() {
        return healthUpgradeCount;
    }

    public void setHealthUpgradeCount(int healthUpgradeCount) {
        this.healthUpgradeCount = healthUpgradeCount;
    }

    public void incrementHealthUpgrades() {
        this.healthUpgradeCount += 1;
    }

    public int getCostOfNextHealthUpgrade() {
        return 100 * (getHealthUpgradeCount());
    }

    public void buyHealthUpgrade() {
        if (playerCurrency > getCostOfNextHealthUpgrade()) {
            playerCurrency -= getCostOfNextHealthUpgrade();
            incrementHealthUpgrades();
            GameSignals.baseHealthIncreased.emit(25);
        }
    }

    public int getSpeedUpgradeCount() {
        return speedUpgradeCount;
    }

    public void setSpeedUpgradeCount(int speedUpgradeCount) {
        this.speedUpgradeCount = speedUpgradeCount;
    }

    public void incrementSpeedUpgradeCount() {
        this.speedUpgradeCount += 1;
    }

    public int getCostOfNextSpeedUpgrade() {
        return 100 * (getSpeedUpgradeCount());
    }

    public void buySpeedUpgrade() {
        if (playerCurrency > getCostOfNextSpeedUpgrade()) {
            playerCurrency -= getCostOfNextSpeedUpgrade();
            incrementSpeedUpgradeCount();
            currentSpeed = 2 + ((speedUpgradeCount - 1) * .1f);
        }
    }

    public int getHealthRefills() {
        return healthRefillCount;
    }

    public void setHealthRefills(int healthRefills) {
        this.healthRefillCount = healthRefills;
    }

    public int getCostOfNextHealthRefill() {
        return 100 * (getHealthRefills());
    }

    public void incrementHealthRefills() {
        this.healthRefillCount += 1;
    }

    public void buyHealthRefill() {
        if (playerCurrency > getCostOfNextHealthRefill()) {
            playerCurrency -= getCostOfNextHealthRefill();
            incrementHealthRefills();
            GameSignals.healthRefilled.emit();
        }
    }

    public void initializeValues() {
        setCurrentSpeed(getBaseSpeed());
        setLevel(1);
        setScore(0);
        setPlayerCurrency(0);
        setHealthUpgradeCount(1);
        setSpeedUpgradeCount(1);
        setHealthRefills(1);
    }

    public float getPlayerCurrency() {
        return playerCurrency;
    }

    public void setPlayerCurrency(float playerCurrency) {
        this.playerCurrency = playerCurrency;
    }

    public void addPlayerCurrency(float currency) {
        playerCurrency += currency;
    }

    public float getBaseSpeed() {
        return baseSpeed;
    }

    public float getCurrentSpeed() {
        return currentSpeed;
    }

    public void setCurrentSpeed(float currentSpeed) {
        this.currentSpeed = currentSpeed;
    }

    public int getScore() {
        return score;
    }

    public void setScore(int score) {
        this.score = score;
    }

    public void incrementScore() {
        score += 1;
    }

    public int getLevel() {
        return level;
    }

    public void setLevel(int level) {
        this.level = level;
    }

}
