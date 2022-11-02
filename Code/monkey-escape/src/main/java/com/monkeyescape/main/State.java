package com.monkeyescape.main;

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
        START,
        PLAY,
        PAUSE,
        EXIT
    };

    private GameState CurrentState;
    private GameState PastState;

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
        GameState tempState = CurrentState;

        boolean enterPressed = kh.isPressedEnter();
        boolean escPressed = kh.isPressedEsc();
        boolean spacePressed = kh.isPressedSpace();
        // Start Menu --> Play Game
        if ((tempState == GameState.START) && enterPressed) {
            CurrentState =  GameState.PLAY;
        }
        // Play Game --> Pause Menu
        else if ((tempState == GameState.PLAY) && spacePressed) {
            CurrentState = State.GameState.PAUSE;
            PastState = GameState.PLAY;
        }
        // Pause Menu --> Continue Playing
        else if ((tempState == GameState.PAUSE) && enterPressed) {
            CurrentState = State.GameState.PLAY;
        }
        // Pause Menu --> Exit Screen
        else if ((tempState == GameState.PAUSE) && escPressed) {
            CurrentState = GameState.EXIT;
        }
        // Don't Change State
        else {
            CurrentState =  tempState;
            PastState = PastState;
        }
    }
}
