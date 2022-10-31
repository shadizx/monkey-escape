package com.monkeyescape.entity.movingentity;

import com.monkeyescape.main.KeyHandler;
import com.monkeyescape.main.Panel;

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
        x = (int)(Math.random() * panel.width);
        y = (int)(Math.random() * panel.height);

        //Checks if zookeeper is too close off of spawn
        while(tilesToMonkey() < 5){
            x = (int)(Math.random() * panel.width);
            y = (int)(Math.random() * panel.height);
        }
    }

    /**
     * Moves the zookeeper towards the monkey
     */
    public void update() { //Can create better pathfinding once walls are implemented
        if (kh.isPressedDown() || kh.isPressedUp() || kh.isPressedLeft() || kh.isPressedRight()) { //Only moves if player is moving
            if (Math.abs(monkey.x - x) > Math.abs(monkey.y - y)) { //If the monkey is further away on the x-axis than y-axis
                //Move one tile towards monkey on x-axis
                if (monkey.x - x > 0) { //Check which direction to move along x-axis
                    x += speed;
                }
                else {
                    x -= speed;
                }
            }
            else { //Monkey is further away on the y-axis than x-axis
                //Move one tile towards monkey on y-axis
                if (monkey.y - y > 0) { //Check which direction to move along y-axis
                    y += speed;
                }
                else {
                    y -= speed;
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
}
