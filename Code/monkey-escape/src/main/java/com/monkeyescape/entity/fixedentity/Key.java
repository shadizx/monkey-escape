package com.monkeyescape.entity.fixedentity;

import com.monkeyescape.main.Game;

/**
 * Represents a Key
 *
 * @author Henry Ruckman-Utting
 * @version 12/06/2022
 */
public class Key extends FixedEntity {
    /**
     * Creates a key with random position
     *
     * @param game A <code>Game</code> to refer to
     */
    public Key(Game game) {
        super(game);
        type = "key";
        impact = 100;

        game.tileMap.addFixedEntitytoMap(this.getYCoordinate()/ game.tileSize, this.getXCoordinate()/ game.tileSize, this);
        loadImage();
    }

    public void update() {}

    /**
     * Uses the key to unlock the door
     */
    void useKey() {
        //unblocks door tile
        game.tileMap.tileMap[game.doorPos.x][game.doorPos.y].isBlocked = false;
        game.tileMap.tileMap[game.doorPos.x][game.doorPos.y].image = game.tileMap.tileImages[0].image;
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
