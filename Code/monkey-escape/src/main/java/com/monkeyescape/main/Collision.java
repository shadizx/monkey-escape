package com.monkeyescape.main;

import com.monkeyescape.entity.fixedentity.FixedEntity;
import com.monkeyescape.entity.movingentity.Monkey;
import com.monkeyescape.entity.movingentity.MovingEntity;
import com.monkeyescape.entity.movingentity.Zookeeper;
import com.monkeyescape.map.Tile;

import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;
import java.util.stream.Stream;

/**
 * Handles Collisions between background tiles and Entities
 *
 * @author Jeffrey Ramacula, Shadi Zoldjalali
 * @version 12/06/2022
 * */
public class Collision {
    Game game;

    public long delayedDamages = 0;

    /**
     * Initializes a Collision Checker to the panel
     *
     * @param game a <code>Game</code> to refer to
     */
    public Collision(Game game){
        this.game = game;
    }

    /**
     * Checks nearby tiles in the direction the entity wants to move to
     *
     * @param entity the entity that wants to move
    */
    public void checkTile(MovingEntity entity){
        int LeftX = entity.x + entity.area.x;
        int RightX = entity.x + entity.area.x + entity.area.width;
        int TopY = entity.y + entity.area.y;
        int BottomY = entity.y + entity.area.y + entity.area.height;

        int ColLeft = LeftX/ game.tileSize;
        int ColRight = RightX/ game.tileSize;
        int RowTop = TopY/ game.tileSize;
        int RowBottom = BottomY/ game.tileSize;

        // Check that tile is in map
        if (ColLeft < 0 || ColRight >= game.cols || RowTop < 0 || RowBottom >= game.rows) {
            switch (entity.direction) {
                case "up" ->
                    System.out.printf("Entity colliding with the tile (%d,%d) is not on the map", entity.x / game.tileSize, RowTop);
                case "right" ->
                    System.out.printf("Entity colliding with the tile (%d,%d) is not on the map", ColRight, entity.y / game.tileSize);
                case "down" ->
                    System.out.printf("Entity colliding with the tile (%d,%d) is not on the map", entity.x / game.tileSize, RowBottom);
                case "left" ->
                    System.out.printf("Entity colliding with the tile (%d,%d) is not on the map", ColLeft, entity.y / game.tileSize);
            }

            //Set that entity is collided so it cannot continue to move
            entity.collided = true;
            return;
        }

        switch (entity.direction) {
            case "up" -> {
                //checks if top left or top right corner of entity is touching another tile
                RowTop = (TopY - entity.speed) / game.tileSize;
                if (game.tileMap.tileMap[ColLeft][RowTop].isBlocked || game.tileMap.tileMap[ColRight][RowTop].isBlocked) {
                    entity.collided = true;
                }
            }
            case "right" -> {
                //checks if top right or bottom right corner of entity is touching another tile
                ColRight = (RightX + entity.speed) / game.tileSize;
                if (game.tileMap.tileMap[ColRight][RowTop].isBlocked || game.tileMap.tileMap[ColRight][RowBottom].isBlocked) {
                    entity.collided = true;
                }
            }
            case "down" -> {
                //checks if bottom left or bottom right corner of entity is touching another tile
                RowBottom = (BottomY + entity.speed) / game.tileSize;
                if (game.tileMap.tileMap[ColLeft][RowBottom].isBlocked || game.tileMap.tileMap[ColRight][RowBottom].isBlocked) {
                    entity.collided = true;
                }
            }
            case "left" -> {
                //checks if top left or bottom left corner of entity is touching another tile
                ColLeft = (LeftX - entity.speed) / game.tileSize;
                if (game.tileMap.tileMap[ColLeft][RowTop].isBlocked || game.tileMap.tileMap[ColLeft][RowBottom].isBlocked) {
                    entity.collided = true;
                }
            }
        }

        //checks if the tile exit is unlocked and the monkey can enter the tile
        if(!(game.tileMap.tileMap[game.doorPos.x][game.doorPos.y].isBlocked)
                && ((ColLeft == game.doorPos.x && RowBottom == game.doorPos.y)
                || (ColRight == game.doorPos.x && RowBottom == game.doorPos.y))) {
            //Calls next level function here
            game.nextLevel();
            //sets tile back to blocked so that monkey does not go past borders and ensures this only triggers once
            game.tileMap.tileMap[game.doorPos.x][game.doorPos.y].isBlocked = true;
        }
    }

    /**
     * Checks the tiles that the monkey is touching for an FixedEntity object
     *
     * @param entity entity that wants check if their tile has an FixedEntity object
     * @return Returns true is there was a fixed entity on that tile, false if not.
     * */
    public boolean checkFixedEntity(MovingEntity entity){
        int LeftX = entity.x + entity.area.x;
        int RightX = entity.x + entity.area.x + entity.area.width;
        int TopY = entity.y + entity.area.y;
        int BottomY = entity.y + entity.area.y + entity.area.height;

        int colLeft = LeftX/ game.tileSize;
        int colRight = RightX/ game.tileSize;
        int rowTop = TopY/ game.tileSize;
        int rowBottom = BottomY/ game.tileSize;

        // Check that tile is in map
        if (colLeft < 0 || colRight >= game.cols || rowTop < 0 || rowBottom >= game.rows) {
            switch (entity.direction) {
                case "up" -> System.out.printf("The tile (%d,%d) is not on the map", entity.x / game.tileSize, rowTop);
                case "right" ->
                    System.out.printf("The tile (%d,%d) is not on the map", colRight, entity.y / game.tileSize);
                case "down" ->
                    System.out.printf("The tile (%d,%d) is not on the map", entity.x / game.tileSize, rowBottom);
                case "left" ->
                    System.out.printf("The tile (%d,%d) is not on the map", colLeft, entity.y / game.tileSize);
            }
            return false;
        }

        List<Tile> potentialCollisions = Stream.of(
            game.tileMap.tileMap[colLeft][rowTop],
            game.tileMap.tileMap[colRight][rowTop],
            game.tileMap.tileMap[colLeft][rowBottom],
            game.tileMap.tileMap[colRight][rowBottom])
            .filter(tile -> tile.FixedEntityObject != null)
            .collect(Collectors.toList());

        processCollision(game, potentialCollisions, entity);
        return (potentialCollisions.size()>0);
    }

    /**
     * Checks if an entity collides with an enemy, by checking if their areas intersect
     *
     * @param entity the entity that is checking for an enemy
     * @param zookeepers a list of Enemy entities
     * */
    public boolean checkZookeeper(MovingEntity entity, List<Zookeeper> zookeepers){
        // Check that tile is in map
        if (entity.x < 0 || entity.x >= game.width || entity.y < 0 || entity.y >= game.height) {
            System.out.println("The entity is not on the map");
            return false;
        }

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
                        game.state.setGameState(State.GameState.GAMEOVER);
                    }
                    break;
                case "right":
                    entity.area.x += entity.speed;
                    if(entity.area.intersects(zookeeper.area)){
                        game.state.setGameState(State.GameState.GAMEOVER);
                    }
                    break;
                case "down":
                    entity.area.y += entity.speed;
                    if(entity.area.intersects(zookeeper.area)){
                        game.state.setGameState(State.GameState.GAMEOVER);
                    }
                    break;
                case "left":
                    entity.area.x -= entity.speed;
                    if(entity.area.intersects(zookeeper.area)){
                        game.state.setGameState(State.GameState.GAMEOVER);
                    }
                    break;
                default:
                    if(entity.area.intersects(zookeeper.area)){
                        game.state.setGameState(State.GameState.GAMEOVER);
                    }
            }

            entity.area.x = entity.areaX;
            entity.area.y = entity.areaY;
            zookeeper.area.x = zookeeper.areaX;
            zookeeper.area.y = zookeeper.areaY;
        }

        return (game.state.getGameState() == State.GameState.GAMEOVER); //Returns if the entity collided with a zookeeper
    }

    /**
     * Updates game information based on collision occurred
     *
     * @param game a <code>Game</code> to refer to
     * @param potentialCollisions a list of potential tile collisions
     * @param entity the <code>MovingEntity</code> that is being collided with
     */
    public void processCollision(Game game, List<Tile> potentialCollisions, MovingEntity entity) {
        if (potentialCollisions.size() == 0) return;

        Tile collidedTile = potentialCollisions.get(0);
        if (collidedTile.hasFixedEntity) {
            FixedEntity entityCollidedWith = collidedTile.FixedEntityObject;

            // We don't want to remove lion pits
            if (!Objects.equals(entityCollidedWith.type, "lionpit")) {
                game.score += entityCollidedWith.impact;
                entityCollidedWith.remove();
                collidedTile.hasFixedEntity = false;
                potentialCollisions.clear();

            }
            else if(entity.type.equals("monkey") && !Monkey.inLionPit && Monkey.lionPitInvincibility <= 0) { //If monkey is colliding with lion pit
                if(Math.abs(entity.x - collidedTile.FixedEntityObject.x) < 16 && Math.abs(entity.y - collidedTile.FixedEntityObject.y) < 16) {
                    //Checks that monkey is directly on the tile to avoid being stuck in lion pit when just going near it
                    delayedDamages = entityCollidedWith.impact;
                    Monkey.inLionPit = true;    
                }
            }
        }
    }
}
