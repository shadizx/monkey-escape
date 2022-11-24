package com.monkeyescape.main;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;

class PanelTest {
    private Panel testPanel;

    @BeforeEach
    void setup(){
        testPanel = new Panel(true);
    }



    @Test
    @DisplayName("Running addSecondsTest")
    void addSecondsTest(){
        testPanel.game.state.setGameState(State.GameState.PLAY);
        testPanel.game.secondsTimer = 0;
        //1000000000 nanoseconds = 1 second
        testPanel.addSeconds(1000000000);
        assertEquals(1,testPanel.game.secondsTimer);
    }
    @Test
    @DisplayName("Running addSecondsTestLessThan1Second")
    void addSecondsTestLessThan1Second(){
        testPanel.game.state.setGameState(State.GameState.PLAY);
        testPanel.game.secondsTimer = 0;
        //1000000000 nanoseconds = 1 second
        testPanel.addSeconds(999999999);
        assertEquals(0,testPanel.game.secondsTimer);
    }
    @Test
    @DisplayName("Running addSecondsTestNotPlayState")
    void addSecondsTestNotPlayState(){
        testPanel.game.state.setGameState(State.GameState.INSTRUCTIONS);
        testPanel.game.secondsTimer = 0;
        //1000000000 nanoseconds = 1 second
        testPanel.addSeconds(1000000001);
        assertEquals(0,testPanel.game.secondsTimer);
    }
}
