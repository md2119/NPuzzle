import java.util.Arrays;


public class Puzzle {
	int puzzleboard[];
	int zero;
	public Puzzle() {
		int[] puzzleboard = {1, 2, 0, 4, 5, 3, 7, 8, 6};
		this.puzzleboard = puzzleboard;
		System.out.println(puzzleboard);
	}
	
	public Puzzle(int n, int[] puzzleboard) {
		this.puzzleboard = puzzleboard;
		System.out.println(puzzleboard);
	}
	
	public int getZero() {
		return Arrays.asList(puzzleboard).indexOf(0);
	}
	
	public void swapState(int swapindex) {
		int zeroindex = getZero();
		int newcell = puzzleboard[swapindex];
		puzzleboard[swapindex] = puzzleboard[zeroindex];
		puzzleboard[zeroindex] = newcell; //////////do we need to create new instance?
		
	}

}
