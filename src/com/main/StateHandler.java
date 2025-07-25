package com.main;

import java.awt.*;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;

/**
 * The Menu tracks the game state, setting it based on mouse clicked events
 */
public class StateHandler extends MouseAdapter {

    private final Mediator mediator;
    private final Handler handler;
    private final Upgrade upgrade;
    private final int centeredX = Game.WIDTH / 2;
    private final int centeredY = Game.HEIGHT / 2 - 16;
    private final Font fontXLarge = new Font("Monospaced", Font.PLAIN, 60);
    private final Font fontLarge = new Font("Monospaced", Font.PLAIN, 45);
    private final Font fontSmall = new Font("Monospaced", Font.PLAIN, 30);
    private final Font fontXSmall = new Font("Monospaced", Font.PLAIN, 15);
    private MenuBoxItem menuMenuBox, menuStartBox, menuSettingsBox, menuExitBox, settingsSettingsBox, settingsBackBox;
    private MenuBoxItem settingsVolumeBox, settingsMenuVolumeMuteBox, settingsVolumeSlider, settingsVolumeSliderLineBox;
    private MenuBoxItem settingsVolumeDownBox, settingsVolumeUpBox, settingsDifficultyEasyBox, settingsDifficultyHardBox;
    private MenuBoxItem shopShopBox, shopUpgradeHealthBox, shopUpgradeSpeedBox, shopRefillHealthBox, shopCurrencyBox, shopBackBox;
    private MenuBoxItem pausedPausedBox, pausedResumeBox, pausedSettingsBox, pausedMainMenuBox, pausedExitGameBox;
    private MenuBoxItem endMainMenuBox, endGameOverBox;
    private boolean gameLive = false;
    private int mouseX, mouseY, menuParticleCount;


    public StateHandler(Mediator mediator) {
        this.mediator = mediator;
        this.handler = mediator.getHandler();
        this.upgrade = mediator.getUpgrade();
        createMenuBoxItems();
    }

    /**
     * drawRect in Java draws +1 to the x and y of a given MenuBoxItem, which is not in line with other draw methods from the same class
     * This removes 1 from the width and height (visually) of the given MenuBoxItem to fix that behavior
     */
    public static void drawRect(Graphics g, MenuBoxItem r) {
        g.drawRect(r.rect.x, r.rect.y, r.rect.width - 1, r.rect.height - 1);
    }

    public void mousePressed(MouseEvent e) {
        mouseX = e.getX();
        mouseY = e.getY();
        // Menu interactions
        switch (mediator.getGame().gameState) {
            case Menu -> mainMenuInteractions();
            case Settings -> settingsInteractions();
            case Paused -> pausedInteractions();
            case Shop -> shopInteractions();
            case End -> endInteractions();
            default -> {
            }
        }
    }

    public void mouseReleased(MouseEvent e) {
    }

    public boolean mouseOverItem(MenuBoxItem menuBoxItem) {
        // If our mouse is within the MenuBoxItem drawn around our strings, return true
        return (mouseX >= menuBoxItem.rect.x && mouseX <= menuBoxItem.rect.x + menuBoxItem.rect.width) && (mouseY >= menuBoxItem.rect.y && mouseY <= menuBoxItem.rect.y + menuBoxItem.rect.height);
    }

    public void changeAudio() {
        mediator.getGameAudio().reverseAudioStream();
        mediator.getMenuAudio().reverseAudioStream();
    }

    public void tick() {
    }

    public void render(Graphics g) {
        g.setColor(Color.WHITE);
        switch (mediator.getGame().gameState) {
            case Menu -> mainMenuRender(g);
            case Settings -> settingsRender(g);
            case Paused -> pausedRender(g);
            case Shop -> shopRender(g);
            case End -> endRender(g);
        }
    }

    public void createMenuBoxItems() {
        // Main menu boxes
        menuMenuBox = new MenuBoxItem(centeredX - 75, centeredY - 150, 150, 70, "MENU");
        menuStartBox = new MenuBoxItem(centeredX - 55, centeredY - 32, 110, 44, "START");
        menuSettingsBox = new MenuBoxItem(centeredX - 80, centeredY + 63, 160, 44, "SETTINGS");
        menuExitBox = new MenuBoxItem(centeredX - 40, centeredY + 158, 80, 44, "EXIT");

        // Settings menu boxes
        settingsSettingsBox = new MenuBoxItem(centeredX - 115, centeredY - 210, 230, 70, "SETTINGS");

        settingsVolumeBox = new MenuBoxItem(centeredX - 60, centeredY - 92, 120, 44, "VOLUME");
        settingsMenuVolumeMuteBox = new MenuBoxItem(centeredX - 40, centeredY + 13, 80, 44, "MUTE");
        settingsVolumeDownBox = new MenuBoxItem(centeredX - 130, centeredY - 40, 31, 31, "");
        settingsVolumeUpBox = new MenuBoxItem(centeredX + 100, centeredY - 40, 31, 31);
        settingsVolumeSlider = new MenuBoxItem(centeredX - 11, centeredY - 36, 22, 22);
        settingsVolumeSliderLineBox = new MenuBoxItem(centeredX - 75, centeredY - 40, 150, 22);

        settingsDifficultyEasyBox = new MenuBoxItem(centeredX - 120, centeredY + 100, 80, 44, "EASY");
        settingsDifficultyHardBox = new MenuBoxItem(centeredX + 40, centeredY + 100, 80, 44, "HARD");

        settingsBackBox = new MenuBoxItem(centeredX - 40, centeredY + 193, 80, 44, "BACK");

        // Shop menu boxes
        shopShopBox = new MenuBoxItem(centeredX - 75, centeredY - 200, 150, 70, "SHOP");
        shopUpgradeHealthBox = new MenuBoxItem(centeredX - 140, centeredY - 82, 280, 44, "UPGRADE HEALTH (" + upgrade.getCostOfNextHealthUpgrade() + ")");
        shopUpgradeSpeedBox = new MenuBoxItem(centeredX - 130, centeredY + 13, 260, 44, "UPGRADE SPEED (" + upgrade.getCostOfNextSpeedUpgrade() + ")");
        shopRefillHealthBox = new MenuBoxItem(centeredX - 130, centeredY + 108, 260, 44, "REFILL HEALTH");
        shopCurrencyBox = new MenuBoxItem(centeredX - 130, centeredY + 183, 260, 44, "GOLD: " + (int) upgrade.getPlayerCurrency());
        shopBackBox = new MenuBoxItem(centeredX - 40, centeredY + 278, 80, 44, "BACK");

        // Paused menu boxes
        pausedPausedBox = new MenuBoxItem(centeredX - 100, centeredY - 210, 200, 70, "PAUSED");
        pausedResumeBox = new MenuBoxItem(centeredX - 60, centeredY - 92, 120, 44, "RESUME");
        pausedSettingsBox = new MenuBoxItem(centeredX - 80, centeredY + 3, 160, 44, "SETTINGS");
        pausedMainMenuBox = new MenuBoxItem(centeredX - 90, centeredY + 98, 180, 44, "MAIN MENU");
        pausedExitGameBox = new MenuBoxItem(centeredX - 90, centeredY + 193, 180, 44, "EXIT GAME");

        // End menu boxes
        endGameOverBox = new MenuBoxItem(centeredX - 175, centeredY - 170, 350, 70, "GAME OVER");
        endMainMenuBox = new MenuBoxItem(centeredX - 90, centeredY + 163, 180, 44, "MAIN MENU");
    }

    /**
     * Fills a MenuBoxItem with a color using graphics. Sets the color back to the original color for the graphics
     *
     * @param graphics    graphics object to draw with
     * @param MenuBoxItem the MenuBoxItem to be drawn
     * @param color       the color to be used
     */
    public void fillRect(Graphics graphics, MenuBoxItem MenuBoxItem, Color color) {
        Color storedColor = graphics.getColor();
        graphics.setColor(color);
        graphics.fillRect(MenuBoxItem.rect.x, MenuBoxItem.rect.y, MenuBoxItem.rect.width, MenuBoxItem.rect.height);
        graphics.setColor(storedColor);
    }

    /**
     * Draws a string within a MenuBoxItem
     *
     * @param graphics    graphics object to draw with
     * @param MenuBoxItem the MenuBoxItem the string is drawn in
     * @param font        the font the string is drawn with
     */
    public void drawCenteredString(Graphics graphics, MenuBoxItem MenuBoxItem, Font font) {
        FontMetrics fontMetric = graphics.getFontMetrics(font);

        // Find x & y coordinate using fontMetrics' help. Use offset because even fontMetrics isn't perfect
        int xCoord = (MenuBoxItem.rect.x - 1 + (MenuBoxItem.rect.width - fontMetric.stringWidth(MenuBoxItem.s)) / 2);
        int yCoord = MenuBoxItem.rect.y - 2 + ((MenuBoxItem.rect.height - fontMetric.getHeight()) / 2) + fontMetric.getAscent();

        graphics.setFont(font);
        graphics.setColor(Color.white);
        graphics.drawString(MenuBoxItem.s, xCoord + 1, yCoord);
    }

    /**
     * Draws the MenuBoxItem and a string within the MenuBoxItem
     *
     * @param g           graphics object to draw with
     * @param MenuBoxItem the MenuBoxItem to be drawn
     * @param font        the font the string is drawn with
     */
    public void drawRectAndString(Graphics g, MenuBoxItem MenuBoxItem, Font font) {
        fillRect(g, MenuBoxItem, Color.black);
        drawRect(g, MenuBoxItem);
        drawCenteredString(g, MenuBoxItem, font);
    }

    /**
     * Draws the MenuBoxItem and a string within the MenuBoxItem
     *
     * @param g           graphics object to draw with
     * @param MenuBoxItem the MenuBoxItem to be drawn
     * @param font        the font the string is drawn with
     */
    public void drawRectAndStringWithColor(Graphics g, MenuBoxItem MenuBoxItem, Font font, Color color) {
        fillRect(g, MenuBoxItem, color);
        drawRect(g, MenuBoxItem);
        drawCenteredString(g, MenuBoxItem, font);
    }

    public void updateShopBoxSizesAndText() {
        shopUpgradeHealthBox.s = "UPGRADE HEALTH (" + upgrade.getCostOfNextHealthUpgrade() + ")";

        shopUpgradeHealthBox.rect.x = centeredX - (shopUpgradeHealthBox.s.length() * 10);
        shopUpgradeHealthBox.rect.width = (shopUpgradeHealthBox.s.length() * 20);

        shopUpgradeSpeedBox.s = "UPGRADE SPEED (" + upgrade.getCostOfNextSpeedUpgrade() + ")";
        shopUpgradeSpeedBox.rect.x = centeredX - (shopUpgradeSpeedBox.s.length() * 10);
        shopUpgradeSpeedBox.rect.width = (shopUpgradeSpeedBox.s.length() * 20);

        shopCurrencyBox.s = "GOLD: " + (int) upgrade.getPlayerCurrency();
        shopCurrencyBox.rect.x = centeredX - (shopCurrencyBox.s.length() * 10);
        shopCurrencyBox.rect.width = (shopCurrencyBox.s.length() * 20);

        shopRefillHealthBox.s = "REFILL HEALTH (" + upgrade.getCostOfNextHealthRefill() + ")";
        shopRefillHealthBox.rect.x = centeredX - (shopRefillHealthBox.s.length() * 10);
        shopRefillHealthBox.rect.width = (shopRefillHealthBox.s.length() * 20);
    }

    public void drawVolumeUpAndDown(Graphics g, MenuBoxItem volDown, MenuBoxItem volUp) {
        // Draw volume down
        g.drawLine(volDown.rect.x, volDown.rect.y + 15, volDown.rect.x + 30, volDown.rect.y + 15);
        g.drawLine(volDown.rect.x, volDown.rect.y + 16, volDown.rect.x + 30, volDown.rect.y + 16);

        // Draw volume up
        g.drawLine(volUp.rect.x, volUp.rect.y + 15, volUp.rect.x + 30, volUp.rect.y + 15);
        g.drawLine(volUp.rect.x, volUp.rect.y + 16, volUp.rect.x + 30, volUp.rect.y + 16);
        g.drawLine(volUp.rect.x + 15, volUp.rect.y, volUp.rect.x + 15, volUp.rect.y + 30);
        g.drawLine(volUp.rect.x + 16, volUp.rect.y, volUp.rect.x + 16, volUp.rect.y + 30);
    }

    public void mainMenuInteractions() {
        // Quick particle count check, just to prevent too many from spawning
        if (menuParticleCount < 25) {
            new EnemyMenuParticle(mouseX, mouseY, ID.MenuParticle, handler);
            menuParticleCount++;
        }
        if (mouseOverItem(menuStartBox)) // If we press the play button, start the game
        {
            // Stop the menu audio and start the game audio
            changeAudio();
            gameLive = true;
            handler.clearAll();
            upgrade.initializeValues();
            menuParticleCount = 0;
            mediator.getGame().sleepThread(500);
            mediator.getGame().gameState = Game.STATE.Game;
            mediator.setPlayer(new Player(Game.WIDTH / 2.f - 32, Game.HEIGHT / 2.f - 32, ID.Player, handler, mediator));
            if (!mediator.getGame().difficulty) new EnemySlow(1, 1, ID.SlowEnemy, handler);
            else new EnemyHard(1, 1, ID.HardEnemy, handler);
        } else if (mouseOverItem(menuSettingsBox)) // If we press the settings button, switch to settings
        {
            mediator.getGame().gameState = Game.STATE.Settings;
        } else if (mouseOverItem(menuExitBox)) // If we press the exit button, exit the game
        {
            mediator.getGame().exitGame();
        }
    }

    public void settingsInteractions() {
        if (mouseOverItem(settingsBackBox)) { // If we press the back button, return to the main menu
            if (gameLive) mediator.getGame().gameState = Game.STATE.Paused;
            else mediator.getGame().gameState = Game.STATE.Menu;
        }
        // Volume up button interaction
        else if (mouseOverItem(settingsVolumeUpBox)) {
            mediator.getMenuAudio().changeVolumeOfAudioStream(5);
            mediator.getGameAudio().changeVolumeOfAudioStream(5);
        } else if (mouseOverItem(settingsVolumeDownBox)) {
            mediator.getMenuAudio().changeVolumeOfAudioStream(-5);
            mediator.getGameAudio().changeVolumeOfAudioStream(-5);
        } else if (mouseOverItem(settingsMenuVolumeMuteBox)) {
            mediator.getMenuAudio().reverseMuteState();
            mediator.getGameAudio().reverseMuteState();
        } else if (mouseOverItem(settingsDifficultyEasyBox) && !gameLive) {
            mediator.getGame().difficulty = false;
        } else if (mouseOverItem(settingsDifficultyHardBox) && !gameLive) {
            mediator.getGame().difficulty = true;
        }
    }

    public void pausedInteractions() {
        if (mouseOverItem(pausedResumeBox)) {
            mediator.getGame().gameState = Game.STATE.Game;
        } else if (mouseOverItem(pausedSettingsBox)) {
            mediator.getGame().gameState = Game.STATE.Settings;
        } else if (mouseOverItem(pausedExitGameBox)) {
            mediator.getGame().exitGame();
        } else if (mouseOverItem(pausedMainMenuBox)) {
            gameLive = false;
            handler.clearAll();
            mediator.getGame().gameState = Game.STATE.Menu;
            changeAudio();
            new EnemyMenuParticle(mouseX, mouseY, ID.MenuParticle, handler);
        }
    }

    public void shopInteractions() {
        if (mouseOverItem(shopUpgradeHealthBox)) {
            upgrade.buyHealthUpgrade();
        } else if (mouseOverItem(shopUpgradeSpeedBox)) {
            upgrade.buySpeedUpgrade();
        } else if (mouseOverItem(shopRefillHealthBox)) {
            upgrade.buyHealthRefill();
        } else if (mouseOverItem(shopBackBox)) {
            mediator.getGame().gameState = Game.STATE.Game;
        }
    }

    public void endInteractions() {
        if (mouseOverItem(endMainMenuBox)) {
            handler.clearAll();
            gameLive = false;
            mediator.getGame().gameState = Game.STATE.Menu;
            changeAudio();
            new EnemyMenuParticle(mouseX, mouseY, ID.MenuParticle, handler);
        }
    }

    public void mainMenuRender(Graphics g) {
        drawRectAndString(g, menuMenuBox, fontLarge);
        drawRectAndString(g, menuStartBox, fontSmall);
        drawRectAndString(g, menuSettingsBox, fontSmall);
        drawRectAndString(g, menuExitBox, fontSmall);
    }

    public void settingsRender(Graphics g) {
        // Settings
        drawRectAndString(g, settingsSettingsBox, fontLarge);

        // Volume
        // Draw the volume box text
        drawRectAndString(g, settingsVolumeBox, fontSmall);

        // Draw the volume mute box
        if (mediator.getMenuAudio().isMuted())
            drawRectAndStringWithColor(g, settingsMenuVolumeMuteBox, fontSmall, new Color(109, 10, 6));
        else drawRectAndStringWithColor(g, settingsMenuVolumeMuteBox, fontSmall, Color.black);

        drawVolumeUpAndDown(g, settingsVolumeDownBox, settingsVolumeUpBox);

        // Adjust position based on the volume
        settingsVolumeSlider.rect.x = (settingsVolumeSliderLineBox.rect.x - 1) + (((int) mediator.getMenuAudio().getCurrentVolume() + 80) * (settingsVolumeSliderLineBox.rect.width - 22) / 85);
        g.fillRect(settingsVolumeSlider.rect.x, settingsVolumeSlider.rect.y, 22, 22);

        // Volume slider line
        g.fillRect(centeredX - 75, settingsVolumeDownBox.rect.y + 15, 150, 2);


        // Difficulty
        if (!mediator.getGame().difficulty) {
            drawRectAndStringWithColor(g, settingsDifficultyEasyBox, fontSmall, new Color(1, 72, 12));
            drawRectAndStringWithColor(g, settingsDifficultyHardBox, fontSmall, Color.black);
        } else {
            drawRectAndStringWithColor(g, settingsDifficultyEasyBox, fontSmall, Color.black);
            drawRectAndStringWithColor(g, settingsDifficultyHardBox, fontSmall, new Color(109, 10, 6));
        }
        // Exit
        drawRectAndString(g, settingsBackBox, fontSmall);
    }

    public void pausedRender(Graphics g) {
        drawRectAndString(g, pausedPausedBox, fontLarge);
        drawRectAndString(g, pausedResumeBox, fontSmall);
        drawRectAndString(g, pausedSettingsBox, fontSmall);
        drawRectAndString(g, pausedMainMenuBox, fontSmall);
        drawRectAndString(g, pausedExitGameBox, fontSmall);
    }

    public void shopRender(Graphics g) {
        drawRectAndString(g, shopShopBox, fontLarge);
        drawRectAndString(g, shopUpgradeHealthBox, fontSmall);
        drawRectAndString(g, shopUpgradeSpeedBox, fontSmall);
        drawRectAndString(g, shopRefillHealthBox, fontSmall);
        drawRectAndString(g, shopCurrencyBox, fontSmall);
        drawRectAndString(g, shopBackBox, fontSmall);
        updateShopBoxSizesAndText();
    }

    public void endRender(Graphics g) {
        // Game over, allow returning to the main menu
        handler.clearAll(); // Clear all game objects from the handler
        drawRectAndString(g, endGameOverBox, fontXLarge);
        // You lost
        g.setFont(fontXSmall);
        g.drawString("You lost with a score of " + upgrade.getScore() + "\n on level " + upgrade.getLevel(), centeredX - 180, centeredY);
        // Main menu box
        drawRectAndString(g, endMainMenuBox, fontSmall);
    }


}
