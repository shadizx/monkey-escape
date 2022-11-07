package com.monkeyescape.main;

import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;

/**
 * Handles key input
 *
 * @author Shadi Zoldjalali & Kaleigh Toering
 * @version 10/26/2022
 */
public class KeyHandler implements KeyListener {
    private boolean pressedUp;
    private boolean pressedRight;
    private boolean pressedDown;
    private boolean pressedLeft;
    private boolean pressedEsc;
    private boolean pressedEnter;
    private boolean pressedSpace;
    private boolean pressedY;
    private boolean pressedN;

    @Override
    public void keyTyped(KeyEvent e) {}

    /**
     * Updates which key has been pressed
     *
     * @param e the event to be processed
     */
    @Override
    public void keyPressed(KeyEvent e) {
        int code = e.getKeyCode();

        if (code == KeyEvent.VK_UP) {
            pressedUp = true;
        }
        else if (code == KeyEvent.VK_RIGHT) {
            pressedRight = true;
        }
        else if (code == KeyEvent.VK_DOWN) {
            pressedDown = true;
        }
        else if (code == KeyEvent.VK_LEFT) {
            pressedLeft = true;
        }
        else if (code == KeyEvent.VK_ESCAPE) {
            pressedEsc = true;
        }
        else if (code == KeyEvent.VK_ENTER) {
            pressedEnter = true;
        }
        else if (code == KeyEvent.VK_SPACE) {
            pressedSpace = true;
        }
        else if (code == KeyEvent.VK_Y) {
            pressedY = true;
        }
        else if (code == KeyEvent.VK_N) {
            pressedN = true;
        }
    }

    /**
     * Updates which key has been released
     *
     * @param e the event to be processed
     */
    @Override
    public void keyReleased(KeyEvent e) {
        int code = e.getKeyCode();

        if (code == KeyEvent.VK_UP) {
            pressedUp = false;
        }
        else if (code == KeyEvent.VK_RIGHT) {
            pressedRight = false;
        }
        else if (code == KeyEvent.VK_DOWN) {
            pressedDown = false;
        }
        else if (code == KeyEvent.VK_LEFT) {
            pressedLeft = false;
        }
        else if (code == KeyEvent.VK_ESCAPE) {
            pressedEsc = false;
        }
        else if (code == KeyEvent.VK_ENTER) {
            pressedEnter = false;
        }
        else if (code == KeyEvent.VK_SPACE) {
            pressedSpace = false;
        }
        else if (code == KeyEvent.VK_Y) {
            pressedY = false;
        }
        else if (code == KeyEvent.VK_N) {
            pressedN = false;
        }
    }

    public boolean isPressedUp() {
        return pressedUp;
    }

    public boolean isPressedRight() {
        return pressedRight;
    }

    public boolean isPressedDown() {
        return pressedDown;
    }

    public boolean isPressedLeft() {
        return pressedLeft;
    }

    public boolean isPressedEsc() { return pressedEsc;}

    public boolean isPressedEnter() {return pressedEnter;}

    public boolean isPressedSpace() {return pressedSpace;}

    public boolean isPressedY() {return pressedY;}
    public boolean isPressedN() {return pressedN;}
}
