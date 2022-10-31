package com.monkeyescape.entity.movingentity;

import com.monkeyescape.entity.Position;
import com.monkeyescape.main.KeyHandler;
import com.monkeyescape.main.Panel;

import java.awt.*;

/**
 * Represents the zookeeper
 * @author Henry Ruckman-Utting
 * @version 10/30/2022
 */
public class Zookeeper extends MovingEntity {
    Monkey monkey;
    
    /**
     * Initializes a zookeeper with random position and connected to the monkey
     * @param panel A <code>Panel</code>> to refer to
     * @param kh a <code>KeyHandler</code> for handling key inputs
     * @param monkey a <code>Monkey</code> to refer to
     */
    public Zookeeper(Panel panel, KeyHandler kh, Monkey monkey) {
        super(panel, kh);
        type = "zookeeper";
        speed = 1;
        this.monkey = monkey;
        loadImage();

        // random starting position
        Position randomPosition = super.createRandomPosition(panel);
        x = randomPosition.x;
        y = randomPosition.y;

        //Checks if zookeeper is too close off of spawn
        while(tilesToMonkey() < 5){
            randomPosition = super.createRandomPosition(panel);
            x = randomPosition.x;
            y = randomPosition.y;
        }

        areaX = 8;
        areaY = 16;

        //set entity area to be smaller than tile so that it can fit in 1 tile spaces
        area = new Rectangle(areaX, areaY, 32, 32);
        speed = 1;

        direction = "down";
        collided = false;
    }

    /**
     * Moves the zookeeper towards the monkey
     */
    public void update(){ //Can create better pashfinding once walls are implemented
        if(kh.isPressedDown() || kh.isPressedUp() || kh.isPressedLeft() || kh.isPressedRight()){ //Only moves if player is moving
            if(Math.abs(monkey.x - x) > Math.abs(monkey.y - y)){ //If the monkey is further away on the x axis than y axis
                //Move one tile towards monkey on x axis
                if(monkey.x - x > 0){ //Check which direction to move along x axis
                    direction = "right";
                }
                else {
                    direction = "left";
                }
            }
            else{ //Monkey is further away on the y axis than x axis
                //Move one tile towards monkey on y axis
                if(monkey.y - y > 0){ //Check which direction to move along y axis
                    direction = "down";
                }
                else {
                    direction = "up";
                }
            }
            collided = false;
            panel.collisionChecker.checkTile(this);
            if (!collided) {
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

    }

    /**
     * Calculates the distance from the zookeeper to the monkey
     */
    public int tilesToMonkey(){
        return Math.abs(monkey.x - x) + Math.abs(monkey.y - y);
    }
    public boolean remove(){
        return panel.removeEntity(this);
    }
    public boolean removeZookeeper(){
        return panel.removeZookeeper(this);
    }
}

