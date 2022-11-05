package com.monkeyescape.main;

import java.util.concurrent.TimeUnit;

/**
 * Represents the States of the game
 * @author Kaleigh Toering
 * @version 10/31/2022
 */
public class State {
    /**
     * Holds various game states
     */
    public enum GameState {
        // display start menu
        START,
        // start playing game
        PLAY,
        // display pause menu
        PAUSE,
        // display game over screen
        GAMEOVER,
        // Start game over again
        RESTART,
        // display exit screen, then exit game window
        EXIT
    };

    private GameState CurrentState;

    /**
     * Sets the current game state.
     */
    public State() { CurrentState = GameState.START;}

    /**
     * Gets the current game state.
     * @return CurrentState
     */
    public GameState getGameState() { return CurrentState;}

    /**
     * Updates the game state to a new state.
     * @param newState
     */
    public void setGameState(GameState newState) { CurrentState = newState;}

    /**
     * Returns updated state based on given state and keypresses
     * @param kh
     * @return GameState
     * @author Kaleigh Toering
     */
    public void changeState(KeyHandler kh) {

        boolean enterPressed = kh.isPressedEnter();
        boolean escPressed = kh.isPressedEsc();
        boolean spacePressed = kh.isPressedSpace();
        boolean yPressed = kh.isPressedY();
        boolean nPressed = kh.isPressedN();
        // Start Menu --> Play Game
        if ((CurrentState == GameState.START) && enterPressed) {
            CurrentState =  GameState.PLAY;
        }
        // Play Game --> Pause Menu
        else if ((CurrentState == GameState.PLAY) && spacePressed) {
            CurrentState = State.GameState.PAUSE;
        }
        // Pause Menu --> Continue Playing
        else if ((CurrentState == GameState.PAUSE) && enterPressed) {
            CurrentState = State.GameState.PLAY;
        }
        // Pause Menu --> GameOver screen
        else if ((CurrentState == GameState.PAUSE) && escPressed) {
            CurrentState = GameState.GAMEOVER;
        }
        // Game over screen --> Restart Game
        else if ((CurrentState == GameState.GAMEOVER) && yPressed) {
            CurrentState = GameState.RESTART;
        }
        // Game over screen --> Exit
        else if ((CurrentState == GameState.GAMEOVER) && nPressed) {
            CurrentState = GameState.EXIT;
        }
        else if (CurrentState == GameState.RESTART) {
            CurrentState = GameState.START;
        }
        // Exit and quit
        else if ((CurrentState == GameState.EXIT)) {
            try {
                TimeUnit.SECONDS.sleep(4);
            }
            catch (InterruptedException e) {
                System.out.println("EXCEPTION: " + e);
            }
            System.exit(0);
        }
    }
}
