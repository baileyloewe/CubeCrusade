package com.github.baileyloewe.cubecrusade.entities.components;

import com.github.baileyloewe.cubecrusade.Game;
import com.github.baileyloewe.cubecrusade.Vector2D;
import com.github.baileyloewe.cubecrusade.signals.Signals;

public class MovementComponent {
    public Vector2D direction;
    public float maxSpeed;
    public final PositionComponent positionComponent;
    public final SizeComponent sizeComponent;
    public final Signals.EventSignal xAxisCollision = new Signals.EventSignal();
    public final Signals.EventSignal yAxisCollision = new Signals.EventSignal();

    public MovementComponent(PositionComponent positionComponent, SizeComponent sizeComponent, Vector2D direction, float maxSpeed) {
        this.positionComponent = positionComponent;
        this.sizeComponent = sizeComponent;
        this.direction = direction;
        this.maxSpeed = maxSpeed;
    }

    public void tick() {
        direction = direction.normalize();
        Vector2D velocity = direction.scale(maxSpeed);
        positionComponent.position = positionComponent.position.add(velocity);


        if (positionComponent.position.x <= 0 || positionComponent.position.x >= Game.WIDTH - sizeComponent.width) {
            xAxisCollision.emit();
        }

        if (positionComponent.position.y <= 0 || positionComponent.position.y >= Game.HEIGHT - sizeComponent.height) {
            yAxisCollision.emit();
        }
    }

    public void bounceX() {
        direction.x *= -1;
    }

    public void bounceY() {
        direction.y *= - 1;
    }

}
