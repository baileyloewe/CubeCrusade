package com.main;

public class Upgrade {

    private int numberOfHealthUpgrades = 1;
    private int numberOfSpeedUpgrades = 1;
    private int numberOfHealthRefills = 1;
    private int score;
    private int level;
    private final float baseSpeed = 2;
    private float currentSpeed;



    private float playerCurrency;
    public static final int baseHealth = 100;
    public int currentHealth;
    public int currentMaxHealth;


    public int getNumberOfHealthUpgrades() {
        return numberOfHealthUpgrades;
    }

    public void setNumberOfHealthUpgrades(int numberOfHealthUpgrades) {
        this.numberOfHealthUpgrades = numberOfHealthUpgrades;
    }

    public void incrementHealthUpgrades() {
        this.numberOfHealthUpgrades += 1;
    }

    public int getCostOfNextHealthUpgrade() {
        return 100 * (getNumberOfHealthUpgrades());
    }

    public void buyHealthUpgrade() {
        if (playerCurrency > getCostOfNextHealthUpgrade()) {
            playerCurrency -= getCostOfNextHealthUpgrade();
            incrementHealthUpgrades();

            currentMaxHealth = 100 + ((numberOfHealthUpgrades - 1) * 25);
            currentHealth = Math.min(currentHealth + 25, currentMaxHealth);
        }
    }

    public int getNumberOfSpeedUpgrades() {
        return numberOfSpeedUpgrades;
    }

    public void setNumberOfSpeedUpgrades(int numberOfSpeedUpgrades) {
        this.numberOfSpeedUpgrades = numberOfSpeedUpgrades;
    }

    public void incrementSpeedUpgrades() {
        this.numberOfSpeedUpgrades += 1;
    }

    public int getCostOfNextSpeedUpgrade() {
        return 100 * (getNumberOfSpeedUpgrades());
    }

    public void buySpeedUpgrade() {
        if (playerCurrency > getCostOfNextSpeedUpgrade()) {
            playerCurrency -= getCostOfNextSpeedUpgrade();
            incrementSpeedUpgrades();
            currentSpeed = 2 + ((numberOfSpeedUpgrades - 1) * .1f);
        }
    }


    public int getNumberOfHealthRefills() {
        return numberOfHealthRefills;
    }

    public void setNumberOfHealthRefills(int numberOfHealthRefills) {
        this.numberOfHealthRefills = numberOfHealthRefills;
    }

    public int getCostOfNextHealthRefill() {
        return 100 * (getNumberOfHealthRefills());
    }

    public void incrementNumberOfHealthRefills() {
        this.numberOfHealthRefills += 1;
    }

    public void buyHealthRefill() {
        if (playerCurrency > getCostOfNextHealthRefill()) {
            playerCurrency -= getCostOfNextHealthRefill();
            incrementNumberOfHealthRefills();
            currentHealth = currentMaxHealth;
        }
    }

    /**
     * Sets the player's max health, current health, level, score, and currency to their initial values
     */
    public void initializeValues() {
        setCurrentMaxHealth(baseHealth);
        setCurrentHealth(currentMaxHealth);
        setCurrentSpeed(getBaseSpeed());
        setLevel(1);
        setScore(0);
        setPlayerCurrency(0);
        numberOfHealthUpgrades = 1;
        numberOfSpeedUpgrades = 1;
        numberOfHealthRefills = 1;

    }

    public float getPlayerCurrency() {
        return playerCurrency;
    }

    public void setPlayerCurrency(float playerCurrency) {
        this.playerCurrency = playerCurrency;
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

    public int getCurrentHealth() {
        return currentHealth;
    }

    public void setCurrentHealth(int currentHealth) {
        this.currentHealth = currentHealth;
    }

    public int getCurrentMaxHealth() {
        return currentMaxHealth;
    }

    public void setCurrentMaxHealth(int currentMaxHealth) {
        this.currentMaxHealth = currentMaxHealth;
    }

    public int getBaseHealth() {
        return baseHealth;
    }

    public int getScore() {
        return score;
    }

    public void setScore(int score) {
        this.score = score;
    }

    public int getLevel() {
        return level;
    }

    public void setLevel(int level) {
        this.level = level;
    }

}
