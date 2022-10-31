package com.monkeyescape.entity.movingentity;

import com.monkeyescape.entity.Entity;
import com.monkeyescape.entity.Position;
import com.monkeyescape.main.KeyHandler;
import com.monkeyescape.main.Panel;

import javax.imageio.ImageIO;
import java.awt.*;
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
    public int speed;

    private final HashMap<String, BufferedImage> images = new HashMap<>();
    public String direction;

    public Rectangle area;
    public int areaX;
    public int areaY;
    private int drawImageDelay = 0;
    private int drawImageVersion = 1;

    public boolean collided = false;
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
        if(kh.isPressedUp() || kh.isPressedRight() || kh.isPressedDown() || kh.isPressedLeft()) {
            if (kh.isPressedUp()) {
                direction = "up";

            } else if (kh.isPressedRight()) {
                direction = "right";

            } else if (kh.isPressedDown()) {
                direction = "down";

            } else if (kh.isPressedLeft()) {
                direction = "left";

            }

            collided = false;
            panel.collisionChecker.checkTile(this);
            panel.collisionChecker.checkInteractable(this);
            panel.collisionChecker.checkZookeeper(this,panel.zookeepers);

            if (!collided) {
                switch (direction) {
                    case "up":
                        y -= speed;
                        break;
                    case "right":
                        x += speed;
                        break;
                    case "down":
                        y += speed;
                        break;
                    case "left":
                        x -= speed;
                        break;
                }
            }
        }

        // after 15 game "ticks" we want to switch the image (version 1 or version 2)
        // version we are showing to make it look more animated
        if (++drawImageDelay > 15) {
            drawImageVersion = drawImageVersion == 1 ? 2 : 1;
            drawImageDelay = 0;
        }
    }
    public Position createRandomPosition(Panel panel){
        boolean found = false;
        Position newpos = null;
        while(!found) {
            //generates random colIndex and rowIndex between 1-14 which are between boundaries of walls
            int colIndex = (int) (Math.random() * (panel.cols - 2) + 1);
            int rowIndex = (int) (Math.random() * (panel.rows - 2) + 1);
            if(!(panel.tm.tileMap[colIndex][rowIndex].blocked) && !(panel.tm.tileMap[colIndex][rowIndex].hasFixedEntity)){
                newpos = new Position(colIndex*panel.tileSize, rowIndex*panel.tileSize);
                found = true;
            }

        }
        return newpos;
    }
    public void draw(Graphics2D g2) {
        g2.drawImage(images.get(direction + drawImageVersion), x, y, panel.tileSize, panel.tileSize, null);
    }
}
