package com.monkeyescape.entity;

import com.monkeyescape.main.KeyHandler;
import com.monkeyescape.main.Panel;

import java.awt.*;

/**
 * Represents the Bonus Reward (banana)
 * @author Henry Ruckman-Utting
 * @version 10/29/2022
 */
public class BonusReward extends Interactable {
    int lifecycle = 1000;

    /**
     * Initializes a banana with random position
     */
    public BonusReward(Panel panel, KeyHandler kh) {
        super(panel, kh);
        impact = 100;

        // random starting position
        x = (int)(Math.random() * panel.width);
        y = (int)(Math.random() * panel.height); //Remove this and use interactable find position once map is implemented
    }

    /**
     * Updates the number of ticks the banana has left
     */
    public void update(){
        lifecycle--; //Banana despawns after set amount of time
    }

    /**
     * Draws the Banana
     * @param g2 the <code>Graphics2D</code> object used to draw
     */
    public void draw(Graphics2D g2) {
        if(lifecycle > 0){
            g2.setColor(Color.yellow);
            g2.fillRect(x, y, panel.tileSize, panel.tileSize);
        }
    }
}
