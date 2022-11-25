package com.monkeyescape.entity.fixedentity;

import com.monkeyescape.main.Game;

/**
 * Represents a banana
 *
 * @author Henry Ruckman-Utting
 * @version 11/23/2022
 */
public class Banana extends FixedEntity {
    int lifecycle = (int) ((Math.random() * 300) + 480); //Lifecycle is random (between 5-10 seconds)

    /**
     * Creates a banana with random position
     *
     * @param game A <code>Game</code> to refer to
     */
    public Banana(Game game) {
        super(game);
        type = "banana";
        impact = 100;

        game.tm.addFixedEntitytoMap(y / game.tileSize, x / game.tileSize, this);

        loadImage();
    }

    /**
     * Returns the lifecycle of Banana
     *
     * @return the lifecycle of the banana
     */
    public int getLifecycle() {
        return lifecycle;
    }

    /**
     * Sets the lifecycle of Banana to new value
     *
     * @param newLifecycle a <code>integer</code> number for the lifecycle
     */
    public void setLifecycle(int newLifecycle) {
        lifecycle = newLifecycle;
    }

    /**
     * Updates the number of ticks the banana has left
     */
    public void update() {
        // Banana despawns after set amount of time
        if (--lifecycle < 0) {
            var tileToRemove = game.tm.tileMap[x / game.tileSize][y / game.tileSize];
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
