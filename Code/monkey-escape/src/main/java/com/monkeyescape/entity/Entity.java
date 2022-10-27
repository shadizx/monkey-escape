package com.monkeyescape.entity;

import com.monkeyescape.main.KeyHandler;
import com.monkeyescape.main.Panel;

import java.awt.Color;
import java.awt.Graphics2D;

/**
 * Represents an Entity
 * @author Shadi Zoldjalali
 * @version 10/26/2022
 */
public abstract class Entity {

    Panel panel;
    KeyHandler kh;

    protected int x;
    protected int y;
    protected int speed;

    /**
     * Creates an Entity
     * @param panel A panel to add the monkey to
     * @param kh a <code>KeyHandler</code> for handling key inputs
     */
    public Entity(Panel panel, KeyHandler kh) {
        this.panel = panel;
        this.kh = kh;
    }

    /**
     * Updates location of Entity based on which key was pressed
     */
    public void update() {
        if (kh.isPressedUp()) {
            y -= speed;
        }
        else if (kh.isPressedRight()) {
            x += speed;
        }
        else if (kh.isPressedDown()) {
            y += speed;
        }
        else if (kh.isPressedLeft()) {
            x -= speed;
        }
    }

    /**
     * Draws the Entity
     * @param g2 the <code>Graphics2D</code> object used to draw
     */
    public void draw(Graphics2D g2) {
        g2.setColor(Color.black);
        g2.fillRect(x, y, panel.tileSize, panel.tileSize);
    }
}
