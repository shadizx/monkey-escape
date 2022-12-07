package com.monkeyescape.main;

import java.util.concurrent.TimeUnit;

/**
 * Represents the States of the game
 *
 * @author Kaleigh Toering
 * @version 11/23/2022
 */
public class State {
    /**
     * Holds various game states
     */
    public enum GameState {
        // display start menu
        START,
        INSTRUCTIONS,
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
     * Creates a new state with default <code>START</code>
     */
    public State() { CurrentState = GameState.START;}

    /**
     * Returns updated state based on given state and key presses
     *
     * @param kh a <code>KeyHandler</code> to refer to
     */
    public void changeState(KeyHandler kh) {
        // Start Menu --> Play Game
        if ((CurrentState == GameState.START) && kh.isPressedEnter()) {
            CurrentState = GameState.INSTRUCTIONS;
        }
        else if (CurrentState == GameState.INSTRUCTIONS) {
            try {
                TimeUnit.SECONDS.sleep(3);
            }
            catch (InterruptedException e) {
                System.out.println("EXCEPTION: " + e);
            }
            CurrentState = GameState.PLAY;
        }
        // Play Game --> Pause Menu
        else if ((CurrentState == GameState.PLAY) && kh.isPressedSpace()) {
            CurrentState = State.GameState.PAUSE;
        }
        // Pause Menu --> Continue Playing
        else if ((CurrentState == GameState.PAUSE) && kh.isPressedEnter()) {
            CurrentState = State.GameState.PLAY;
        }
        // Pause Menu --> GameOver screen
        else if ((CurrentState == GameState.PAUSE) && kh.isPressedEsc()) {
            CurrentState = GameState.GAMEOVER;
        }
        // Game over screen --> Restart Game
        else if ((CurrentState == GameState.GAMEOVER) && kh.isPressedY()) {
            CurrentState = GameState.RESTART;
        }
        // Game over screen --> Exit
        else if ((CurrentState == GameState.GAMEOVER) && kh.isPressedN()) {
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

    public GameState getGameState() { return CurrentState; }

    public void setGameState(GameState newState) { CurrentState = newState; }
}
