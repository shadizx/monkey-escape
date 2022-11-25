package com.monkeyescape.main;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.awt.Panel;
import java.awt.event.KeyEvent;

import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;

class KeyHandlerTest {
    Panel panel;
    private KeyHandler keyhandler;
    private KeyEvent keyevent;

    @BeforeEach
    public void setup(){
        keyhandler = new KeyHandler();
        panel = new Panel();
    }

    @Test
    @DisplayName("Running KeyEventPressed for UP key")
    void KeyEventPressedUp(){
        keyevent = new KeyEvent(panel, KeyEvent.KEY_PRESSED,System.currentTimeMillis(),0,KeyEvent.VK_UP,KeyEvent.CHAR_UNDEFINED);
        keyhandler.keyPressed(keyevent);
        assertTrue(keyhandler.isPressedUp());
    }

    @Test
    @DisplayName("Running KeyEventPressed for RIGHT key")
    void KeyEventPressedRight(){
        keyevent = new KeyEvent(panel, KeyEvent.KEY_PRESSED,System.currentTimeMillis(),0,KeyEvent.VK_RIGHT,KeyEvent.CHAR_UNDEFINED);
        keyhandler.keyPressed(keyevent);
        assertTrue(keyhandler.isPressedRight());
    }

    @Test
    @DisplayName("Running KeyEventPressed for DOWN key")
    void KeyEventPressedDown(){
        keyevent = new KeyEvent(panel, KeyEvent.KEY_PRESSED,System.currentTimeMillis(),0,KeyEvent.VK_DOWN,KeyEvent.CHAR_UNDEFINED);
        keyhandler.keyPressed(keyevent);
        assertTrue(keyhandler.isPressedDown());
    }

    @Test
    @DisplayName("Running KeyEventPressed for LEFT key")
    void KeyEventPressedLeft(){
        keyevent = new KeyEvent(panel, KeyEvent.KEY_PRESSED,System.currentTimeMillis(),0,KeyEvent.VK_LEFT,KeyEvent.CHAR_UNDEFINED);
        keyhandler.keyPressed(keyevent);
        assertTrue(keyhandler.isPressedLeft());
    }

    @Test
    @DisplayName("Running KeyEventPressed for ESC key")
    void KeyEventPressedEsc(){
        keyevent = new KeyEvent(panel, KeyEvent.KEY_PRESSED,System.currentTimeMillis(),0,KeyEvent.VK_ESCAPE,KeyEvent.CHAR_UNDEFINED);
        keyhandler.keyPressed(keyevent);
        assertTrue(keyhandler.isPressedEsc());
    }
    @Test
    @DisplayName("Running KeyEventPressed for ENTER key")
    void KeyEventPressedEnter(){
        keyevent = new KeyEvent(panel, KeyEvent.KEY_PRESSED,System.currentTimeMillis(),0,KeyEvent.VK_ENTER,KeyEvent.CHAR_UNDEFINED);
        keyhandler.keyPressed(keyevent);
        assertTrue(keyhandler.isPressedEnter());
    }
    @Test
    @DisplayName("Running KeyEventPressed for SPACE key")
    void KeyEventPressedSpace(){
        keyevent = new KeyEvent(panel, KeyEvent.KEY_PRESSED,System.currentTimeMillis(),0,KeyEvent.VK_SPACE,KeyEvent.CHAR_UNDEFINED);
        keyhandler.keyPressed(keyevent);
        assertTrue(keyhandler.isPressedSpace());
    }

    @Test
    @DisplayName("Running KeyEventPressed for Y key")
    void KeyEventPressedY(){
        keyevent = new KeyEvent(panel, KeyEvent.KEY_PRESSED,System.currentTimeMillis(),0,KeyEvent.VK_Y,KeyEvent.CHAR_UNDEFINED);
        keyhandler.keyPressed(keyevent);
        assertTrue(keyhandler.isPressedY());
    }

    @Test
    @DisplayName("Running KeyEventPressed for N key")
    void KeyEventPressedN(){
        keyevent = new KeyEvent(panel, KeyEvent.KEY_PRESSED,System.currentTimeMillis(),0,KeyEvent.VK_N,KeyEvent.CHAR_UNDEFINED);
        keyhandler.keyPressed(keyevent);
        assertTrue(keyhandler.isPressedN());
    }

    @Test
    @DisplayName("Running KeyEventPressed for invalid key")
    void InvalidKeyEventPressed(){
        keyevent = new KeyEvent(panel, KeyEvent.KEY_PRESSED,System.currentTimeMillis(),0,KeyEvent.VK_F24,KeyEvent.CHAR_UNDEFINED);
        keyhandler.keyPressed(keyevent);
        assertFalse(keyhandler.isPressedUp());
        assertFalse(keyhandler.isPressedRight());
        assertFalse(keyhandler.isPressedDown());
        assertFalse(keyhandler.isPressedLeft());
        assertFalse(keyhandler.isPressedEsc());
        assertFalse(keyhandler.isPressedEnter());
        assertFalse(keyhandler.isPressedSpace());
        assertFalse(keyhandler.isPressedY());
        assertFalse(keyhandler.isPressedN());
    }

    @Test
    @DisplayName("Running KeyEventReleased for UP key")
    void KeyEventReleasedUp(){
        keyevent = new KeyEvent(panel, KeyEvent.KEY_RELEASED,System.currentTimeMillis(),0,KeyEvent.VK_UP,KeyEvent.CHAR_UNDEFINED);
        keyhandler.keyReleased(keyevent);
        assertFalse(keyhandler.isPressedN());
    }

    @Test
    @DisplayName("Running KeyEventReleased for RIGHT key")
    void KeyEventReleasedRight(){
        keyevent = new KeyEvent(panel, KeyEvent.KEY_RELEASED,System.currentTimeMillis(),0,KeyEvent.VK_RIGHT,KeyEvent.CHAR_UNDEFINED);
        keyhandler.keyReleased(keyevent);
        assertFalse(keyhandler.isPressedRight());
    }

    @Test
    @DisplayName("Running KeyEventReleased for DOWN key")
    void KeyEventReleasedDown(){
        keyevent = new KeyEvent(panel, KeyEvent.KEY_RELEASED,System.currentTimeMillis(),0,KeyEvent.VK_DOWN,KeyEvent.CHAR_UNDEFINED);
        keyhandler.keyReleased(keyevent);
        assertFalse(keyhandler.isPressedDown());
    }

    @Test
    @DisplayName("Running KeyEventReleased for LEFT key")
    void KeyEventReleasedLeft(){
        keyevent = new KeyEvent(panel, KeyEvent.KEY_RELEASED,System.currentTimeMillis(),0,KeyEvent.VK_LEFT,KeyEvent.CHAR_UNDEFINED);
        keyhandler.keyReleased(keyevent);
        assertFalse(keyhandler.isPressedLeft());
    }
    @Test
    @DisplayName("Running KeyEventReleased for ESC key")
    void KeyEventReleasedEsc(){
        keyevent = new KeyEvent(panel, KeyEvent.KEY_RELEASED,System.currentTimeMillis(),0,KeyEvent.VK_ESCAPE,KeyEvent.CHAR_UNDEFINED);
        keyhandler.keyReleased(keyevent);
        assertFalse(keyhandler.isPressedEsc());
    }

    @Test
    @DisplayName("Running KeyEventReleased for ENTER key")
    void KeyEventReleasedEnter(){
        keyevent = new KeyEvent(panel, KeyEvent.KEY_RELEASED,System.currentTimeMillis(),0,KeyEvent.VK_ENTER,KeyEvent.CHAR_UNDEFINED);
        keyhandler.keyReleased(keyevent);
        assertFalse(keyhandler.isPressedEnter());
    }

    @Test
    @DisplayName("Running KeyEventReleased for SPACE key")
    void KeyEventReleasedSpace(){
        keyevent = new KeyEvent(panel, KeyEvent.KEY_RELEASED,System.currentTimeMillis(),0,KeyEvent.VK_SPACE,KeyEvent.CHAR_UNDEFINED);
        keyhandler.keyReleased(keyevent);
        assertFalse(keyhandler.isPressedSpace());
    }

    @Test
    @DisplayName("Running KeyEventReleased for Y key")
    void KeyEventReleasedY(){
        keyevent = new KeyEvent(panel, KeyEvent.KEY_RELEASED,System.currentTimeMillis(),0,KeyEvent.VK_Y,KeyEvent.CHAR_UNDEFINED);
        keyhandler.keyReleased(keyevent);
        assertFalse(keyhandler.isPressedY());
    }

    @Test
    @DisplayName("Running KeyEventReleased for N key")
    void KeyEventReleasedN(){
        keyevent = new KeyEvent(panel, KeyEvent.KEY_RELEASED,System.currentTimeMillis(),0,KeyEvent.VK_N,KeyEvent.CHAR_UNDEFINED);
        keyhandler.keyReleased(keyevent);
        assertFalse(keyhandler.isPressedN());
    }
}
