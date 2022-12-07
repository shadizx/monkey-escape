package com.monkeyescape.main;

import com.monkeyescape.entity.Entity;
import com.monkeyescape.painter.Painter;

import javax.swing.JPanel;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.util.List;

/**
 * Represents a JPanel panel that is used for interaction
 *
 * @author Shadi Zoldjalali & Kaleigh Toering & Jeffrey Ramacula
 * @version 12/06/2022
 */
public class Panel extends JPanel implements Runnable {
    public final Game game;
    Sound sound;

    public final int tileSize = 48;
    public final int rows = 16;
    public final int cols = 16;
    public final int width = tileSize * cols;
    public final int height = tileSize * rows;
    public final int sideBarWidth = 200;
    public final int FPS = 60;

    protected KeyHandler keyHandler = new KeyHandler();
    private final Painter painter;
    private Thread gameThread;

    private List<Entity> entities;

    /**
     * Creates a new Panel with no sound for testing purposes
     *
     * @param noSound true for sound off, otherwise false
     */
    public Panel(boolean... noSound) {
        this.setPreferredSize(new Dimension(width + sideBarWidth, height));
        this.setBackground(Color.GREEN);
        this.setDoubleBuffered(true); // improves games rendering performance
        this.addKeyListener(keyHandler);
        this.setFocusable(true); // Game panel is "focused" to receive key input

        game = new Game(keyHandler);
        this.painter = new Painter(game, this);
        startGameThread();

        if (noSound.length == 0) {
            sound = new Sound();
            playMusic(0);
        }
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
            currentTime = System.nanoTime();

            delta += (currentTime - lastTime) / drawInterval;

            timerCount += (currentTime - lastTime);
            timerCount = addSeconds(timerCount);

            delayCount += (currentTime - lastTime);
            lastTime = currentTime;

            if (delta >= 1) {
                update();
                repaint();

                delta--;
            }
            if (game.collisionChecker.delayedDamages < 0 && delayCheck) {
                game.score += game.collisionChecker.delayedDamages;
                game.collisionChecker.delayedDamages = 0;
                delayCheck = false;
                cachedNanoTime = delayCount;
                startDelayTimer = true;
            }
            if ((delayCount >= 1000000000 + cachedNanoTime) && startDelayTimer) {
                delayCheck = true;
                game.collisionChecker.delayedDamages = 0;
                startDelayTimer = false;
            }
        }
    }
    /**
     * Adds 1 second to timer every 1000000000 nanoseconds
     *
     * @param timerCount time elapsed in nanoseconds
     * @return the time elapsed after 1 second
     */
    public long addSeconds(long timerCount) {
        if (game.state.getGameState() == State.GameState.PLAY) {
            if (timerCount >= 1000000000) {
                game.secondsTimer += 1;
                timerCount = 0;
            }
        }
        return timerCount;
    }

    /**
     * Updates game information
     */
    public void update() {
        game.update();
    }

    /**
     * Plays specified sound
     *
     * @param i index for the sound file
     */
    public void playMusic(int i) {
        sound.setFile(i);
        sound.play();
        sound.loop();
    }

    /**
     * Draws the UI
     */
    public void paintComponent(Graphics g) {
        super.paintComponent(g);
        Graphics2D g2 = (Graphics2D) g;
        painter.paintScore(g2);
        painter.paintTimer(g2);
        painter.paintLevel(g2);

        State.GameState gameState = game.state.getGameState();
        if (gameState != State.GameState.PLAY) {
            painter.paintMenu(g2, gameState.toString().toLowerCase());
        } else {
            game.tileMap.drawMap(g2);
            entities = game.getEntities();
            // use this type of for loop to not throw exception ConcurrentModificationException
            for (int i = 0; i < entities.size(); i++) {
                entities.get(i).draw(g2);
            }
            g2.dispose(); // good practice to save memory
        }
    }
}
