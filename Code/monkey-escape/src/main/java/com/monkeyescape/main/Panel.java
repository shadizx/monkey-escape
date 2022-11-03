package com.monkeyescape.main;

import com.monkeyescape.entity.Entity;
import com.monkeyescape.entity.fixedentity.Banana;
import com.monkeyescape.entity.movingentity.Zookeeper;
import com.monkeyescape.map.TileMap;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.image.BufferedImage;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import javax.imageio.ImageIO;
import javax.swing.ImageIcon;
import javax.swing.JLabel;
import javax.swing.JPanel;

/**
 * Represents a JPanel panel that is used for interaction
 * @author Shadi Zoldjalali & Kaleigh Toering
 * @version 10/30/2022
 */
public class Panel extends JPanel implements Runnable {
    public final int tileSize = 48;
    public final int rows = 16;
    public final int cols = 16;
    public final int width = tileSize * cols;
    public final int height = tileSize * rows;
    public final int FPS = 60;

    public int score = 0;
    public int secondsTimer = 0;

    public TileMap tm = new TileMap(this);
    KeyHandler kh = new KeyHandler();
    Thread gameThread;

    State state = new State();

    List<Entity> entities = new ArrayList<>();
    public List<Zookeeper> zookeepers = new ArrayList<>();
    public Collision collisionChecker = new Collision(this);

    /**
     * Creates a new Panel
     */
    public Panel() {
        this.setPreferredSize(new Dimension(width, height));
        this.setBackground(Color.GREEN);
        this.setDoubleBuffered(true); // improves games rendering performance
        this.addKeyListener(kh);
        this.setFocusable(true); // Game panel is "focused" to receive key input
    }

    /**
     * Starts the game thread
     */
    public void startGameThread() {
        gameThread = new Thread(this);
        gameThread.start();
    }

    /**
     * Starts the game loop
     */
    @Override
    public void run() {
        double drawInterval = 1_000_000_000/FPS;
        double delta = 0;
        long lastTime = System.nanoTime();
        long currentTime;
        long timerCount = 0;

        // Game loop
        while (gameThread != null) {
            currentTime = System.nanoTime();

            delta += (currentTime - lastTime) / drawInterval;
            timerCount += (currentTime - lastTime);
            lastTime = currentTime;

            if (delta >= 1) {
                update();
                repaint();

                delta--;
            }

            if (timerCount >= 1000000000) {
                secondsTimer += 1;
                score += collisionChecker.delayedDamages;
                collisionChecker.delayedDamages = 0;
                timerCount = 0;
            }
        }
    }

    /**
     * Updates game information
     */
    public void update() {
        state.changeState(kh);
        for (int i = 0; i < entities.size(); i++) {
            entities.get(i).update();
        }
    }

    /**
     * Draws the UI
     */
    public void paintComponent(Graphics g) {
        super.paintComponent(g);
        Graphics2D g2 = (Graphics2D) g;

        if (state.getGameState() == State.GameState.START) {
            paintStartMenu(g2);
        }
        else if (state.getGameState() == State.GameState.PAUSE) {
            paintPauseMenu(g2);
        }
        else if (state.getGameState() == State.GameState.EXIT) {
            paintExitMenu(g2);
        }
        else {
            // draw stuff here
            tm.drawMap(g2);
            // use this type of for loop to not throw exception ConcurrentModificationException
            for (int i = 0; i < entities.size(); i++) {
                entities.get(i).draw(g2);
            }
            // draw stuff here

            g2.dispose(); // good practice to save memory
        }
    }

    /**
     * Adds an entity into the panel
     * @param entity A non-null entity
     */
    public void addEntity(Entity entity) {
        entities.add(entity);
    }

    /**
     * Adds an zookeeper to a list of enemies
     * @param zookeeper A non-null entity
     */
    public void addZookeeper(Zookeeper zookeeper) {
        zookeepers.add(zookeeper);
    }

    /**
     * Removes selected entity from the panel
     * @param entity A non-null entity
     */
    public boolean removeEntity(Entity entity) {
        return entities.remove(entity);
    }

    /**
     * Paints the Start Menu screen onto the Jpanel
     * @param g2 graphics item
     */
    public void paintStartMenu(Graphics2D g2) {
        BufferedImage image = null;
        try {
            image = ImageIO.read(this.getClass().getResource("/menu/start.png"));
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
        JLabel wIcon = new JLabel(new ImageIcon(image));
        g2.drawImage(image, 0, 0, this);
    }

    /**
     * Paints the Pause Menu screen onto the Jpanel
     * @param g2 graphics item
     */
    public void paintPauseMenu(Graphics2D g2) {
        BufferedImage image = null;
        try {
            image = ImageIO.read(this.getClass().getResource("/menu/pause.png"));
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
        JLabel wIcon = new JLabel(new ImageIcon(image));
        g2.drawImage(image, 0, 0, this);
    }

    /**
     * Paints the Exit Menu screen onto the Jpanel
     * @param g2 graphics item
     */
    public void paintExitMenu(Graphics2D g2) {
        BufferedImage image = null;
        try {
            image = ImageIO.read(this.getClass().getResource("/menu/exit.png"));
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
        JLabel wIcon = new JLabel(new ImageIcon(image));
        g2.drawImage(image, 0, 0, this);
    }
    
    /**
     * Removes selected zookeeper from the list of enemies
     * @param zookeeper A non-null entity
     */
    public boolean removeZookeeper(Zookeeper zookeeper) {
        return zookeepers.remove(zookeeper);
    }
}
