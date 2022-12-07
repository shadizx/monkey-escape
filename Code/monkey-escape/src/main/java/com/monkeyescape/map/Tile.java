package com.monkeyescape.map;

import com.monkeyescape.entity.fixedentity.FixedEntity;

import java.awt.image.BufferedImage;

/**
 * Tile object that has the image, whether its blocked and an object if it contains one.
 *
 * @author Jeffrey Ramacula
 * @version 12/06/2022
 */
public class Tile {
    public BufferedImage image;
    public boolean isBlocked = false;
    public boolean hasFixedEntity = false;
    public FixedEntity FixedEntityObject;
}
