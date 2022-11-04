package com.monkeyescape.entity.fixedentity;

import com.monkeyescape.main.Panel;


/**
 * Represents a Key
 * @author Henry Ruckman-Utting
 * @version 10/30/2022
 */
public class Key extends FixedEntity {



    int exitCol;
    int exitRow;
    /**
     * Creates a key with random position
     * @param panel A <code>Panel</code>> to refer to
     */
    public Key(Panel panel) {
        super(panel);
        type = "key";
        impact = 100;
        //position of door
        this.exitCol = panel.exitCol;
        this.exitRow = panel.exitRow;

        panel.tm.addFixedEntitytoMap(y/ panel.tileSize, x/ panel.tileSize, this);
        loadImage();
    }

    public void update() {}

    /**
     * Key unlocks the exit door
     */
    void useKey() {
        //unblocks door tile
        panel.tm.tileMap[exitCol][exitRow].blocked = false;
        panel.tm.tileMap[exitCol][exitRow].image = panel.tm.tileImages[0].image;
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
