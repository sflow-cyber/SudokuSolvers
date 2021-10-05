
public class BackTracking extends Solver {

	private int[][] grid;

	public BackTracking(int[] puzzle) {
		super(puzzle);
	}

	@Override
	public int[] execute(int[] puzzle) {
		int gridSize = (int)Math.sqrt(puzzle.length);
		grid = new int[gridSize][gridSize];
		for(int i = 0; i < gridSize; i++) {
			for(int j = 0; j < gridSize; j++) {
				grid[i][j] = puzzle[i * gridSize + j];
			}
		}
		run();
		int[] solution = new int[puzzle.length];
		for(int i = 0; i < gridSize; i++) {
			for(int j = 0; j < gridSize; j++) {
				solution[i * gridSize + j] = grid[i][j];
			}
		}
		return solution;
	}


	/** Checks if num is an acceptable value for the given row */
	protected boolean checkRow( int row, int num )
	{
		for( int col = 0; col < 9; col++ )
			if( grid[row][col] == num )
				return false ;

		return true ;
	}

	/** Checks if num is an acceptable value for the given column */
	protected boolean checkCol( int col, int num )
	{
		for( int row = 0; row < 9; row++ )
			if( grid[row][col] == num )
				return false ;

		return true ;
	}

	/** Checks if num is an acceptable value for the box around row and col */
	protected boolean checkBox( int row, int col, int num )
	{
		row = (row / 3) * 3 ;
		col = (col / 3) * 3 ;

		for( int r = 0; r < 3; r++ )
			for( int c = 0; c < 3; c++ )
				if( grid[row+r][col+c] == num )
					return false ;

		return true ;
	}



	/** The active part begins here */
	public void run()
	{
		try
		{
			// Start to solve the puzzle in the left upper corner
			solve( 0, 0 ) ;
		}
		catch( Exception e )
		{
		}
	}

	/** Recursive function to find a valid number for one single cell */
	public void solve( int row, int col ) throws Exception
	{
		// Throw an exception to stop the process if the puzzle is solved
		if( row > 8 )
			throw new Exception( "Solution found" ) ;

		// If the cell is not empty, continue with the next cell
		if( grid[row][col] != 0 )
			next( row, col ) ;
		else
		{
			// Find a valid number for the empty cell
			for( int num = 1; num < 10; num++ )
			{
				if( checkRow(row,num) && checkCol(col,num) && checkBox(row,col,num) )
				{
					grid[row][col] = num ;
					// Delegate work on the next cell to a recursive call
					next( row, col ) ;
				}
			}

			// No valid number was found, clean up and return to caller
			grid[row][col] = 0 ;
		}
	}

	/** Calls solve for the next cell */
	public void next( int row, int col ) throws Exception
	{
		if( col < 8 )
			solve( row, col + 1 ) ;
		else
			solve( row + 1, 0 ) ;
	}

}
