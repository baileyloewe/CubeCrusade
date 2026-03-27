package com.github.baileyloewe.cubecrusade.entities;

import com.github.baileyloewe.cubecrusade.GameHandler;
import com.github.baileyloewe.cubecrusade.ID;
import com.github.baileyloewe.cubecrusade.entities.components.DisplayComponent;
import com.github.baileyloewe.cubecrusade.entities.components.MovementComponent;
import com.github.baileyloewe.cubecrusade.entities.components.PositionComponent;
import com.github.baileyloewe.cubecrusade.entities.components.SizeComponent;

/**
 * Creates a SmartEnemy that extends the Enemy class
 */
public class EnemySmart extends Enemy {

    private final Player player;
    private static final int initialXVelocity = Math.random() < .5 ? 1 : -1;
    private static final int initialYVelocity = Math.random() < .5 ? 1 : -1;


    public EnemySmart(ID id, GameHandler gameHandler, Player player, PositionComponent positionComponent, SizeComponent sizeComponent, MovementComponent movementComponent, DisplayComponent displayComponent) {
        super(id, gameHandler, positionComponent, sizeComponent, movementComponent, displayComponent);
        this.player = player;
    }

    public static EnemySmart create(ID id, GameHandler gameHandler, Player player, float xPos, float yPos) {
        PositionComponent positionComponent = new PositionComponent(xPos, yPos);
        SizeComponent sizeComponent = new SizeComponent(32, 32);
        MovementComponent movementComponent = new MovementComponent(positionComponent, sizeComponent, initialXVelocity, initialYVelocity, 1);
        DisplayComponent displayComponent = new DisplayComponent(sizeComponent, 160, 0);
        return new EnemySmart(id, gameHandler, player, positionComponent, sizeComponent, movementComponent, displayComponent);
    }

    /**
     * Enables to the smart to track the player by pulling the player location
     * Calculates the different in position and the angle, then sets velocity based on these values
     */
    @Override
    public void tick() {
        movementComponent.tick();

        float targetPlayerX = player.positionComponent.xPos + 8;
        float targetPlayerY = player.positionComponent.yPos + 8;
        float differenceX = positionComponent.xPos - targetPlayerX;
        float differenceY = positionComponent.yPos - targetPlayerY;
        float angle = (float) Math.atan2(differenceX, differenceY);

        movementComponent.xVelocity = ((float) -Math.sin(angle) + r.nextFloat(-.25f, .25f));
        movementComponent.yVelocity = ((float) -Math.cos(angle) + r.nextFloat(-.25f, .25f));
    }
}
