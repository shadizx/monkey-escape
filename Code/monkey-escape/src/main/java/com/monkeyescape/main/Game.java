package com.monkeyescape.main;

import com.monkeyescape.entity.BonusReward;
import com.monkeyescape.entity.Key;
import com.monkeyescape.entity.Punishment;
import com.monkeyescape.entity.Enemy;

/**
 * Represents the game
 * @author Shadi Zoldjalali
 * @version 10/26/2022
 */
public class Game {

    Window window;
    Panel panel;
    int level;

    /**
     * Initializes window and panel, and starts the game loop
     */
    public Game() {
        window = new Window();
        panel = new Panel();
        window.addPanel(panel);
        panel.startGameThread();

        level = 1;

        spawnInteractibles();
    }

    /**
     * Spawns the interactibles
     */
    public void spawnInteractibles() {
        //Spawn one key
        panel.addEntity(new Key(panel,panel.kh));

        //Spawn three bananas
        panel.addEntity(new BonusReward(panel,panel.kh));
        panel.addEntity(new BonusReward(panel,panel.kh));
        panel.addEntity(new BonusReward(panel,panel.kh));

        //Spawn 2 lion pits
        panel.addEntity(new Punishment(panel,panel.kh));
        panel.addEntity(new Punishment(panel,panel.kh));
    }

    /**
     * Spawns the enemies
     */
    public void spawnEnemies() {
        //Spawns same # of enemies as level
        for(int i = 0; i < level; i++){
            panel.addEntity(new Enemy(panel,panel.kh,panel.monkey));
        }
    }
}
