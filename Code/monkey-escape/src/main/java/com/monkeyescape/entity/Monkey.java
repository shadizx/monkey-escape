package com.monkeyescape.entity;

import com.monkeyescape.main.KeyHandler;
import com.monkeyescape.main.Panel;

/**
 * Represents the Monkey entity
 * @author Shadi Zoldjalali
 * @version 10/26/2022
 */
public class Monkey extends Entity {

    /**
     * Creates a Monkey
     * @param panel A panel to add the monkey to
     * @param kh a <code>KeyHandler</code> for handling key inputs
     */
    public Monkey(Panel panel, KeyHandler kh) {
        super(panel, kh);
        // random starting values
        x = panel.width / 2;
        y = panel.height / 2;
        speed = 4;
    }
}
