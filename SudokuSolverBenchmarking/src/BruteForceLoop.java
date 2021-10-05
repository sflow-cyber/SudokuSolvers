import java.util.Stack;


public class BruteForceLoop extends Solver {

	private int[] puzzle;
	private int[][] grid;
	private int gridSize;
	private int subGridSize;
	private Stack<int[][]> stack;
	private double maxMemory;
	
	public BruteForceLoop(int[] puzzle) {
		this.puzzle = puzzle;
		gridSize = (int)Math.sqrt(puzzle.length);
		subGridSize = (int)Math.sqrt(gridSize);
		grid = new int[gridSize][gridSize];
		stack = new Stack<int[][]>();
		for(int i = 0; i < gridSize; i++) {
			for(int j = 0; j < gridSize; j++) {
				grid[i][j] = puzzle[i * gridSize + j];
			}
		}
	}
	
	private boolean checkRow(int row, int num) {
		for( int col = 0; col < gridSize; col++ )
			if( grid[row][col] == num )
				return false ;

		return true ;
	}
	
	private boolean checkCol(int col, int num) {
		for( int row = 0; row < gridSize; row++ )
			if( grid[row][col] == num )
				return false ;

		return true ;
	}
	
	private boolean checkBox(int row, int col, int num) {
		row = (row / subGridSize) * subGridSize ;
		col = (col / subGridSize) * subGridSize ;

		for( int r = 0; r < subGridSize; r++ )
			for( int c = 0; c < subGridSize; c++ )
				if( grid[row+r][col+c] == num )
					return false ;

		return true ;
	}
	
	@Override
	public int[] execute(int[] puzzle) {
		maxMemory = 0d;
		// 2B implemented
		
		return null;
	}

	@Override
	public double getMaxMemory() {
		return maxMemory;
	}
	
	
}
