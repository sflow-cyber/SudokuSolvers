/**
 * This class contains methods to read a sudoku puzzle from a text file,
 * solve the puzzle, and output the results.
 * 
 * Jacob Karshin
 * CS 111, Section 001
 * 2/15/13
 *
 * Adapted by Stefan Zischka and Friedrich Decker
 * 1/15/2014
 */
public class BruteForce extends Solver {
	// constants

	// the width (and height) of the whole puzzle
	private final int PUZZLE_WIDTH;

	// the dimensions of the inner boxes
	private final int SUB_WIDTH, SUB_HEIGHT;

	// variables

	// the board of cells
	private int[][] board;

	/**
	 * Creates an instance of SudokuSolver to solve puzzles of the supplied dimensions.
	 * 
	 * Pre-Conditions: puzzleWidth, subWidth, and subHeight must be greater than 0.
	 *                 subWidth * subHeight must equal puzzleWidth (for the puzzle to be a valid size).
	 * Post-Conditions: The instance will be initialized and ready to accept and solve puzzles.
	 * Responses: Throws an IllegalDimensionException if puzzleWidth, subWidth, or subHeight are invalid.
	 * 
	 * @param puzzleWidth the width (and height) of the whole puzzle.
	 * @param subWidth the width of the inner boxes.
	 * @throws IllegalDimensionException if puzzleWidth, subWidth, or subHeight are invalid.
	 */
	public BruteForce(int puzzleWidth, int subWidth) throws IllegalDimensionException {
		if(puzzleWidth <= 0 || subWidth <= 0 || 
				subWidth * subWidth != puzzleWidth)
			throw new IllegalDimensionException();

		PUZZLE_WIDTH = puzzleWidth;
		SUB_WIDTH = subWidth;
		SUB_HEIGHT = subWidth;
	}

	public BruteForce(int[] puzzle) throws IllegalDimensionException {
		if(puzzle.length == 0 || 
				(int)Math.sqrt(puzzle.length) * (int)Math.sqrt(puzzle.length) != puzzle.length)
			throw new IllegalDimensionException();
		PUZZLE_WIDTH = (int)Math.sqrt(puzzle.length);
		SUB_WIDTH = (int)Math.sqrt(Math.sqrt(puzzle.length));
		SUB_HEIGHT = (int)Math.sqrt(Math.sqrt(puzzle.length));
	}


	/**
	 * Determines if x can be placed in the r'th row and c'th column.
	 * 
	 * Pre-Conditions: r and c must be greater than or equal to zero and less than the puzzle width.
	 *                 x must be greater than 0 and less than or equal to the puzzle width.
	 *                 The board must be initialized.
	 * Post-Condition: true is returned if x can be placed at (r, c), false is returned otherwise.
	 * Responses: Throws an exception if r or c are invalid.
	 *            Returns an erroneous result if x is invalid.
	 *            Throws an exception if the board is uninitialized.
	 * 
	 * @param r The row that will be checked.
	 * @param c The column that will be checked.
	 * @param x The value that will be searched for.
	 * @return true if x can be placed at (r, c), false otherwise.
	 */
	private boolean canPlace(int r, int c, int x) {
		if(r < 0 || c < 0 || r >= PUZZLE_WIDTH || c >= PUZZLE_WIDTH || x <= 0 || x > PUZZLE_WIDTH)
			throw new IllegalArgumentException();

		return canPlaceInRow(r, x) && canPlaceInCol(c, x) && canPlaceInSubBox(r, c, x);
	}

	/**
	 * Determines if x can be placed in the r'th row.
	 * 
	 * Pre-Conditions: r must be greater than or equal to zero and less than the puzzle width.
	 *                 x must be greater than 0 and less than or equal to the puzzle width.
	 *                 The board must be initialized.
	 * Post-Condition: true is returned if x can be placed in the r'th row, false is returned otherwise.
	 * Responses: Throws an exception if r is invalid.
	 *            Returns an erroneous result if x is invalid.
	 *            Throws an exception if the board is uninitialized.
	 * 
	 * @param r The row that will be checked.
	 * @param x The value that will be searched for.
	 * @return true if x can be placed in the r'th row, false otherwise.
	 */
	private boolean canPlaceInRow(int r, int x) {
		if(r < 0 || r >= PUZZLE_WIDTH || x <= 0 || x > PUZZLE_WIDTH)
			throw new IllegalArgumentException();

		for(int value : board[r])
			if(value == x)
				return false;
		return true;
	}

	/**
	 * Determines if x can be placed in the c'th column.
	 * 
	 * Pre-Conditions: c must be greater than or equal to zero and less than the puzzle width.
	 *                 x must be greater than 0 and less than or equal to the puzzle width.
	 *                 The board must be initialized.
	 * Post-Condition: true is returned if x can be placed in the c'th column, false is returned otherwise.
	 * Responses: Throws an exception if c is invalid.
	 *            Returns an erroneous result if x is invalid.
	 *            Throws an exception if the board is uninitialized.
	 * 
	 * @param c The column that will be checked.
	 * @param x The value that will be searched for.
	 * @return true if x can be placed in the c'th column, false otherwise.
	 */
	private boolean canPlaceInCol(int c, int x) {
		if(c < 0 || c >= PUZZLE_WIDTH || x <= 0 || x > PUZZLE_WIDTH)
			throw new IllegalArgumentException();

		for(int r = 0; r != board.length; ++r)
			if(board[r][c] == x)
				return false;
		return true;
	}

	/**
	 * Determines if x can be placed in the sub-box that contains the (r, c) cell.
	 * 
	 * Pre-Conditions: r and c must be greater than or equal to zero and less than the puzzle width.
	 *                 x must be greater than 0 and less than or equal to the puzzle width.
	 *                 The board must be initialized.
	 * Post-Condition: true is returned if x can be placed in the sub-box that contains the (r, c) cell,
	 *                 false is returned otherwise.
	 * Responses: Throws an exception if r or c are invalid.
	 *            Returns an erroneous result if x is invalid.
	 *            Throws an exception if the board is uninitialized.
	 * 
	 * @param r The row of the cell to be checked.
	 * @param c The column of the cell to be checked.
	 * @param x The value that will be searched for.
	 * @return true if x can be placed in the sub-box that contains the (r, c) cell, false otherwise.
	 */
	private boolean canPlaceInSubBox(int r, int c, int x) {
		if(r < 0 || c < 0 || r >= PUZZLE_WIDTH || c >= PUZZLE_WIDTH || x <= 0 || x > PUZZLE_WIDTH)
			throw new IllegalArgumentException();

		// determine the "clip" rows and columns for the sub-box to be searched

		// round down to the nearest leftmost cell of a sub-box
		int leftCol = (c / SUB_WIDTH) * SUB_WIDTH;
		int rightCol = leftCol + SUB_WIDTH - 1;
		// round down to the nearest topmost cell of a sub-box
		int topRow = (r / SUB_HEIGHT) * SUB_HEIGHT;
		int bottomRow = topRow + SUB_HEIGHT - 1;

		// search the sub-box for the value
		for(int searchR = topRow; searchR <= bottomRow; ++searchR)
			for(int searchC = leftCol; searchC <= rightCol; ++searchC)
				if(board[searchR][searchC] == x)
					return false;

		return true;
	}

	/**
	 * Prints the board to the screen.
	 * 
	 * Pre-Conditions: The board must be initialized.
	 * Post-Conditions: The board is printed to the screen.
	 * Responses: Throws an exception if the board is uninitialized.
	 */
	public void printBoard() {
		for(int i = 0; i != board.length; ++i) {
			System.out.print(" --");
			if((i + 1) % SUB_WIDTH == 0)
				System.out.print(" ");
		}
		System.out.println();

		for(int row = 0; row < PUZZLE_WIDTH; ++row) {
			for(int col = 0; col < PUZZLE_WIDTH; ++col) {
				int value = board[row][col];
				String out = "|" + (col % SUB_WIDTH == 0 && col != 0 ? "|" : "");
				if(value == 0)
					out += "  ";
				else {
					out += value + (value < 10 ? " " : "");
				}
				System.out.print(out);
			}
			System.out.println("|");

			if((row + 1) % SUB_HEIGHT == 0) {
				for(int i = 0; i != board.length; ++i) {
					System.out.print(" --");
					if((i + 1) % SUB_WIDTH == 0)
						System.out.print(" ");
				}
				System.out.println();
			}
		}

		System.out.println();
	}

	/**
	 * Determines the row of the cell that follows the (r, c) cell.
	 * 
	 * Pre-Conditions: r and c must be greater than or equal to zero and less than the puzzle width.
	 *                (r, c) must not be the last cell in the board.
	 * Post-Condition: r + 1 is returned if (r, c) is the last cell in the row, r is returned otherwise.
	 * Responses: Throws an exception if r or c is invalid.
	 *            Returns an erroneous result if (r, c) is the last cell in the board.
	 * 
	 * @param r The row of the cell.
	 * @param c The column of the cell.
	 * @return r + 1 if (r, c) is the last cell in the row, r otherwise.
	 */
	private int getNextRow(int r, int c) {
		if(r < 0 || c < 0 || r >= PUZZLE_WIDTH || c >= PUZZLE_WIDTH)
			throw new IllegalArgumentException();
		return (c == PUZZLE_WIDTH - 1 ? r + 1 : r);
	}

	/**
	 * Determines the column of the cell that follows the (r, c) cell.
	 * 
	 * Pre-Conditions: r and c must be greater than or equal to zero and less than the puzzle width.
	 *                (r, c) must not be the last cell in the board.
	 * Post-Condition: 0 is returned if (r, c) is the last cell in the row, c + 1 is returned otherwise.
	 * Responses: Throws an exception if r or c is invalid.
	 *            Returns an erroneous result if (r, c) is the last cell in the board.
	 * 
	 * @param r The row of the cell.
	 * @param c The column of the cell.
	 * @return 0 if (r, c) is the last cell in the row, c + 1 otherwise.
	 */
	private int getNextCol(int r, int c) {
		if(r < 0 || c < 0 || r >= PUZZLE_WIDTH || c >= PUZZLE_WIDTH)
			throw new IllegalArgumentException();

		return (c == PUZZLE_WIDTH - 1 ? 0 : c + 1);
	}

	/**
	 * Determines if the (r, c) cell is the last cell in the board; in other words, (r, c) is the bottom right cell.
	 * 
	 * Pre-Conditions: r and c must be greater than or equal to zero and less than the puzzle width.
	 * Post-Condition: true is returned if the (r, c) cell is the last cell in the board, false is returned otherwise.
	 * Responses: Throws an exception if r or c is invalid.
	 * 
	 * @param r The row of the cell.
	 * @param c The column of the cell.
	 * @return true if the (r, c) cell is the last cell in the board, false otherwise.
	 */
	private boolean isLastCell(int r, int c) {
		return r == PUZZLE_WIDTH - 1 && c == PUZZLE_WIDTH - 1;
	}

	/**
	 * Attempts to solve for the value of the (r, c) cell. (This is a helper method for solvePuzzle()).
	 * 
	 * Pre-Conditions: r and c must be greater than or equal to zero and less than the puzzle width.
	 *                 The cell must NOT already contain a value (besides 0).
	 * Post-Conditions: Cell (r, c) and all following cells will contain the correct value and true will be returned if the previous cells are
	 *                  all correct (and a solution exists). This indicates that the puzzle is solved.
	 *                  Cell (r, c) will contain 0 and false will be returned if any previous values are incorrect.
	 * Responses: Throws an exception if r or c is invalid, or if the board is uninitialized.
	 *            Overwrites the old value at (r, c) if an old value exists.
	 * 
	 * @param r The row of the cell being solved
	 * @param c The column of the cell being solved.
	 * @return true if the previous cells are all correct (and a solution exists), false otherwise.
	 */
	private boolean solveCell(int r, int c) {
		if(r < 0 || c < 0 || r >= PUZZLE_WIDTH || c >= PUZZLE_WIDTH)
			throw new IllegalArgumentException();

		for(int guess = 1; guess <= PUZZLE_WIDTH; ++guess) {
			// if the guess can be placed at (r, c)
			if(canPlace(r, c, guess)) {
				// try it
				board[r][c] = guess;
				// check for base case (finished)
				if(isLastCell(r, c))
					return true;
				// otherwise, keep going
				if(solvePuzzle(getNextRow(r, c), getNextCol(r, c)))
					return true;
				// if the puzzle could not be solved, try a new guess
			}
		}
		// if all the guesses have been tried and none worked, erase the last guess
		board[r][c] = 0;
		return false; // backtrack
	}

	/**
	 * Solves the puzzle, starting at cell (r, c).
	 * 
	 * Pre-Conditions: r and c must be greater than or equal to zero and less than the puzzle width.
	 * Post-Conditions: Cell (r, c) and all following cells will contain the correct value and true will be returned if the previous cells are
	 *                  all correct (and a solution exists). This indicates that the puzzle is solved.
	 *                  Cell (r, c) will contain 0 or its old value, and false will be returned if any previous values are incorrect.
	 * Responses: Throws an exception if r or c is invalid, or if the board is uninitialized.
	 * 
	 * @param r The row of the cell being solved
	 * @param c The column of the cell being solved.
	 * @return true if the previous cells are all correct (and a solution exists), false otherwise.
	 */
	public boolean solvePuzzle(int r, int c) {
		if(r < 0 || c < 0 || r >= PUZZLE_WIDTH || c >= PUZZLE_WIDTH)
			throw new IllegalArgumentException();

		// check if the cell already has a value
		if(board[r][c] != 0) {
			// check if this is the last spot
			if(isLastCell(r, c))
				return true; // puzzle is solved
			// otherwise, go to the next cell WITHOUT overwriting this value
			if(solvePuzzle(getNextRow(r, c), getNextCol(r, c)))
				return true; // the puzzle is solved
			// if the puzzle could not be solved, backtrack without overwriting this value
			return false;
		}
		// if the cell does not have a value, try all possible guesses
		return solveCell(r, c);
	}

	private int[][] execute(int[][] puzzle, int puzzleWidth) {

		//Load board
		board = puzzle;
		solvePuzzle(0, 0);

		return board;
	}

	@Override
	public int[] execute(int[] puzzle) {
		int[][] twoDPuzz = new int[PUZZLE_WIDTH][PUZZLE_WIDTH];
		int[] solution = new int[puzzle.length];
		for(int i = 0; i < PUZZLE_WIDTH; i++) {
			for(int j = 0; j < PUZZLE_WIDTH; j++) {
				twoDPuzz[i][j] = puzzle[i * PUZZLE_WIDTH + j];
			}
		}
		execute(twoDPuzz, PUZZLE_WIDTH);
		for(int i = 0; i < PUZZLE_WIDTH; i++) {
			for(int j = 0; j < PUZZLE_WIDTH; j++) {
				solution[i * PUZZLE_WIDTH + j] = board[i][j];
			}
		}
		return solution;
	}

} // end class

// Named exceptions for the above methods

// Exception thrown if supplied dimensions to the SudokuSolver do not form a valid puzzle.
class IllegalDimensionException extends Exception {
	/**
	 * Default.
	 */
	private static final long serialVersionUID = 1L;
}

// Exception thrown if a file's sudoku puzzle's format is invalid, or if it's size does not
// match the supplied constants
class FileFormatException extends Exception {
	/**
	 * Default.
	 */
	private static final long serialVersionUID = 1L;

}

// Exception thrown if a file's puzzle is in an invalid state. For example, if the file contains
// two 1's in the same column, this exception is thrown.
class IllegalPuzzleException extends Exception {
	/**
	 * Default.
	 */
	private static final long serialVersionUID = 1L;

}
