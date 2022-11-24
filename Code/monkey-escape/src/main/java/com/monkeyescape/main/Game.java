package com.monkeyescape.main;

import com.monkeyescape.entity.Entity;
import com.monkeyescape.entity.fixedentity.Banana;
import com.monkeyescape.entity.fixedentity.Key;
import com.monkeyescape.entity.fixedentity.LionPit;
import com.monkeyescape.entity.movingentity.Monkey;
import com.monkeyescape.entity.movingentity.Zookeeper;
import com.monkeyescape.map.TileMap;

import java.util.ArrayList;
import java.util.List;

/**
 * Represents the game
 *
 * @author Shadi Zoldjalali & Jeffrey Ramacula
 * @version 11/23/2022
 */
public class Game {
    Monkey monkey;
    public static int level;
    public TileMap tm;

    public final int tileSize = 48;
    public final int rows = 16;
    public final int cols = 16;
    public final int width = tileSize * cols;
    public final int height = tileSize * rows;
    //set start tile where the cage will be to the top-left corner
    public final int startCol = 1;
    public final int startRow = 0;

    //set end tile where the door/exit will be to the bottom-right corner
    public final int exitCol = cols-2;
    public final int exitRow = rows-1;

    public State state = new State();
    public int score = 0;
    public int secondsTimer = 0;

    private final List<Entity> entities = new ArrayList<>();
    public List<Zookeeper> zookeepers = new ArrayList<>();
    public Collision collisionChecker;
    KeyHandler kh;

    /**
     * Initializes the game
     *
     * @param keyhandler a keyhandler for key inputs
     */
    public Game(KeyHandler keyhandler) {
        tm = new TileMap(this);
        collisionChecker = new Collision(this);
        setLevel(1);
        kh = keyhandler;
        spawnInitialEntities();
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
     * Updates game information
     */
    public void update() {
        state.changeState(kh);
        // Create new game if in restart state
        if (state.getGameState() == State.GameState.RESTART) {
            restart();
            state.changeState(kh);
        }
        if (state.getGameState() == State.GameState.PLAY) {
            //updates entities
            for (int i = 0; i < entities.size(); i++) {
                entities.get(i).update();
            }
            //Spawns a new banana on average every 15 seconds
            if(((int) (Math.random() * 900)) == 1) {
                spawnBananas(1);
            }
            if (score < 0) {
                state.setGameState(State.GameState.GAMEOVER);
                state.changeState(kh);
            }
        }
    }

    /**
     * Spawns the Monkey
     */
    public void spawnMonkey() {
        monkey = new Monkey(this, kh);
        addEntity(monkey);
    }

    /**
     * Spawns the same number of zookeepers as the level
     */
    public void spawnZookeepers() {
        for(int i = 0; i < level; i++){
            Zookeeper zookeeper = new Zookeeper(this,kh,monkey);
            addEntity(zookeeper);
            addZookeeper(zookeeper);
        }
    }

    /**
     * Creates a Banana Entity and adds it to the Entity List
     *
     * @param numBananas number of bananas to spawn
     */
    public void spawnBananas(int numBananas) {
        for(int i = 0; i < numBananas;i++) {
            addEntity(new Banana(this));
        }
    }

    /**
     * Spawns keys
     */
    public void spawnKeys() {
        addEntity(new Key(this));
    }

    /**
     * Spawns lion pits
     *
     * @param numLionPits the number of lionpits to spawn
     */
    public void spawnLionPits(int numLionPits) {
        for(int i = 0; i<numLionPits;i++) {
            addEntity(new LionPit(this));
        }
    }

    /**
     * Sets level
     *
     * @param num integer to set the level to
     * */
    public void setLevel(int num) {
        level = num;
    }

    /**
     * Gets level
     *
     * @return returns the level
     */
    public int getLevel() {
        return level;
    }

    /**
     * Restarts the game and respawns the entities
     */
    public void restart() {
        setLevel(1);
        entities.clear();
        zookeepers.clear();
        score = 0;
        secondsTimer = 0;
        tm.makeNewMap();
        spawnInitialEntities();
    }

    /**
     * Moves the game to the next using
     */
    public void nextLevel() {
        setLevel(level + 1);
        entities.clear();
        zookeepers.clear();
        tm.makeNewMap();
        spawnInitialEntities();
    }

    /**
     * Adds an entity into the game
     *
     * @param entity A non-null entity
     */
    public void addEntity(Entity entity) {
        entities.add(0,entity);
    }

    /**
     * Adds a zookeeper to a list of enemies
     *
     * @param zookeeper A non-null entity
     */
    public void addZookeeper(Zookeeper zookeeper) {
        zookeepers.add(zookeeper);
    }

    /**
     * Removes selected entity from the game
     *
     * @param entity A non-null entity
     * @return true if remove was successful
     */
    public boolean removeEntity(Entity entity) {
        return entities.remove(entity);
    }

    /**
     * Returns a List of current Entities in the game
     *
     * @return the list of entities
     * */
    public List<Entity> getEntities() {
        return entities;
    }
}
