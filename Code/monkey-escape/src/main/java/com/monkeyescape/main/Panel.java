package com.monkeyescape.main;

import com.monkeyescape.entity.Entity;
import com.monkeyescape.entity.fixedentity.Banana;
import com.monkeyescape.entity.movingentity.Zookeeper;
import com.monkeyescape.map.TileMap;
import com.monkeyescape.painter.Painter;

import javax.swing.JPanel;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.util.ArrayList;
import java.util.List;

/**
 * Represents a JPanel panel that is used for interaction
 *
 * @author Shadi Zoldjalali & Kaleigh Toering
 * @version 11/04/2022
 */
public class Panel extends JPanel implements Runnable {
    private final Game game;
    public final int tileSize = 48;
    public final int rows = 16;
    public final int cols = 16;
    public final int width = tileSize * cols;
    public final int height = tileSize * rows;
    public final int sideBarWidth = 200;
    public final int FPS = 60;

    public int score = 0;
    public int secondsTimer = 0;

    //set start tile where the cage will be to the top-left corner
    public final int startCol = 1;
    public final int startRow = 0;

    //set end tile where the door/exit will be to the bottom-right corner
    public final int exitCol = cols-2;
    public final int exitRow = rows-1;

    public TileMap tm = new TileMap(this);
    protected KeyHandler kh = new KeyHandler();
    private final Painter painter = new Painter(this);
    private Thread gameThread;

    public State state = new State();

    private final List<Entity> entities = new ArrayList<>();
    public List<Zookeeper> zookeepers = new ArrayList<>();
    public Collision collisionChecker;

    /**Ï
     * Creates a new Panel
     *
     * @param game a <code>Game</code> to refer to
     */
    public Panel(Game game) {
        this.setPreferredSize(new Dimension(width + sideBarWidth, height));
        this.setBackground(Color.GREEN);
        this.setDoubleBuffered(true); // improves games rendering performance
        this.addKeyListener(kh);
        this.setFocusable(true); // Game panel is "focused" to receive key input
        collisionChecker = new Collision(this, game);
        this.game = game;
    }

    /**
     * Starts the game thread
     */
    public void startGameThread() {
        gameThread = new Thread(this);
        gameThread.start();
    }

    /**
     * Starts the game loop
     */
    @Override
    public void run() {
        double drawInterval = 1_000_000_000 / FPS;

        double delta = 0;
        long lastTime = System.nanoTime();
        long currentTime;
        long timerCount = 0;

        long delayCount = 0;
        var delayCheck = true;
        var startDelayTimer = false;
        long cachedNanoTime = 0;

        // Game loop
        while (gameThread != null) {
            // Create new game if in restart state
            if (state.getGameState() == State.GameState.RESTART) {
                state.changeState(kh);
                game.restart();
            }
            currentTime = System.nanoTime();

            delta += (currentTime - lastTime) / drawInterval;

            if (state.getGameState() == State.GameState.PLAY) {
                timerCount += (currentTime - lastTime);
                if (timerCount >= 1000000000) {
                    secondsTimer += 1;
                    timerCount = 0;
                }
            }

            delayCount += (currentTime - lastTime);
            lastTime = currentTime;

            if (delta >= 1) {
                update();
                repaint();

                delta--;
            }

            if (collisionChecker.delayedDamages < 0 && delayCheck) {
                score += collisionChecker.delayedDamages;
                collisionChecker.delayedDamages = 0;
                delayCheck = false;
                cachedNanoTime = delayCount;
                startDelayTimer = true;
            }

            if ((delayCount >= 1000000000 + cachedNanoTime) && startDelayTimer) {
                delayCheck = true;
                collisionChecker.delayedDamages = 0;
                startDelayTimer = false;
            }
        }
    }

    /**
     * Updates game information
     */
    public void update() {
        state.changeState(kh);
        for (int i = 0; i < entities.size(); i++) {
            entities.get(i).update();
        }

        //Spawns a new banana on average every 15 seconds
        if(((int) (Math.random() * 900)) == 1){
            addEntity(new Banana(this));
        }

        if (score < 0) {
            state.setGameState(State.GameState.GAMEOVER);
            state.changeState(kh);
        }
    }

    /**
     * Draws the UI
     */
    public void paintComponent(Graphics g) {
        super.paintComponent(g);
        Graphics2D g2 = (Graphics2D) g;
        painter.paintScore(g2);
        painter.paintTimer(g2);
        painter.paintLevel(g2);

        State.GameState gameState = state.getGameState();
        if (gameState != State.GameState.PLAY) {
            painter.paintMenu(g2, gameState.toString().toLowerCase());
        } else {
            // draw stuff here
            tm.drawMap(g2);

            // use this type of for loop to not throw exception ConcurrentModificationException
            for (int i = 0; i < entities.size(); i++) {
                entities.get(i).draw(g2);
            }
            // draw stuff here

            g2.dispose(); // good practice to save memory
        }
    }

    /**
     * Adds an entity into the panel
     *
     * @param entity A non-null entity
     */
    public void addEntity(Entity entity) {
        entities.add(entity);
    }

    /**
     * Adds an zookeeper to a list of enemies
     *
     * @param zookeeper A non-null entity
     */
    public void addZookeeper(Zookeeper zookeeper) {
        zookeepers.add(zookeeper);
    }

    /**
     * Removes selected entity from the panel
     *
     * @param entity A non-null entity
     */
    public boolean removeEntity(Entity entity) {
        return entities.remove(entity);
    }

    /**
     * Removes selected zookeeper from the list of enemies
     *
     * @param zookeeper A non-null entity
     */
    public boolean removeZookeeper(Zookeeper zookeeper) {
        return zookeepers.remove(zookeeper);
    }

    /**
     * Restarts the game by resetting the game progress
     */
    public void restartGame() {
        entities.clear();
        zookeepers.clear();
        score = 0;
        secondsTimer = 0;

        tm.makeNewMap();
    }

    /**
     * Resets the list of entities and zookeepers for the next level to be played and generates a new map
     */
    public void nextLevel(){
        entities.clear();
        zookeepers.clear();

        tm.makeNewMap();
    }
}
