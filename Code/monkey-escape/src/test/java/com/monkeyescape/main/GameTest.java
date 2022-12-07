package com.monkeyescape.main;

import com.monkeyescape.entity.fixedentity.FixedEntity;
import com.monkeyescape.entity.movingentity.MovingEntity;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class GameTest {
    private Game testGame;
    private KeyHandler testKeyHandler;

    @BeforeEach
    void setup(){
        testKeyHandler = new KeyHandler();
        testGame = new Game(testKeyHandler);
        testGame.getEntities().clear();
        testGame.zookeepers.clear();
    }

    @Test
    @DisplayName("Running spawnInitialEntities Tes")
    void spawnInitialEntitiesTest(){
        testGame.spawner.spawnInitialEntities();
        assertEquals(7,testGame.getEntities().size());
    }

    @Test
    @DisplayName("Running spawnMonkeyTest")
    void spawnMonkeyTest(){
        testGame.spawner.spawnMonkey();
        int numEntities = testGame.getEntities().size();
        assertEquals(1, numEntities);
        MovingEntity testMonkey = (MovingEntity) testGame.getEntities().get(0);
        assertEquals("monkey",testMonkey.type);
    }

    @Test
    @DisplayName("Running spawnZookeepersValidTest Level = 1")
    void spawnZookeepersValidLevelTest(){
        testGame.setLevel(1);
        testGame.spawner.spawnZookeepers();
        assertEquals(1,testGame.zookeepers.size());
    }
    @Test
    @DisplayName("Running spawnZookeepersValidTest level = 5")
    void spawnZookeepersValidLevelTest2(){
        testGame.setLevel(5);
        testGame.spawner.spawnZookeepers();
        assertEquals(5,testGame.zookeepers.size());
    }

    @Test
    @DisplayName("Running spawnZookeepersInValidTest Level = 0")
    void spawnZookeepersInValidLevelTest(){
        testGame.setLevel(0);
        testGame.spawner.spawnZookeepers();
        assertEquals(0,testGame.zookeepers.size());
    }

    @Test
    @DisplayName("Running spawnBananasTest")
    void spawnBananasTest(){
        testGame.spawner.spawnBananas(2);
        int numEntities = testGame.getEntities().size();
        assertEquals(2, numEntities);
        FixedEntity testBanana1 = (FixedEntity) testGame.getEntities().get(0);
        FixedEntity testBanana2 = (FixedEntity) testGame.getEntities().get(1);
        assertEquals("banana",testBanana1.type);
        assertEquals("banana",testBanana2.type);
    }
    @Test
    @DisplayName("Running spawnKeyTest")
    void spawnKeyTest(){
        testGame.spawner.spawnKeys();
        int numEntities = testGame.getEntities().size();
        assertEquals(1, numEntities);
        FixedEntity testKey = (FixedEntity) testGame.getEntities().get(0);
        assertEquals("key",testKey.type);
    }

    @Test
    @DisplayName("Running spawnLionPitsTest")
    void spawnLionPitsTest(){
        testGame.spawner.spawnLionPits(2);
        int numEntities = testGame.getEntities().size();
        assertEquals(2, numEntities);
        FixedEntity testLionPit1 = (FixedEntity) testGame.getEntities().get(0);
        FixedEntity testLionPit2 = (FixedEntity) testGame.getEntities().get(1);
        assertEquals("lionpit",testLionPit1.type);
        assertEquals("lionpit",testLionPit2.type);
    }

    @Test
    @DisplayName("Running restartTest")
    void restartTest(){
        testGame.restart();
        assertEquals(1, testGame.getLevel());
        assertEquals(7,testGame.getEntities().size());
        assertEquals(1,testGame.zookeepers.size());
        assertEquals(0,testGame.score);
        assertEquals(0,testGame.secondsTimer);
    }
    @Test
    @DisplayName("Running nextLevelTest level 2=>3")
    void nextLevelTest(){
        testGame.setLevel(2);
        testGame.nextLevel();
        assertEquals(3, testGame.getLevel());
        assertEquals(9,testGame.getEntities().size());
        assertEquals(3,testGame.zookeepers.size());
        assertEquals(0,testGame.score);
        assertEquals(0,testGame.secondsTimer);
    }

    @Test
    @DisplayName("Running GameOverTestNegativeScore score < 0")
    void GameOverTestNegativeScore(){
        testGame.state.setGameState(State.GameState.PLAY);
        testGame.score = -1;
        testGame.update();
        assertEquals(State.GameState.GAMEOVER, testGame.state.getGameState());
    }
    @Test
    @DisplayName("Running GameOverTestNegativeScoreWithGameStateSTART score < 0, state = START")
    void GameOverTestNegativeScoreWithGameStateSTART(){
        testGame.state.setGameState(State.GameState.START);
        testGame.score = -1;
        assertEquals(State.GameState.START, testGame.state.getGameState());
        testGame.update();
        assertEquals(State.GameState.START, testGame.state.getGameState());
    }

    @Test
    @DisplayName("Running NotGameOverTestPositiveScore score >= 0")
    void NotGameOverTestPositiveScore(){
        testGame.state.setGameState(State.GameState.PLAY);
        testGame.score = 0;
        testGame.update();
        assertEquals(State.GameState.PLAY, testGame.state.getGameState());
    }
    @Test
    @DisplayName("Running RestartTestAfterUpdate")
    void RestartTestAfterUpdate(){
        testGame.state.setGameState(State.GameState.RESTART);
        assertEquals(State.GameState.RESTART, testGame.state.getGameState());
        testGame.update();
        assertEquals(State.GameState.START, testGame.state.getGameState());
    }
}
