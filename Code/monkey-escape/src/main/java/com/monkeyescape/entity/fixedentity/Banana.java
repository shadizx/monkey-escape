package com.monkeyescape.entity.fixedentity;

import com.monkeyescape.main.Game;

/**
 * Represents a banana
 *
 * @author Henry Ruckman-Utting
 * @version 12/06/2022
 */
public class Banana extends FixedEntity {
    private int lifecycle;

    /**
     * Creates a banana with random position
     *
     * @param game A <code>Game</code> to refer to
     */
    public Banana(Game game) {
        super(game);
        type = "banana";
        impact = 100;
        generateRandomLifecycle(8, 13);

        game.tileMap.addFixedEntitytoMap(this.getYCoordinate() / game.tileSize, this.getXCoordinate() / game.tileSize, this);

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
     * Returns a random lifecycle (for 60 frames/second) for banana between the specified thresholds
     *
     * @param int shortestLifecycle the shortest possible lifecycle [seconds]
     * @param int longestLifecycle the longest possible lifecycle [seconds]
     *
     */
    private void generateRandomLifecycle(int shortestLifecycle, int longestLifecycle) {
        // Error check params
        if (longestLifecycle < shortestLifecycle) {
            System.out.println("longestLifecycle must be greater than shortestLifecycle. Setting to default of 8-13 seconds");
            shortestLifecycle = 8;
            longestLifecycle = 13;
        }

        if (longestLifecycle <= 0 || shortestLifecycle <= 0) {
            System.out.println("longestLifecycle and/or shortestLifecycle must be greater than 0. Setting to default of 8-13 seconds");
            shortestLifecycle = 8;
            longestLifecycle = 13;
        }
        lifecycle = (int) ((Math.random() * 60*(longestLifecycle-shortestLifecycle)) + 60*shortestLifecycle);
        return;
    }


    /**
     * Updates the number of ticks the banana has left
     */
    public void update() {
        // Banana despawns after set amount of time
        if (--lifecycle < 0) {
            var tileToRemove = game.tileMap.tileMap[this.getXCoordinate() / game.tileSize][this.getYCoordinate() / game.tileSize];
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
