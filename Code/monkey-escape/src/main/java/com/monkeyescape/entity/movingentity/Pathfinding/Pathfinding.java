package com.monkeyescape.entity.movingentity.Pathfinding;

import com.monkeyescape.main.Game;

import java.util.ArrayList;

/**
 * Represents the pathfinding algorithm that is used by entities
 *
 * @author Henry Ruckman-Utting
 * @version 11/23/2022
 */
public class Pathfinding {
    protected Game game;
    protected Node[][] map;

    //A list of nodes that are open and have not been touched
    ArrayList<Node> openList = new ArrayList<>();
    //A list of nodes in the path to the goal
    public ArrayList<Node> pathList = new ArrayList<>();

    protected Node startNode;
    protected Node goalNode;
    protected Node currentNode;
    protected boolean goalReached = false;
    protected int step = 0;

    /**
     * Initializes the pathfinder and creates the new nodes
     *
     * @param game A <code>Game</code> to refer to
     */
    public Pathfinding(Game game){
        this.game = game;

        instantiateNodes();
    }  
    
    /**
     * Initializes the nodes to be used
     */
    public void instantiateNodes(){
        map = new Node[game.cols][game.rows];

        for (int row = 0; row < game.rows; row++) {
            for (int col = 0; col < game.cols; col++) {
                map[col][row] = new Node(col,row);
            }
        }
    }

    /**
     * Resets the nodes to search for the best path again
     */
    public void resetNodes(){
        for (int row = 0; row < game.rows; row++) {
            for (int col = 0; col < game.cols; col++) {
                map[col][row].open = false;
                map[col][row].checked = false;
                map[col][row].solid = false;
                map[col][row].path = false;
            }
        }

        openList.clear();
        pathList.clear();
        goalReached = false;
        step = 0;
    }

    /**
     * Sets up the nodes for pathfinding using the inputs for starting and ending positions
     *
     * @param startCol the column the search begins at
     * @param startRow the row the search begins at
     * @param goalCol the column we are searching to get to
     * @param goalRow the row we are searching to get to
     */
    public void setNodes(int startCol, int startRow, int goalCol, int goalRow){
        //Resets the nodes before setting them again
        resetNodes();

        //Sets the start,current, and goal nodes using the inputs
        startNode = map[startCol][startRow];
        currentNode = startNode;
        goalNode = map[goalCol][goalRow];
        openList.add(currentNode);

        //Set which tiles are blocked/solid
        for (int row = 0; row < game.rows; row++) {
            for (int col = 0; col < game.cols; col++) {
                map[col][row].solid = game.tileMap.tileMap[col][row].isBlocked;
            }
        }

        //Set monkeys tile to not solid in case it is the cage tile
        map[goalCol][goalRow].solid = false;

        //Gets the cost of the nodes
        for (int row = 0; row < game.rows; row++) {
            for (int col = 0; col < game.cols; col++) {
                getCost(map[col][row]);
            }
        }
    }

    /**
     * Gets the cost of that node by its position relative to the start and end position
     *
     * @param node the node that we are getting the cost of 
     */
    public void getCost(Node node){
        int xDist = Math.abs(node.col - startNode.col);
        int yDist = Math.abs(node.row - startNode.row);
        node.gCost = xDist + yDist;

        xDist = Math.abs(node.col - goalNode.col);
        yDist = Math.abs(node.row - goalNode.row);
        node.hCost = xDist + yDist;

        node.fCost = node.gCost + node.hCost;
    }

    /**
     * Performs the search using the A* algorithm
     *
     * @return true whether the goal can be reached, false otherwise
     */
    public boolean search(){
        while(!goalReached && step < 500){
            int col = currentNode.col;
            int row = currentNode.row;

            currentNode.checked = true;
            openList.remove(currentNode);

            //Opens the nodes adjacent to current node
            if(row - 1 >= 0){
                openNode(map[col][row-1]);
            }
            if(col - 1 >= 0){
                openNode(map[col-1][row]);
            }
            if(row + 1 < game.rows){
                openNode(map[col][row+1]);
            }
            if(col + 1 < game.cols){
                openNode(map[col+1][row]);
            }    
            
            int bestNodeIndex = 0;
            int bestNodefCost = 999;

            //Uses all open nodes to check which is the best to run the next cycle of the algorithm on by checking their costs
            for(int i = 0; i < openList.size(); i++){
                if(openList.get(i).fCost < bestNodefCost){
                    bestNodeIndex = i;
                    bestNodefCost = openList.get(i).fCost;
                }
                else if(openList.get(i).fCost == bestNodefCost){
                    if(openList.get(i).gCost < openList.get(bestNodeIndex).gCost){
                        bestNodeIndex = i;
                    }
                }
            }
            if(openList.size() == 0){
                break;
            }

            //Gets the best node for the next cycle
            currentNode = openList.get(bestNodeIndex);

            //If we've found the goal then retrace the path
            if(currentNode == goalNode){
                goalReached = true;
                trackThePath();
            }
            step++;
        }
        
        return goalReached;
    }

    /**
     * Opens up the specified node for search if it meets the parameters
     *
     * @param node the node to open up
     */
    public void openNode(Node node){
        if(!node.open && !node.checked && !node.solid){
            node.open = true;
            node.parent = currentNode;
            openList.add(node);
        }
    }

    /**
     * Once the goal node has been found we retrace our steps to find the path to that node
     */
    public void trackThePath(){
        Node current = goalNode;

        //Cycles back to the start node using current.parent to retrace steps
        while(current != startNode && current != null){
            pathList.add(0,current);
            current.path = true;
            current = current.parent;
        }
    }
}
