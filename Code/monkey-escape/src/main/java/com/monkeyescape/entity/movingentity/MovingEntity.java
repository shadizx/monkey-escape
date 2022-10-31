package com.monkeyescape.entity.movingentity;

import com.monkeyescape.entity.Entity;
import com.monkeyescape.main.KeyHandler;
import com.monkeyescape.main.Panel;

import javax.imageio.ImageIO;
import java.awt.Graphics2D;
import java.awt.image.BufferedImage;
import java.util.HashMap;

/**
 * Represents a moving entity
 * @author Shadi Zoldjalali
 * @version 10/30/2022
 */
public abstract class MovingEntity implements Entity {
    Panel panel;
    KeyHandler kh;

    public String type = null;

    public int x;
    public int y;
    protected int speed;

    private final HashMap<String, BufferedImage> images = new HashMap<>();
    public String direction;

    private int drawImageDelay = 0;
    private int drawImageVersion = 1;

    /**
     * Creates a moving entity
     * @param panel A <code>Panel</code>> to refer to
     * @param kh a <code>KeyHandler</code> for handling key inputs
     */
    public MovingEntity(Panel panel, KeyHandler kh) {
        this.panel = panel;
        this.kh = kh;
        direction = "down";
    }

    /**
     * Loads the images and places them in the <code>images</code> hashmap
     */
    public void loadImage() {
        try {
            images.put("up1", ImageIO.read(getClass().getClassLoader().getResourceAsStream(String.format("%s/up1.png", this.type))));
            images.put("up2", ImageIO.read(getClass().getClassLoader().getResourceAsStream(String.format("%s/up2.png", this.type))));
            images.put("right1", ImageIO.read(getClass().getClassLoader().getResourceAsStream(String.format("%s/right1.png", this.type))));
            images.put("right2", ImageIO.read(getClass().getClassLoader().getResourceAsStream(String.format("%s/right2.png", this.type))));
            images.put("down1", ImageIO.read(getClass().getClassLoader().getResourceAsStream(String.format("%s/down1.png", this.type))));
            images.put("down2", ImageIO.read(getClass().getClassLoader().getResourceAsStream(String.format("%s/down2.png", this.type))));
            images.put("left1", ImageIO.read(getClass().getClassLoader().getResourceAsStream(String.format("%s/left1.png", this.type))));
            images.put("left2", ImageIO.read(getClass().getClassLoader().getResourceAsStream(String.format("%s/left2.png", this.type))));
        } catch (Exception ex) {
            System.out.printf("Error %s occurred while getting images for %s%n", ex, this.type);
            ex.printStackTrace();
        }
    }

    /**
     * Updates location of moving entity based on key input
     */
    public void update() {
        if (kh.isPressedUp()) {
            direction = "up";
            y -= speed;
        }
        else if (kh.isPressedRight()) {
            direction = "right";
            x += speed;
        }
        else if (kh.isPressedDown()) {
            direction = "down";
            y += speed;
        }
        else if (kh.isPressedLeft()) {
            direction = "left";
            x -= speed;
        }
        else {
            // if no keys are pressed down then set direction to down and return
            // this makes it look like the monkey is not moving
            direction = "down";
            return;
        }

        // after 15 game "ticks" we want to switch the image (version 1 or version 2)
        // version we are showing to make it look more animated
        if (++drawImageDelay > 15) {
            drawImageVersion = drawImageVersion == 1 ? 2 : 1;
            drawImageDelay = 0;
        }
    }

    public void draw(Graphics2D g2) {
        g2.drawImage(images.get(direction + drawImageVersion), x, y, panel.tileSize, panel.tileSize, null);
    }
}
