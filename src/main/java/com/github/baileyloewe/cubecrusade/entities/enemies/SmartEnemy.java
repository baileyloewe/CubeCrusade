package com.github.baileyloewe.cubecrusade.entities.enemies;

import com.github.baileyloewe.cubecrusade.ID;
import com.github.baileyloewe.cubecrusade.Vector2D;
import com.github.baileyloewe.cubecrusade.entities.Player;
import com.github.baileyloewe.cubecrusade.entities.components.DisplayComponent;
import com.github.baileyloewe.cubecrusade.entities.components.MovementComponent;
import com.github.baileyloewe.cubecrusade.entities.components.PositionComponent;
import com.github.baileyloewe.cubecrusade.entities.components.SizeComponent;

import java.util.Random;

public class SmartEnemy extends Enemy {

    private final Player player;
    private static final Random RNG = new Random();

    public SmartEnemy(ID id, Player player, PositionComponent positionComponent, SizeComponent sizeComponent, DisplayComponent displayComponent, MovementComponent movementComponent) {
        super(id, positionComponent, sizeComponent, displayComponent, movementComponent);
        this.player = player;
    }

    public static SmartEnemy create(ID id, Player player, Vector2D position) {
        PositionComponent positionComponent = new PositionComponent(position);
        SizeComponent sizeComponent = new SizeComponent(32, 32);

        int initialXVelocity = RNG.nextBoolean() ? 1 : -1;
        int initialYVelocity = RNG.nextBoolean() ? 1 : -1;
        MovementComponent movementComponent = new MovementComponent(positionComponent, sizeComponent, new Vector2D(initialXVelocity, initialYVelocity), 1);
        DisplayComponent displayComponent = new DisplayComponent(positionComponent, sizeComponent, 160, 0);
        return new SmartEnemy(id, player, positionComponent, sizeComponent, displayComponent, movementComponent);
    }

    @Override
    public void tick() {
        movementComponent.direction = player.getPositionComponent().position.subtract(this.positionComponent.position);
        movementComponent.tick();
    }
}
