/**
 * 
 */

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Scanner;


public class Test {
	public static int ROWS;
	public static int COLUMNS;
	
	static int puzzleoption;
	
	Board initialBoard, goalBoard;

	 
	public String buildGoalBoard() {
		return "1 2 3 4 5 6 7 8 0";
	}

	public String buildDefaultBoard() {
		//8puzzle
		return "8 6 7 2 5 4 3 0 1";
		//return "0 1 2 4 5 3 7 8 6"; //doable
		//return "8 7 1 6 0 2 5 4 3";  //oh boy
		//return "1 2 3 4 0 6 7 5 8"; 
		
		//15puzzle
		//return "1 11 14 4 ";
	}	
	
	public void runSearchAlgo() {
		
		Scanner sc = new Scanner(System.in);
		
	//	System.out.println("Enter N value for puzzle e.g. 3, 8, 15, etc.: ");
		
		int boardsize = 9; //sc.nextInt() + 1;
		ROWS = (int) Math.sqrt(boardsize);
		COLUMNS = ROWS;
		String moreInput = "n";
		do {
			System.out.println("1 for default puzzle and 2 to enter new puzzle: ");
			
			puzzleoption = sc.nextInt();
			
			if(puzzleoption == 1) {
				initialBoard = new Board(buildDefaultBoard());
			} else {
				StringBuilder userBoardTiles = new StringBuilder();
				for(int i = 1; i <= ROWS; i++) {
					//System.out.print("Enter row " + i + ":\t");
					for(int j = 1; j <= COLUMNS; j++) {
						userBoardTiles.append(sc.nextInt() + " ");
					}
				}
				initialBoard = new Board(userBoardTiles.toString().trim());
				System.out.println(userBoardTiles.toString());
			}
			goalBoard = new Board(buildGoalBoard());
			
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
