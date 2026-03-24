package com.main;

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
            mediator.getMenuAudio().reverseMuteState();
            mediator.getGameAudio().reverseMuteState();
        }
        switch (mediator.getGame().gameState) {
            case Game:
                switch (keyCode) // Begin nested switch in the case game
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
                    case KeyEvent.VK_P, KeyEvent.VK_ESCAPE -> mediator.getGame().gameState = Game.STATE.Paused;
                    case KeyEvent.VK_SPACE -> mediator.getGame().gameState = Game.STATE.Shop;

                    //Temporary testing tool
                    case KeyEvent.VK_R -> mediator.getUpgrade().setCurrentHealth(mediator.getUpgrade().getCurrentMaxHealth());
                } // End nested switch in the case game
                updateVelocity();
                break;
            case Shop:
                if (keyCode == KeyEvent.VK_SPACE) mediator.getGame().gameState = Game.STATE.Game;
                break;
            case Paused:
                if (keyCode == KeyEvent.VK_P || keyCode == KeyEvent.VK_ESCAPE) mediator.getGame().gameState = Game.STATE.Game;
                break;
            default:
                break;
        }
    }

    @Override
    public void keyReleased(KeyEvent e) {
        if (mediator.getGame().gameState == Game.STATE.Game) {
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

    @Override
    public void keyTyped(KeyEvent e) {
        // This method is not used
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

