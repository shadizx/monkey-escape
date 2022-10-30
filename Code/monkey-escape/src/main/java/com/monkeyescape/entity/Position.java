package com.monkeyescape.entity;

/**
 * Represents a position
 * @author Henry Ruckman-Utting
 * @version 10/29/2022
 */
public class Position{
    protected int x;
    protected int y;
    
    /**
     * Initializes a position with a selected x and y
     */
    public Position(int _x, int _y) {
        x = _x;
        y = _y;
    }
}