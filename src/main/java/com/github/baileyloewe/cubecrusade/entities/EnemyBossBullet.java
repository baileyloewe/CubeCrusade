package com.github.baileyloewe.cubecrusade.entities;

import com.github.baileyloewe.cubecrusade.Game;
import com.github.baileyloewe.cubecrusade.GameHandler;
import com.github.baileyloewe.cubecrusade.ID;
import com.github.baileyloewe.cubecrusade.entities.components.DisplayComponent;
import com.github.baileyloewe.cubecrusade.entities.components.MovementComponent;
import com.github.baileyloewe.cubecrusade.entities.components.PositionComponent;
import com.github.baileyloewe.cubecrusade.entities.components.SizeComponent;

/**
 * Creates a BossEnemyBullet that extends the Enemy class
 */
public class EnemyBossBullet extends Enemy {

    public EnemyBossBullet(ID id, GameHandler gameHandler, PositionComponent positionComponent, SizeComponent sizeComponent, MovementComponent movementComponent, DisplayComponent displayComponent) {
        super(id, gameHandler, positionComponent, sizeComponent, movementComponent, displayComponent);
    }

    public static EnemyBossBullet create(ID id, GameHandler gameHandler, float xPos, float yPos) {
        PositionComponent positionComponent = new PositionComponent(xPos, yPos);
        SizeComponent sizeComponent = new SizeComponent(32, 32);
        MovementComponent movementComponent = new MovementComponent(positionComponent, sizeComponent, 2 * Math.random() < .5 ? 1 : -1, 2, 2);
        int[] rowCol = getImagePos();
        DisplayComponent displayComponent = new DisplayComponent(sizeComponent, rowCol[0], rowCol[1]);
        return new EnemyBossBullet(id, gameHandler, positionComponent, sizeComponent, movementComponent, displayComponent);
    }

    @Override
    public void tick() {
        movementComponent.tick();

        if (positionComponent.yPos >= Game.HEIGHT) {
            gameHandler.removeEntity(this);
        }
    }

    public static int[] getImagePos() {
        int randomNum = (int) (Math.random() * 9) + 1;
        return switch (randomNum) {
            case 1 -> new int[]{192, 0};
            case 2 -> new int[]{224, 0};
            case 3 -> new int[]{64, 32};
            case 4 -> new int[]{96, 32};
            case 5 -> new int[]{128, 32};
            case 6 -> new int[]{160, 32};
            case 7 -> new int[]{192, 32};
            case 8 -> new int[]{224, 32};
            default -> new int[]{192, 0};
        };
    }
}
