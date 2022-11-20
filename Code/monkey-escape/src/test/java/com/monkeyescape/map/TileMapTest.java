package com.monkeyescape.map;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import com.monkeyescape.entity.fixedentity.FixedEntity;
import com.monkeyescape.entity.fixedentity.Key;
import com.monkeyescape.main.Game;
import com.monkeyescape.main.Panel;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;

class TileMapTest {
    private TileMap tileMap;

    private Panel panel;
    private Game game = new Game(false, false);

    @BeforeEach
    void setup() {
        panel = new Panel(game);
        tileMap = new TileMap(panel);
    }

    @Test
    @DisplayName("Running GetTiles Then Checking TileImages[]")
    void getTiles() {
        tileMap.getTiles();

        //Check that it correctly assignes the tileimages
        assertFalse(tileMap.tileImages[0].blocked);
        assertTrue(tileMap.tileImages[1].blocked);
        assertTrue(tileMap.tileImages[2].blocked);
        assertTrue(tileMap.tileImages[3].blocked);
    }

    @Test
    @DisplayName("Running Make New Map")
    void makeNewMap() {
        //The original map
        Tile[][] oldTileMap = new Tile[panel.cols][panel.rows];
        for(int col = 0; col < panel.cols; col++){
            for(int row = 0; row < panel.rows; row++){
                oldTileMap[col][row] = new Tile();
                oldTileMap[col][row].image = tileMap.tileMap[col][row].image;
            }
        }

        //Make a new map
        tileMap.makeNewMap();

        //Check that the new map is different than the old one
        Tile[][] newTileMap = tileMap.tileMap;

        boolean same = true;
        for(int col = 0; col < panel.cols; col++){
            for(int row = 0; row < panel.rows; row++){
                if(oldTileMap[col][row].image != newTileMap[col][row].image){
                    same = false;
                }
            }
        }
        assertFalse(same);
    }

    @Test
    @DisplayName("Running GenerateMap")
    void generateMap() {
        //Make a new map
        tileMap.generateMap();

        //Check that randomMap was copied into tileMap
        boolean same = true;
        for(int col = 0; col < panel.cols; col++){
            for(int row = 0; row < panel.rows; row++){
                if(tileMap.tileMap[col][row].image != tileMap.tileImages[tileMap.randomMap[col][row]].image){
                    same = false;
                }
            }
        }
        assertTrue(same);
    }

    @Test
    @DisplayName("Running addFixedEntitytoMap")
    void addFixedEntitytoMap() {
        //Assign FixedEntity to a position
        FixedEntity fixedEntity = new Key(panel);
        tileMap.addFixedEntitytoMap(8, 8, fixedEntity);

        assertEquals(fixedEntity,tileMap.tileMap[8][8].FixedEntityObject);
    }

    //TO DO drawMap() ?
}