package com.monkeyescape.entity.fixedentity;

import com.monkeyescape.entity.Position;
import com.monkeyescape.main.Game;
import com.monkeyescape.main.KeyHandler;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;

class BananaTest {
    private Banana testBanana;
    private Game testGame;
    private KeyHandler testKeyhandler;

    @BeforeEach
    void setup() {
        testKeyhandler = new KeyHandler();
        testGame = new Game(testKeyhandler);
        testBanana = new Banana(testGame);
    }

    @Test
    @DisplayName("Running constructor to validate lifecycle is between 5 and 10 seconds")
    void lifecycleWithinRange() {
        // min frames is 480, max frames is 780
        assertTrue(testBanana.getLifecycle() >= 480);
        assertTrue(testBanana.getLifecycle() <= 780);
    }

    @Test
    @DisplayName("Running update to ensure lifecycle is decremented")
    void updateBananaLifecycleLessThanZero() {
        int newLifeCycle = -10;
        testBanana.setLifecycle(newLifeCycle);
        testBanana.update();
        assertEquals(testBanana.getLifecycle(), newLifeCycle - 1);
    }

    @Test
    @DisplayName("Running loadImage with invalid image")
    void loadImageInvalidImage() {
        testBanana.type = "notBanana";
        assertFalse(testBanana.loadImage());
    }

    @Test
    @DisplayName("Running createRandomPosition to ensure boundary conditions of positions")
    void createRandomPositionValidRange() {
         Position result = testBanana.createRandomPosition(testGame);

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
