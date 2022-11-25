package com.monkeyescape.main;

/**
 * Runs the main method
 *
 * @author Shadi Zoldjalali & Jeffrey Ramacula
 * @version 11/23/2022
 */
public class Main {
    /**
     * Initializes a new game
     *
     * @param args The command line arguments.
     */
    public static void main(String[] args) {
        Window window = new Window();
        Panel panel = new Panel();
        window.addPanel(panel);
    }
}
