package com.monkeyescape.entity.movingentity;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import com.monkeyescape.main.Game;
import com.monkeyescape.main.KeyHandler;
import com.monkeyescape.main.State;
import com.monkeyescape.map.Tile;

import java.awt.Panel;
import java.awt.event.KeyEvent;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;

class MonkeyTest {
    private Panel panel = new Panel();
    private Monkey monkey;

    private KeyHandler keyHandler = new KeyHandler();
    private Game game = new Game(keyHandler);

    @BeforeEach
    void setup() {
        monkey = new Monkey(game,keyHandler);
        for (Tile[] tileRow : game.tileMap.tileMap) {
            for (Tile tile : tileRow) {
                tile.isBlocked = false;
            }
        }
    }

    @Test
    @DisplayName("Testing Monkey update function while not in play mode")
    void updateWhileNotInPlay() {
        //Set the game state to not be in play
        game.state.setGameState(State.GameState.START);
        int x = monkey.getXCoordinate();
        int y = monkey.getYCoordinate();

        monkey.update();

        //Check that monkey did not move positions
        assertEquals(x, monkey.getXCoordinate());
        assertEquals(y, monkey.getYCoordinate());
    }   

    @Test
    @DisplayName("Testing Monkey update function while in play mode")
    void updateWhileInPlay() {
        game.state.setGameState(State.GameState.PLAY);
        Monkey.inLionPit = false;
        //Check the monkey's movement depending on its direction
        //Jump
        int beforeX = monkey.getXCoordinate();
        int beforeY = monkey.getYCoordinate();
        monkey.update();
        assertEquals(beforeX, monkey.getXCoordinate());
        assertEquals(beforeY, monkey.getYCoordinate());

        //Left
        keyHandler.keyPressed(new KeyEvent(panel, KeyEvent.KEY_PRESSED,System.currentTimeMillis(),0,KeyEvent.VK_LEFT,KeyEvent.CHAR_UNDEFINED));
        if(keyHandler.isPressedLeft()){
            beforeX = monkey.getXCoordinate();
            monkey.update();
            assertEquals(beforeX-monkey.speed, monkey.getXCoordinate());
        }
        keyHandler.keyReleased(new KeyEvent(panel, KeyEvent.KEY_RELEASED,System.currentTimeMillis(),0,KeyEvent.VK_LEFT,KeyEvent.CHAR_UNDEFINED));

        //Right
        keyHandler.keyPressed(new KeyEvent(panel, KeyEvent.KEY_PRESSED,System.currentTimeMillis(),0,KeyEvent.VK_RIGHT,KeyEvent.CHAR_UNDEFINED));
        if(keyHandler.isPressedRight()){
            beforeX = monkey.getXCoordinate();
            monkey.update();
            assertEquals(beforeX+monkey.speed, monkey.getXCoordinate());
        }
        keyHandler.keyReleased(new KeyEvent(panel, KeyEvent.KEY_RELEASED,System.currentTimeMillis(),0,KeyEvent.VK_RIGHT,KeyEvent.CHAR_UNDEFINED));

        //Up
        keyHandler.keyPressed(new KeyEvent(panel, KeyEvent.KEY_PRESSED,System.currentTimeMillis(),0,KeyEvent.VK_UP,KeyEvent.CHAR_UNDEFINED));
        if(keyHandler.isPressedUp()){
            beforeY = monkey.getYCoordinate();
            monkey.update();
            assertEquals(beforeY-monkey.speed, monkey.getYCoordinate());
        }
        keyHandler.keyReleased(new KeyEvent(panel, KeyEvent.KEY_RELEASED,System.currentTimeMillis(),0,KeyEvent.VK_UP,KeyEvent.CHAR_UNDEFINED));

        //Down
        keyHandler.keyPressed(new KeyEvent(panel, KeyEvent.KEY_PRESSED,System.currentTimeMillis(),0,KeyEvent.VK_DOWN,KeyEvent.CHAR_UNDEFINED));
        if(keyHandler.isPressedDown()){
            beforeY = monkey.getYCoordinate();
            monkey.update();
            assertEquals(beforeY+monkey.speed, monkey.getYCoordinate());
        }
        keyHandler.keyReleased(new KeyEvent(panel, KeyEvent.KEY_RELEASED,System.currentTimeMillis(),0,KeyEvent.VK_DOWN,KeyEvent.CHAR_UNDEFINED));


        //Test MonkeyInLionPit functionality
        Monkey.inLionPit = true;

        //Being in lion pit triggers jump
        monkey.direction = "up";
        monkey.update();
        assertEquals("jump",monkey.direction);

        //Check that timeInLionPit increments
        int beforeTime = monkey.timeInLionPit;
        monkey.update();
        assertEquals(beforeTime+1,monkey.timeInLionPit);

        //Check that timeInLionPit caps at 120
        monkey.timeInLionPit = 119;
        monkey.update();
        assertEquals(0,monkey.timeInLionPit); //It resets
        assertFalse(Monkey.inLionPit);
        assertEquals(60, Monkey.lionPitInvincibility);

        //Check that lionPitInvincibility decrements
        int beforeInvincibility = 60;
        assertEquals(59, beforeInvincibility-1);
    }    
}
