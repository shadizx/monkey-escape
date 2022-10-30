package com.monkeyescape.entity;

import com.monkeyescape.main.KeyHandler;
import com.monkeyescape.main.Panel;

import java.awt.*;

/**
 * Represents the Enemy (Zoo keeper)
 * @author Henry Ruckman-Utting
 * @version 10/29/2022
 */
public class Enemy extends Entity {
    Monkey monkey;
    
    /**
     * Initializes an enemy with random position and connected to the monkey
     */
    public Enemy(Panel panel, KeyHandler kh, Monkey mnky) {
        super(panel, kh);
        
        monkey = mnky;

        // random starting position
        x = (int)(Math.random() * panel.width);
        y = (int)(Math.random() * panel.height);

        while(tilesToMonkey() < 5){
            x = (int)(Math.random() * panel.width);
            y = (int)(Math.random() * panel.height);
        } //Checks if zookeeper is too close off of spawn

        speed = 1;
    }

    /**
     * Moves the enemy towards the monkey
     */
    public void update(){ //Can create better pashfinding once walls are implemented
        if(kh.isPressedDown() || kh.isPressedUp() || kh.isPressedLeft() || kh.isPressedRight()){ //Only moves if player is moving
            if(Math.abs(monkey.x - x) > Math.abs(monkey.y - y)){ //If the monkey is further away on the x axis than y axis
                //Move one tile towards monkey on x axis
                if(monkey.x - x > 0){ //Check which direction to move along x axis
                    x += speed;
                }
                else {
                    x -= speed;
                }
            }
            else{ //Monkey is further away on the y axis than x axis
                //Move one tile towards monkey on y axis
                if(monkey.y - y > 0){ //Check which direction to move along y axis
                    y += speed;
                }
                else {
                    y -= speed;
                }
            }
        }
        
    }

    /**
     * Draws the Enemy
     * @param g2 the <code>Graphics2D</code> object used to draw
     */
    public void draw(Graphics2D g2) {
        g2.setColor(Color.red);
        g2.fillRect(x, y, panel.tileSize, panel.tileSize);
    }

    /**
     * Calculates the distance from the zookeeper to the monkey
     */
    public int tilesToMonkey(){
        return Math.abs(monkey.x - x) + Math.abs(monkey.y - y);
    }
}
