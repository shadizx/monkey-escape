package com.monkeyescape.entity.movingentity;

import com.monkeyescape.main.Game;
import com.monkeyescape.main.KeyHandler;
import com.monkeyescape.main.State;

import java.awt.Rectangle;

/**
 * Represents a monkey
 *
 * @author Shadi Zoldjalali
 * @version 12/06/2022
 */
public class Monkey extends MovingEntity {
    public static boolean inLionPit = false;
    public static int lionPitInvincibility;
    int timeInLionPit;

    boolean isMoving = false;

    /**
     * Creates a Monkey
     *
     * @param game A <code>Game</code> to refer to
     * @param keyHandler a <code>KeyHandler</code> for handling key inputs
     */
    public Monkey(Game game, KeyHandler keyHandler) {
        super(game, keyHandler);
        type = "monkey";
        loadImage();

        this.type = "monkey";

        areaX = 8;
        areaY = 16;
        //set entity area to be smaller than tile so that it can fit in 1 tile spaces
        area = new Rectangle(areaX, areaY, 32, 32);

        // start to wherever the cage is
        this.setXCoordinate(game.cagePos.x * game.tileSize);
        this.setYCoordinate(game.cagePos.y * game.tileSize);

        speed = 4;
    }

    /**
     * Updates monkey information
     */
    public void update() {
        //No movement if game is not in play
        if(game.state.getGameState() != State.GameState.PLAY) return;
        
        if (Monkey.inLionPit){
            direction = "jump";
            isMoving = true;
        } else if(keyHandler.isPressedUp() || keyHandler.isPressedRight() || keyHandler.isPressedDown() || keyHandler.isPressedLeft()) {
            if (keyHandler.isPressedUp()) {
                direction = "up";
                isMoving = true;

            } else if (keyHandler.isPressedRight()) {
                direction = "right";
                isMoving = true;

            } else if (keyHandler.isPressedDown()) {
                direction = "down";
                isMoving = true;

            } else if (keyHandler.isPressedLeft()) {
                direction = "left";
                isMoving = true;
            } 
        }

        collided = false;
        game.collisionChecker.checkTile(this);
        game.collisionChecker.checkFixedEntity(this);
        game.collisionChecker.checkZookeeper(this, game.zookeepers);

        if (!collided && !Monkey.inLionPit && isMoving) {
            movement.move();
        }
        isMoving = false;

        checkLionPit();

        // after 15 game "ticks" we want to switch the image (version 1 or version 2)
        // version we are showing to make it look more animated
        if (++drawImageDelay > 15) {
            drawImageVersion = drawImageVersion == 1 ? 2 : 1;
            drawImageDelay = 0;
        }
    }

    /**
     * Checks if the monkey is in the lion-pit and
     * holds the logic for <code>Monkey</code> - <code>LionPit</code> interaction
     */
    private void checkLionPit() {
        //Keeps track of how long the monkey has been in the lion pit
        if(Monkey.inLionPit) {
            timeInLionPit++;

            //The monkey escapes the pit after 2 seconds
            if(timeInLionPit == 120) {
                timeInLionPit = 0;
                Monkey.inLionPit = false;
                Monkey.lionPitInvincibility = 60; //Creates a 1-second timer that the monkey can't get caught in the pit again for
            }
        }
        else if(Monkey.lionPitInvincibility > 0) {
            Monkey.lionPitInvincibility--;
        }

        //Switches back to normal direction after in lion pit even if no keys are being pressed
        if(!Monkey.inLionPit && direction.equals("jump")) {
            direction = "down";
        }
    }
}
