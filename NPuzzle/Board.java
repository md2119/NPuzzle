import java.util.ArrayList;


public class Board {
	private String state;
	private int depth;
	private int hn;
	private int gn;
	private int fn;
	
	public Board() {
		
	}
	
	public Board(String board) {
		this.state = board;
	}

	public Board(String board, int depth) {
		this.state = board;
		this.depth = depth;
		this.gn = depth;
		this.hn = 0;
		
	}
	
	/*
	public Board(String board, int gn, int hn, int depth) {
		this.state = board;
		this.fn = gn + hn;
		this.gn = gn;
		this.hn = hn;
		this.depth = depth;
		
	}*/
	
	public String getBoard() {
	
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

	public int findTile(int tile) {
		return this.state.indexOf(tile);
		
	}
		
	public ArrayList<Integer> checkNeighbouringTiles(int index){

	    ArrayList<Integer> neighbouringTiles = new ArrayList();
	    
	    int left = ( index % Test.COLUMNS == 0 ) ? -1 : index - 2;
		int right = ( index % (Test.COLUMNS * 2) == (Test.COLUMNS * 2)- 2) ? -1 : index + 2;
		int top = ( index - (Test.COLUMNS * 2) < 0) ? -1 : (index - (Test.COLUMNS * 2) );
		int down = ( index + (Test.COLUMNS * 2) >= state.length() ) ? -1 : (index + (Test.COLUMNS * 2) );
		
		
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


	public boolean checkGoal(Board goalBoard) {
		
		return this.state.equals(goalBoard.getBoard());
	}

	public String moveTile(int index1, int index2) {
	    
		StringBuilder s = new StringBuilder(this.state);
		char tile2 = s.charAt(index2);
		char tile1 = s.charAt(index1);
		s.setCharAt(index1, tile2);
		s.setCharAt(index2, tile1);
			
		//System.out.println( " " + tile2 + " " + tile1 + "==" + board);
		return s.toString();
	}

	public void misplacedTiles(Board goalBoard) {
		String goalString = goalBoard.getBoard();
		for(int i = 0; i < this.state.length(); i+=2) {
			if( this.state.charAt(i) != '0' && this.state.charAt(i) != goalString.charAt(i) ) {
				this.hn++;
			}
		}
		this.fn = this.gn + this.hn;
		//return this.hn;
	}

	public void manhattanDist(Board goalBoard) {
		String goalString = goalBoard.getBoard();
		
		for(int i = 0; i < this.state.length(); i+=2) {
			char tile = this.state.charAt(i);
			if( tile != '0' && tile != goalString.charAt(i) ) {
				int x = i/(Test.COLUMNS*2);
				int y = i - x * Test.COLUMNS*2;
				int gx = goalBoard.findTile(tile)/(Test.COLUMNS*2);
				int gy = goalBoard.findTile(tile) - gx * Test.COLUMNS*2;

				int diffX = Math.abs(x - gx);
				int diffY = Math.abs(y - gy);
	            this.hn += Math.abs(diffX) + Math.abs(diffY);
	            
	            
			}
		}
		this.fn = this.gn + this.hn;
		//return this.hn;
	}
	
	/**
	 * Checks if the board is solvable.
	 *
	 * @return		is solvable?
	 */
	public boolean isSolvable() {

	    int numberOfInversions = 0;
		    
	   // StringBuilder str = new StringBuilder(board);
		    
	    for(int i = 0; i < state.length(); i+=2) {
		
	        for (int j = 0; j < state.length(); j+=2) {
		
	            //If the tile j is after(has a greater index) then tile i. 
	        	if (findTile(state.charAt(i)) < findTile(state.charAt(j))) {
		
		                if (state.charAt(i) != '0' && state.charAt(j) != '0') {
		                    // If j's number is lower then i's 
		                    if (state.charAt(i) > state.charAt(j))
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
		    if(((Test.COLUMNS % 2 == 0) && ((findTile(0)/(Test.COLUMNS*2)) % 2 == 0))
		            && (numberOfInversions % 2 == 1))
		        return false;
		
		    /* If the grid width is even, and the blank is on an odd row counting from the bottom (last, third-last, fifth-last etc)
		     * then the number of inversions in a solvable situation is even. 
		     */
		    if(((Test.COLUMNS % 2 == 0) && (( findTile(0)/(Test.COLUMNS*2)) % 2 == 1))
		            && (numberOfInversions % 2 == 0))
		        return false;
		
		    return true;
		}
	
		public void printBoard() {

			String[] arr = this.state.split(" ");
			System.out.println("---------");

			    for(int i = 0; i < Test.ROWS; i++){
			        System.out.print("|");

			        for(int j = 0; j < Test.COLUMNS; j++){

			            if(arr[i * Test.COLUMNS + j].equals("0"))
			                System.out.print("  ");
			            else if(arr[i * Test.COLUMNS + j] != null)
			                System.out.print(" " + arr[i * Test.COLUMNS + j]);

			        }
			        System.out.println(" |");
			    }

			    System.out.println("---------");

		}

}
