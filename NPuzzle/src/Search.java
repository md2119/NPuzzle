/**
 * N-PUZZLE SOLVER Search Algorithms
 * @Author	Mandar Darwatkar
 * @Institution University of California, Riverside
 * @Date	10/29/2015
 * 
 */

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.Comparator;
import java.util.HashSet;
import java.util.LinkedHashMap;
import java.util.Map;
import java.util.Set;
import java.util.TreeMap;


public class Search {
	
private Board board;
private Board goalBoard;
private int algorithm;
int statesExpanded = 0;
int maxQueueSize = 0;
int goalDepth = 0;

Set<String> closed = new HashSet<String>();
Set<String> open = new HashSet<String>();
Map<String, Integer> queue = new LinkedHashMap<String, Integer>();

ArrayList<Integer> nodeFvalues = new ArrayList();
ArrayList<Board> nodes = new ArrayList();

/**
 * Main Search
 * @param userboard
 * @param goalBoard
 * @param algorithm
 */
public Search(Board userboard, Board goalBoard, int algorithm) {
    this.board = new Board(userboard.getBoard(), 0);
    this.goalBoard = goalBoard;
    this.algorithm = algorithm;
    if (!this.board.isSolvable()) {
	    System.out.println("This puzzle is not solvable");
	    return;
	}
    
    if( algorithm == 1 ) {
    	uniformCostSearch();
    } else {
    	aStar();
    }
	  
}

public void uniformCostSearch() {
	
	/* root node */
	Board nodeBoard = new Board(board.getBoard(), board.getDepth());
	System.out.println("<---Search Path--->");
	
	/* Traverse the search tree until we reach goal state */
	while(!nodeBoard.checkGoal(goalBoard) && nodeBoard != null) {
		
		statesExpanded++;
		nodeBoard.printBoard();
		
		/* remove from queue and put into closed to check for repeated states*/
		closed.add(nodeBoard.boardToString());
				   	
		int zeroPosition = nodeBoard.findTile("0");
		for(int tileIndex : nodeBoard.checkNeighbouringTiles(zeroPosition)){
			
			/* expand node and get child nodes */
			String boardString = nodeBoard.moveTile(zeroPosition, tileIndex);
			if(!queue.containsKey(boardString) && !closed.contains(boardString)) {
				queue.put(boardString, nodeBoard.getDepth()+1);          
			}
		}
		
		/* remove next node from head of Queue */
		for(Map.Entry<String, Integer> entry : queue.entrySet()) {
			
			goalDepth = entry.getValue();
			nodeBoard = new Board(new ArrayList(Arrays.asList(entry.getKey().split(" "))), goalDepth);
			break;
		}
		maxQueueSize = maxQueueSize>queue.size() ? maxQueueSize : queue.size();
		queue.remove(nodeBoard.boardToString());
	}	
	
	nodeBoard.printBoard();
	cleanup();
	printResult();
	
	return;
	
}

/**
 * Perform A* with Misplaced Tiles heuristic
 * or A* with Manhattan Distance heuristic
 * 
 */
public void aStar() {
	
	/* root node */
	Board nodeBoard = new Board(board.getBoard(), board.getDepth());
	nodeBoard.misplacedTiles(goalBoard);
	System.out.println("<---Search Path--->");
	
	/* Traverse the search tree until we reach goal state */
	while(!nodeBoard.checkGoal(goalBoard) && nodeBoard != null) {
		
		statesExpanded++;
		System.out.println("g(n) = " + nodeBoard.getDepth() + " :: h(n) = " + nodeBoard.getHvalue() +" :: f(n) = " + nodeBoard.getFvalue());	
		nodeBoard.printBoard();
		
		/* remove from queue and put into closed to check for repeated states*/
		closed.add(nodeBoard.boardToString());
					   	
		int zeroPosition = nodeBoard.findTile("0");
		for(int tileIndex: nodeBoard.checkNeighbouringTiles(zeroPosition)){
			
			/* expand node and get child nodes */
			String boardString = nodeBoard.moveTile(zeroPosition, tileIndex);
			Board childBoard = new Board(new ArrayList(Arrays.asList(boardString.split(" "))), nodeBoard.getDepth() + 1);
			
			if(algorithm == 2)
				childBoard.misplacedTiles(goalBoard);
			if(algorithm == 3)
				childBoard.manhattanDist(goalBoard);
			
			if(!open.contains(boardString) && !closed.contains(boardString)) {
				nodes.add(childBoard);
				nodeFvalues.add(childBoard.getFvalue() );    
				open.add(boardString);
			}
		}
		
		/* next node is node with minimum f(n) */
		int minF = Collections.min(nodeFvalues);
		int minFindex = nodeFvalues.indexOf(minF);
		nodeBoard = nodes.get(minFindex);
		goalDepth = nodeBoard.getDepth();
		
		maxQueueSize = maxQueueSize > nodes.size() ? maxQueueSize : nodes.size();
		nodeFvalues.remove(minFindex);
		nodes.remove(minFindex);
		open.remove(nodeBoard.getBoard());
	}	
	System.out.println("g(n) = " + nodeBoard.getDepth() + " :: h(n) = " + nodeBoard.getHvalue() +" :: f(n) = " + nodeBoard.getFvalue());	
	nodeBoard.printBoard();
	cleanup();
	printResult();

    return;
}

public void cleanup() {
	
	queue.clear();
	nodeFvalues.clear();
	nodes.clear();
	closed.clear();
	open.clear();
	
}


public void printResult() {
	
	System.out.println("*** Complete! ***");
    System.out.println("Number of nodes expanded : " + statesExpanded);
    System.out.println("Maximum #nodes in queue at any one time : " + maxQueueSize);
    System.out.println("Goal state is at depth : " + goalDepth);

}

}
