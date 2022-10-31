package com.monkeyescape.entity;

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
    public void loadImage();

    /**
     * Updates the location of this entity based on game input
     */
    public void update();

    /**
     * Draws the Entity
     * @param g2 the <code>Graphics2D</code> object used to draw
     */
    public void draw(Graphics2D g2);
}
