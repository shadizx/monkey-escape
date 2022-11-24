package com.monkeyescape.map;

import com.monkeyescape.main.Game;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.Random;

/**
 * Generates a random maze-like map
 *
 * @author Jeffrey Ramacula
 * @version 11/02/2022
 * */
public class MapGenerator {
    enum Direction {UP, DOWN, LEFT, RIGHT}

    int numRows;
    int numCols;

    int startCol, startRow;
    int exitCol, exitRow;

    int[][] map;
    int col;
    int row;

    /**
     * Creates a new map generator
     *
     * @param game a <code>Panel</code> to refer to
     */
    public MapGenerator(Game game) {
        this.numRows = game.rows;
        this.numCols = game.cols;
        this.map = new int[numCols][numRows];

        //starting position of the cage around boundary
        startCol = game.startCol;
        startRow = game.startRow;

        //start of maze
        col = startCol;
        row = startRow +1;

        //ending position of the door/exit around boundary
        exitCol = game.exitCol;
        exitRow = game.exitRow;
    }

    /**Initializes the starting map to all walls,
     *  and calls {@link #newRandomMaze(int[][], int, int) newRandomMaze} on it,
     *  and adds spaces using {@link #addSpaces(int[][]) addSpaces}
     *
     * @return the generated random map
     */
    public int[][] generateRandomMap() {
        Arrays.stream(map).forEach(tile -> Arrays.fill(tile, 1));
        int[][] randomMap = newRandomMaze(map, startCol, startRow);
        addSpaces(map);
        return randomMap;
    }

    /**
     * Recursively generates a perfect solvable maze
     * col and row are currently set to be in front of the starting position.
     * The initial map is assumed to be initialized with all walls
     *
     * @param map the map to put the maze on
     * @param col seed column for the generation of maze
     * @param row seed row for the generation of maze
     * @return a perfect random maze
     */
    public int[][] newRandomMaze(int[][] map, int col, int row) {
        ArrayList<Direction> directions = new ArrayList<>() {
            {
                add(Direction.UP);
                add(Direction.DOWN);
                add(Direction.RIGHT);

                add(Direction.LEFT);
            }
        };
        //shuffle directions
        Collections.shuffle(directions, new Random());
        //set current tile to 0
        map[col][row] = 0;

        int nextRow;
        int nextCol;
        int rowBetween;
        int colBetween;

        while (directions.size() > 0) {
            nextRow = row;
            nextCol = col;
            rowBetween = row;
            colBetween = col;
            Direction chosenDirection = directions.remove(0);
            switch (chosenDirection) {
                case UP:
                    nextRow -= 2;
                    rowBetween -= 1;
                    break;
                case DOWN:
                    nextRow += 2;
                    rowBetween += 1;
                    break;
                case LEFT:
                    nextCol -= 2;
                    colBetween -= 1;
                    break;
                case RIGHT:
                    nextCol += 2;
                    colBetween += 1;
                    break;
            }
            if (nextRow > 0 && nextRow < numRows-1 && nextCol > 0 && nextCol < numCols-1) {
                if (map[nextCol][nextRow] == 1) {
                    map[colBetween][rowBetween] = 0;
                    newRandomMaze(map, nextCol, nextRow);
                }
            }
        }
        return map;
    }
    /**
     *This method takes a perfect Maze and deletes some walls to
     * introduce cycles and empty space in the map
     *
     * @param map a perfect maze generated from {@link #newRandomMaze(int[][], int, int) newRandomMaze} method
     */
    public void addSpaces(int[][] map){
        int spacesToAdd = 15;
        //clear corners
        //Top-Left Corner
        map[1][1] = 0;
        map[2][1] = 0;
        map[1][2] = 0;
        map[2][2] = 0;

        //Top-Right Corner
        map[numCols-2][1] = 0;
        map[numCols-2][2] = 0;
        map[numCols-3][2] = 0;
        map[numCols-3][1] = 0;

        //Bottom-Right Corner
        map[numCols-2][numRows-2] = 0;
        map[numCols-2][numRows-3] = 0;
        map[numCols-3][numRows-2] = 0;
        map[numCols-3][numRows-3] = 0;

        //Bottom-Left Corner
        map[1][numRows-2] = 0;
        map[1][numRows-3] = 0;
        map[2][numRows-2] = 0;
        map[2][numRows-3] = 0;

        //add 2x2 spaces randomly to create cycles
        while(spacesToAdd > 0){
            int randomCol = (int) (Math.random()*(numCols) +1);
            int randomRow = (int) (Math.random()*(numRows) +1);
            if((randomRow+1 < numRows-1) && (randomCol+1 < numCols-1) && (map[randomCol][randomRow] != 0)){
                map[randomCol][randomRow] = 0;
                map[randomCol + 1][randomRow] = 0;
                map[randomCol][randomRow + 1] = 0;
                map[randomCol + 1][randomRow + 1] = 0;
                spacesToAdd--;
            }
        }
        //add start and exit tiles
        map[startCol][startRow] = 3;
        map[exitCol][exitRow] = 2;
    }
}
