/**
 * N-PUZZLE SOLVER Test Cases
 * @Author	Mandar Darwatkar
 * @Institution University of California, Riverside
 * @Date	10/29/2015
 * @Input	Puzzle Type 3, 8, 15, etc.
 * 			Puzzle Difficulty	1 - Simple
 * 								2 - Difficult
 * 								3 - Impossible
 * 			Puzzle Choice		1 - Default Puzzle
 * 								2 - User Puzzle
 * 			Algorithm Choice	1 - Uniform Cost Search
 * 								2 - A* with Misplaced Tile Heuristic
 * 								3 - A* with Manhattan Distance Heuristic
 * @Output	Path Trace
 * 			Number of nodes expanded
 * 			Maximum #nodes in queue
 * 			Depth of Goal State
 */

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Scanner;


public class Test {
	public static int ROWS;
	public static int COLUMNS;
	static int puzzleoption;
	
	Board initialBoard, goalBoard;
	 
	public ArrayList<String> buildGoalBoard(int boardsize) {
		
		String goalpuzzle = null;
		if(boardsize == 9) {
			goalpuzzle = "1 2 3 4 5 6 7 8 0";
		}
		if(boardsize == 16) {
			goalpuzzle = "1 2 3 4 5 6 7 8 9 10 11 12 13 14 15 0";
		}
		String[] arr = (goalpuzzle).split(" ");
		return new ArrayList(Arrays.asList(arr));

	}

	public ArrayList<String> buildDefaultBoard(int boardsize, int difflevel) {
		//8puzzle
		String puzzle = null;
		if(boardsize == 9) {
			switch(difflevel) {
			case 1: puzzle = "1 2 3 4 0 6 7 5 8"; break;
			case 2: puzzle = "8 7 1 6 0 2 5 4 3"; break;
			case 3: puzzle = "1 2 3 4 5 6 8 7 0"; break;
			}
			
		}
		if (boardsize == 16) {
			switch(difflevel) {
			case 1: puzzle = "1 2 3 4 5 6 7 8 9 10 11 12 13 0 14 15"; break;
			case 2: puzzle = "1 2 3 4 5 6 0 8 9 10 7 12 13 14 11 15"; break;
			case 3: puzzle = "1 2 3 4 5 6 7 8 9 10 11 12 13 15 14 0"; break;
			}
		}
		
		String[] arr = (puzzle).split(" ");
		return new ArrayList(Arrays.asList(arr));

	}	
	
	public void runSearchAlgo() {
		
		Scanner sc = new Scanner(System.in);
		
		System.out.println("Enter N value for puzzle e.g. 8, 15, etc. : ");
		
		int boardsize = sc.nextInt() + 1;
		ROWS = (int) Math.sqrt(boardsize);
		COLUMNS = ROWS;
		String moreInput = "n";
		do {
			System.out.println("1 for default puzzle and 2 to enter new puzzle : ");
			
			puzzleoption = sc.nextInt();
			
			if(puzzleoption == 1) {
				System.out.println("Select puzzle difficulty (1 - Simple, 2 - Difficult, 3 - Impossible) : ");
				initialBoard = new Board(buildDefaultBoard(boardsize, sc.nextInt()));
			} else {
				ArrayList<String> userBoardTiles = new ArrayList<String>();
				for(int i = 1; i <= ROWS; i++) {
					System.out.print("Enter row " + i + ":\t");
					for(int j = 1; j <= COLUMNS; j++) {
						userBoardTiles.add (sc.next());
					}
				}
				initialBoard = new Board(userBoardTiles);
			}
			goalBoard = new Board(buildGoalBoard(boardsize));
			
			String algoContinue = "n";
			do {
				System.out.println("Enter your choice of Algorithm");
				System.out.println("\t1. Uniform Cost Search");
				System.out.println("\t2. A* with Misplaced Tile Heuristic");
				System.out.println("\t3. A* with Manhattan distance Heuristic");
			
				int algorithmchoice = sc.nextInt();
	
				Search solvepuzzle = new Search(initialBoard, goalBoard, algorithmchoice);
				
				System.out.println("Do you want select another algorithm(y/Y/n/N) :");
				algoContinue = sc.next();
	
			} while(algoContinue.equals("y") || algoContinue.equals("Y"));

			System.out.println("Do you want to play more(y/Y/n/N) :");
			moreInput = sc.next();
		
		}while(moreInput.equals("y") || moreInput.equals("Y"));
		
		System.out.println("Exiting!!");
	}
	
	public static void main(String[] args) {

		Test t1 = new Test();
		t1.runSearchAlgo();
	}

}
