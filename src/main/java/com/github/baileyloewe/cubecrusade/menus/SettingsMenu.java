package com.github.baileyloewe.cubecrusade.menus;

import com.github.baileyloewe.cubecrusade.Game;
import com.github.baileyloewe.cubecrusade.MenuBoxItem;
import com.github.baileyloewe.cubecrusade.signals.GameSignals;

import java.awt.*;
import static com.github.baileyloewe.cubecrusade.GraphicsUtil.*;

public class SettingsMenu extends Menu {
    private final MenuBoxItem settingsTitle, backBox, volumeTitle, muteBox, volumeSliderBox, volumeSliderLineBox, volumeDownBox, volumeUpBox, difficultyEasyBox, difficultyHardBox;

    public SettingsMenu(MenuManager menuManager) {
        super(menuManager);
        settingsTitle = new MenuBoxItem(centeredX - 115, centeredY - 210, 230, 70, "SETTINGS");
        volumeTitle = new MenuBoxItem(centeredX - 60, centeredY - 92, 120, 44, "VOLUME");
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
        if (mouseOverItem(backBox, mouseX, mouseY)) {
            if (mediator.getGame().gameActive) mediator.getGame().gameState = Game.GAMESTATE.Paused;
            else mediator.getGame().gameState = Game.GAMESTATE.Menu;
        }
        else if (mouseOverItem(volumeUpBox, mouseX, mouseY)) {
            GameSignals.AudioAdjusted.emit(5);
        } else if (mouseOverItem(volumeDownBox, mouseX, mouseY)) {
            GameSignals.AudioAdjusted.emit(-5);
        } else if (mouseOverItem(muteBox, mouseX, mouseY)) {
            GameSignals.MuteToggled.emit();
        } else if (!mediator.getGame().gameActive) {
            if (mouseOverItem(difficultyEasyBox, mouseX, mouseY)) {
                mediator.getGame().difficulty = Game.DIFFICULTY.Easy;
            } else if (mouseOverItem(difficultyHardBox, mouseX, mouseY)) {
                mediator.getGame().difficulty = Game.DIFFICULTY.Hard;
            }
        }
    }

    public void render(Graphics g) {
        drawRectAndString(g, settingsTitle, Fonts.LARGE);
        drawRectAndString(g, volumeTitle, Fonts.MEDIUM);
        if (mediator.getAudioStream().isPlaying()) {
            drawRectAndStringWithColor(g, muteBox, Fonts.MEDIUM, Color.black);
        }
        else drawRectAndStringWithColor(g, muteBox, Fonts.MEDIUM, new Color(109, 10, 6));

        drawVolumeUpAndDown(g, volumeDownBox, volumeUpBox);

        volumeSliderBox.rect.x = (volumeSliderLineBox.rect.x - 1) + (((int) mediator.getAudioStream().getCurrentVolume() + 80) * (volumeSliderLineBox.rect.width - 22) / 85);
        g.fillRect(volumeSliderBox.rect.x, volumeSliderBox.rect.y, 22, 22);

        g.fillRect(centeredX - 75, volumeDownBox.rect.y + 15, 150, 2);

        if (mediator.getGame().difficulty == Game.DIFFICULTY.Easy) {
            drawRectAndStringWithColor(g, difficultyEasyBox, Fonts.MEDIUM, new Color(1, 72, 12));
            drawRectAndStringWithColor(g, difficultyHardBox, Fonts.MEDIUM, Color.black);
        } else {
            drawRectAndStringWithColor(g, difficultyEasyBox, Fonts.MEDIUM, Color.black);
            drawRectAndStringWithColor(g, difficultyHardBox, Fonts.MEDIUM, new Color(109, 10, 6));
        }
        drawRectAndString(g, backBox, Fonts.MEDIUM);
    }

    public void drawVolumeUpAndDown(Graphics g, MenuBoxItem volDown, MenuBoxItem volUp) {
        g.drawLine(volDown.rect.x, volDown.rect.y + 15, volDown.rect.x + 30, volDown.rect.y + 15);
        g.drawLine(volDown.rect.x, volDown.rect.y + 16, volDown.rect.x + 30, volDown.rect.y + 16);
        g.drawLine(volUp.rect.x, volUp.rect.y + 15, volUp.rect.x + 30, volUp.rect.y + 15);
        g.drawLine(volUp.rect.x, volUp.rect.y + 16, volUp.rect.x + 30, volUp.rect.y + 16);
        g.drawLine(volUp.rect.x + 15, volUp.rect.y, volUp.rect.x + 15, volUp.rect.y + 30);
        g.drawLine(volUp.rect.x + 16, volUp.rect.y, volUp.rect.x + 16, volUp.rect.y + 30);
    }


}
