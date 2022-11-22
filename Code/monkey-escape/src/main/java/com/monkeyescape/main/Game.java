package com.monkeyescape.main;

import com.monkeyescape.entity.fixedentity.Banana;
import com.monkeyescape.entity.fixedentity.Key;
import com.monkeyescape.entity.fixedentity.LionPit;
import com.monkeyescape.entity.movingentity.Monkey;
import com.monkeyescape.entity.movingentity.Zookeeper;

/**
 * Represents the game
 *
 * @author Shadi Zoldjalali
 * @version 11/21/2022
 */
public class Game {
    Window window;
    Panel panel;
    Monkey monkey;
    public static int level;
    Sound sound;

    /**
     * Initializes the game
     *
     * @param playSound  true to play sound, otherwise false
     * @param showWindow true to show the window, otherwise false
     */
    public Game(boolean playSound, boolean showWindow) {
        panel = new Panel(this);
        panel.startGameThread();

        level = 1;
        spawnInitialEntities();

        sound = new Sound();

        if (showWindow) {
            window = new Window();
            window.addPanel(panel);
        }

        if (playSound) {
            playMusic(0);
        }
    }

    /**
     * Sets the level
     *
     * @param lvl integer to set the level to
     * */
    public void setLevel(int lvl){
        Game.level = lvl;
    }

    /**
     * Gets the level
     *
     * @return the current level
     * */
    public int getLevel(){
        return Game.level;
    }

    /**
     * Spawns the Entities
     */
    public void spawnInitialEntities() {

        spawnMonkey();
        spawnZookeepers();
        spawnBananas();
        spawnKeys();
        spawnLionPits();
    }

    /**
     * Spawns the Monkey
     */
    public void spawnMonkey() {
        monkey = new Monkey(panel, panel.kh);
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
     *
     * @param i index for the sound file
     */
    public void playMusic(int i){
        sound.setFile(i);
        sound.play();
        sound.loop();
    }

    /**
     * Restarts the game and respawns the entities
     */
    public void restart() {
        level = 1;

        panel.restartGame();
        spawnInitialEntities();
    }

    /**
     * Moves the game to the next using
     */
    public void nextLevel(){
        Game.level++;

        panel.nextLevel();
        spawnInitialEntities();
    }
}
