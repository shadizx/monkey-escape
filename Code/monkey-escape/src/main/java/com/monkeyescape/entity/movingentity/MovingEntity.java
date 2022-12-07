package com.monkeyescape.entity.movingentity;

import com.monkeyescape.entity.Entity;
import com.monkeyescape.entity.Position;
import com.monkeyescape.main.Game;
import com.monkeyescape.main.KeyHandler;
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
 * @version 12/06/2022
 */
public abstract class MovingEntity implements Entity {
    Game game;
    protected Movement movement = new Movement(this);
    KeyHandler keyHandler;

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
     * @param game A <code>Game</code> to refer to
     * @param keyHandler a <code>KeyHandler</code> for handling key inputs
     */
    public MovingEntity(Game game, KeyHandler keyHandler) {
        this.game = game;
        this.keyHandler = keyHandler;
        direction = "down";
    }

    /**
     * Loads the images and places them in the <code>images</code> hashmap
     *
     * @return true if successful, false if exception occurred
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
        if(game.state.getGameState() != State.GameState.PLAY) return;
        
        if(keyHandler.isPressedUp() || keyHandler.isPressedRight() || keyHandler.isPressedDown() || keyHandler.isPressedLeft()) {
            if (keyHandler.isPressedUp()) {
                direction = "up";

            } else if (keyHandler.isPressedRight()) {
                direction = "right";

            } else if (keyHandler.isPressedDown()) {
                direction = "down";

            } else if (keyHandler.isPressedLeft()) {
                direction = "left";
            } 

            collided = false;
            game.collisionChecker.checkTile(this);
            game.collisionChecker.checkFixedEntity(this);
            game.collisionChecker.checkZookeeper(this, game.zookeepers);

            if (!collided) {
                movement.move();
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
     * @param game A <code>Game</code> to refer to
     * @return the random position that was created
     */
    public Position createRandomPosition(Game game){
        return getPosition(game);
    }

    /**
     * Creates a random position
     *
     * @param game A <code>Game</code> to refer to
     * @return the random position that was created
     */
    public static Position getPosition(Game game) {
        boolean found = false;
        Position newPos = null;
        while(!found) {
            //generates random colIndex and rowIndex between 1-14 which are between boundaries of walls
            int colIndex = (int) (Math.random() * (game.cols - 2) + 1);
            int rowIndex = (int) (Math.random() * (game.rows - 2) + 1);
            if(!(game.tileMap.tileMap[colIndex][rowIndex].isBlocked) && !(game.tileMap.tileMap[colIndex][rowIndex].hasFixedEntity)
                    && (colIndex < game.exitCol - 2 && rowIndex < game.exitRow - 2)
                    && (colIndex > game.startCol + 2 && rowIndex > game.startRow + 2)){
                newPos = new Position(colIndex*game.tileSize, rowIndex*game.tileSize);
                found = true;
            }

        }
        return newPos;
    }

    public void draw(Graphics2D g2) {
        g2.drawImage(images.get(direction + drawImageVersion), x, y, game.tileSize, game.tileSize, null);
    }
}
