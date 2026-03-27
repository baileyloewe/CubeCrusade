package com.github.baileyloewe.cubecrusade.entities.components;

import com.github.baileyloewe.cubecrusade.Game;
import com.github.baileyloewe.cubecrusade.Sprite;

import java.awt.*;
import java.awt.image.BufferedImage;

public class DisplayComponent {
    private BufferedImage image;
    private final SizeComponent sizeComponent;
    private PositionComponent positionComponent;
    private Color color;

    public DisplayComponent(SizeComponent sizeComponent, int spriteXPos, int spriteYPos) {
        this.sizeComponent = sizeComponent;
        Sprite sprite = new Sprite(Game.spriteSheet);
        image = sprite.grabSprite(spriteXPos, spriteYPos, sizeComponent.width, sizeComponent.height);
    }

    // For entities with no sprite sheet
    public DisplayComponent(SizeComponent sizeComponent, PositionComponent positionComponent, Color color) {
        this.sizeComponent = sizeComponent;
        this.positionComponent = positionComponent;
        this.color = color;
    }

    public void render(Graphics g) {
        Color prevColor = g.getColor();
        if (image != null) {
            g.drawImage(image, sizeComponent.width, sizeComponent.height, null);
        }
        else if (color != null) {
            g.setColor(color);
            g.drawRect((int) positionComponent.xPos, (int) positionComponent.yPos, sizeComponent.width, sizeComponent.height);
            g.fillRect((int) positionComponent.xPos, (int) positionComponent.yPos, sizeComponent.width, sizeComponent.height);
        }
        g.setColor(prevColor);
    }
}
