package com.monkeyescape.entity.movingentity.Pathfinding;

import com.monkeyescape.main.Game;
import com.monkeyescape.main.Panel;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertNotEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertTrue;

class PathfindingTest {
    private final Game game = new Game(false, false);
    private final Panel panel = new Panel(game);
    private final Pathfinding pf = new Pathfinding(panel);

    @Test
    @DisplayName("Running instantiateNodes test")
    void instantiateNodes() {
        assertNotNull(pf.node);
        assertEquals(pf.node.length, panel.cols);

        for (Node[] nodeRow : pf.node) {
            for (Node node : nodeRow) {
                assertNotEquals(node, null);
            }
        }
    }

    @Test
    @DisplayName("Running resetNodes test")
    void resetNodes() {
        for (int i = 0; i < pf.panel.rows; i++) {
            for (int j = 0; j < pf.panel.cols; j++) {
                assertFalse(pf.node[j][i].open);
                assertFalse(pf.node[j][i].checked);
                assertFalse(pf.node[j][i].solid);
                assertFalse(pf.node[j][i].path);
            }
        }

        assertEquals(pf.openList.size(), 0);
        assertEquals(pf.pathList.size(), 0);
        assertFalse(pf.goalReached);
        assertEquals(pf.step, 0);
    }

    @Test
    @DisplayName("Running setNodes test")
    void setNodes() {
        pf.resetNodes();
        pf.instantiateNodes();
        panel.tm.getTiles();
        panel.tm.generateMap();

        pf.setNodes(0, 0, 10, 10);
        for (int i = 0; i < pf.panel.rows; i++) {
            for (int j = 0; j < pf.panel.cols; j++) {
                assertEquals(pf.node[j][i].solid, panel.tm.tileMap[j][i].blocked);
            }
        }

        assertFalse(pf.node[10][10].solid);
        for (int i = 0; i < pf.panel.rows; i++) {
            for (int j = 0; j < pf.panel.cols; j++) {
                assertEquals(pf.node[j][i].gCost, Math.abs(pf.node[j][i].col - pf.startNode.col) + Math.abs(pf.node[j][i].row - pf.startNode.row));
                assertEquals(pf.node[j][i].hCost, Math.abs(pf.node[j][i].col - pf.goalNode.col) + Math.abs(pf.node[j][i].row - pf.goalNode.row));
                assertEquals(pf.node[j][i].fCost, pf.node[j][i].gCost + pf.node[j][i].hCost);
            }
        }
    }

    @Test
    @DisplayName("Running getCost test")
    void getCost() {
        pf.startNode = new Node(1, 1);
        pf.goalNode = new Node(5, 5);

        Node node = new Node(3, 3);
        pf.getCost(node);
        assertEquals(node.gCost, Math.abs(node.col - pf.startNode.col) + Math.abs(node.row - pf.startNode.row));
        assertEquals(node.hCost, Math.abs(node.col - pf.goalNode.col) + Math.abs(node.row - pf.goalNode.row));
        assertEquals(node.fCost, node.gCost + node.hCost);
    }

    @Test
    void search() {
        pf.currentNode = new Node(10, 1);
        pf.goalNode = new Node(13, 4);
        pf.resetNodes();
        pf.instantiateNodes();
        pf.setNodes(10, 1, 13, 4);
        assertTrue(pf.search());

        pf.currentNode = new Node(0, 0);
        pf.goalNode = new Node(1, 1);
        pf.instantiateNodes();
        pf.setNodes(0, 0, 1, 1);
        assertFalse(pf.search());
    }

    @Test
    void openNodeTest() {
        Node node = new Node(5, 5);

        // clear list before calling openNode to verify if node gets added
        pf.openList.clear();
        node.open = false;
        node.checked = false;
        node.solid = false;
        pf.openNode(node);
        assertEquals(pf.openList.size(), 1);

        pf.openList.clear();
        node.open = true;
        pf.openNode(node);
        assertEquals(pf.openList.size(), 0);
    }

    @Test
    void trackThePath() {
        Node testStartCurrentNode = new Node(3, 3);
        pf.startNode = testStartCurrentNode;
        pf.goalNode = testStartCurrentNode;
        pf.pathList.clear();

        pf.trackThePath();
        assertTrue(pf.pathList.isEmpty());

        pf.goalNode = new Node(10, 10);
        pf.trackThePath();
        assertEquals(pf.pathList.size(), 1);
    }
}
