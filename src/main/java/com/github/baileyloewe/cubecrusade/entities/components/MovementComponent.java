package com.github.baileyloewe.cubecrusade.entities.components;

import com.github.baileyloewe.cubecrusade.Game;
import com.github.baileyloewe.cubecrusade.signals.Signals;

public class MovementComponent {
    public float xVelocity;
    public float yVelocity;
    public float maxSeed;
    private final PositionComponent positionComponent;
    private final SizeComponent sizeComponent;
    public final Signals.EventSignal xAxisCollision = new Signals.EventSignal();
    public final Signals.EventSignal yAxisCollision = new Signals.EventSignal();

    public MovementComponent(PositionComponent positionComponent, SizeComponent sizeComponent, float xVelocity, float yVelocity, float maxSpeed) {
        this.positionComponent = positionComponent;
        this.sizeComponent = sizeComponent;
        this.xVelocity = xVelocity;
        this.yVelocity = yVelocity;
        this.maxSeed = maxSpeed;
    }

    public void tick() {
        move();
    }

    private void move() {
        normalizeSpeed();
        positionComponent.xPos += xVelocity;
        positionComponent.yPos += yVelocity;
        clampPosition();
    }

    public void clampPosition() {
        if (positionComponent.xPos <= 0 || positionComponent.xPos >= Game.WIDTH - sizeComponent.width) {
            xVelocity *= -1;
            xAxisCollision.emit();
        }

        if (positionComponent.yPos <= 0 || positionComponent.yPos >= Game.HEIGHT - sizeComponent.height) {
            yVelocity *= - 1;
            yAxisCollision.emit();
        }
    }

    public void normalizeSpeed()
    {
        if (xVelocity != 0 && yVelocity != 0)
        {
            float magnitude = (float) Math.sqrt(xVelocity * xVelocity + yVelocity * yVelocity);
            xVelocity = (xVelocity / magnitude) * Math.abs(maxSeed);
            yVelocity = (yVelocity / magnitude) * Math.abs(maxSeed);
        }
    }
}
