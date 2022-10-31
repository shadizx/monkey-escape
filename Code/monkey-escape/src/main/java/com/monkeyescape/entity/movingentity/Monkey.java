package com.monkeyescape.entity.movingentity;

import com.monkeyescape.main.KeyHandler;
import com.monkeyescape.main.Panel;

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
        speed = 4;
        loadImage();

        // random starting values
        x = panel.width / 2;
        y = panel.height / 2;
    }
}
