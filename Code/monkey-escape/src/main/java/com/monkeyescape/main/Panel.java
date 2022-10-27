package com.monkeyescape.main;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.Graphics2D;

import javax.swing.JPanel;

/**
 * Represents a JPanel panel that is used for interaction
 * @author Shadi Zoldjalali
 * @version 10/26/2022
 */
public class Panel extends JPanel implements Runnable {

    public final int tileSize = 48;
    public final int rows = 16;
    public final int cols = 16;
    public final int width = tileSize * cols;
    public final int height = tileSize * rows;
    public final int FPS = 60;

    KeyHandler kh = new KeyHandler();
    Thread gameThread;

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

        // Game loop
        while (gameThread != null) {

            currentTime = System.nanoTime();

            delta += (currentTime - lastTime) / drawInterval;
            lastTime = currentTime;

            if (delta >= 1) {
                update();
                repaint();
                delta--;
            }
        }
    }

    /**
     * Updates game information
     */
    public void update() {

    }

    /**
     * Draws the UI
     */
    public void paintComponent(Graphics g) {
        super.paintComponent(g);
        Graphics2D g2 = (Graphics2D) g;

        // draw stuff here
        // draw stuff here

        g2.dispose(); // good practice to save memory
    }
}
