package com.monkeyescape.entity.movingentity;

import com.monkeyescape.main.Game;
import com.monkeyescape.main.KeyHandler;
import com.monkeyescape.main.Panel;
import com.monkeyescape.main.State;
import com.monkeyescape.map.Tile;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestInstance;

import static org.junit.jupiter.api.Assertions.assertEquals;

@TestInstance(TestInstance.Lifecycle.PER_CLASS)
class ZookeeperTest {
    private final Game game = new Game(false, false);
    private final Panel panel = new Panel(game);
    private final KeyHandler kh = new KeyHandler();
    private final Monkey monkey = new Monkey(panel, kh);
    private final Zookeeper zookeeper = new Zookeeper(panel, kh, monkey);

    @BeforeAll
    public void setup() {
        for (Tile[] tileRow : panel.tm.tileMap) {
            for (Tile tile : tileRow) {
                tile.blocked = false;
            }
        }
    }

    @Test
    @DisplayName("Running update for zookeeper when game is not in play mode")
    void updateNotInPlayTest() {
        // Assert that zookeeper is not moving when game is not in play mode
        panel.state.setGameState(State.GameState.PAUSE);
        int currX = zookeeper.x;
        int currY = zookeeper.y;

        zookeeper.update();
        assertEquals(zookeeper.x, currX);
        assertEquals(zookeeper.y, currY);
    }

    @Test
    @DisplayName("Running update for zookeeper when game is in play mode")
    void updateInPlayTest() {
        panel.state.setGameState(State.GameState.PLAY);

        // When direction is up zookeeper's y pos should decrease
        zookeeper.direction = "up";
        int currY = zookeeper.y;
        zookeeper.update();
        assertEquals(zookeeper.y, currY - zookeeper.speed);

        // When direction is right zookeeper's x pos should increase
        zookeeper.direction = "right";
        int currX = zookeeper.x;
        zookeeper.update();
        assertEquals(zookeeper.x, currX + zookeeper.speed);

        // When direction is down zookeeper's y pos should increase
        zookeeper.direction = "down";
        currY = zookeeper.y;
        zookeeper.update();
        assertEquals(zookeeper.y, currY + zookeeper.speed);

        // When direction is left zookeeper's x pos should decrease
        zookeeper.direction = "left";
        currX = zookeeper.x;
        zookeeper.update();
        assertEquals(zookeeper.x, currX - zookeeper.speed);
    }

    @Test
    @DisplayName("running searchPath for zookeeper when monkey is above")
    void searchPathMonkeyAbove() {
        monkey.x = 300;
        monkey.y = 300;

        zookeeper.x = 300;
        zookeeper.y = 500;

        zookeeper.searchPath((monkey.x + (monkey.areaX))/panel.tileSize, (monkey.y + (monkey.areaY))/panel.tileSize);
        assertEquals(zookeeper.direction, "up");
    }

    @Test
    @DisplayName("running searchPath for zookeeper when monkey is below")
    void searchPathMonkeyBelow() {
        monkey.x = 300;
        monkey.y = 500;

        zookeeper.x = 300;
        zookeeper.y = 300;

        zookeeper.searchPath((monkey.x + (monkey.areaX))/panel.tileSize, (monkey.y + (monkey.areaY))/panel.tileSize);
        assertEquals(zookeeper.direction, "down");
    }

    @Test
    @DisplayName("running searchPath for zookeeper when monkey is right")
    void searchPathMonkeyRight() {
        monkey.x = 500;
        monkey.y = 300;

        zookeeper.x = 300;
        zookeeper.y = 300;

        zookeeper.searchPath((monkey.x + (monkey.areaX))/panel.tileSize, (monkey.y + (monkey.areaY))/panel.tileSize);
        assertEquals(zookeeper.direction, "right");
    }

    @Test
    @DisplayName("running searchPath for zookeeper when monkey is left")
    void searchPathMonkeyLeft() {
        monkey.x = 300;
        monkey.y = 300;

        zookeeper.x = 500;
        zookeeper.y = 300;

        zookeeper.searchPath((monkey.x + (monkey.areaX))/panel.tileSize, (monkey.y + (monkey.areaY))/panel.tileSize);
        assertEquals(zookeeper.direction, "left");
    }

    @Test
    @DisplayName("running searchPath for zookeeper when monkey is above and right")
    void searchPathMonkeyAboveRight() {
        monkey.x = 300;
        monkey.y = 300;

        zookeeper.x = 100;
        zookeeper.y = 500;

        zookeeper.searchPath((monkey.x + (monkey.areaX))/panel.tileSize, (monkey.y + (monkey.areaY))/panel.tileSize);
        // zookeeper first goes up when deciding
        assertEquals(zookeeper.direction, "up");
    }

    @Test
    @DisplayName("running searchPath for zookeeper when monkey is above and left")
    void searchPathMonkeyAboveLeft() {
        monkey.x = 300;
        monkey.y = 300;

        zookeeper.x = 500;
        zookeeper.y = 500;

        zookeeper.searchPath((monkey.x + (monkey.areaX))/panel.tileSize, (monkey.y + (monkey.areaY))/panel.tileSize);
        // zookeeper first goes up when deciding
        assertEquals(zookeeper.direction, "up");
    }

    @Test
    @DisplayName("running searchPath for zookeeper when monkey is below and right")
    void searchPathMonkeyBelowRight() {
        monkey.x = 500;
        monkey.y = 500;

        zookeeper.x = 300;
        zookeeper.y = 300;

        zookeeper.searchPath((monkey.x + (monkey.areaX))/panel.tileSize, (monkey.y + (monkey.areaY))/panel.tileSize);
        // zookeeper first goes down when deciding
        assertEquals(zookeeper.direction, "down");
    }

    @Test
    @DisplayName("running searchPath for zookeeper when monkey is below and left")
    void searchPathMonkeyBelowLeft() {
        monkey.x = 500;
        monkey.y = 500;

        zookeeper.x = 600;
        zookeeper.y = 100;

        zookeeper.searchPath((monkey.x + (monkey.areaX))/panel.tileSize, (monkey.y + (monkey.areaY))/panel.tileSize);
        // zookeeper first goes left when deciding
        assertEquals(zookeeper.direction, "left");
    }
}