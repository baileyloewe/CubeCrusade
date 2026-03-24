package com.main.menus;

import com.main.Game;
import com.main.MenuBoxItem;

import java.awt.*;

import static com.main.GraphicsUtil.*;

public class SettingsMenu extends Menu {
    private final MenuBoxItem outlineBox, backBox, volumeBox, muteBox, volumeSliderBox, volumeSliderLineBox, volumeDownBox, volumeUpBox, difficultyEasyBox, difficultyHardBox;

    public SettingsMenu(MenuManager menuManager) {
        super(menuManager);

        outlineBox = new MenuBoxItem(centeredX - 115, centeredY - 210, 230, 70, "SETTINGS");
        volumeBox = new MenuBoxItem(centeredX - 60, centeredY - 92, 120, 44, "VOLUME");
        muteBox = new MenuBoxItem(centeredX - 40, centeredY + 13, 80, 44, "MUTE");
        volumeDownBox = new MenuBoxItem(centeredX - 130, centeredY - 40, 31, 31);
        volumeUpBox = new MenuBoxItem(centeredX + 100, centeredY - 40, 31, 31);
        volumeSliderBox = new MenuBoxItem(centeredX - 11, centeredY - 36, 22, 22);
        volumeSliderLineBox = new MenuBoxItem(centeredX - 75, centeredY - 40, 150, 22);
        difficultyEasyBox = new MenuBoxItem(centeredX - 120, centeredY + 100, 80, 44, "EASY");
        difficultyHardBox = new MenuBoxItem(centeredX + 40, centeredY + 100, 80, 44, "HARD");
        backBox = new MenuBoxItem(centeredX - 40, centeredY + 193, 80, 44, "BACK");
    }

    public void interact(int mouseX, int mouseY) {
        if (mouseOverItem(backBox, mouseX, mouseY)) { // If we press the back button, return to the main menu
            if (mm.gameLive) mediator.getGame().gameState = Game.STATE.Paused;
            else mediator.getGame().gameState = Game.STATE.Menu;
        }
        // Volume up button interaction
        else if (mouseOverItem(volumeUpBox, mouseX, mouseY)) {
            mediator.getMenuAudio().changeVolumeOfAudioStream(5);
            mediator.getGameAudio().changeVolumeOfAudioStream(5);
        } else if (mouseOverItem(volumeDownBox, mouseX, mouseY)) {
            mediator.getMenuAudio().changeVolumeOfAudioStream(-5);
            mediator.getGameAudio().changeVolumeOfAudioStream(-5);
        } else if (mouseOverItem(muteBox, mouseX, mouseY)) {
            mediator.getMenuAudio().reverseMuteState();
            mediator.getGameAudio().reverseMuteState();
        } else if (mouseOverItem(difficultyEasyBox, mouseX, mouseY) && !mm.gameLive) {
            mediator.getGame().difficulty = false;
        } else if (mouseOverItem(difficultyHardBox, mouseX, mouseY) && !mm.gameLive) {
            mediator.getGame().difficulty = true;
        }
        else if (!mm.gameLive) {
            mediator.getGame().difficulty = mouseOverItem(difficultyEasyBox, mouseX, mouseY);
        }
    }

    public void render(Graphics g) {
        // Settings
        drawRectAndString(g, outlineBox, Fonts.LARGE);

        // Volume
        // Draw the volume box text
        drawRectAndString(g, volumeBox, Fonts.MEDIUM);

        // Draw the volume mute box
        if (mediator.getMenuAudio().isMuted())
            drawRectAndStringWithColor(g, muteBox, Fonts.MEDIUM, new Color(109, 10, 6));
        else drawRectAndStringWithColor(g, muteBox, Fonts.MEDIUM, Color.black);

        drawVolumeUpAndDown(g, volumeDownBox, volumeUpBox);

        // Adjust position based on the volume
        volumeSliderBox.rect.x = (volumeSliderLineBox.rect.x - 1) + (((int) mediator.getMenuAudio().getCurrentVolume() + 80) * (volumeSliderLineBox.rect.width - 22) / 85);
        g.fillRect(volumeSliderBox.rect.x, volumeSliderBox.rect.y, 22, 22);

        // Volume slider line
        g.fillRect(centeredX - 75, volumeDownBox.rect.y + 15, 150, 2);


        // Difficulty
        if (!mediator.getGame().difficulty) {
            drawRectAndStringWithColor(g, difficultyEasyBox, Fonts.MEDIUM, new Color(1, 72, 12));
            drawRectAndStringWithColor(g, difficultyHardBox, Fonts.MEDIUM, Color.black);
        } else {
            drawRectAndStringWithColor(g, difficultyEasyBox, Fonts.MEDIUM, Color.black);
            drawRectAndStringWithColor(g, difficultyHardBox, Fonts.MEDIUM, new Color(109, 10, 6));
        }
        // Exit
        drawRectAndString(g, backBox, Fonts.MEDIUM);
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


}
