package com.github.baileyloewe.cubecrusade;

import com.github.baileyloewe.cubecrusade.signals.GameSignals;

import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;

public class KeyInput extends KeyAdapter {

    public int wPressed, aPressed, sPressed, dPressed;
    private final Game game;

    public KeyInput(Game game) {
        this.game = game;
    }

    @Override
    public void keyPressed(KeyEvent e) {
        int keyCode = e.getKeyCode();
        if (keyCode == KeyEvent.VK_M) {
            GameSignals.muteToggled.emit();
        }
        switch (game.gameState) {
            case GAME:
                switch (keyCode) {
                    case KeyEvent.VK_W -> wPressed = 1;
                    case KeyEvent.VK_A -> aPressed = 1;
                    case KeyEvent.VK_S -> sPressed = 1;
                    case KeyEvent.VK_D -> dPressed = 1;
                    case KeyEvent.VK_P, KeyEvent.VK_ESCAPE -> GameSignals.openPauseMenu.emit();
                    case KeyEvent.VK_SPACE -> GameSignals.openShop.emit();
                }
                break;
            case SHOP:
                if (keyCode == KeyEvent.VK_SPACE || keyCode == KeyEvent.VK_ESCAPE) GameSignals.gameResumed.emit();
                break;
            case PAUSED:
                if (keyCode == KeyEvent.VK_P || keyCode == KeyEvent.VK_ESCAPE) GameSignals.gameResumed.emit();
                break;
            default:
                break;
        }
    }

    @Override
    public void keyReleased(KeyEvent e) {
        if (game.gameState == GameState.GAME) {
            int keyCode = e.getKeyCode();
            if (keyCode == KeyEvent.VK_W) {
                wPressed = 0;
            } else if (keyCode == KeyEvent.VK_A) {
                aPressed = 0;
            } else if (keyCode == KeyEvent.VK_S) {
                sPressed = 0;
            } else if (keyCode == KeyEvent.VK_D) {
                dPressed = 0;
            }
        }
    }

    public void resetStates() {
        wPressed = aPressed = sPressed = dPressed = 0;
    }

}

