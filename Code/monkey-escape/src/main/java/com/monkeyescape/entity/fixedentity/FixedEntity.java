package com.monkeyescape.entity.fixedentity;

import com.monkeyescape.entity.Entity;
import com.monkeyescape.entity.Position;
import com.monkeyescape.main.Game;
import com.monkeyescape.main.Sound;

import javax.imageio.ImageIO;
import java.awt.Graphics2D;
import java.awt.image.BufferedImage;

import static com.monkeyescape.entity.movingentity.MovingEntity.getPosition;

/**
 * Represents a fixed entity
 *
 * @author Henry Ruckman-Utting
 * @version 11/02/2022
 */
public abstract class FixedEntity implements Entity {
    protected final Game game;

    public String type;

    public int x;
    public int y;

    private BufferedImage image;
    public int impact;
    public Sound sound;

    /**
     * Creates a fixed entity
     *
     * @param game A <code>Game</code>> to refer to
     */
    public FixedEntity(Game game) {
        this.game = game;
        Position pos = createRandomPosition(game);
        x = pos.x;
        y = pos.y;
        this.sound = new Sound();
    }

    public boolean loadImage() {
        try {
            image = ImageIO.read(getClass().getClassLoader().getResourceAsStream(String.format("%s/%s.png", this.type, this.type)));
        } catch (Exception ex) {
            System.out.printf("Error %s occurred while getting images for %s%n", ex.toString(), this.type);
            ex.printStackTrace();
            return false;
        }
        return true;
    }

    public void draw(Graphics2D g2) {
        g2.drawImage(image, x, y, game.tileSize, game.tileSize, null);
    }

    /**
     * Creates a random position
     *
     * @param game A <code>Game</code>> to refer to
     */
    public Position createRandomPosition(Game game){
        return getPosition(game);
    }

    /**
     * Removes the fixed entity from the Game
     */
    public void remove() {
        game.removeEntity(this);
    }


    /**
     * Plays the sound effect of the Fixed Entity
     */
    public void playSound(){}
}
