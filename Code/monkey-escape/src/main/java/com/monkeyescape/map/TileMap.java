package com.monkeyescape.map;

import com.monkeyescape.entity.fixedentity.FixedEntity;
import com.monkeyescape.main.Game;

import javax.imageio.ImageIO;
import java.awt.Graphics2D;
import java.awt.image.BufferedImage;
import java.io.IOException;

/**
 * Holds information of all the tiles in the map and can be
 * accessed by tileMap[col][row]
 *
 * @author Jeffrey Ramacula
 * @version 12/06/2022
 * */
public class TileMap {
    Game game;
    public Tile [] tileImages;

    public int numCols;
    public int numRows;

    public Tile[][] tileMap;
    int[][] randomMap;
    MapGenerator mapGenerator;

    /**
     * Creates a new tile map
     *
     * @param g a <code>Game</code> to refer to
     */
    public TileMap(Game g){
        this.game = g;
        numCols = g.cols;
        numRows = g.rows;
        tileImages = new Tile[4];
        tileMap = new Tile[numCols][numRows];
        mapGenerator = new MapGenerator(game);
        randomMap = mapGenerator.generateRandomMap();
        getTiles();
        generateMap();
    }

    /**
     * Gets tiles from resources and stores them in tileImages array
     */
    public void getTiles(){
        try {
            //path tile
            tileImages[0] = new Tile();
            tileImages[0].image = ImageIO.read(getClass().getResourceAsStream("/background/grass.png"));

            //wall tile
            tileImages[1] = new Tile();
            tileImages[1].image = ImageIO.read(getClass().getResourceAsStream("/background/wall.png"));
            tileImages[1].isBlocked = true;

            //door tile
            tileImages[2] = new Tile();
            tileImages[2].image = ImageIO.read(getClass().getResourceAsStream("/background/cage.png"));
            tileImages[2].isBlocked = true;

            //cage tile
            tileImages[3] = new Tile();
            tileImages[3].image = ImageIO.read(getClass().getResourceAsStream("/background/cage.png"));
            tileImages[3].isBlocked = true;

        }catch (IOException e){
            e.printStackTrace();
        }
    }

    /**
     * Generates a new map
     */
    public void makeNewMap(){
        randomMap = mapGenerator.generateRandomMap();
        generateMap();

    }
    
    /**
     * Initializes tileMap with tiles based on a random map from MapGenerator
     * Draws the map onto the screen
     */
    public void generateMap() {
        int tileIndex;

        for (int row = 0; row < numRows; row++) {
            for (int col = 0; col < numCols; col++) {
                tileMap[col][row] = new Tile();
                tileIndex = randomMap[col][row];
                tileMap[col][row].image = tileImages[tileIndex].image;
                tileMap[col][row].isBlocked = tileImages[tileIndex].isBlocked;
                tileMap[col][row].hasFixedEntity = tileImages[tileIndex].hasFixedEntity;
                tileMap[col][row].FixedEntityObject = tileImages[tileIndex].FixedEntityObject;
            }
        }
    }

    /**
     * Adds fixedEntity to tileMap
     *
     * @param column column on map to add fixedEntity
     * @param row row on map to add fixedEntity
     * @param fixedEntity fixedEntity object to add to map
     * */
    public void addFixedEntitytoMap(int column, int row, FixedEntity fixedEntity) {
        if(column < 0 || column >= game.cols || row < 0 || row >= game.rows) {
            System.out.printf("(%d,%d) is an invalid position", column,row);
            return;
        }
        
        this.tileMap[row][column].FixedEntityObject = fixedEntity;
        this.tileMap[row][column].hasFixedEntity = true;
    }

    /**
     * Draws the map to the screen
     *
     * @param g2 the <code>Graphics2D</code> object used to draw
     */
    public void drawMap(Graphics2D g2){
        for (int row = 0; row <numRows; row++) {
            for (int col = 0; col < numCols; col++) {
                drawTile(g2, tileMap[col][row].image, col* game.tileSize, row*game.tileSize);
            }
        }
    }

    /**
     * Draws a tile on the screen
     *
     * @param g2 the <code>Graphics2D</code> object used to draw
     * @param tileImage the image of the tile to draw
     * @param col the column where the tile is to be drawn
     * @param row the row where the tile is to be drawn
     * */
    public void drawTile(Graphics2D g2, BufferedImage tileImage, int col, int row){
        g2.drawImage(tileImage, col, row, game.tileSize, game.tileSize, null);
    }
}
