package com.monkeyescape.entity.movingentity;

import com.monkeyescape.main.KeyHandler;
import com.monkeyescape.main.Panel;

import java.awt.*;

/**
 * Represents a monkey
 * @author Shadi Zoldjalali
 * @version 10/30/2022
 */
public class Monkey extends MovingEntity {
    /**
     * Creates a Monkey
     *
     * @param panel A <code>Panel</code>> to refer to
     * @param kh a <code>KeyHandler</code> for handling key inputs
     */
    public Monkey(Panel panel, KeyHandler kh) {

        super(panel, kh);
        type = "monkey";
        loadImage();


        this.type = "monkey";

        areaX = 8;
        areaY = 16;
        //set entity area to be smaller than tile so that it can fit in 1 tile spaces
        area = new Rectangle(areaX, areaY, 32, 32);

        // start to wherever the cage is
        x = panel.startCol * panel.tileSize;
        y = panel.startRow * panel.tileSize;

        speed = 4;
    }
}
