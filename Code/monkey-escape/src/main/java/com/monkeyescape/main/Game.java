package com.monkeyescape.main;

import com.monkeyescape.entity.Entity;
import com.monkeyescape.entity.Position;
import com.monkeyescape.entity.movingentity.Monkey;
import com.monkeyescape.entity.movingentity.Zookeeper;
import com.monkeyescape.map.TileMap;

import java.util.ArrayList;
import java.util.List;

/**
 * Represents the game
 *
 * @author Shadi Zoldjalali & Jeffrey Ramacula
 * @version 12/06/2022
 */
public class Game {
    Monkey monkey;
    public static int level;
    public TileMap tileMap;

    public final int tileSize = 48;
    public final int rows = 16;
    public final int cols = 16;
    public final int width = tileSize * cols;
    public final int height = tileSize * rows;

    //set start tile where the cage will be to the top-left corner
    public final Position cagePos = new Position(1, 0);
    //set end tile where the door/exit will be to the bottom-right corner
    public final Position doorPos = new Position(cols - 2, rows - 1);

    public State state = new State();
    public int score = 0;
    public int secondsTimer = 0;

    private final List<Entity> entities = new ArrayList<>();
    public List<Zookeeper> zookeepers = new ArrayList<>();
    public Collision collisionChecker;
    KeyHandler keyHandler;
    Spawner spawner;

    /**
     * Initializes the game
     *
     * @param keyhandler a keyhandler for key inputs
     */
    public Game(KeyHandler keyhandler) {
        tileMap = new TileMap(this);
        collisionChecker = new Collision(this);
        setLevel(1);
        keyHandler = keyhandler;
        spawner = new Spawner(this);
        spawner.spawnInitialEntities();
    }

    /**
     * Updates game information
     */
    public void update() {
        state.changeState(keyHandler);
        // Create new game if in restart state
        if (state.getGameState() == State.GameState.RESTART) {
            restart();
            state.changeState(keyHandler);
        }
        if (state.getGameState() == State.GameState.PLAY) {
            //updates entities
            for (int i = 0; i < entities.size(); i++) {
                entities.get(i).update();
            }
            //Spawns a new banana on average every 15 seconds
            if(((int) (Math.random() * 900)) == 1) {
                spawner.spawnBananas(1);
            }
            if (score < 0) {
                state.setGameState(State.GameState.GAMEOVER);
                state.changeState(keyHandler);
            }
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
        tileMap.makeNewMap();
        spawner.spawnInitialEntities();
    }

    /**
     * Moves the game to the next using
     */
    public void nextLevel() {
        setLevel(level + 1);
        entities.clear();
        zookeepers.clear();
        tileMap.makeNewMap();
        spawner.spawnInitialEntities();
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
