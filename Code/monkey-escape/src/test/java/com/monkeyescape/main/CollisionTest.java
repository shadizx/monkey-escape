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
    Monkey entity;

    @BeforeEach
    void setup() {
        panel = new Panel(game);
        collision = new Collision(panel,game);

        //Create a monkey
        entity = new Monkey(panel, panel.kh);
    }

    @Test
    @DisplayName("Running checkTile for invalid position")
    void checkTileInvalidPosition() {
        //Give the monkey a position that is off the map (to the left and above)
        entity.x = (-1)*panel.tileSize;
        entity.y = (-1)*panel.tileSize;

        collision.checkTile(entity);

        assertTrue(entity.collided);
    }

    @Test
    @DisplayName("Running checkTile for valid position")
    void checkTileValidPosition() {
        //Give the monkey a position that is on the map (tile 1,1)
        entity.x = (1)*panel.tileSize;
        entity.y = (1)*panel.tileSize;

        collision.checkTile(entity);

        assertFalse(entity.collided);
    }

    @Test
    @DisplayName("Running checkTile for tile that is blocked")
    void checkTileBlockedPosition() {
        //Give the monkey a position that is blocked (tile 0,5)
        entity.x = (0)*panel.tileSize;
        entity.y = (5)*panel.tileSize;

        collision.checkTile(entity);

        assertTrue(entity.collided);
    }

    @Test
    @DisplayName("Running checkFixedEntity for invalid position")
    void checkFixedEntityInvalidPosition() {
        //Give the monkey a position that is off the map (to the left and above)
        entity.x = (-1)*panel.tileSize;
        entity.y = (-1)*panel.tileSize;

        boolean fixedEntity = collision.checkFixedEntity(entity);

        assertFalse(fixedEntity);
    }

    @Test
    @DisplayName("Running checkFixedEntity for valid position")
    void checkFixedEntityValidPosition() {
        //Set monkey to a tile in the middle of the map that might have a fixed entity
        entity.x = (8)*panel.tileSize;
        entity.y = (8)*panel.tileSize;

        boolean fixedEntity = collision.checkFixedEntity(entity);

        assertEquals(panel.tm.tileMap[8][8].hasFixedEntity, fixedEntity);
    }

    @Test
    @DisplayName("Running checkZookeeper for invalid position")
    void checkZookeeperInvalidPosition() {
        //Give the monkey a position that is off the map (to the left and above)
        entity.x = (-1)*panel.tileSize;
        entity.y = (-1)*panel.tileSize;

        boolean fixedEntity = collision.checkZookeeper(entity,panel.zookeepers);

        assertFalse(fixedEntity);
    }

    @Test
    @DisplayName("Running checkZookeeper for position a zookeeper is in")
    void checkZookeeperValidPosition() {
        //Set monkey to a the same tile that a zookeeper is on
        Zookeeper zookeeper = new Zookeeper(panel, panel.kh, entity);
        panel.addZookeeper(zookeeper);
        entity.x = zookeeper.x;
        entity.y = zookeeper.y;

        boolean fixedEntity = collision.checkZookeeper(entity,panel.zookeepers);

        assertTrue(fixedEntity);
    }

    @Test
    @DisplayName("Running process collision on an empty list of tiles")
    void processCollisionEmptyList() {
        List<Tile> potentialCollisions = new ArrayList<Tile>(); //Empty list

        int beforeScore = panel.score;

        collision.processCollision(panel, potentialCollisions, entity);

        assertEquals(beforeScore, panel.score);
    }

    @Test
    @DisplayName("Running process collision on a lion pit")
    void processCollisionLionPit() {
        List<Tile> potentialCollisions = new ArrayList<Tile>(); //Empty list
        Tile tiletoadd = new Tile();
        tiletoadd.hasFixedEntity = true;
        tiletoadd.FixedEntityObject = new LionPit(panel);

        entity.x = tiletoadd.FixedEntityObject.x;
        entity.y = tiletoadd.FixedEntityObject.y;

        potentialCollisions.add(tiletoadd);

        boolean beforeStuckInPit = Monkey.inLionPit;

        collision.processCollision(panel, potentialCollisions, entity);

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

        collision.processCollision(panel, potentialCollisions, entity);

        assertEquals(beforeScore+tiletoadd.FixedEntityObject.impact, panel.score);
    }
}