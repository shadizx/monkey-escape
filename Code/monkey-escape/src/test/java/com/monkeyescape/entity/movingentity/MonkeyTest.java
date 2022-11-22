package com.monkeyescape.entity.movingentity;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import com.monkeyescape.main.Game;
import com.monkeyescape.main.KeyHandler;
import com.monkeyescape.main.State;
import com.monkeyescape.map.Tile;
import com.monkeyescape.main.Panel;

import static org.junit.jupiter.api.Assertions.assertEquals;

class MonkeyTest {
    private Monkey monkey;

    private Panel panel;
    private Game game = new Game(false,false);
    private KeyHandler keyHandler = new KeyHandler();

    @BeforeEach
    void setup() {
        panel = new Panel(game);
        monkey = new Monkey(panel,keyHandler);
        for (Tile[] tileRow : panel.tm.tileMap) {
            for (Tile tile : tileRow) {
                tile.blocked = false;
            }
        }
    }

    @Test
    @DisplayName("Testing Monkey update function while not in play mode")
    void updateWhileNotInPlay() {
        //Set the game state to not be in play
        panel.state.setGameState(State.GameState.START);
        int x = monkey.x;
        int y = monkey.y;

        monkey.update();

        //Check that monkey did not move positions
        assertEquals(x, monkey.x);
        assertEquals(y, monkey.y);
    }   

    @Test
    @DisplayName("Testing Monkey update function while in play mode")
    void updateWhileInPlay() {
        panel.state.setGameState(State.GameState.PLAY);

        //Check the monkey's movement depending on it's direction
        //Jump
        int beforeX = monkey.x;
        int beforeY = monkey.y;
        monkey.update();
        assertEquals(beforeX, monkey.x);
        assertEquals(beforeY, monkey.y);

        //Left
        if(keyHandler.isPressedLeft()){
            beforeX = monkey.x;
            monkey.update();
            assertEquals(beforeX-monkey.speed, monkey.x);
        }

        //Right
        if(keyHandler.isPressedRight()){
            beforeX = monkey.x;
            monkey.update();
            assertEquals(beforeX+monkey.speed, monkey.x);
        }

        //Up
        if(keyHandler.isPressedUp()){
            beforeY = monkey.y;
            monkey.update();
            assertEquals(beforeY-monkey.speed, monkey.y);
        }

        //Down
        if(keyHandler.isPressedDown()){
            beforeY = monkey.y;
            monkey.update();
            assertEquals(beforeY+monkey.speed, monkey.y);
        }


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
        assertEquals(false, Monkey.inLionPit);
        assertEquals(60, Monkey.lionPitInvincibility);

        //Check that lionPitInvincibility decrements
        int beforeInvincibility = 60;
        assertEquals(59, beforeInvincibility-1);
    }    
}