package com.monkeyescape.entity.movingentity.Pathfinding;

/**
 * Represents a node used in pathfinding
 * @author Henry Ruckman-Utting
 * @version 11/01/2022
 */
public class Node {
    public int col, row, gCost, hCost, fCost;
    public boolean solid, checked, open, path;
    public Node parent;

    
    /**
     * Initializes a node using params as its position
     * @param col the x position
     * @param row the y position
     */
    Node(int col, int row){
        this.col = col;
        this.row = row;
    }

    /**
     * Updates the open boolean
     */
    public void setAsOpen(){
        open = true;
    }

    /**
     * Updates the checked boolean
     */
    public void setAsChecked(){
        checked = true;
    }
}
