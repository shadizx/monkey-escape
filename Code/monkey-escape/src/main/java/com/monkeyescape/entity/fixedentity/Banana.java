package com.monkeyescape.entity.fixedentity;

import com.monkeyescape.main.Panel;

/**
 * Represents a banana
 * @author Henry Ruckman-Utting
 * @version 11/02/2022
 */
public class Banana extends FixedEntity {
    int lifecycle = (int) ((Math.random() * 300) + 300); //Lifecycle is random (between 5-10 seconds)

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
            super.remove();
        }
    }

    @Override
    public void remove() {
        playSound();
        super.remove();
    }

    @Override
    public void playSound() {
        super.sound.setFile(2);
        super.sound.play();
    }
}
