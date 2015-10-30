import java.util.ArrayList;
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


public Search(Board userboard, Board goalBoard, int algorithm) {
    this.board = new Board(userboard.getBoard(), 0);
    this.goalBoard = goalBoard;
    this.algorithm = algorithm;
    if (!this.board.isSolvable()) {
	    System.out.println("This puzzle is not solvable");
	    return;
	}
    
   switch( algorithm ) {
	   case 1: uniformCostSearch(); break;
	   case 2: misplacedTilesHeuristic(); break;
	   case 3: manhattanHeuristic(); break;
	   default: System.out.println("Incorrect Choice!!"); break;
   }
     
}

public void uniformCostSearch() {
	Board nodeBoard = new Board(board.getBoard(), board.getDepth());
	
	while(!nodeBoard.checkGoal(goalBoard)) {
		
		statesExpanded++;
		nodeBoard.printBoard();
		//System.out.println(nodeBoard.getBoard() + " \t" + nodeBoard.getDepth() );	
		closed.add(nodeBoard.getBoard());
			
		if(nodeBoard == null)
			return;
		   	
		int zeroPosition = nodeBoard.findTile( (char) (0 + 48) );
		for(int tileIndex : nodeBoard.checkNeighbouringTiles(zeroPosition)){
		
			String boardString = nodeBoard.moveTile(zeroPosition, tileIndex);
			if(!queue.containsKey(boardString) && !closed.contains(boardString)) {
				queue.put(boardString, nodeBoard.getDepth()+1);          
			}
		}
		
		for(Map.Entry<String, Integer> entry : queue.entrySet()) {
			
			goalDepth = entry.getValue();
			nodeBoard = new Board(entry.getKey(), goalDepth);
			break;
		}
		maxQueueSize = maxQueueSize>queue.size() ? maxQueueSize : queue.size();
		queue.remove(nodeBoard.getBoard());
	}	
	
	cleanup();
	printResult();
	
	return;
	
}


public void misplacedTilesHeuristic() {
	
	Board nodeBoard = new Board(board.getBoard(), board.getDepth());
	nodeBoard.misplacedTiles(goalBoard);
	
	while(!nodeBoard.checkGoal(goalBoard)) {
		
		statesExpanded++;
		System.out.println("g(n) = " + nodeBoard.getDepth() + " :: h(n) = " + nodeBoard.getHvalue() +" :: f(n) = " + nodeBoard.getFvalue());	
		nodeBoard.printBoard();
		closed.add(nodeBoard.getBoard());
			
		if(nodeBoard == null)
			return;
		   	
		int zeroPosition = nodeBoard.findTile( (char) (0 + 48) );
		for(int tileIndex: nodeBoard.checkNeighbouringTiles(zeroPosition)){
			
			String boardString = nodeBoard.moveTile(zeroPosition, tileIndex);
			Board childBoard = new Board(boardString, nodeBoard.getDepth() + 1);
			childBoard.misplacedTiles(goalBoard);
			
			if(!open.contains(boardString) && !closed.contains(boardString)) {
				nodes.add(childBoard);
				nodeFvalues.add(childBoard.getFvalue() );    
				open.add(boardString);
			}
		}
		int minF = Collections.min(nodeFvalues);
		int minFindex = nodeFvalues.indexOf(minF);
		nodeBoard = nodes.get(minFindex);
		goalDepth = nodeBoard.getDepth();
		
		maxQueueSize = maxQueueSize > nodes.size() ? maxQueueSize : nodes.size();
		nodeFvalues.remove(minFindex);
		nodes.remove(minFindex);
		open.remove(nodeBoard.getBoard());
	}	
	
	cleanup();
	printResult();

    return;
}

public void manhattanHeuristic() {
	
	Board nodeBoard = new Board(board.getBoard(), board.getDepth());
	nodeBoard.manhattanDist(goalBoard);
	
	while(!nodeBoard.checkGoal(goalBoard)) {
		
		statesExpanded++;
		System.out.println("g(n) = " + nodeBoard.getDepth() + " :: h(n) = " + nodeBoard.getHvalue() +" :: f(n) = " + nodeBoard.getFvalue());	
		nodeBoard.printBoard();
		//System.out.println(nodeBoard.getBoard() + " \t" + nodeBoard.getDepth() +"f(n) = " + nodeBoard.getFvalue()+" h(n) = " + nodeBoard.getHvalue());	
		closed.add(nodeBoard.getBoard());
			
		if(nodeBoard == null)
			return;
		   	
		int zeroPosition = nodeBoard.findTile( (char) (0 + 48) );
		for(int tileIndex: nodeBoard.checkNeighbouringTiles(zeroPosition)){
			
			String boardString = nodeBoard.moveTile(zeroPosition, tileIndex);
			Board childBoard = new Board(boardString, nodeBoard.getDepth() + 1);
			childBoard.manhattanDist(goalBoard);
			
			if(!open.contains(boardString) && !closed.contains(boardString)) {
				nodes.add(childBoard);
				nodeFvalues.add(childBoard.getFvalue() );    
				open.add(boardString);
			}
		}
		int minF = Collections.min(nodeFvalues);
		int minFindex = nodeFvalues.indexOf(minF);
		nodeBoard = nodes.get(minFindex);
		goalDepth = nodeBoard.getDepth();
		
		maxQueueSize = maxQueueSize > nodes.size() ? maxQueueSize : nodes.size();
		nodeFvalues.remove(minFindex);
		nodes.remove(minFindex);
		open.remove(nodeBoard.getBoard());
	}	
	
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
    System.out.println("Number of states expanded : " + statesExpanded);
    System.out.println("Maximum #nodes in queue at any one time : " + maxQueueSize);
    System.out.println("Goal state is at depth : " + goalDepth);

}

}
