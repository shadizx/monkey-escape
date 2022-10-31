package com.monkeyescape.entity.fixedentity;

import com.monkeyescape.main.Panel;

/**
 * Represents a lion pit
 * @author Henry Ruckman-Utting
 * @version 10/30/2022
 */
public class LionPit extends FixedEntity {
    /**
     * Initializes a lion pit
     */
    public LionPit(Panel panel) {
        super(panel);
        type = "lionpit";
        impact = -150;
        panel.tm.addFixedEntitytoMap(y/ panel.tileSize, x/ panel.tileSize, this);
        loadImage();
    }

    @Override
    public void update() {}
}
