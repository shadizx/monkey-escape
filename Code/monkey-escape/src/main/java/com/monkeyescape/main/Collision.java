package com.monkeyescape.main;

import com.monkeyescape.entity.fixedentity.FixedEntity;
import com.monkeyescape.entity.movingentity.MovingEntity;
import com.monkeyescape.entity.movingentity.Zookeeper;
import com.monkeyescape.map.Tile;

import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;
import java.util.stream.Stream;

/**
 * Handles Collisions between background tiles and Entities
 * @author Jeffrey Ramacula
 * @version 11/02/2022
 * */
public class Collision {
    Panel panel;

    public long delayedDamages = 0;

    /**
     * Initializes a Collision Checker to the panel
     * @param panel the panel to where the map is drawn on */
    public Collision(Panel panel){
        this.panel = panel;
    }

    /**
     * Checks nearby tiles in the direction the entity wants to move to
     * @param entity the entity that wants to move*/
    public void checkTile(MovingEntity entity){
        int LeftX = entity.x + entity.area.x;
        int RightX = entity.x + entity.area.x + entity.area.width;
        int TopY = entity.y + entity.area.y;
        int BottomY = entity.y + entity.area.y + entity.area.height;

        int ColLeft = LeftX/ panel.tileSize;
        int ColRight = RightX/ panel.tileSize;
        int RowTop = TopY/ panel.tileSize;
        int RowBottom = BottomY/ panel.tileSize;

        switch (entity.direction){
            case "up":
                //checks if top left or top right corner of entity is touching another tile
                RowTop = (TopY - entity.speed)/ panel.tileSize;
                if(panel.tm.tileMap[ColLeft][RowTop].blocked || panel.tm.tileMap[ColRight][RowTop].blocked){
                     entity.collided = true;
                }
                break;

            case "right":
                //checks if top right or bottom right corner of entity is touching another tile
                ColRight = (RightX + entity.speed)/ panel.tileSize;
                if(panel.tm.tileMap[ColRight][RowTop].blocked || panel.tm.tileMap[ColRight][RowBottom].blocked){
                    entity.collided = true;
                }
                break;

            case "down":
                //checks if bottom left or bottom right corner of entity is touching another tile
                RowBottom = (BottomY + entity.speed)/ panel.tileSize;
                if(panel.tm.tileMap[ColLeft][RowBottom].blocked || panel.tm.tileMap[ColRight][RowBottom].blocked){
                    entity.collided = true;
                }
                break;

            case "left":
                //checks if top left or bottom left corner of entity is touching another tile
                ColLeft = (LeftX - entity.speed)/ panel.tileSize;
                if(panel.tm.tileMap[ColLeft][RowTop].blocked || panel.tm.tileMap[ColLeft][RowBottom].blocked){
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

        int colLeft = LeftX/ panel.tileSize;
        int colRight = RightX/ panel.tileSize;
        int rowTop = TopY/ panel.tileSize;
        int rowBottom = BottomY/ panel.tileSize;

        List<Tile> potentialCollisions = Stream.of(
            panel.tm.tileMap[colLeft][rowTop],
            panel.tm.tileMap[colRight][rowTop],
            panel.tm.tileMap[colLeft][rowBottom],
            panel.tm.tileMap[colRight][rowBottom])
            .filter(tile -> tile.FixedEntityObject != null)
            .collect(Collectors.toList());

        processCollision(panel, potentialCollisions);
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

    public void processCollision(Panel panel, List<Tile> potentialCollisions) {
        if (potentialCollisions.size() == 0) return;

        Tile collidedTile = potentialCollisions.get(0);
        if (collidedTile.hasFixedEntity) {
            FixedEntity entityCollidedWith = collidedTile.FixedEntityObject;

            // We don't want to remove lion pits
            if (!Objects.equals(entityCollidedWith.type, "lionpit")) {
                panel.score += entityCollidedWith.impact;
                entityCollidedWith.remove();
                collidedTile.hasFixedEntity = false;
            }
            else {
                delayedDamages = entityCollidedWith.impact;
            }
        }
    }
}
