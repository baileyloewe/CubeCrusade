package com.main;

import java.awt.*;

/**
 * The HUD, or Heads Up Display, tracks and draws the player health, the score, and the current level
 */
public class HUD {
    private final Mediator mediator;

    private int greenValue = 255;
    private int redValue = 55;

    public HUD(Mediator mediator) {
        this.mediator = mediator;
    }


    /**
     * Clamps health to 0 - 100, updates health value colors, and increments the score
     */
    public void tick() {
        // Clamp to prevent these value going out of their ranges
        mediator.getUpgrade().setCurrentHealth((int)Game.clamp(mediator.getUpgrade().getCurrentHealth(), 0, mediator.getUpgrade().getCurrentMaxHealth()));
        // Update green value
        greenValue = (255 * mediator.getUpgrade().getCurrentHealth() / mediator.getUpgrade().getCurrentMaxHealth());
        redValue =  (255 * ( mediator.getUpgrade().getCurrentMaxHealth() -  mediator.getUpgrade().getCurrentHealth()) /  mediator.getUpgrade().getCurrentMaxHealth());

        mediator.getUpgrade().setScore(mediator.getUpgrade().getScore() + 1);
        mediator.getUpgrade().setPlayerCurrency(mediator.getUpgrade().getPlayerCurrency() + .1f);
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
        g.fillRect(15, 15,  (int) ((float)mediator.getUpgrade().getCurrentHealth() / (float)mediator.getUpgrade().getCurrentMaxHealth() * 200.f), 16);

        // Draw the actual health value on top of the health bar
        g.setColor(Color.BLACK);
        g.drawString("" + mediator.getUpgrade().getCurrentHealth(), 17, 28);

        // Set border color & draw
        g.setColor(Color.WHITE);
        g.drawRect(15, 15, 200, 16);
        g.setFont(new Font("TimesRoman", Font.BOLD, 12));
        g.drawString("Score: " + mediator.getUpgrade().getScore(), 15, 46);
        g.drawString("Level: " + mediator.getUpgrade().getLevel(), 15, 62);
        g.drawString("Gold: " + (int) mediator.getUpgrade().getPlayerCurrency(), 15, 78);
        g.drawString("Shop", 15, 94);
    }

}

