package com.monkeyescape.entity.movingentity;

import com.monkeyescape.entity.Position;
import com.monkeyescape.entity.movingentity.Pathfinding.Pathfinding;
import com.monkeyescape.main.KeyHandler;
import com.monkeyescape.main.Panel;

import java.awt.*;

/**
 * Represents the zookeeper
 * @author Henry Ruckman-Utting
 * @version 10/30/2022
 */
public class Zookeeper extends MovingEntity {
    Monkey monkey;
    public Pathfinding pathfinder;
    
    /**
     * Initializes a zookeeper with random position and connected to the monkey
     * @param panel A <code>Panel</code>> to refer to
     * @param kh a <code>KeyHandler</code> for handling key inputs
     * @param monkey a <code>Monkey</code> to refer to
     */
    public Zookeeper(Panel panel, KeyHandler kh, Monkey monkey) {
        super(panel, kh);
        type = "zookeeper";
        speed = 4;
        this.monkey = monkey;
        loadImage();

        // random starting position
        Position randomPosition = super.createRandomPosition(panel);
        x = randomPosition.x;
        y = randomPosition.y;

        //Checks if zookeeper is too close off of spawn
        while(tilesToMonkey() < 15){
            randomPosition = super.createRandomPosition(panel);
            x = randomPosition.x;
            y = randomPosition.y;
        }

        areaX = 6;
        areaY = 14;

        //set entity area to be smaller than tile so that it can fit in 1 tile spaces
        area = new Rectangle(areaX, areaY, 32, 32);
        speed = 1;

        direction = "down";
        collided = false;

        //Connects to new pathfinder
        pathfinder = new Pathfinding(panel);
    }

    /**
     * Moves the zookeeper towards the monkey
     */
    public void update(){ //Can create better pashfinding once walls are implemented
        
        //If the Zookeeper is fully on a tile, it gets the next tile to move to
        if(x % panel.tileSize == 0 && y % panel.tileSize == 0){
            //Add monkey.area to x and y to avoid thinking monkey is on wrong tile due to overlap
            searchPath((monkey.x + (monkey.areaX))/panel.tileSize, (monkey.y + (monkey.areaY))/panel.tileSize); 
        }
        
        //Checks for collisions before moving
        collided = false;
        panel.collisionChecker.checkTile(this);
        if (!collided) {
            switch (direction) {
                case "up":
                    y -= speed;
                    break;
                case "right":
                    x += speed;
                    break;
                case "down":
                    y += speed;
                    break;
                case "left":
                    x -= speed;
                    break;
            }
        }

    }

    /**
     * Search for the best path to the goal position and then updates direction
     * @param goalCol the colomn that the zookeeper needs to move to
     * @param goalRow the row that the zookeeper needs to move to
     */
    public void searchPath(int goalCol, int goalRow){
        //Gets zookeepers tile
        int startCol = x/panel.tileSize;
        int startRow = y/panel.tileSize;

        //Updates the pathfinders nodes for new goal and start positions
        pathfinder.setNodes(startCol, startRow, goalCol, goalRow);

        if(pathfinder.search() == true){
            //Only runs if pathfinder was able to find a path

            //Get the x and y of the next tile
            int nextX = pathfinder.pathList.get(0).col * panel.tileSize;
            int nextY = pathfinder.pathList.get(0).row * panel.tileSize;

            //Get the entities x and y
            int enLeftX = x - areaX;
            int enRightX = x;
            int enTopY = y + areaY;
            int enBottomY = y;


            //If statements to decide best movement to the next tile while avoiding collisions
    
            if(enTopY > nextY && enLeftX >= nextX && enRightX < nextX + panel.tileSize){
                //Zookeeper is directly under the next tile
                direction = "up";
            }
            else if(enTopY < nextY && enLeftX >= nextX && enRightX < nextX + panel.tileSize){
                //Zookeeper is directly above the next tile
                direction = "down";
            }
            else if(enTopY >= nextY && enBottomY < nextY + panel.tileSize){
                //Zookeeper is directly to the side of the next tile
                if(enLeftX > nextX){
                    direction = "left";
                }
                if(enLeftX < nextX){
                    direction = "right";
                }
            }
            else if (enTopY > nextY && enLeftX > nextX){
                //Zookeeper is below and to the right of the next tile
                direction = "up";
                
                collided = false;
                panel.collisionChecker.checkTile(this);

                if(collided){
                    //If it is colliding with an object then move left first
                    direction = "left";
                }
            }
            else if (enTopY > nextY && enLeftX < nextX){
                //Zookeeper is below and to the left of the next tile
                direction = "up";
                
                collided = false;
                panel.collisionChecker.checkTile(this);

                if(collided){
                    //If it is colliding with an object then move right first
                    direction = "right";
                }
            }
            else if (enTopY < nextY && enLeftX > nextX){
                //Zookeeper is above and to the right of the next tile
                direction = "down";
                
                collided = false;
                panel.collisionChecker.checkTile(this);

                if(collided){
                    //If it is colliding with an object then move left first
                    direction = "left";
                }
            }
            else if (enTopY < nextY && enLeftX < nextX){
                //Zookeeper is above and to the left of the next tile
                direction = "down";
                
                collided = false;
                panel.collisionChecker.checkTile(this);

                if(collided){
                    //If it is colliding with an object then move right first
                    direction = "right";
                }
            }
        }
    }

    /**
     * Calculates the distance from the zookeeper to the monkey
     */
    public int tilesToMonkey(){
        return Math.abs(monkey.x - x)*panel.tileSize + Math.abs(monkey.y - y)*panel.tileSize;
    }
    public boolean remove(){
        return panel.removeEntity(this);
    }
    public boolean removeZookeeper(){
        return panel.removeZookeeper(this);
    }
}

