package com.main;

import com.main.signals.GameSignals;

import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;

public class KeyInput extends KeyAdapter {
    private final Mediator mediator;
    private boolean wPressed, aPressed, sPressed, dPressed;
    private boolean mostRecentXAxis, mostRecentYaxis;

    public KeyInput(Mediator mediator) {
        this.mediator = mediator;
    }

    @Override
    public void keyPressed(KeyEvent e) {
        int keyCode = e.getKeyCode();
        if (keyCode == KeyEvent.VK_M) {
            GameSignals.MuteToggled.emit();
        }
        switch (mediator.getGame().gameState) {
            case Game:
                switch (keyCode)
                {
                    case KeyEvent.VK_W -> {
                        wPressed = true;
                        mostRecentYaxis = false;
                    }
                    case KeyEvent.VK_A -> {
                        aPressed = true;
                        mostRecentXAxis = false;
                    }
                    case KeyEvent.VK_S -> {
                        sPressed = true;
                        mostRecentYaxis = true;
                    }
                    case KeyEvent.VK_D -> {
                        dPressed = true;
                        mostRecentXAxis = true;
                    }
                    case KeyEvent.VK_P, KeyEvent.VK_ESCAPE -> mediator.getGame().gameState = Game.GAMESTATE.Paused;
                    case KeyEvent.VK_SPACE -> GameSignals.OpenShop.emit();
                }
                updateVelocity();
                break;
            case Shop:
                if (keyCode == KeyEvent.VK_SPACE) mediator.getGame().gameState = Game.GAMESTATE.Game;
                break;
            case Paused:
                if (keyCode == KeyEvent.VK_P || keyCode == KeyEvent.VK_ESCAPE) mediator.getGame().gameState = Game.GAMESTATE.Game;
                break;
            default:
                break;
        }
    }

    @Override
    public void keyReleased(KeyEvent e) {
        if (mediator.getGame().gameState == Game.GAMESTATE.Game) {
            int keyCode = e.getKeyCode();
            if (keyCode == KeyEvent.VK_W) {
                wPressed = false;
            } else if (keyCode == KeyEvent.VK_A) {
                aPressed = false;
            } else if (keyCode == KeyEvent.VK_S) {
                sPressed = false;
            } else if (keyCode == KeyEvent.VK_D) {
                dPressed = false;
            }
            updateVelocity();
        }
    }

    public void updateVelocity() {
        float velocityX = 0;
        float velocityY = 0;

        if (wPressed) velocityY = mediator.getUpgrade().getCurrentSpeed() * -1;
        if (sPressed) velocityY = mediator.getUpgrade().getCurrentSpeed();
        if (aPressed) velocityX = mediator.getUpgrade().getCurrentSpeed() * -1;
        if (dPressed) velocityX = mediator.getUpgrade().getCurrentSpeed();

        if (wPressed && sPressed) {
            if (!mostRecentYaxis) velocityY = mediator.getUpgrade().getCurrentSpeed() * -1;
        } else if (aPressed && dPressed) {
            if (!mostRecentXAxis) velocityX = mediator.getUpgrade().getCurrentSpeed() * -1;
        }

        mediator.getPlayer().setVelocityX(mediator.getPlayer().normalizeSpeed(velocityX, velocityY, mediator.getUpgrade().getCurrentSpeed())[0]);
        mediator.getPlayer().setVelocityY(mediator.getPlayer().normalizeSpeed(velocityX, velocityY, mediator.getUpgrade().getCurrentSpeed())[1]);
    }

    public void resetStates() {
        wPressed = aPressed = sPressed = dPressed = mostRecentXAxis = mostRecentYaxis = false;
        mediator.getPlayer().setVelocityX(0);
        mediator.getPlayer().setVelocityY(0);
    }

}

