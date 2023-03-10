package com.monkeyescape.main;

import javax.swing.JFrame;

/**
 * Represents a JFrame window
 *
 * @author Shadi Zoldjalali
 * @version 11/23/2022
 */
public class Window {
    public static JFrame window;

    /**
     * Creates a resizable default window with title <p>Monkey Escape</p>
     */
    public Window() {
        window = new JFrame();
        window.setTitle("Monkey Escape");
        window.setResizable(false);
        window.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
    }

    /**
     * Adds a panel to the window
     *
     * @param panel A <code>Panel</code> to refer to
     */
    public void addPanel(Panel panel) {
        window.add(panel);
        window.pack();
        window.setLocationRelativeTo(null);
        window.setVisible(true);
    }
}
