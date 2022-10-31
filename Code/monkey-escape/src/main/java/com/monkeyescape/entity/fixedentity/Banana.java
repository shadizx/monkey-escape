package com.monkeyescape.entity.fixedentity;

import com.monkeyescape.main.Panel;

import java.awt.Graphics2D;

/**
 * Represents a banana
 * @author Henry Ruckman-Utting
 * @version 10/30/2022
 */
public class Banana extends FixedEntity {
    int lifecycle = 1000;

    /**
     * Creates a banana with random position
     * @param panel A <code>Panel</code>> to refer to
     */
    public Banana(Panel panel) {
        super(panel);
        type = "banana";
        impact = 100;

        // random starting position
        x = (int)(Math.random() * panel.width);
        y = (int)(Math.random() * panel.height); //Remove this and use interactable find position once map is implemented
        loadImage();
    }

    /**
     * Updates the number of ticks the banana has left
     */
    public void update() {
        // Banana despawns after set amount of time
        lifecycle--;
    }

    /**
     * Draws the Banana
     * @param g2 the <code>Graphics2D</code> object used to draw
     */
    public void draw(Graphics2D g2) {
        if(lifecycle > 0){
            super.draw(g2);
        }
    }
}
