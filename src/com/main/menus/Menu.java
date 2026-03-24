package com.main.menus;

import com.main.Handler;
import com.main.Mediator;
import com.main.Upgrade;
import com.main.MenuBoxItem;

public abstract class Menu {
    protected MenuManager mm;
    protected Mediator mediator;
    protected Handler handler;
    protected Upgrade upgrade;
    protected int centeredX, centeredY;

    public Menu(MenuManager mm) {
        this.mm = mm;
        this.mediator = mm.mediator;
        this.handler = mm.handler;
        this.upgrade = mm.upgrade;
        this.centeredX = mm.centeredX;
        this.centeredY = mm.centeredY;
    }

    public boolean mouseOverItem(MenuBoxItem menuBoxItem, int mouseX, int mouseY) {
        return (mouseX >= menuBoxItem.rect.x && mouseX <= menuBoxItem.rect.x + menuBoxItem.rect.width) && (mouseY >= menuBoxItem.rect.y && mouseY <= menuBoxItem.rect.y + menuBoxItem.rect.height);
    }

    public void toggleMuteMusic() {
        mediator.getGameAudio().toggleMute();
        mediator.getMenuAudio().toggleMute();
    }

    public void togglePauseMusic() {
        mediator.getGameAudio().toggleMusicPause();
        mediator.getMenuAudio().toggleMusicPause();
    }

    public void adjustAudio(int delta) {
        mediator.getGameAudio().adjustAudioVolume(delta);
        mediator.getMenuAudio().adjustAudioVolume(delta);
    }
}