package com.github.baileyloewe.cubecrusade.entities;

import com.github.baileyloewe.cubecrusade.Game;
import com.github.baileyloewe.cubecrusade.GameHandler;
import com.github.baileyloewe.cubecrusade.ID;
import com.github.baileyloewe.cubecrusade.Vector2D;
import com.github.baileyloewe.cubecrusade.entities.components.DisplayComponent;
import com.github.baileyloewe.cubecrusade.entities.components.MovementComponent;
import com.github.baileyloewe.cubecrusade.entities.components.PositionComponent;
import com.github.baileyloewe.cubecrusade.entities.components.SizeComponent;

/**
 * Creates a BossEnemy that extends the Enemy class
 */
public class BossEnemy extends Enemy {
    private final long lifespan;
    private long spawnTimer;

    public BossEnemy(ID id, GameHandler gameHandler, PositionComponent positionComponent, SizeComponent sizeComponent, MovementComponent movementComponent, DisplayComponent displayComponent) {
        super(id, gameHandler, positionComponent, sizeComponent, movementComponent, displayComponent);
        lifespan = System.currentTimeMillis() + 8000;
        spawnTimer = System.currentTimeMillis() + 100;
    }
    public static BossEnemy create(ID id, GameHandler gameHandler) {
        PositionComponent positionComponent = new PositionComponent(new Vector2D(Game.WIDTH / 2.f - 32, -50));
        SizeComponent sizeComponent = new SizeComponent(128, 128);
        MovementComponent movementComponent = new MovementComponent(positionComponent, sizeComponent, new Vector2D(2, 1), 2);
        DisplayComponent displayComponent = new DisplayComponent(positionComponent, sizeComponent, 0, 128);
        return new BossEnemy(id, gameHandler, positionComponent, sizeComponent, movementComponent, displayComponent);
    }

    @Override
    public void tick() {
        long currentTime = System.currentTimeMillis();
        if (currentTime > lifespan) {
            gameHandler.clearEnemies();
        }
        movementComponent.tick();

        if (positionComponent.position.y >= sizeComponent.width + 100) {
            movementComponent.direction.y = 0;
        }

        if (positionComponent.position.x <= sizeComponent.width + 50 || positionComponent.position.x >= Game.WIDTH - sizeComponent.width - 50) {
            movementComponent.direction.x *= -1;
        }

        if (currentTime > spawnTimer) {
            BossBullet.create(
                    ID.BossEnemyBullet,
                    gameHandler,
                    new Vector2D(positionComponent.position.x + ((float) sizeComponent.width / 2) - 8,
                    positionComponent.position.y + ((float) sizeComponent.height / 2) - 8));
            spawnTimer = currentTime + 100;
        }

        if (positionComponent.position.x <= 100 || positionComponent.position.x >= Game.WIDTH - sizeComponent.width - 50) {
            movementComponent.direction.x *= -1;
        }

        if (positionComponent.position.y <= -200 || positionComponent.position.y >= Game.HEIGHT - sizeComponent.height + 150) {
            movementComponent.direction.y *= -1;
        }
    }
}

