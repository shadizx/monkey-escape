package com.monkeyescape.map;

import com.monkeyescape.entity.fixedentity.FixedEntity;
import com.monkeyescape.main.Panel;

import javax.imageio.ImageIO;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;

/**
 * holds information of all the tiles in the map and can be
 * accessed by tileMap[col][row]
 * @author Jeffrey Ramacula
 * @version 10/30/2022
 * */
public class TileMap {

    Panel p;
    Tile [] tileImages;


    int numCols;
    int numRows;


    public Tile[][] tileMap;

    public TileMap(Panel p){
        this.p = p;
        numCols = p.cols;
        numRows = p.rows;
        tileImages = new Tile[4];
        tileMap = new Tile[numCols][numRows];
        getTiles();
        generateMap();

    }

    /**
     * Gets tiles from resources and stores them in tileImages array
     * */
    public void getTiles(){

        try {
            //path tile
            tileImages[0] = new Tile();
            tileImages[0].image = ImageIO.read(getClass().getResourceAsStream("/background/grass.png"));

            //wall tile
            tileImages[1] = new Tile();
            tileImages[1].image = ImageIO.read(getClass().getResourceAsStream("/background/wall.png"));
            tileImages[1].blocked = true;

            //TODO change image to door once we have a door
            //door tile
            tileImages[2] = new Tile();
            tileImages[2].image = ImageIO.read(getClass().getResourceAsStream("/background/cage.png"));
            tileImages[2].blocked = true;

            //cage tile
            tileImages[3] = new Tile();
            tileImages[3].image = ImageIO.read(getClass().getResourceAsStream("/background/cage.png"));
            tileImages[3].blocked = true;

        }catch (IOException e){
            e.printStackTrace();
        }

    }

    /**
     * Initializes tileMap with tiles based on a map in the stored in maps as a txt file
     * Draws the map onto the screen*/
    public void generateMap(){
        try {
            InputStream is = getClass().getResourceAsStream("/maps/map1.txt");
            BufferedReader br = new BufferedReader(new InputStreamReader(is));
            int row = 0;
            int col = 0;
            int tileIndex;

            while(col < numCols && row < numRows){
                String ln = br.readLine();
                String [] tile = ln.split(" ");
                while(col < numCols){
                    tileMap[col][row] = new Tile();
                    tileIndex = Integer.parseInt(tile[col]);
                    tileMap[col][row].image = tileImages[tileIndex].image;
                    tileMap[col][row].blocked = tileImages[tileIndex].blocked;
                    tileMap[col][row].hasFixedEntity = tileImages[tileIndex].hasFixedEntity;
                    tileMap[col][row].FixedEntityObject = tileImages[tileIndex].FixedEntityObject;
                    col++;
                }
                col = 0;
                row++;
            }
        } catch (IOException e) {
            throw new RuntimeException(e);
        }

    }
    /**
     * Adds fixedEntity to tileMap
     * @param column column on map to add fixedEntity
     * @param row row on map to add fixedEntity
     * @param fixedEntity fixedEntity object to add to map
     * */
    public void addFixedEntitytoMap(int column, int row, FixedEntity fixedEntity){
        this.tileMap[row][column].FixedEntityObject = fixedEntity;
        this.tileMap[row][column].hasFixedEntity = true;
    }

    /**
     * Draws the map to the screen*/
    public void drawMap(Graphics2D g2){
        int row = 0;
        int col = 0;
        while(col < numCols && row < numRows){
            while(col < numCols){
                drawTile(g2, tileMap[col][row].image, col* p.tileSize, row*p.tileSize);
                col++;
            }
            col = 0;
            row++;
        }
    }
    /**
     * Draws a tile on the screen
     * @param tileImage the image of the tile to draw
     * @param col the column where the tile is to be drawn
     * @param row the row where the tile is to be drawn
     * */
    public void drawTile(Graphics2D g2, BufferedImage tileImage, int col, int row){
        g2.drawImage(tileImage, col, row, p.tileSize, p.tileSize, null);
    }


}
