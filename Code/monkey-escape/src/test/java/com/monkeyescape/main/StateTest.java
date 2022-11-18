package com.monkeyescape.main;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.awt.Panel;
import java.awt.event.KeyEvent;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotEquals;

class StateTest {
    State state;
    Panel panel;
    KeyHandler kh;

    @BeforeEach
    void setup() {
        state = new State();
    }

    @Test
    @DisplayName("Running getGameState for default state")
    void getGameState() {
        assertEquals(state.getGameState(),State.GameState.START);
    }

    @Test
    @DisplayName("Running setGameState")
    void setGameState() {
        state.setGameState(State.GameState.EXIT);
        assertEquals(state.getGameState(), State.GameState.EXIT);
    }

    @BeforeEach
    void setup2() {
        kh = new KeyHandler();
        panel = new Panel();
    }

    @Test
    @DisplayName("Running changeState for start menu")
    void changeStateInstructions() {
        state.setGameState(State.GameState.START);
        kh.keyPressed(new KeyEvent(panel, KeyEvent.KEY_PRESSED, System.currentTimeMillis(), 0, KeyEvent.VK_ENTER, KeyEvent.CHAR_UNDEFINED));
        state.changeState(kh);
        assertEquals(state.getGameState(), State.GameState.INSTRUCTIONS);

        state.setGameState(State.GameState.START);
        kh.keyReleased(new KeyEvent(panel, KeyEvent.KEY_PRESSED, System.currentTimeMillis(), 0, KeyEvent.VK_ENTER, KeyEvent.CHAR_UNDEFINED));
        state.changeState(kh);
        assertNotEquals(state.getGameState(), State.GameState.INSTRUCTIONS);
    }

    @Test
    @DisplayName("Running changeState for instructions menu")
    void changeStatePlay() {
        state.setGameState(State.GameState.INSTRUCTIONS);
        state.changeState(kh);
        assertEquals(state.getGameState(), State.GameState.PLAY);
    }

    @Test
    @DisplayName("Running changeState for pause menu")
    void changeStatePause() {
        state.setGameState(State.GameState.PAUSE);
        kh.keyPressed(new KeyEvent(panel, KeyEvent.KEY_PRESSED, System.currentTimeMillis(), 0, KeyEvent.VK_ENTER, KeyEvent.CHAR_UNDEFINED));
        state.changeState(kh);
        assertEquals(state.getGameState(), State.GameState.PLAY);

        state.setGameState(State.GameState.PAUSE);
        kh.keyReleased(new KeyEvent(panel, KeyEvent.KEY_PRESSED, System.currentTimeMillis(), 0, KeyEvent.VK_ENTER, KeyEvent.CHAR_UNDEFINED));
        state.changeState(kh);
        assertEquals(state.getGameState(), State.GameState.PAUSE);

        kh.keyPressed(new KeyEvent(panel, KeyEvent.KEY_PRESSED, System.currentTimeMillis(), 0, KeyEvent.VK_ESCAPE, KeyEvent.CHAR_UNDEFINED));
        state.changeState(kh);
        assertEquals(state.getGameState(), State.GameState.GAMEOVER);
    }

    @Test
    @DisplayName("Running changeState for gameover menu")
    void changeStateGameover() {
        state.setGameState(State.GameState.GAMEOVER);
        state.changeState(kh);
        assertEquals(state.getGameState(), State.GameState.GAMEOVER);

        kh.keyPressed(new KeyEvent(panel, KeyEvent.KEY_PRESSED, System.currentTimeMillis(), 0, KeyEvent.VK_Y, KeyEvent.CHAR_UNDEFINED));
        state.changeState(kh);
        assertEquals(state.getGameState(), State.GameState.RESTART);

        kh.keyReleased(new KeyEvent(panel, KeyEvent.KEY_PRESSED, System.currentTimeMillis(), 0, KeyEvent.VK_Y, KeyEvent.CHAR_UNDEFINED));
        state.setGameState(State.GameState.GAMEOVER);
        kh.keyPressed(new KeyEvent(panel, KeyEvent.KEY_PRESSED, System.currentTimeMillis(), 0, KeyEvent.VK_N, KeyEvent.CHAR_UNDEFINED));
        state.changeState(kh);
        assertEquals(state.getGameState(), State.GameState.EXIT);
    }
}