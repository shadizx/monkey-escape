package com.monkeyescape.entity;

import com.monkeyescape.main.KeyHandler;
import com.monkeyescape.main.Panel;

import javax.imageio.ImageIO;
import java.awt.Graphics2D;
import java.awt.image.BufferedImage;
import java.util.HashMap;

/**
 * Represents an Entity
 * @author Shadi Zoldjalali
 * @version 10/26/2022
 */
public abstract class Entity {

    Panel panel;
    KeyHandler kh;

    public String type;

    protected int x;
    protected int y;
    protected int speed;

    public HashMap<String, BufferedImage> images = new HashMap<>();
    public String direction;

    private int drawImageDelay = 0;
    private int drawImageVersion = 1;

    /**
     * Creates an Entity
     * @param panel A panel to add the monkey to
     * @param kh a <code>KeyHandler</code> for handling key inputs
     */
    public Entity(Panel panel, KeyHandler kh) {
        this.panel = panel;
        this.kh = kh;
        // set default direction to down
        direction = "down";
    }

    /**
     * Reads in the image for each direction and places it in the <code>images</code> hashmap
     */
    public void getImage() {
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
            System.out.printf("Error %s occurred while getting images for entity %s%n", ex.toString(), this.type);
            ex.printStackTrace();
        }
    }

    /**
     * Updates location of Entity based on which key was pressed
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

    /**
     * Draws the Entity
     * @param g2 the <code>Graphics2D</code> object used to draw
     */
    public void draw(Graphics2D g2) {
        g2.drawImage(images.get(direction + String.valueOf(drawImageVersion)), x, y, panel.tileSize, panel.tileSize, null);
    }
}
