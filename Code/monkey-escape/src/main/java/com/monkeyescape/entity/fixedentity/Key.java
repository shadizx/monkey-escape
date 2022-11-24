package com.monkeyescape.entity.fixedentity;

import com.monkeyescape.main.Game;

/**
 * Represents a Key
 *
 * @author Henry Ruckman-Utting
 * @version 10/30/2022
 */
public class Key extends FixedEntity {
    int exitCol;
    int exitRow;

    /**
     * Creates a key with random position
     *
     * @param game A <code>Game</code>> to refer to
     */
    public Key(Game game) {
        super(game);
        type = "key";
        impact = 100;
        //position of door
        this.exitCol = game.exitCol;
        this.exitRow = game.exitRow;

        game.tm.addFixedEntitytoMap(y/ game.tileSize, x/ game.tileSize, this);
        loadImage();
    }

    public void update() {}

    /**
     * Uses the key to unlock the door
     */
    void useKey() {
        //unblocks door tile
        game.tm.tileMap[exitCol][exitRow].blocked = false;
        game.tm.tileMap[exitCol][exitRow].image = game.tm.tileImages[0].image;
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
