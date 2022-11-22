package com.monkeyescape.main;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;

class PanelTest {

    private Panel testPanel;
    private Game testGame;

    @BeforeEach
    void setup(){
        testGame = new Game(false, false);
        testPanel = new Panel(testGame);
    }

    @Test
    @DisplayName("Running GameOverTestNegativeScore score < 0")
    void GameOverTestNegativeScore(){
        testPanel.state.setGameState(State.GameState.PLAY);
        testPanel.score = -1;
        testPanel.update();
        assertEquals(State.GameState.GAMEOVER, testPanel.state.getGameState());
    }
    @Test
    @DisplayName("Running GameOverTestNegativeScoreWithGameStateInstructions score < 0, state = START")
    void GameOverTestNegativeScoreWithGameStateSTART(){
        testPanel.state.setGameState(State.GameState.START);
        testPanel.score = -1;
        assertEquals(State.GameState.START, testPanel.state.getGameState());
        testPanel.update();
        assertEquals(State.GameState.START, testPanel.state.getGameState());
    }

    @Test
    @DisplayName("Running NotGameOverTestPositiveScore score >= 0")
    void NotGameOverTestPositiveScore(){
        testPanel.state.setGameState(State.GameState.PLAY);
        testPanel.score = 0;
        testPanel.update();
        assertEquals(State.GameState.PLAY, testPanel.state.getGameState());
    }

    @Test
    @DisplayName("Running RestartTestAfterUpdate")
    void RestartTestAfterUpdate(){
        testPanel.state.setGameState(State.GameState.RESTART);
        assertEquals(State.GameState.RESTART, testPanel.state.getGameState());
        testPanel.update();
        assertEquals(State.GameState.START, testPanel.state.getGameState());
    }

    @Test
    @DisplayName("Running addSecondsTest")
    void addSecondsTest(){
        testPanel.state.setGameState(State.GameState.PLAY);
        testPanel.secondsTimer = 0;
        //1000000000 nanoseconds = 1 second
        testPanel.addSeconds(1000000000);
        assertEquals(1,testPanel.secondsTimer);
    }
    @Test
    @DisplayName("Running addSecondsTestLessThan1Second")
    void addSecondsTestLessThan1Second(){
        testPanel.state.setGameState(State.GameState.PLAY);
        testPanel.secondsTimer = 0;
        //1000000000 nanoseconds = 1 second
        testPanel.addSeconds(999999999);
        assertEquals(0,testPanel.secondsTimer);
    }
    @Test
    @DisplayName("Running addSecondsTestNotPlayState")
    void addSecondsTestNotPlayState(){
        testPanel.state.setGameState(State.GameState.INSTRUCTIONS);
        testPanel.secondsTimer = 0;
        //1000000000 nanoseconds = 1 second
        testPanel.addSeconds(1000000001);
        assertEquals(0,testPanel.secondsTimer);
    }

}
