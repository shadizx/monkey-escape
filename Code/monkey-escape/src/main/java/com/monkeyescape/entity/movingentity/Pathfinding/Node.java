package com.monkeyescape.entity.movingentity.Pathfinding;

/**
 * Represents a node used in pathfinding
 *
 * @author Henry Ruckman-Utting
 * @version 11/23/2022
 */
public class Node {
    public int col;
    public int row;
    public int gCost;
    public int hCost;
    public int fCost;
    public boolean solid;
    public boolean checked;
    public boolean open;
    public boolean path;
    public Node parent;

    /**
     * Initializes a node using params as its position
     *
     * @param col the x position
     * @param row the y position
     */
    Node(int col, int row){
        this.col = col;
        this.row = row;
    }
}
