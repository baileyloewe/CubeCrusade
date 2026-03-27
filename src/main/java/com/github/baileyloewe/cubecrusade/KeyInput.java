package com.github.baileyloewe.cubecrusade;

import com.github.baileyloewe.cubecrusade.entities.Player;
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
            case GAME:
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
                    case KeyEvent.VK_P, KeyEvent.VK_ESCAPE -> serviceLocator.getGame().gameState = GameState.PAUSED;
                    case KeyEvent.VK_SPACE -> GameSignals.OpenShop.emit();
                }
                updateVelocity();
                break;
            case SHOP:
                if (keyCode == KeyEvent.VK_SPACE) serviceLocator.getGame().gameState = GameState.GAME;
                break;
            case PAUSED:
                if (keyCode == KeyEvent.VK_P || keyCode == KeyEvent.VK_ESCAPE) serviceLocator.getGame().gameState = GameState.GAME;
                break;
            default:
                break;
        }
    }

    @Override
    public void keyReleased(KeyEvent e) {
        if (serviceLocator.getGame().gameState == GameState.GAME) {
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

        Upgrade upgrade = serviceLocator.getUpgrade();
        Player player = serviceLocator.getPlayer();

        if (wPressed) velocityY = upgrade.getCurrentSpeed() * -1;
        if (sPressed) velocityY = upgrade.getCurrentSpeed();
        if (aPressed) velocityX = upgrade.getCurrentSpeed() * -1;
        if (dPressed) velocityX = upgrade.getCurrentSpeed();

        if (wPressed && sPressed) {
            if (!mostRecentYaxis) velocityY = upgrade.getCurrentSpeed() * -1;
        } else if (aPressed && dPressed) {
            if (!mostRecentXAxis) velocityX = upgrade.getCurrentSpeed() * -1;
        }

        player.setVelocityX(player.normalizeSpeed(velocityX, velocityY, upgrade.getCurrentSpeed())[0]);
        player.setVelocityY(player.normalizeSpeed(velocityX, velocityY, upgrade.getCurrentSpeed())[1]);
    }

    public void resetStates() {
        wPressed = aPressed = sPressed = dPressed = mostRecentXAxis = mostRecentYaxis = false;
        serviceLocator.getPlayer().setVelocityX(0);
        serviceLocator.getPlayer().setVelocityY(0);
    }

}

