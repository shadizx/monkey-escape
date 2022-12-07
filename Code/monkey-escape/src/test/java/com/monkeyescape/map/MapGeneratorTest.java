package com.monkeyescape.map;

import com.monkeyescape.main.Game;
import com.monkeyescape.main.KeyHandler;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.util.Arrays;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

class MapGeneratorTest {
    private MapGenerator mapGenerator;

    private Game game;

    private KeyHandler keyHandler = new KeyHandler();

    @BeforeEach
    void setup() {
        game = new Game(keyHandler);
        mapGenerator = new MapGenerator(game);
    }

    @Test
    @DisplayName("Running generateRandomMap")
    void generateRandomMap() {
        int[][] map = mapGenerator.generateRandomMap();

        //Check that every tile in the map has an integer value between 0 and 3
        boolean between0and3 = true;
        for(int col = 0; col < game.cols; col++){
            for(int row = 0; row < game.rows; row++){
                if(map[col][row] < 0 || map[col][row] > 3){
                    between0and3 = false;
                }
            }
        }
        assertTrue(between0and3);

        //Check that the start and end are in the right places
        assertEquals(3,map[1][0]); //start
        assertEquals(2,map[14][15]); //end
    }

    @Test
    @DisplayName("Running newRandomMaze")
    void newRandomMaze() {
        //Create a new map that is only walls
        int[][] map = new int[game.cols][game.rows];
        Arrays.stream(map).forEach(tile -> Arrays.fill(tile, 1));

        //Call new random maze on the map
        int[][] mapnew = mapGenerator.newRandomMaze(map, mapGenerator.cagePos.x, mapGenerator.cagePos.y);

        //Check that every tile not on the edge is either a wall or grass block
        boolean between0and1 = true;
        for(int col = 1; col < game.cols-1; col++){
            for(int row = 1; row < game.rows-1; row++){
                if (mapnew[col][row] < 0 || mapnew[col][row] > 1) {
                    between0and1 = false;
                    break;
                }
            }
        }
        assertTrue(between0and1);
    }

    @Test
    @DisplayName("Running addSpaces")
    void addSpaces() {
        //Create a new map of only walls
        int[][] map = new int[game.cols][game.rows];
        Arrays.stream(map).forEach(tile -> Arrays.fill(tile, 1));

        //Call add spaces on map
        mapGenerator.addSpaces(map);

        // Check that at least 31 spaces have been added. 31 is worst case scenario
        // with 16 in corners and 15 additional cycles
        int spacesAdded = 0;
        for(int col = 0; col < game.cols; col++){
            for(int row = 0; row < game.rows; row++){
                if(map[col][row] == 0){
                    spacesAdded++;
                }
            }
        }
        assertTrue(spacesAdded>31);
    }
}