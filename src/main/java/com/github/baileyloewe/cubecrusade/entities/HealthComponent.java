package com.github.baileyloewe.cubecrusade.entities;

import com.github.baileyloewe.cubecrusade.signals.Signals.EventSignal;

public class HealthComponent {
    int currentHealth;
    int maxHealth;
    EventSignal died = new EventSignal();

    public HealthComponent(int health) {
        this.maxHealth = health;
        this.currentHealth = health;
    }

    void damage(int damage) {
        currentHealth = Math.max(0, currentHealth - damage);
        if (currentHealth == 0) {
            died.emit();
        }
    }

    void heal(int health) {
        currentHealth += health;
    }

    void fullHeal() {
        currentHealth = maxHealth;
    }

    void increaseMaxHealth(int health) {
        maxHealth += health;
        currentHealth += health;
    }

}
