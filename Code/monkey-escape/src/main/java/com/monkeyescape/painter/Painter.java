package com.monkeyescape.painter;

import com.monkeyescape.main.Game;
import com.monkeyescape.main.Panel;

import javax.imageio.ImageIO;
import java.awt.Font;
import java.awt.Graphics2D;
import java.awt.image.BufferedImage;

/**
 * Represents a Painter class used for painting UI components
 *
 * @author Kaleigh Toering & Shadi Zoldjalali
 * @version 12/06/2022
 */
public class Painter {
    private final Panel panel;
    private final Game game;

    /**
     * Creates a Painter object
     *
     * @param game  a <code>Game</code> refer to
     * @param panel a <code>Panel</code> to draw on
     */
    public Painter(Game game, Panel panel) {
        this.panel = panel;
        this.game = game;
    }

    /**
     * Paints a menu on the screen
     *
     * @param g2 the <code>Graphics2D</code> object used to draw
     * @param menuType the menu type to be displayed
     */
    public void paintMenu(Graphics2D g2, String menuType) {
        BufferedImage image = getImage(menuType);
        g2.drawImage(image, 0, 0, panel);
    }

    /**
     * Gets the Image of then menu to be displayed
     *
     * @param menuType the menu type to be displayed
     * @return The image of the selected menu type
     * */
    public BufferedImage getImage(String menuType) {
        BufferedImage image = null;
        try {
            image = ImageIO.read(this.getClass().getResource(String.format("/menu/%s.png", menuType)));
        } catch (Exception e) {
            System.out.printf("Error %s occurred while getting images for %s%n", e, menuType);
            e.printStackTrace();
        }
        return image;
    }

    /**
     * Paints the score on the panel
     *
     * @param g2 the <code>Graphics2D</code> object used to draw
     */
    public void paintScore(Graphics2D g2) {
        int fontSize = 30;
        g2.setFont(new Font("Helvetica", Font.BOLD, fontSize));
        g2.drawString("Score:", (game.width + panel.sideBarWidth / 2) - (fontSize + 20), 60);

        g2.setFont(new Font("Helvetica", Font.PLAIN, fontSize));
        g2.drawString(String.valueOf(game.score), game.width + panel.sideBarWidth / 2 - (fontSize + 2), 100);
    }

    /**
     * Paints the timer on the panel
     *
     * @param g2 the <code>Graphics2D</code> object used to draw
     */
    public void paintTimer(Graphics2D g2) {
        int fontSize = 30;
        g2.setFont(new Font("Helvetica", Font.BOLD, fontSize));
        g2.drawString("Time:", (game.width + panel.sideBarWidth / 2) - (fontSize + 8), game.height - 100);

        g2.setFont(new Font("Helvetica", Font.PLAIN, fontSize));
        g2.drawString(formatSeconds(game.secondsTimer), game.width + panel.sideBarWidth / 2 - (fontSize + 2), game.height - 60);
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
        g2.drawString("Level:", (game.width + panel.sideBarWidth / 2) - (fontSize + 20), game.height/2);

        g2.setFont(new Font("Helvetica", Font.PLAIN, fontSize));
        g2.drawString(String.valueOf(Game.level), game.width + panel.sideBarWidth / 2 - (fontSize + 2), (game.height/2) + 40);
    }
}
