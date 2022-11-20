package com.monkeyescape.fixedentity;

import com.monkeyescape.entity.Position;
import com.monkeyescape.entity.fixedentity.Key;
import com.monkeyescape.entity.fixedentity.LionPit;
import com.monkeyescape.main.Game;
import com.monkeyescape.main.Panel;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;

class LionPitTest {

    private LionPit testLionPit;
    private Panel testPanel;
    private Game testGame;

    @BeforeEach
    void setup() {
        testGame = new Game(false, false);
        testPanel = new Panel(testGame);
        testLionPit = new LionPit(testPanel);
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
        Position result = testLionPit.createRandomPosition(testPanel);

        int expectedMaxX = testPanel.tileSize * 14;
        int expectedMinX = testPanel.tileSize * 1;
        int expectedMaxY = testPanel.tileSize * 14;
        int expectedMinY = testPanel.tileSize * 1;

        assertTrue(result.x >= expectedMinX);
        assertTrue(result.x <= expectedMaxX);

        assertTrue(result.y >= expectedMinY);
        assertTrue(result.y <= expectedMaxY);
    }

}
