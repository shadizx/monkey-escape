package com.monkeyescape.entity.fixedentity;

import com.monkeyescape.entity.Entity;
import com.monkeyescape.main.Panel;

import javax.imageio.ImageIO;
import java.awt.Graphics2D;
import java.awt.image.BufferedImage;

/**
 * Represents a fixed entity
 * @author Henry Ruckman-Utting
 * @version 10/30/2022
 */
public abstract class FixedEntity implements Entity {
    private final Panel panel;

    public String type;

    protected int x;
    protected int y;

    private BufferedImage image;
    public int impact;

    /**
     * Creates a fixed entity
     * @param panel A <code>Panel</code>> to refer to
     */
    public FixedEntity(Panel panel) {
        this.panel = panel;
    }

    public void loadImage() {
        try {
            image = ImageIO.read(getClass().getClassLoader().getResourceAsStream(String.format("%s/%s.png", this.type, this.type)));
        } catch (Exception ex) {
            System.out.printf("Error %s occurred while getting images for %s%n", ex.toString(), this.type);
            ex.printStackTrace();
        }
    }

    public void draw(Graphics2D g2) {
        g2.drawImage(image, x, y, panel.tileSize, panel.tileSize, null);
    }

    /**
     * Returns the fixed entity from the map
     */
    boolean remove() {
        return panel.removeEntity(this);
    }
}
