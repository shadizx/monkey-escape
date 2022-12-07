package com.monkeyescape.entity.fixedentity;

import com.monkeyescape.entity.Position;
import com.monkeyescape.main.Game;
import com.monkeyescape.main.KeyHandler;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;

class KeyTest {
    private Key testKey;
    private Game testGame;
    private KeyHandler testKeyhandler;

    @BeforeEach
    void setup() {
        testKeyhandler = new KeyHandler();
        testGame = new Game(testKeyhandler);
        testKey = new Key(testGame);
    }

    @Test
    @DisplayName("Running loadImage with invalid image")
    void loadImageInvalidImage() {
        testKey.type = "notKey";
        assertFalse(testKey.loadImage());
    }

    @Test
    @DisplayName("Running createRandomPosition to ensure boundary conditions of positions")
    void createRandomPositionValidRange() {
        Position result = testKey.createRandomPosition(testGame);

        int expectedMaxX = testGame.tileSize * 14;
        int expectedMinX = testGame.tileSize * 1;
        int expectedMaxY = testGame.tileSize * 14;
        int expectedMinY = testGame.tileSize * 1;

        assertTrue(result.x >= expectedMinX);
        assertTrue(result.x <= expectedMaxX);

        assertTrue(result.y >= expectedMinY);
        assertTrue(result.y <= expectedMaxY);
    }

    @Test
    @DisplayName("Running useKey to ensure updating of panel")
    void useKeyValidRemoveUpdate() {
        testKey.remove();
        assertFalse(testGame.tileMap.tileMap[testGame.doorPos.x][testGame.doorPos.y].isBlocked);
    }
}
