package com.monkeyescape.main;

import com.monkeyescape.entity.Entity;
import com.monkeyescape.entity.movingentity.Zookeeper;
import com.monkeyescape.map.TileMap;

import javax.imageio.ImageIO;
import javax.swing.*;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

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
    private final int sideBarWidth = 200;
    public final int FPS = 60;

    public int score = 0;
    public int secondsTimer = 0;

    //set start tile where the cage will be to the top-left corner
    public final int startCol = 1;
    public final int startRow = 0;

    //set end tile where the door/exit will be to the bottom-right corner
    public final int exitCol = cols-2;
    public final int exitRow = rows-1;



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
        this.setPreferredSize(new Dimension(width + sideBarWidth, height));
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
        double drawInterval = 1_000_000_000 / FPS;

        double delta = 0;
        long lastTime = System.nanoTime();
        long currentTime;
        long timerCount = 0;

        long delayCount = 0;
        var delayCheck = true;
        var startDelayTimer = false;
        long cachedNanoTime = 0;

        // Game loop
        while (gameThread != null) {
            // Create new game if in restart state
            if (state.getGameState() == State.GameState.RESTART) {
                state.changeState(kh);
                new Game();
            }
            currentTime = System.nanoTime();

            delta += (currentTime - lastTime) / drawInterval;
            timerCount += (currentTime - lastTime);
            delayCount += (currentTime - lastTime);
            lastTime = currentTime;

            if (delta >= 1) {
                update();
                repaint();

                delta--;
            }

            if (collisionChecker.delayedDamages < 0 && delayCheck) {
                score += collisionChecker.delayedDamages;
                collisionChecker.delayedDamages = 0;
                delayCheck = false;
                cachedNanoTime = delayCount;
                startDelayTimer = true;
            }

            if (timerCount >= 1000000000) {
                secondsTimer += 1;
                timerCount = 0;
            }

            if ((delayCount >= 1000000000 + cachedNanoTime) && startDelayTimer) {
                delayCheck = true;
                collisionChecker.delayedDamages = 0;
                startDelayTimer = false;
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
        paintScore(g2);
        paintTimer(g2);

        State.GameState checkState = state.getGameState();
        if (checkState == State.GameState.START) {
            paintStartMenu(g2);
        }
        else if (checkState == State.GameState.PAUSE) {
            paintPauseMenu(g2);
        }
        else if (checkState == State.GameState.EXIT) {
            paintExitMenu(g2);
        }
        else if (checkState == State.GameState.GAMEOVER) {
            paintGameOver(g2);
        } else {
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
        g2.drawImage(image, 0, 0, this);
    }

    public void paintGameOver(Graphics2D g2) {
        BufferedImage image = null;
        try {
            image = ImageIO.read(this.getClass().getResource("/menu/gameover.png"));
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
        g2.drawImage(image, 0, 0, this);
    }

    /**
     * Paints the score on the panel
     *
     * @param g2 the <code>Graphics2D</code> object used to draw
     */
    public void paintScore(Graphics2D g2) {
        int fontSize = 30;
        g2.setFont(new Font("Helvetica", Font.BOLD, fontSize));
        g2.drawString("Score:", (width + sideBarWidth / 2) - (fontSize + 20), 60);

        g2.setFont(new Font("Helvetica", Font.PLAIN, fontSize));
        g2.drawString(String.valueOf(score), width + sideBarWidth / 2 - (fontSize + 2), 100);
    }

    /**
     * Paints the timer on the panel
     *
     * @param g2 the <code>Graphics2D</code> object used to draw
     */
    public void paintTimer(Graphics2D g2) {
        int fontSize = 30;
        g2.setFont(new Font("Helvetica", Font.BOLD, fontSize));
        g2.drawString("Time:", (width + sideBarWidth / 2) - (fontSize + 8), height - 100);

        g2.setFont(new Font("Helvetica", Font.PLAIN, fontSize));
        g2.drawString(formatSeconds(secondsTimer), width + sideBarWidth / 2 - (fontSize + 2), height - 60);
    }

    /**
     * Formats seconds in integer form to a string of format XX:XX
     * @param seconds The seconds that will be formatted
     * @return The formatted seconds in format XX:XX
     */
    private String formatSeconds(int seconds) {
        String secondsHolder = "";
        String minutesHolder = "";
        int minute = seconds / 60;
        int second = seconds % 60;

        if (minute < 10) {
            minutesHolder = "0";
        }
        if (second < 10) {
            secondsHolder = "0";
        }
        return String.format("%s%d:%s%d", minutesHolder, minute, secondsHolder, second);
    }

    /**
     * Removes selected zookeeper from the list of enemies
     * @param zookeeper A non-null entity
     */
    public boolean removeZookeeper(Zookeeper zookeeper) {
        return zookeepers.remove(zookeeper);
    }
}
