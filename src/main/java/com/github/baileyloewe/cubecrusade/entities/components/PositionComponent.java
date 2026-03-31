package com.github.baileyloewe.cubecrusade.entities.components;

import com.github.baileyloewe.cubecrusade.Game;
import com.github.baileyloewe.cubecrusade.Vector2D;

public class PositionComponent {
    public Vector2D position;

    public PositionComponent(Vector2D position) {
        this.position = position;
    }

    public void clamp(SizeComponent sizeComponent) {
        position.x = Math.clamp(position.x, 0, Game.WIDTH - sizeComponent.width);
        position.y = Math.clamp(position.y, 0, Game.HEIGHT - sizeComponent.height);
    }
}
