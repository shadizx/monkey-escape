package com.monkeyescape.main;

import com.monkeyescape.entity.fixedentity.FixedEntity;
import com.monkeyescape.entity.movingentity.MovingEntity;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class GameTest {
    private Game testGame;
    private Panel testPanel;

    @BeforeEach
    void setup(){
        testGame = new Game(false, false);
        testPanel = testGame.panel;
        testPanel.zookeepers.clear();
        testPanel.getEntities().clear();
    }

    @Test
    @DisplayName("Running spawnInitialEntities Tes")
    void spawnInitialEntitiesTest(){
        testGame.spawnInitialEntities();
        assertEquals(7, testPanel.getEntities().size());
    }

    @Test
    @DisplayName("Running spawnMonkeyTest")
    void spawnMonkeyTest(){
        testGame.spawnMonkey();
        int numEntities = testPanel.getEntities().size();
        assertEquals(1, numEntities);
        MovingEntity testMonkey = (MovingEntity) testPanel.getEntities().get(0);
        assertEquals("monkey",testMonkey.type);
    }

    @Test
    @DisplayName("Running spawnZookeepersValidTest Level = 1")
    void spawnZookeepersValidLevelTest(){
        testGame.setLevel(1);
        testGame.spawnZookeepers();
        assertEquals(1, testPanel.zookeepers.size());
    }
    @Test
    @DisplayName("Running spawnZookeepersValidTest level = 5")
    void spawnZookeepersValidLevelTest2(){
        testGame.setLevel(5);
        testGame.spawnZookeepers();
        assertEquals(5, testPanel.zookeepers.size());
    }

    @Test
    @DisplayName("Running spawnZookeepersInValidTest Level = 0")
    void spawnZookeepersInValidLevelTest(){
        testGame.setLevel(0);
        testGame.spawnZookeepers();
        assertEquals(0, testPanel.zookeepers.size());
    }

    @Test
    @DisplayName("Running spawnBananasTest")
    void spawnBananasTest(){
        testGame.spawnBananas();
        int numEntities = testPanel.getEntities().size();
        assertEquals(2, numEntities);
        FixedEntity testBanana1 = (FixedEntity) testPanel.getEntities().get(0);
        FixedEntity testBanana2 = (FixedEntity) testPanel.getEntities().get(1);
        assertEquals("banana",testBanana1.type);
        assertEquals("banana",testBanana2.type);
    }
    @Test
    @DisplayName("Running spawnKeyTest")
    void spawnKeyTest(){
        testGame.spawnKeys();
        int numEntities = testPanel.getEntities().size();
        assertEquals(1, numEntities);
        FixedEntity testKey = (FixedEntity) testPanel.getEntities().get(0);
        assertEquals("key",testKey.type);
    }

    @Test
    @DisplayName("Running spawnLionPitsTest")
    void spawnLionPitsTest(){
        testGame.spawnLionPits();
        int numEntities = testPanel.getEntities().size();
        assertEquals(2, numEntities);
        FixedEntity testLionPit1 = (FixedEntity) testPanel.getEntities().get(0);
        FixedEntity testLionPit2 = (FixedEntity) testPanel.getEntities().get(1);
        assertEquals("lionpit",testLionPit1.type);
        assertEquals("lionpit",testLionPit2.type);
    }

    @Test
    @DisplayName("Running restartTest")
    void restartTest(){
        testGame.restart();
        assertEquals(1, testGame.getLevel());
        assertEquals(7, testPanel.getEntities().size());
        assertEquals(1, testPanel.zookeepers.size());
        assertEquals(0, testPanel.score);
        assertEquals(0, testPanel.secondsTimer);
    }
    @Test
    @DisplayName("Running nextLevelTest level 2=>3")
    void nextLevelTest(){
        testGame.setLevel(2);
        testGame.nextLevel();
        assertEquals(3, testGame.getLevel());
        assertEquals(9, testPanel.getEntities().size());
        assertEquals(3, testPanel.zookeepers.size());
        assertEquals(0, testPanel.score);
        assertEquals(0, testPanel.secondsTimer);
    }







}
