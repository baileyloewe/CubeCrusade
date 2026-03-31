package com.github.baileyloewe.cubecrusade.entities.components;

import com.github.baileyloewe.cubecrusade.Game;
import com.github.baileyloewe.cubecrusade.Sprite;

import java.awt.*;
import java.awt.image.BufferedImage;

public class DisplayComponent {
    private BufferedImage image;
    private final SizeComponent sizeComponent;
    private final PositionComponent positionComponent;
    private Color color;
    private boolean useSprite;

    public DisplayComponent(PositionComponent positionComponent, SizeComponent sizeComponent, int spriteXPos, int spriteYPos) {
        this.sizeComponent = sizeComponent;
        this.positionComponent = positionComponent;
        Sprite sprite = new Sprite(Game.spriteSheet);
        image = sprite.grabSprite(spriteXPos, spriteYPos, sizeComponent.width, sizeComponent.height);
        useSprite = true;
    }

    // For entities with no sprite sheet
    public DisplayComponent(PositionComponent positionComponent, SizeComponent sizeComponent, Color color) {
        this.sizeComponent = sizeComponent;
        this.positionComponent = positionComponent;
        this.color = color;
    }

    public void render(Graphics g) {
        if (useSprite) {
            g.drawImage(image, (int) positionComponent.position.x, (int) positionComponent.position.y, null);
        } else {
            Color prevColor = g.getColor();
            g.setColor(color);
            g.drawRect((int) positionComponent.position.x, (int) positionComponent.position.y, sizeComponent.width, sizeComponent.height);
            g.fillRect((int) positionComponent.position.x, (int) positionComponent.position.y, sizeComponent.width, sizeComponent.height);
            g.setColor(prevColor);
        }

    }
}
