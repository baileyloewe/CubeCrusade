package com.github.baileyloewe.cubecrusade.entities.components;

import com.github.baileyloewe.cubecrusade.signals.Signals.EventSignal;

public class HealthComponent {
    public int currentHealth;
    public int maxHealth;
    public final EventSignal died = new EventSignal();

    public HealthComponent(int health) {
        this.maxHealth = health;
        this.currentHealth = health;
    }

    public void damage(int damage) {
        currentHealth = Math.max(0, currentHealth - damage);
        if (currentHealth == 0) {
            died.emit();
        }
    }

    public void fullHeal() {
        currentHealth = maxHealth;
    }

    public void increaseMaxHealth(int health) {
        maxHealth += health;
        currentHealth += health;
    }

}
