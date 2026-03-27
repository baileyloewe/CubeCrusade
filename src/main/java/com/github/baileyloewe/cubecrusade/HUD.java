package com.github.baileyloewe.cubecrusade;

import com.github.baileyloewe.cubecrusade.entities.Player;

import java.awt.*;

/**
 * The HUD, or Heads Up Display, tracks and draws the player health, the score, and the current level
 */
public class HUD {
    private final ServiceLocator serviceLocator;
    private Player player;
    private Upgrade upgrade;

    private int greenValue = 255;
    private int redValue = 55;

    public HUD(ServiceLocator serviceLocator) {
        this.serviceLocator = serviceLocator;

    }
    /**
     * Clamps health to 0 - 100, updates health value colors, and increments the score
     */
    public void tick() {
        player = serviceLocator.getPlayer();
        upgrade = serviceLocator.getUpgrade();

        int health = player.getHealth();
        int maxHealth = player.getMaxHealth();

        // Update green value
        greenValue = (255 * health / maxHealth);
        redValue =  (255 * ( maxHealth -  health) /  maxHealth);

        upgrade.incrementScore();
        upgrade.addPlayerCurrency(.1f);
    }

    /**
     * Draws the health bar, health color, and health bar border
     *
     * @param g Graphics object
     */
    public void render(Graphics g) {
        player = serviceLocator.getPlayer();
        upgrade = serviceLocator.getUpgrade();

        // Set background color of health & draw
        g.setColor(Color.white);
        g.fillRect(15, 15, 200, 16);

        // Set health bar color & draw
        // Health changes color as it lowers
        g.setColor(new Color(redValue, greenValue, 0));
        g.fillRect(15, 15,  (int) ((float) player.getHealth() / (float) player.getMaxHealth() * 200.f), 16);

        // Draw the actual health value on top of the health bar
        g.setColor(Color.BLACK);
        g.drawString("" + player.getHealth(), 17, 28);

        // Set border color & draw
        g.setColor(Color.WHITE);
        g.drawRect(15, 15, 200, 16);
        g.setFont(new Font("TimesRoman", Font.BOLD, 12));
        g.drawString("Score: " + upgrade.getScore(), 15, 46);
        g.drawString("Level: " + upgrade.getLevel(), 15, 62);
        g.drawString("Gold: " + (int) upgrade.getPlayerCurrency(), 15, 78);
        g.drawString("Shop (Space)", 15, 94);
        g.drawString("Pause (P)", 15, 110);
    }

}

