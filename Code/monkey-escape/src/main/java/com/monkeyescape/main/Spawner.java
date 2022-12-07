package com.monkeyescape.main;

import com.monkeyescape.entity.fixedentity.Banana;
import com.monkeyescape.entity.fixedentity.Key;
import com.monkeyescape.entity.fixedentity.LionPit;
import com.monkeyescape.entity.movingentity.Monkey;
import com.monkeyescape.entity.movingentity.Zookeeper;

/**
 * Spawns the entities and adds them to the game.
 *
 * @author Jeffrey Ramacula
 * @version 12/06/2022
 */
public class Spawner {
    private Game game;

    /**
     * Initializes spawner for the Game
     *
     * @param game the <code>Game</code> to spawn entities to
     */
    public Spawner(Game game) {
        this.game = game;
    }

    /**
     * Spawns the Entities
     */
    public void spawnInitialEntities() {
        spawnMonkey();
        spawnZookeepers();
        spawnBananas(2);
        spawnKeys();
        spawnLionPits(2);
    }

    /**
     * Spawns the Monkey
     */
    public void spawnMonkey() {
        game.monkey = new Monkey(game, game.keyHandler);
        game.addEntity(game.monkey);
    }

    /**
     * Spawns the same number of zookeepers as the level
     */
    public void spawnZookeepers() {
        for(int i = 0; i < game.level; i++){
            Zookeeper zookeeper = new Zookeeper(game, game.keyHandler,game.monkey);
            game.addEntity(zookeeper);
            game.addZookeeper(zookeeper);
        }
    }

    /**
     * Creates a Banana Entity and adds it to the Entity List
     *
     * @param numBananas number of bananas to spawn
     */
    public void spawnBananas(int numBananas) {
        for(int i = 0; i < numBananas;i++) {
            game.addEntity(new Banana(game));
        }
    }

    /**
     * Spawns keys
     */
    public void spawnKeys() {
        game.addEntity(new Key(game));
    }

    /**
     * Spawns lion pits
     *
     * @param numLionPits the number of lionpits to spawn
     */
    public void spawnLionPits(int numLionPits) {
        for(int i = 0; i<numLionPits;i++) {
            game.addEntity(new LionPit(game));
        }
    }
}
