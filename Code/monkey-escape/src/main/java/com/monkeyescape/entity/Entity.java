package com.monkeyescape.entity;

import com.monkeyescape.main.Panel;

import java.awt.Graphics2D;

/**
 * Represents an Entity
 * @author Shadi Zoldjalali
 * @version 10/30/2022
 */
public interface Entity {
    /**
     * Loads the image for this entity
     */
     void loadImage();

    /**
     * Updates the location of this entity based on game input
     */
     void update();

    /**
     * Draws the Entity
     * @param g2 the <code>Graphics2D</code> object used to draw
     */
     void draw(Graphics2D g2);

    /**
     * Creates a random  valid Position within the map
     * */
     Position createRandomPosition(Panel panel);

}
