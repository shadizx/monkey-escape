package com.monkeyescape.entity.movingentity;

import com.monkeyescape.main.KeyHandler;
import com.monkeyescape.main.Panel;
import com.monkeyescape.main.State;

import java.awt.*;

/**
 * Represents a monkey
 * @author Shadi Zoldjalali
 * @version 10/30/2022
 */
public class Monkey extends MovingEntity {
    //Well monkey falls into lion pit
    public static boolean inLionPit = false;
    public static int lionPitInvincibility;
    int timeInLionPit;
    
    /**
     * Creates a Monkey
     * @param panel A <code>Panel</code>> to refer to
     * @param kh a <code>KeyHandler</code> for handling key inputs
     */
    public Monkey(Panel panel, KeyHandler kh) {

        super(panel, kh);
        type = "monkey";
        loadImage();


        this.type = "monkey";

        areaX = 8;
        areaY = 16;
        //set entity area to be smaller than tile so that it can fit in 1 tile spaces
        area = new Rectangle(areaX, areaY, 32, 32);

        // start to wherever the cage is
        x = panel.startCol * panel.tileSize;
        y = panel.startRow * panel.tileSize;

        speed = 4;
    }

    /**
     * Updates location of moving entity based on key input
     * and checks for any possible collisions with other entities
     */
    public void update() {
        //No movement if game is not in play
        if(panel.state.getGameState() != State.GameState.PLAY) return;
        
        if(kh.isPressedUp() || kh.isPressedRight() || kh.isPressedDown() || kh.isPressedLeft()) {
            if (Monkey.inLionPit){
                direction = "jump";
            } else if (kh.isPressedUp()) {
                direction = "up";

            } else if (kh.isPressedRight()) {
                direction = "right";

            } else if (kh.isPressedDown()) {
                direction = "down";

            } else if (kh.isPressedLeft()) {
                direction = "left";
            } 

            collided = false;
            panel.collisionChecker.checkTile(this);
            panel.collisionChecker.checkFixedEntity(this);
            panel.collisionChecker.checkZookeeper(this,panel.zookeepers);

            if (!collided && !Monkey.inLionPit) {
                switch (direction) {
                    case "up":
                        y -= speed;
                        break;
                    case "right":
                        x += speed;
                        break;
                    case "down":
                        y += speed;
                        break;
                    case "left":
                        x -= speed;
                        break;
                }
            }
        }

        //Keeps track of how long the monkey has been in the lion pit
        if(Monkey.inLionPit){
            timeInLionPit++;

            //The monkey escapes the pit after 2 seconds
            if(timeInLionPit == 120){
                timeInLionPit = 0;
                Monkey.inLionPit = false;
                Monkey.lionPitInvincibility = 60; //Creates a 1 second timer that the monkey can't get caught in the pit again for
            }
        }
        else if(Monkey.lionPitInvincibility > 0){
            Monkey.lionPitInvincibility--;
        }

        //Switches back to normal direction after in lion pit even if no keys are being pressed
        if(!Monkey.inLionPit && direction.equals("jump")){
            direction = "down";
        }

        // after 15 game "ticks" we want to switch the image (version 1 or version 2)
        // version we are showing to make it look more animated
        if (++drawImageDelay > 15) {
            drawImageVersion = drawImageVersion == 1 ? 2 : 1;
            drawImageDelay = 0;
        }
    }
}
