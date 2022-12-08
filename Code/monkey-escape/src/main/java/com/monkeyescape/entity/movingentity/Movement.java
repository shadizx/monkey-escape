package com.monkeyescape.entity.movingentity;

/**
 * Class that performs the movement for a Moving Entity
 *
 * @author Henry Ruckman-Utting
 * @version 12/03/2022
 */
public class Movement {
    private MovingEntity movingEntity;

    Movement(MovingEntity movingEntity){
        this.movingEntity = movingEntity;
    }

    /**
     * Called by the movingEntity to move
     */
    protected void move(){
        switch (movingEntity.direction) {
            case "up":
                movingEntity.setYCoordinate(movingEntity.getYCoordinate() - movingEntity.speed);
                break;
            case "right":
                movingEntity.setXCoordinate(movingEntity.getXCoordinate() + movingEntity.speed);
                break;
            case "down":
                movingEntity.setYCoordinate(movingEntity.getYCoordinate() + movingEntity.speed);
                break;
            case "left":
                movingEntity.setXCoordinate(movingEntity.getXCoordinate() - movingEntity.speed);
                break;
        }
    }
}

