package com.github.baileyloewe.cubecrusade.entities.enemies;

import com.github.baileyloewe.cubecrusade.Game;
import com.github.baileyloewe.cubecrusade.ID;
import com.github.baileyloewe.cubecrusade.Vector2D;
import com.github.baileyloewe.cubecrusade.entities.BossBullet;
import com.github.baileyloewe.cubecrusade.entities.Entity;
import com.github.baileyloewe.cubecrusade.entities.components.DisplayComponent;
import com.github.baileyloewe.cubecrusade.entities.components.MovementComponent;
import com.github.baileyloewe.cubecrusade.entities.components.PositionComponent;
import com.github.baileyloewe.cubecrusade.entities.components.SizeComponent;
import com.github.baileyloewe.cubecrusade.signals.GameSignals;

import java.util.concurrent.TimeUnit;


public class BossEnemy extends Entity {
    private final long lifespan;
    private long spawnTimer;
    private boolean xUpdated;

    public BossEnemy(ID id, PositionComponent positionComponent, SizeComponent sizeComponent, DisplayComponent displayComponent, MovementComponent movementComponent) {
        super(id, positionComponent, sizeComponent, displayComponent, movementComponent);
        lifespan = System.nanoTime() + TimeUnit.SECONDS.toNanos(10L);
        spawnTimer = System.nanoTime() + TimeUnit.MILLISECONDS.toNanos(500);
    }

    public static BossEnemy create(ID id) {
        PositionComponent positionComponent = new PositionComponent(new Vector2D(Game.WIDTH / 2.f - 64, -128));
        SizeComponent sizeComponent = new SizeComponent(128, 128);
        MovementComponent movementComponent = new MovementComponent(positionComponent, sizeComponent, new Vector2D(0, 1), 2);
        DisplayComponent displayComponent = new DisplayComponent(positionComponent, sizeComponent, 0, 128);
        return new BossEnemy(id, positionComponent, sizeComponent, displayComponent, movementComponent);
    }

    @Override
    public void tick() {
        long currentTime = System.nanoTime();
        if (currentTime > lifespan) {
            GameSignals.clearEnemies.emit();
        }
        movementComponent.tick();

        if (positionComponent.position.y >= 20) {
            movementComponent.direction.y = 0;

            if (!xUpdated) {
                movementComponent.direction.x = 2;
                xUpdated = true;
            }
        }

        if (positionComponent.position.x <= sizeComponent.width + 20 || positionComponent.position.x >= Game.WIDTH - sizeComponent.width - 20) {
            movementComponent.direction.x *= -1;
        }

        if (currentTime > spawnTimer) {
            BossBullet.create(
                    ID.BossEnemyBullet,
                    new Vector2D(positionComponent.position.x + ((float) sizeComponent.width / 2) - 8,
                            positionComponent.position.y + ((float) sizeComponent.height / 2) - 8));
            spawnTimer = currentTime + TimeUnit.MILLISECONDS.toNanos(50);
        }
    }
}

