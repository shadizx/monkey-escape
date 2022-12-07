package com.monkeyescape.entity.fixedentity;

import com.monkeyescape.main.Game;

/**
 * Represents a lion pit
 *
 * @author Henry Ruckman-Utting
 * @version 11/23/2022
 */
public class LionPit extends FixedEntity {
    /**
     * Initializes a lion pit
     *
     * @param game a <code>Game</code> to refer to
     */
    public LionPit(Game game) {
        super(game);
        type = "lionpit";
        impact = -150;
        game.tileMap.addFixedEntitytoMap(y/ game.tileSize, x/ game.tileSize, this);
        loadImage();
    }

    @Override
    public void update() {}
}
