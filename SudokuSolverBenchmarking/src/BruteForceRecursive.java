
public class BruteForceRecursive extends Solver {

	private int[][] grid;
	private int gridsize;
	private int subGridsize;
	private double maxMemory;

	public BruteForceRecursive(int[] puzzle) {
		super(puzzle);
		gridsize = (int)(Math.sqrt(puzzle.length));
		subGridsize = (int)(Math.sqrt(gridsize));
	}

	@Override
	public int[] execute(int[] puzzle) {
		maxMemory = 0d;
		gridsize = (int)(Math.sqrt(puzzle.length));
		subGridsize = (int)(Math.sqrt(gridsize));
		grid = new int[gridsize][gridsize];
		for(int i = 0; i < gridsize; i++) {
			for(int j = 0; j < gridsize; j++) {
				grid[i][j] = puzzle[i * gridsize + j];
			}
		}
		run();
		int[] solution = new int[puzzle.length];
		for(int i = 0; i < gridsize; i++) {
			for(int j = 0; j < gridsize; j++) {
				solution[i * gridsize + j] = grid[i][j];
			}
		}
		return solution;
	}


	/** Checks if num is an acceptable value for the given row */
	protected boolean checkRow( int row, int num )
	{
		for( int col = 0; col < gridsize; col++ )
			if( grid[row][col] == num )
				return false ;

		return true ;
	}

	/** Checks if num is an acceptable value for the given column */
	protected boolean checkCol( int col, int num )
	{
		for( int row = 0; row < gridsize; row++ )
			if( grid[row][col] == num )
				return false ;

		return true ;
	}

	/** Checks if num is an acceptable value for the box around row and col */
	protected boolean checkBox( int row, int col, int num )
	{
		row = (row / subGridsize) * subGridsize ;
		col = (col / subGridsize) * subGridsize ;

		for( int r = 0; r < subGridsize; r++ )
			for( int c = 0; c < subGridsize; c++ )
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
	public void solve( int row, int col ) throws Exception {
		double currentMemory = ( (double)((double)(Runtime.getRuntime().totalMemory()/1024)/1024))- ((double)((double)(Runtime.getRuntime().freeMemory()/1024)/1024));
		if(currentMemory > maxMemory)
			maxMemory = currentMemory;
			
		// Throw an exception to stop the process if the puzzle is solved
		if( row >= gridsize )
			throw new Exception( "Solution found" ) ;

		// If the cell is not empty, continue with the next cell
		if( grid[row][col] != 0 )
			next( row, col ) ;
		else
		{
			// Find a valid number for the empty cell
			for( int num = 1; num <= gridsize; num++ )
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
		if( col < (gridsize - 1) )
			solve( row, col + 1 ) ;
		else
			solve( row + 1, 0 ) ;
	}
	
	@Override
	public double getMaxMemory() {
		return maxMemory;
	}

}
