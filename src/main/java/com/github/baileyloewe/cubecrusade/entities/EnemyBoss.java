package com.github.baileyloewe.cubecrusade.entities;

import com.github.baileyloewe.cubecrusade.Game;
import com.github.baileyloewe.cubecrusade.GameHandler;
import com.github.baileyloewe.cubecrusade.ID;
import com.github.baileyloewe.cubecrusade.entities.components.DisplayComponent;
import com.github.baileyloewe.cubecrusade.entities.components.MovementComponent;
import com.github.baileyloewe.cubecrusade.entities.components.PositionComponent;
import com.github.baileyloewe.cubecrusade.entities.components.SizeComponent;

/**
 * Creates a BossEnemy that extends the Enemy class
 */
public class EnemyBoss extends Enemy {
    private int timer = 80;
    private int timer2 = 50;
    private final long lifespan;

    public EnemyBoss(ID id, GameHandler gameHandler, PositionComponent positionComponent, SizeComponent sizeComponent, MovementComponent movementComponent, DisplayComponent displayComponent) {
        super(id, gameHandler, positionComponent, sizeComponent, movementComponent, displayComponent);
        lifespan = System.currentTimeMillis() + 8000;
    }
    public static EnemyBoss create(ID id, GameHandler gameHandler) {
        PositionComponent positionComponent = new PositionComponent(Game.WIDTH / 2.f - 32, -50);
        SizeComponent sizeComponent = new SizeComponent(128, 128);
        MovementComponent movementComponent = new MovementComponent(positionComponent, sizeComponent, 2, 1, 2);
        DisplayComponent displayComponent = new DisplayComponent(sizeComponent, 0, 128);
        return new EnemyBoss(id, gameHandler, positionComponent, sizeComponent, movementComponent, displayComponent);
    }

    @Override
    public void tick() {
        if (System.currentTimeMillis() > lifespan) {
            gameHandler.clearEnemies();
        }
        movementComponent.tick();

        if (timer <= 0) {
            movementComponent.yVelocity = 0;
            timer2--;
        } else {
            timer--;
        }
        if (timer2 <= 0) {
            if (movementComponent.xVelocity == 0)movementComponent.xVelocity = 2;
            int spawn = r.nextInt(10);
            if (spawn == 0) {
                gameHandler.addEntity(EnemyBossBullet.create(
                    ID.BossEnemyBullet,
                    gameHandler,
                    positionComponent.xPos + ((float) sizeComponent.width / 2) - 8,
                    positionComponent.yPos + ((float) sizeComponent.height / 2) - 8));
            }
        }


        if (positionComponent.xPos <= 100 || positionComponent.xPos >= Game.WIDTH - sizeComponent.width - 50) {
            movementComponent.xVelocity *= -1;
        }

        if (positionComponent.yPos <= -200 || positionComponent.yPos >= Game.HEIGHT - sizeComponent.height + 150) {
            movementComponent.yVelocity *= -1;
        }
    }
}

