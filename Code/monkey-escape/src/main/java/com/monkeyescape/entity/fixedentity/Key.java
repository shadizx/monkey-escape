package com.monkeyescape.entity.fixedentity;

import com.monkeyescape.main.Panel;

/**
 * Represents a Key
 * @author Henry Ruckman-Utting
 * @version 10/30/2022
 */
public class Key extends FixedEntity {
    //Connect to a door once door is implemented

    /**
     * Creates a key with random position
     * @param panel A <code>Panel</code>> to refer to
     */
    public Key(Panel panel) {  //Include door in constructor
        super(panel);
        type = "key";
        impact = 100;

        panel.tm.addFixedEntitytoMap(y/ panel.tileSize, x/ panel.tileSize, this);
        loadImage();
    }

    public void update() {}

    /**
     * Key unlocks the exit door
     */
    void useKey() {
        //Open door that key is connected to
    }
}
