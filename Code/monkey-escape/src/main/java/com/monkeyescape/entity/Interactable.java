package com.monkeyescape.entity;

import com.monkeyescape.main.KeyHandler;
import com.monkeyescape.main.Panel;

/**
 * Represents an Interactable
 * @author Henry Ruckman-Utting
 * @version 10/29/2022
 */
public abstract class Interactable extends Entity {
    int impact;
    
    /**
     * Initializes an interactable in an empty tile
     */
    public Interactable(Panel panel, KeyHandler kh) {
        super(panel, kh);

        Position pos = createPosition();
        x = pos.x;
        y = pos.y;
    }

    /**
     * Returns a position on the map that is not taken up
     */
    Position createPosition(){
        //While loop
        //Create random position on board
        //Check if that position is occupied
        //Implement using Jeffrey's map
        
        return new Position(700, 700); //Change 700 and 700 once while loop implemented
    }

    /**
     * Returns the interactable from the map
     */
    boolean remove(){
        return panel.removeEntity(this);
    }
}