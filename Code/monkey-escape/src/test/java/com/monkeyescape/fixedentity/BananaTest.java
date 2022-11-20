package com.monkeyescape.fixedentity;

import com.monkeyescape.entity.fixedentity.Banana;
import com.monkeyescape.entity.Position;
import com.monkeyescape.main.Game;
import com.monkeyescape.main.Panel;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;


import static org.junit.jupiter.api.Assertions.*;

class BananaTest {
        private Banana testBanana;
        private Panel testPanel;
        private Game testGame;

        @BeforeEach
        void setup() {
            testGame = new Game(false, false);
            testPanel = new Panel(testGame);
            testBanana = new Banana(testPanel);
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
            assertTrue(testBanana.getLifecycle() == newLifeCycle - 1);
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
             Position result = testBanana.createRandomPosition(testPanel);

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

