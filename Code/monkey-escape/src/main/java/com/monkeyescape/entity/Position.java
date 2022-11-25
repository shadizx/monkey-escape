package com.monkeyescape.entity;

/**
 * Represents a position
 *
 * @author Henry Ruckman-Utting
 * @version 11/23/2022
 */
public class Position {
    public int x;
    public int y;

    /**
     * Initializes a position with a selected x and y
     *
     * @param x the x position
     * @param y the y position
     */
    public Position(int x, int y) {
        this.x = x;
        this.y = y;
    }
}
