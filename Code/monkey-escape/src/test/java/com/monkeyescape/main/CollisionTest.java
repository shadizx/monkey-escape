package com.monkeyescape.main;

import com.monkeyescape.entity.fixedentity.Key;
import com.monkeyescape.entity.fixedentity.LionPit;
import com.monkeyescape.entity.movingentity.Monkey;
import com.monkeyescape.entity.movingentity.Zookeeper;
import com.monkeyescape.map.Tile;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;

class CollisionTest {
    private Collision collision;

    private Panel panel;
    private Game game = new Game(false, false);
    Monkey monkey;

    @BeforeEach
    void setup() {
        panel = new Panel(game);
        collision = new Collision(panel,game);

        //Create a monkey
        monkey = new Monkey(panel, panel.kh);
    }

    @Test
    @DisplayName("Running checkTile for invalid position")
    void checkTileInvalidPosition() {
        //Give the monkey a position that is off the map (to the left and above)
        monkey.x = (-1)*panel.tileSize;
        monkey.y = (-1)*panel.tileSize;

        collision.checkTile(monkey);

        assertTrue(monkey.collided);
    }

    @Test
    @DisplayName("Running checkTile for valid position")
    void checkTileValidPosition() {
        //Give the monkey a position that is on the map (tile 1,1)
        monkey.x = (1)*panel.tileSize;
        monkey.y = (1)*panel.tileSize;

        collision.checkTile(monkey);

        assertFalse(monkey.collided);
    }

    @Test
    @DisplayName("Running checkTile for tile that is blocked")
    void checkTileBlockedPosition() {
        //Give the monkey a position that is blocked (tile 0,5)
        monkey.x = (0)*panel.tileSize;
        monkey.y = (5)*panel.tileSize;

        collision.checkTile(monkey);

        assertTrue(monkey.collided);
    }

    @Test
    @DisplayName("Running checkFixedEntity for invalid position")
    void checkFixedEntityInvalidPosition() {
        //Give the monkey a position that is off the map (to the left and above)
        monkey.x = (-1)*panel.tileSize;
        monkey.y = (-1)*panel.tileSize;

        boolean fixedEntity = collision.checkFixedEntity(monkey);

        assertFalse(fixedEntity);
    }

    @Test
    @DisplayName("Running checkFixedEntity for valid position")
    void checkFixedEntityValidPosition() {
        //Set monkey to a tile in the middle of the map that might have a fixed entity
        monkey.x = (8)*panel.tileSize;
        monkey.y = (8)*panel.tileSize;

        boolean fixedEntity = collision.checkFixedEntity(monkey);

        assertEquals(panel.tm.tileMap[8][8].hasFixedEntity, fixedEntity);
    }

    @Test
    @DisplayName("Running checkZookeeper for invalid position")
    void checkZookeeperInvalidPosition() {
        //Give the monkey a position that is off the map (to the left and above)
        monkey.x = (-1)*panel.tileSize;
        monkey.y = (-1)*panel.tileSize;

        boolean fixedEntity = collision.checkZookeeper(monkey,panel.zookeepers);

        assertFalse(fixedEntity);
    }

    @Test
    @DisplayName("Running checkZookeeper for position a zookeeper is in")
    void checkZookeeperValidPosition() {
        //Set monkey to a the same tile that a zookeeper is on
        Zookeeper zookeeper = new Zookeeper(panel, panel.kh, monkey);
        panel.addZookeeper(zookeeper);
        monkey.x = zookeeper.x;
        monkey.y = zookeeper.y;

        boolean fixedEntity = collision.checkZookeeper(monkey,panel.zookeepers);

        assertTrue(fixedEntity);
    }

    @Test
    @DisplayName("Running process collision on an empty list of tiles")
    void processCollisionEmptyList() {
        List<Tile> potentialCollisions = new ArrayList<Tile>(); //Empty list

        int beforeScore = panel.score;

        collision.processCollision(panel, potentialCollisions, monkey);

        assertEquals(beforeScore, panel.score);
    }

    @Test
    @DisplayName("Running process collision on a lion pit")
    void processCollisionLionPit() {
        List<Tile> potentialCollisions = new ArrayList<Tile>(); //Empty list
        Tile tiletoadd = new Tile();
        tiletoadd.hasFixedEntity = true;
        tiletoadd.FixedEntityObject = new LionPit(panel);

        monkey.x = tiletoadd.FixedEntityObject.x;
        monkey.y = tiletoadd.FixedEntityObject.y;

        potentialCollisions.add(tiletoadd);

        boolean beforeStuckInPit = Monkey.inLionPit;
        Monkey.lionPitInvincibility = 0;

        collision.processCollision(panel, potentialCollisions, monkey);

        assertEquals(tiletoadd.FixedEntityObject.impact, collision.delayedDamages);
        assertEquals(!beforeStuckInPit, Monkey.inLionPit);
    }

    @Test
    @DisplayName("Running process collision on a key")
    void processCollisionKey() {
        List<Tile> potentialCollisions = new ArrayList<Tile>(); //Empty list
        Tile tiletoadd = new Tile();
        tiletoadd.hasFixedEntity = true;
        tiletoadd.FixedEntityObject = new Key(panel);

        potentialCollisions.add(tiletoadd);

        int beforeScore = panel.score;

        collision.processCollision(panel, potentialCollisions, monkey);

        assertEquals(beforeScore+tiletoadd.FixedEntityObject.impact, panel.score);
    }
}