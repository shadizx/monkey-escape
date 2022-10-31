package com.monkeyescape.entity;

import com.monkeyescape.main.Panel;

/**
 * Represents a position
 * @author Henry Ruckman-Utting
 * @version 10/30/2022
 */
public class Position {
    public int x;
    public int y;

    /**
     * Initializes a position with a selected x and y
     */
    public Position(int x, int y) {
        this.x = x;
        this.y = y;
    }

}
