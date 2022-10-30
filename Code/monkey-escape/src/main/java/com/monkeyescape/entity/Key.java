package com.monkeyescape.entity;

import com.monkeyescape.main.KeyHandler;
import com.monkeyescape.main.Panel;

import java.awt.*;

/**
 * Represents a Key
 * @author Henry Ruckman-Utting
 * @version 10/29/2022
 */
public class Key extends Interactable { 
    //Connect to a door once door is implemented

    /**
     * Initializes a key
     */
    public Key(Panel panel, KeyHandler kh) {  //Include door in constructor
        super(panel, kh);
        impact = 100;

        // random starting position
        x = (int)(Math.random() * panel.width);
        y = (int)(Math.random() * panel.height); //Remove this and use interactable find position once map is implemented
    }

    /**
     * Draws the Enemy
     * @param g2 the <code>Graphics2D</code> object used to draw
     */
    public void draw(Graphics2D g2) {
        g2.setColor(Color.orange);
        g2.fillRect(x, y, panel.tileSize, panel.tileSize);
    }

    /**
     * Key unlocks the exit door
     */
    void useKey(){
        //Open door that key is connected to
    }
}
