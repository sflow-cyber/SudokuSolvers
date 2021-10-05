import java.util.*;

public abstract class Solver {

	private int[] puzzle;
	private int[] solution;

	public Solver() {}

	public Solver(int[] puzzle) {
		this.puzzle = puzzle;
		if(hasValidSize() && isValid()) {
			int[] hcPuzzle = new int[puzzle.length];
			for(int i = 0; i < puzzle.length; i++)
				hcPuzzle[i] = puzzle[i];
			solution = execute(hcPuzzle);
		} 
	}

	/**
	 * @param puzzle is a one-dimensional integer array
	 * representation of the sudoku puzzle to be solved; 
	 * it contains integers between 0 and sqrt(puzzle.length) 
	 * exclusively where 0 represents an unknown value;
	 * a soduko puzzle can be of sizes 
	 * - 4 x 4 (subgrid 2 x 2),  
	 * - 9 x 9 (subgrid 3 x 3),
	 * - 16 x 16 (subgrid 4 x 4),
	 * - 25 x 25 (subgrid 5 x 5)
	 * @return one-dimensional integer array representing the
	 * solution to the input puzzle 
	 * 
	 * Method has to assert:
	 * - the output array has the same length as the input 
	 * array
	 * - the output contains only integers between 1 and
	 * sqrt(puzzle.length)
	 * - the output is a valid solution to the given puzzle
	 * 
	 * Method is assured that:
	 * - input has a valid size
	 * - input is a valid sudoku puzzle
	 * 
	 * The method has to find only one possible solution; it
	 * won't be checked whether the solution is unique.
	 */
	public abstract int[] execute(int[] puzzle);
	
	public abstract double getMaxMemory();

	private boolean hasValidSize() {
		int pLength = puzzle.length;
		if(pLength == Math.pow(4, 2) ||
				pLength == Math.pow(9, 2) ||
				pLength == Math.pow(16, 2) ||
				pLength == Math.pow(25, 2))
			return true;
		return false;
	}

	private boolean isValid() {
		int pLength = (int)Math.sqrt(puzzle.length);
		int sqrtPL = (int)Math.sqrt(pLength);
		int[][] sArray = new int[pLength][pLength];
		for(int i = 0; i < pLength; i++) {
			for(int j = 0; j < pLength; j++) {
				sArray[i][j] = puzzle[i * pLength + j];
			}
		}
		int[][] subSArray = new int[pLength][pLength];
		for(int i = 0; i < pLength; i++) {
			for(int j = 0; j < pLength; j++) {
				subSArray[i][j] = 
						sArray[(i / sqrtPL) * sqrtPL + j / sqrtPL]
								[(i * sqrtPL) % pLength + j % sqrtPL];
			}
		}
		for(int i = 0; i < pLength; i++) {
			int[] hCheck = new int[pLength];
			int[] vCheck = new int[pLength];
			int[] bCheck = subSArray[i];
			for(int j = 0; j < pLength; j++) {
				hCheck[j] = puzzle[i * pLength + j];
				vCheck[j] = puzzle[i + j * pLength];
			}
			Arrays.sort(hCheck);
			Arrays.sort(vCheck);
			Arrays.sort(bCheck);
			if(hCheck[pLength - 1] > pLength
					|| vCheck[pLength - 1] > pLength
					|| bCheck[pLength - 1] > pLength)
				return false;
			for(int j = 0; j < pLength - 1; j++) {
				if(hCheck[j] > 0 && hCheck[j] == hCheck[j+1])
					return false;
				if(vCheck[j] > 0 && vCheck[j] == vCheck[j+1])
					return false;
				if(bCheck[j] > 0 && bCheck[j] == bCheck[j+1]) 
					return false;
			}
		}
		return true;
	}

	public void setPuzzle(int[] puzzle) {
		this.puzzle = puzzle;
	}

	public int[] getPuzzle() {
		return puzzle;
	}

	public int[] getSolution() {
		return solution;
	}
	
}
