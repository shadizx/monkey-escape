package com.monkeyescape.entity.movingentity;

import com.monkeyescape.entity.Entity;
import com.monkeyescape.entity.Position;
import com.monkeyescape.main.KeyHandler;
import com.monkeyescape.main.Panel;
import com.monkeyescape.main.State;

import javax.imageio.ImageIO;
import java.awt.Graphics2D;
import java.awt.Rectangle;
import java.awt.image.BufferedImage;
import java.util.HashMap;

/**
 * Represents a moving entity
 *
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
    protected int drawImageDelay = 0;
    protected int drawImageVersion = 1;

    public boolean collided = false;

    /**
     * Creates a moving entity
     *
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
     * @return true if sucessful, false if exception occured
     */
    public boolean loadImage() {
        try {
            images.put("up1", ImageIO.read(getClass().getClassLoader().getResourceAsStream(String.format("%s/up1.png", this.type))));
            images.put("up2", ImageIO.read(getClass().getClassLoader().getResourceAsStream(String.format("%s/up2.png", this.type))));
            images.put("right1", ImageIO.read(getClass().getClassLoader().getResourceAsStream(String.format("%s/right1.png", this.type))));
            images.put("right2", ImageIO.read(getClass().getClassLoader().getResourceAsStream(String.format("%s/right2.png", this.type))));
            images.put("down1", ImageIO.read(getClass().getClassLoader().getResourceAsStream(String.format("%s/down1.png", this.type))));
            images.put("down2", ImageIO.read(getClass().getClassLoader().getResourceAsStream(String.format("%s/down2.png", this.type))));
            images.put("left1", ImageIO.read(getClass().getClassLoader().getResourceAsStream(String.format("%s/left1.png", this.type))));
            images.put("left2", ImageIO.read(getClass().getClassLoader().getResourceAsStream(String.format("%s/left2.png", this.type))));
            if(type.equals("monkey")){
                images.put("jump1", ImageIO.read(getClass().getClassLoader().getResourceAsStream(String.format("%s/jump.png", this.type))));  
                images.put("jump2", ImageIO.read(getClass().getClassLoader().getResourceAsStream(String.format("%s/jump.png", this.type))));    
            }
        } catch (Exception ex) {
            System.out.printf("Error %s occurred while getting images for %s%n", ex, this.type);
            ex.printStackTrace();
            return false;
        }
        return true;
    }

    /**
     * Updates moving entity information
     */
    public void update() {
        //No movement if game is not in play
        if(panel.state.getGameState() != State.GameState.PLAY) return;
        
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
            panel.collisionChecker.checkFixedEntity(this);
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

    /**
     * Creates a random position
     *
     * @param panel A <code>Panel</code>> to refer to
     */
    public Position createRandomPosition(Panel panel){
        boolean found = false;
        Position newpos = null;
        while(!found) {
            //generates random colIndex and rowIndex between 1-14 which are between boundaries of walls
            int colIndex = (int) (Math.random() * (panel.cols - 2) + 1);
            int rowIndex = (int) (Math.random() * (panel.rows - 2) + 1);
            if(!(panel.tm.tileMap[colIndex][rowIndex].blocked) && !(panel.tm.tileMap[colIndex][rowIndex].hasFixedEntity)
                    && (colIndex < panel.exitCol - 2 && rowIndex < panel.exitRow - 2)
                    && (colIndex > panel.startCol + 2 && rowIndex > panel.startRow + 2)){
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
