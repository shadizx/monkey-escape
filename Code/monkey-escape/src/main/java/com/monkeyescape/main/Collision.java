package com.monkeyescape.main;

import com.monkeyescape.entity.movingentity.MovingEntity;
import com.monkeyescape.entity.movingentity.Zookeeper;

import java.util.List;

/**
 * Handles Collisions between background tiles and Entities
 * @author Jeffrey Ramacula
 * @version 10/30/2022
 * */


public class Collision {
    Panel p;

    /**
     * Initializes a Collision Checker to the panel
     * @param panel the panel to where the map is drawn on */
    public Collision(Panel panel){
        this.p = panel;
    }

    /**
     * Checks nearby tiles in the direction the entity wants to move to
     * @param entity the entity that wants to move*/

    public void checkTile(MovingEntity entity){
        int LeftX = entity.x + entity.area.x;
        int RightX = entity.x + entity.area.x + entity.area.width;
        int TopY = entity.y + entity.area.y;
        int BottomY = entity.y + entity.area.y + entity.area.height;

        int ColLeft = LeftX/p.tileSize;
        int ColRight = RightX/p.tileSize;
        int RowTop = TopY/p.tileSize;
        int RowBottom = BottomY/p.tileSize;




        switch (entity.direction){
            case "up":
                //checks if top left or top right corner of entity is touching another tile
                RowTop = (TopY - entity.speed)/p.tileSize;
                if(p.tm.tileMap[ColLeft][RowTop].blocked || p.tm.tileMap[ColRight][RowTop].blocked){
                     entity.collided = true;
                }
                break;

            case "right":
                //checks if top right or bottom right corner of entity is touching another tile
                ColRight = (RightX + entity.speed)/p.tileSize;
                if(p.tm.tileMap[ColRight][RowTop].blocked || p.tm.tileMap[ColRight][RowBottom].blocked){
                    entity.collided = true;
                }

                break;

            case "down":
                //checks if bottom left or bottom right corner of entity is touching another tile
                RowBottom = (BottomY + entity.speed)/p.tileSize;
                if(p.tm.tileMap[ColLeft][RowBottom].blocked || p.tm.tileMap[ColRight][RowBottom].blocked){
                    entity.collided = true;
                }


                break;

            case "left":
                //checks if top left or bottom left corner of entity is touching another tile
                ColLeft = (LeftX - entity.speed)/p.tileSize;
                if(p.tm.tileMap[ColLeft][RowTop].blocked || p.tm.tileMap[ColLeft][RowBottom].blocked){
                    entity.collided = true;
                }


                break;
        }



    }

    /**
     * Checks the tiles that the monkey is touching for an Interacrtable object
     * @param entity entity that wants check if their tile has an Interactable object
     * */
    public void checkInteractable(MovingEntity entity){
        int LeftX = entity.x + entity.area.x;
        int RightX = entity.x + entity.area.x + entity.area.width;
        int TopY = entity.y + entity.area.y;
        int BottomY = entity.y + entity.area.y + entity.area.height;

        int colLeft = LeftX/p.tileSize;
        int colRight = RightX/p.tileSize;
        int rowTop = TopY/p.tileSize;
        int rowBottom = BottomY/p.tileSize;

        //removes interactable for now
        //TODO: make interactable does something(increase score) when player hits it.
        if(p.tm.tileMap[colLeft][rowTop].hasFixedEntity){
            p.tm.tileMap[colLeft][rowTop].FixedEntityObject.remove();
            p.tm.tileMap[colLeft][rowTop].hasFixedEntity = false;

        }
        if(p.tm.tileMap[colRight][rowTop].hasFixedEntity){
            p.tm.tileMap[colRight][rowTop].FixedEntityObject.remove();
            p.tm.tileMap[colRight][rowTop].hasFixedEntity = false;
        }
        if(p.tm.tileMap[colLeft][rowBottom].hasFixedEntity){
            p.tm.tileMap[colLeft][rowBottom].FixedEntityObject.remove();
            p.tm.tileMap[colLeft][rowBottom].hasFixedEntity = false;

        }
        if(p.tm.tileMap[colRight][rowBottom].hasFixedEntity){
            p.tm.tileMap[colRight][rowBottom].FixedEntityObject.remove();
            p.tm.tileMap[colRight][rowBottom].hasFixedEntity = false;

        }

    }

    /**
     * Checks if an entity collides with an enemy, by checking if their areas intersect
     * @param entity the entity that is checking for an enemy
     * @param zookeepers a list of Enemy entities*/
    public void checkZookeeper(MovingEntity entity, List<Zookeeper> zookeepers){

        for (int i = 0; i < zookeepers.size(); i++) {
            Zookeeper zookeeper = zookeepers.get(i);

            //entities area
            entity.area.x = entity.x + entity.area.x;
            entity.area.y = entity.y + entity.area.y;

            //Enemies area
            zookeeper.area.x = zookeeper.x + zookeeper.area.x;
            zookeeper.area.y = zookeeper.y + zookeeper.area.y;

            switch (entity.direction){

                case "up":
                    entity.area.y -= entity.speed;
                    if(entity.area.intersects(zookeeper.area)){
                        //remove zookeeper for now
                        zookeeper.removeZookeeper();
                        zookeeper.remove();
                    }
                    break;
                case "right":
                    entity.area.x += entity.speed;
                    if(entity.area.intersects(zookeeper.area)){
                        //remove zookeeper for now
                        zookeeper.removeZookeeper();
                        zookeeper.remove();
                    }
                    break;
                case "down":
                    entity.area.y += entity.speed;
                    if(entity.area.intersects(zookeeper.area)){
                        //remove zookeeper for now
                        zookeeper.removeZookeeper();
                        zookeeper.remove();
                    }
                    break;
                case "left":
                    entity.area.x -= entity.speed;
                    if(entity.area.intersects(zookeeper.area)){
                        //remove zookeeper for now
                        zookeeper.removeZookeeper();
                        zookeeper.remove();
                    }
                    break;

            }

            entity.area.x = entity.areaX;
            entity.area.y = entity.areaY;
            zookeeper.area.x = zookeeper.areaX;
            zookeeper.area.y = zookeeper.areaY;
        }
    }

}


