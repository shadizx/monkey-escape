package com.monkeyescape.entity;

import com.monkeyescape.main.KeyHandler;
import com.monkeyescape.main.Panel;

import java.awt.*;

/**
 * Represents a punishment (lion pit)
 * @author Henry Ruckman-Utting
 * @version 10/29/2022
 */
public class Punishment extends Interactable {
    /**
     * Initializes a lion pit
     */
    public Punishment(Panel panel, KeyHandler kh) {
        super(panel, kh);
        impact = -150;

        // random starting position
        x = (int)(Math.random() * panel.width);
        y = (int)(Math.random() * panel.height); //Remove this and use interactable find position once map is implemented
    }

    /**
     * Draws the Enemy
     * @param g2 the <code>Graphics2D</code> object used to draw
     */
    public void draw(Graphics2D g2) {
        g2.setColor(Color.MAGENTA);
        g2.fillRect(x, y, panel.tileSize, panel.tileSize);
    }
}
