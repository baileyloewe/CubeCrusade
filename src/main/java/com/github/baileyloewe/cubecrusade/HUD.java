package com.github.baileyloewe.cubecrusade;

import com.github.baileyloewe.cubecrusade.entities.Player;

import java.awt.*;

/**
 * The HUD, or Heads Up Display, tracks and draws the player health, the score, and the current level
 */
public class HUD {
    private final Game game;
    private final Player player;

    private int greenValue = 255;
    private int redValue = 55;
    
    public HUD(Game game, Player player) {
        this.game = game;
        this.player = player;
    }

    /**
     * Clamps health to 0 - 100, updates health value colors, and increments the score
     */
    public void tick() {
        int health = player.getHealth();
        int maxHealth = player.getMaxHealth();

        greenValue = (255 * health / maxHealth);
        redValue = (255 * (maxHealth - health) / maxHealth);
    }

    /**
     * Draws the health bar, health color, and health bar border
     *
     * @param g Graphics object
     */
    public void render(Graphics g) {
        // Set background color of health & draw
        g.setColor(Color.white);
        g.fillRect(15, 15, 200, 16);

        // Set health bar color & draw
        // Health changes color as it lowers
        g.setColor(new Color(redValue, greenValue, 0));
        g.fillRect(15, 15, (int) ((float) player.getHealth() / (float) player.getMaxHealth() * 200.f), 16);

        // Draw the actual health value on top of the health bar
        g.setColor(Color.BLACK);
        g.drawString("" + player.getHealth(), 17, 28);

        // Set border color & draw
        g.setColor(Color.WHITE);
        g.drawRect(15, 15, 200, 16);
        g.setFont(new Font("TimesRoman", Font.BOLD, 12));
        g.drawString("Level: " + game.level, 15, 46);
        g.drawString("Gold: " + (int) player.getGold(), 15, 62);
        g.drawString("Shop (Space)", 15, 78);
        g.drawString("Pause (P)", 15, 94);
    }

}

