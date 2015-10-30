/**
 * N-PUZZLE SOLVER Board
 * @Author	Mandar Darwatkar
 * @Institution University of California, Riverside
 * @Date	10/29/2015
 * 
 */

import java.util.ArrayList;
import java.util.Arrays;


public class Board {
	private ArrayList<String> state;
	private int depth;
	private int hn;
	private int gn;
	private int fn;
	
	public Board() {
		
	}
	
	public Board(ArrayList board) {
		this.state = board;
	}

	public Board(ArrayList board, int depth) {
		this.state = board;
		this.depth = depth;
		this.gn = depth;
		this.hn = 0;
		
	}
	
	public ArrayList getBoard() {
	
		return this.state;
	}
	
	public int getDepth() {
		
		return this.depth;	
	}
	
	public int getFvalue() {
		
		return this.fn;
	}

	public int getHvalue() {
		
		return this.hn;
	}

	public int findTile(String tile) {
		return this.state.indexOf(tile);
		
	}
		
	/**
	 * Check and compute swappable neighboring tile indexes
	 * @param index
	 * @return list of valid swappable indexes
	 */
	public ArrayList<Integer> checkNeighbouringTiles(int index){

	    ArrayList<Integer> neighbouringTiles = new ArrayList();
	    
	    int left = ( index % Test.COLUMNS == 0 ) ? -1 : index - 1;
		int right = ( index % Test.COLUMNS == Test.COLUMNS - 1) ? -1 : index + 1;
		int top = ( index - Test.COLUMNS < 0) ? -1 : (index - Test.COLUMNS );
		int down = ( index + (Test.COLUMNS) >= state.size() ) ? -1 : (index + Test.COLUMNS );
		
	    if(left != -1)
	    	neighbouringTiles.add(left);
	    if(right != -1)
	    	neighbouringTiles.add(right);
	    if(top != -1)
	    	neighbouringTiles.add(top);
	    if(down != -1)
	    	neighbouringTiles.add(down);
	    
	    return neighbouringTiles;
	}

	/**
	 * Check if <code>this</code> is goal board
	 * @param goalBoard
	 * @return true/false
	 */
	public boolean checkGoal(Board goalBoard) {
		
		return this.state.equals(goalBoard.getBoard());
	}
	
	/**
	 * Swap tiles
	 * @param index1
	 * @param index2
	 * @return String board
	 */
	public String moveTile(int index1, int index2) {
	    
		StringBuilder str = new StringBuilder();
		ArrayList<String> s = new ArrayList(this.state);
		String tile2 = this.state.get(index2);
		String tile1 = this.state.get(index1);
		s.set(index1, tile2);
		s.set(index2, tile1);
			
		for(String tile : s) {
			str.append(tile + " ");
		}
		return str.toString().trim();
	}

	/**
	 * Calculate #misplaced tiles
	 * @param goalBoard
	 */
	public void misplacedTiles(Board goalBoard) {
		
		for(String tile : this.state) {
			if( !tile.equals("0") && this.state.indexOf(tile) != goalBoard.state.indexOf(tile) ) {
				this.hn++;
			}
		}
		this.fn = this.gn + this.hn;
		
	}
	
	/**
	 * Calculate manhattan distance heuristic
	 * @param goalBoard
	 */
	public void manhattanDist(Board goalBoard) {
		ArrayList<String> goalString = new ArrayList(goalBoard.getBoard());
		
		for(String tile : this.state) {
			int i = this.state.indexOf(tile);
			if( !tile.equals("0") && !tile.equals(goalString.get(i)) ) {
				
				int x = i / Test.COLUMNS;
				int y = i - x * Test.COLUMNS;
				int gx = goalBoard.findTile(tile) / Test.COLUMNS;
				int gy = goalBoard.findTile(tile) - gx * Test.COLUMNS;

				int diffX = Math.abs(x - gx);
				int diffY = Math.abs(y - gy);
	            this.hn += Math.abs(diffX) + Math.abs(diffY);
	         
			}
		}
		this.fn = this.gn + this.hn;

	}
	
	public String boardToString() {
		
		StringBuilder s = new StringBuilder();
		for(String tile : this.state) {
			s.append(tile+" ");
		}
		return s.toString().trim();
	}
	
	/**
	 * Checks if the board is solvable.
	 *
	 * @return		is solvable?
	 */
	public boolean isSolvable() {

	    int numberOfInversions = 0;
		    
	    for(String tile1 : this.state) {
		
	        for (String tile2 : this.state) {
		
	            //If the tile j is after(has a greater index) then tile i. 
	        	if (this.state.indexOf(tile1) < this.state.indexOf(tile2)) {
		
		                if (!tile1.equals("0") && !tile2.equals("0")) {
		                    // If j's number is lower then i's 
		                    if (Integer.parseInt(tile1) > Integer.parseInt(tile2))
		                    	numberOfInversions += 1;
		                }
		            }
		
		        }
		
		    }

		    /* If the grid width is odd, then the number of inversions in a solvable situation is even.
		     */
	    	if((Test.COLUMNS % 2 == 1) && (numberOfInversions % 2 == 1))
		        return false;
		
		    /* If the grid width is even, and the blank is on an even row counting from the bottom (second-last, fourth-last etc),
		     * then the number of inversions in a solvable situation is odd.
		     */
		    if(((Test.COLUMNS % 2 == 0) && ((findTile("0")/Test.COLUMNS) + numberOfInversions) % 2 == 0))
		        return false;
				
		    return true;
		}
	
		public void printBoard() {

			if(Test.ROWS == 3)
				System.out.println("-------------");
			else
				System.out.println("----------------");
			
		   for(int i = 0; i < Test.ROWS; i++){
			        System.out.print("|  ");

			        for(int j = 0; j < Test.COLUMNS; j++){

			           
						if(this.state.get(i * Test.COLUMNS + j).equals("0")) {
							String str = String.format("%-3s"," ");
							System.out.print(str);
						}
			            else if(this.state.get(i * Test.COLUMNS + j) != null) {
			            	String str = String.format("%-3s", this.state.get(i * Test.COLUMNS + j));
			                System.out.print(str);
			            }
			        }
			        System.out.println("|");
			    }

			if(Test.ROWS == 3)
				System.out.println("-------------");
			else
				System.out.println("----------------");

		}

}
