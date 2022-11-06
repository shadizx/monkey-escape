package com.monkeyescape.painter;

import com.monkeyescape.main.Game;
import com.monkeyescape.main.Panel;

import javax.imageio.ImageIO;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.IOException;

/**
 * Represents a Painter class used for painting UI components
 *
 * @author Kaleigh Toering & Shadi Zoldjalali
 * @version 11/04/2022
 */
public class Painter {
    private final Panel panel;

    /**
     * Creates a Painter object
     * @param panel a <code>Panel</code> to refer to
     */
    public Painter(Panel panel) {
        this.panel = panel;
    }

    /**
     * Paints a menu on the screen
     * @param g2 graphics item
     * @param menuType the menu type to be displayed
     */
    public void paintMenu(Graphics2D g2, String menuType) {
        BufferedImage image = null;
        try {
            image = ImageIO.read(this.getClass().getResource(String.format("/menu/%s.png", menuType)));
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
        g2.drawImage(image, 0, 0, panel);
    }

    /**
     * Paints the score on the panel
     *
     * @param g2 the <code>Graphics2D</code> object used to draw
     */
    public void paintScore(Graphics2D g2) {
        int fontSize = 30;
        g2.setFont(new Font("Helvetica", Font.BOLD, fontSize));
        g2.drawString("Score:", (panel.width + panel.sideBarWidth / 2) - (fontSize + 20), 60);

        g2.setFont(new Font("Helvetica", Font.PLAIN, fontSize));
        g2.drawString(String.valueOf(panel.score), panel.width + panel.sideBarWidth / 2 - (fontSize + 2), 100);
    }

    /**
     * Paints the timer on the panel
     *
     * @param g2 the <code>Graphics2D</code> object used to draw
     */
    public void paintTimer(Graphics2D g2) {
        int fontSize = 30;
        g2.setFont(new Font("Helvetica", Font.BOLD, fontSize));
        g2.drawString("Time:", (panel.width + panel.sideBarWidth / 2) - (fontSize + 8), panel.height - 100);

        g2.setFont(new Font("Helvetica", Font.PLAIN, fontSize));
        g2.drawString(formatSeconds(panel.secondsTimer), panel.width + panel.sideBarWidth / 2 - (fontSize + 2), panel.height - 60);
    }

    /**
     * Formats seconds in integer form to a string of format XX:XX
     *
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
     * Paints the level on the panel
     *
     * @param g2 the <code>Graphics2D</code> object used to draw
     */
    public void paintLevel(Graphics2D g2) {
        int fontSize = 30;
        g2.setFont(new Font("Helvetica", Font.BOLD, fontSize));
        g2.drawString("Level:", (panel.width + panel.sideBarWidth / 2) - (fontSize + 20), panel.height/2);

        g2.setFont(new Font("Helvetica", Font.PLAIN, fontSize));
        g2.drawString(String.valueOf(Game.level), panel.width + panel.sideBarWidth / 2 - (fontSize + 2), (panel.height/2) + 40);
    }
}
