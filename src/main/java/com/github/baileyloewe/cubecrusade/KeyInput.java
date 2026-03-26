package com.github.baileyloewe.cubecrusade;

import com.github.baileyloewe.cubecrusade.signals.GameSignals;

import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;

public class KeyInput extends KeyAdapter {
    private final ServiceLocator serviceLocator;
    private boolean wPressed, aPressed, sPressed, dPressed;
    private boolean mostRecentXAxis, mostRecentYaxis;

    public KeyInput(ServiceLocator serviceLocator) {
        this.serviceLocator = serviceLocator;
    }

    @Override
    public void keyPressed(KeyEvent e) {
        int keyCode = e.getKeyCode();
        if (keyCode == KeyEvent.VK_M) {
            GameSignals.MuteToggled.emit();
        }
        switch (serviceLocator.getGame().gameState) {
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
                    case KeyEvent.VK_P, KeyEvent.VK_ESCAPE -> serviceLocator.getGame().gameState = Game.GAMESTATE.Paused;
                    case KeyEvent.VK_SPACE -> GameSignals.OpenShop.emit();
                }
                updateVelocity();
                break;
            case Shop:
                if (keyCode == KeyEvent.VK_SPACE) serviceLocator.getGame().gameState = Game.GAMESTATE.Game;
                break;
            case Paused:
                if (keyCode == KeyEvent.VK_P || keyCode == KeyEvent.VK_ESCAPE) serviceLocator.getGame().gameState = Game.GAMESTATE.Game;
                break;
            default:
                break;
        }
    }

    @Override
    public void keyReleased(KeyEvent e) {
        if (serviceLocator.getGame().gameState == Game.GAMESTATE.Game) {
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

        if (wPressed) velocityY = serviceLocator.getUpgrade().getCurrentSpeed() * -1;
        if (sPressed) velocityY = serviceLocator.getUpgrade().getCurrentSpeed();
        if (aPressed) velocityX = serviceLocator.getUpgrade().getCurrentSpeed() * -1;
        if (dPressed) velocityX = serviceLocator.getUpgrade().getCurrentSpeed();

        if (wPressed && sPressed) {
            if (!mostRecentYaxis) velocityY = serviceLocator.getUpgrade().getCurrentSpeed() * -1;
        } else if (aPressed && dPressed) {
            if (!mostRecentXAxis) velocityX = serviceLocator.getUpgrade().getCurrentSpeed() * -1;
        }

        serviceLocator.getPlayer().setVelocityX(serviceLocator.getPlayer().normalizeSpeed(velocityX, velocityY, serviceLocator.getUpgrade().getCurrentSpeed())[0]);
        serviceLocator.getPlayer().setVelocityY(serviceLocator.getPlayer().normalizeSpeed(velocityX, velocityY, serviceLocator.getUpgrade().getCurrentSpeed())[1]);
    }

    public void resetStates() {
        wPressed = aPressed = sPressed = dPressed = mostRecentXAxis = mostRecentYaxis = false;
        serviceLocator.getPlayer().setVelocityX(0);
        serviceLocator.getPlayer().setVelocityY(0);
    }

}

