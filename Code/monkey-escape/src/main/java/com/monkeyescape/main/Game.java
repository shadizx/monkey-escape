package com.monkeyescape.main;

import com.monkeyescape.entity.fixedentity.Banana;
import com.monkeyescape.entity.fixedentity.Key;
import com.monkeyescape.entity.fixedentity.LionPit;
import com.monkeyescape.entity.movingentity.Monkey;
import com.monkeyescape.entity.movingentity.Zookeeper;

/**
 * Represents the game
 * @author Shadi Zoldjalali
 * @version 10/30/2022
 */
public class Game {

    Window window;
    Panel panel;
    Monkey monkey;
    public static int level;
    Sound sound;

    /**
     * Initializes the game
     */
    public Game() {
        window = new Window();
        panel = new Panel(this);
        window.addPanel(panel);
        panel.startGameThread();

        level = 1;
        spawnEntities();

        sound = new Sound();
        playMusic(0);
    }

    /**
     * Spawns the Entities
     */
    public void spawnEntities() {
        monkey = new Monkey(panel, panel.kh);

        spawnBananas();
        spawnKeys();
        spawnLionPits();
        spawnZookeepers();
        spawnMonkey();
    }

    /**
     * Spawns the Monkey
     */
    public void spawnMonkey() {
        panel.addEntity(monkey);
    }

    /**
     * Spawns the same number of zookeeper's as the level
     */
    public void spawnZookeepers() {
        for(int i = 0; i < level; i++){
            Zookeeper zookeeper = new Zookeeper(panel,panel.kh,monkey);
            panel.addEntity(zookeeper);
            panel.addZookeeper(zookeeper);
        }
    }

    /**
     * Spawns bananas
     */
    public void spawnBananas() {
        panel.addEntity(new Banana(panel));
        panel.addEntity(new Banana(panel));
    }

    /**
     * Spawns keys
     */
    public void spawnKeys() {
        panel.addEntity(new Key(panel));
    }


    /**
     * Spawns lion pits
     */
    public void spawnLionPits() {
        panel.addEntity(new LionPit(panel));
        panel.addEntity(new LionPit(panel));
    }

    /**
     * Plays specified sound
     */
    public void playMusic(int i){
        sound.setFile(i);
        sound.play();
        sound.loop();
    }    
    
    /**
     * Moves the game to the next using
     */
    public void nextLevel(){
        Game.level++;

        panel.nextLevel();

        spawnEntities();
    }
}
