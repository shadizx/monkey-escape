package com.monkeyescape.entity;

import com.monkeyescape.main.Game;

import java.awt.Graphics2D;

/**
 * Represents an Entity
 *
 * @author Shadi Zoldjalali
 * @version 11/23/2022
 */
public interface Entity {
    /**
     * Loads the image for this entity
     *
     * @return true whether the image was loaded, false otherwise
     */
     boolean loadImage();

    /**
     * Updates the location of this entity based on game input
     */
     void update();

    /**
     * Draws the Entity
     *
     * @param g2 the <code>Graphics2D</code> object used to draw
     */
     void draw(Graphics2D g2);

    /**
     * Creates a random  valid Position within the map
     *
     * @param game the <code>Game</code> to refer to
     * @return the random position that was created
     */
     Position createRandomPosition(Game game);
}
