package com.monkeyescape.main;

/**
 * Represents the game
 * @author Shadi Zoldjalali
 * @version 10/26/2022
 */
public class Game {

    Window window;
    Panel panel;

    /**
     * Initializes window and panel, and starts the game loop
     */
    public Game() {
        window = new Window();
        panel = new Panel();
        window.addPanel(panel);
        panel.startGameThread();
    }
}
