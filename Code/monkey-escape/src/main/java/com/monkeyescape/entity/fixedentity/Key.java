package com.monkeyescape.entity.fixedentity;

import com.monkeyescape.main.Game;

/**
 * Represents a Key
 *
 * @author Henry Ruckman-Utting
 * @version 11/23/2022
 */
public class Key extends FixedEntity {
    int exitCol;
    int exitRow;

    /**
     * Creates a key with random position
     *
     * @param game A <code>Game</code> to refer to
     */
    public Key(Game game) {
        super(game);
        type = "key";
        impact = 100;
        //position of door
        this.exitCol = game.exitCol;
        this.exitRow = game.exitRow;

        game.tileMap.addFixedEntitytoMap(y/ game.tileSize, x/ game.tileSize, this);
        loadImage();
    }

    public void update() {}

    /**
     * Uses the key to unlock the door
     */
    void useKey() {
        //unblocks door tile
        game.tileMap.tileMap[exitCol][exitRow].isBlocked = false;
        game.tileMap.tileMap[exitCol][exitRow].image = game.tileMap.tileImages[0].image;
        playSound();
    }

    @Override
    public void playSound() {
        super.sound.setFile(1);
        super.sound.play();
    }

    @Override
    public void remove() {
        this.useKey();
        super.remove();
    }
}
