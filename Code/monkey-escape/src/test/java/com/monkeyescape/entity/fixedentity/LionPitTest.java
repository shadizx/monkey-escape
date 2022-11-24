package com.monkeyescape.entity.fixedentity;

import com.monkeyescape.entity.Position;
import com.monkeyescape.entity.fixedentity.Key;
import com.monkeyescape.entity.fixedentity.LionPit;
import com.monkeyescape.main.Game;
import com.monkeyescape.main.KeyHandler;
import com.monkeyescape.main.Panel;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;

class LionPitTest {

    private LionPit testLionPit;
    private Game testGame;
    private KeyHandler testKeyhandler;

    @BeforeEach
    void setup() {
        testKeyhandler = new KeyHandler();
        testGame = new Game(testKeyhandler);
        testLionPit = new LionPit(testGame);
    }

    @Test
    @DisplayName("Running loadImage with invalid image")
    void loadImageInvalidImage() {
        testLionPit.type = "notLionPit";
        assertFalse(testLionPit.loadImage());
    }

    @Test
    @DisplayName("Running createRandomPosition to ensure boundary conditions of positions")
    void createRandomPositionValidRange() {
        Position result = testLionPit.createRandomPosition(testGame);

        int expectedMaxX = testGame.tileSize * 14;
        int expectedMinX = testGame.tileSize * 1;
        int expectedMaxY = testGame.tileSize * 14;
        int expectedMinY = testGame.tileSize * 1;

        assertTrue(result.x >= expectedMinX);
        assertTrue(result.x <= expectedMaxX);

        assertTrue(result.y >= expectedMinY);
        assertTrue(result.y <= expectedMaxY);
    }

}
