package com.monkeyescape.entity.fixedentity;

import com.monkeyescape.main.Panel;

/**
 * Represents a banana
 * @author Henry Ruckman-Utting
 * @version 11/02/2022
 */
public class Banana extends FixedEntity {
    int lifecycle = 1000;

    /**
     * Creates a banana with random position
     * @param panel A <code>Panel</code>> to refer to
     */
    public Banana(Panel panel) {
        super(panel);
        type = "banana";
        impact = 100;

        panel.tm.addFixedEntitytoMap(y / panel.tileSize, x / panel.tileSize, this);

        loadImage();
    }

    /**
     * Updates the number of ticks the banana has left
     */
    public void update() {
        // Banana despawns after set amount of time
        if (--lifecycle < 0) {
            var tileToRemove = panel.tm.tileMap[x / panel.tileSize][y / panel.tileSize];

            tileToRemove.hasFixedEntity = false;
            tileToRemove.FixedEntityObject.remove();
            panel.removeEntity(this);
        }
    }
}
