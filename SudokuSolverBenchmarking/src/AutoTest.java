

public class AutoTest {

	private BruteForceRecursive bt;
	private BruteForceLoop bf;

	// default constructor

	public int[][] sizeScaling() {
		int[][] output = new int[4][2];

		for(int i = 0; i < 4; i++) {

		}

		return output;
	}

	public double[][] getData() {
		long[][][] values = new long[2][2][1000];
		BoardBuilder bb = new BoardBuilder();
		bt = new BruteForceRecursive(bb.build(3, 0));

		bf = new BruteForceLoop(bb.build(3, 0));

		double[][] output = new double[2][2];
		int rand = 0;
		int[] puzzle = null;
		for(int i = 0; i < 1000; i++) {
			rand = (int)(Math.random() * 4);
			puzzle = bb.build(3, rand);
			long startTime = System.nanoTime();
			bt.execute(puzzle);
			values[0][0][i] = (System.nanoTime() - startTime);
			values[0][1][i] = (long)(bt.getMaxMemory() * (1024 * 1024));
			startTime = System.nanoTime();
			bf.execute(puzzle);
			values[1][0][i] = (System.nanoTime() - startTime);
			values[1][1][i] = (long)(bf.getMaxMemory() * (1024 * 1024));
		}
		output[0][0] = avg(values[0][0]);
		output[1][0] = avg(values[1][0]);
		output[0][1] = avg(values[0][1]);
		output[1][1] = avg(values[1][1]);
		return output;
	}

	public double[][] difficultyScaling() {
		BoardBuilder bb = new BoardBuilder();

		bt = new BruteForceRecursive(bb.build(3, 0));

		bf = new BruteForceLoop(bb.build(3, 0));



		double[][] output = new double[4][2];
		for(int i = 0; i < 4; i++) {
			long[] btValues = new long[1000];
			long[] bfValues = new long[1000];
			for(int j = 0; j < 1000; j++) {
				int[] puzzle = bb.build(3, i);
				long startTime = System.nanoTime();
				bt.execute(puzzle);
				btValues[j] = (System.nanoTime() - startTime);
				startTime = System.nanoTime();
				bf.execute(puzzle);
				bfValues[j] = (System.nanoTime() - startTime);
			}
			output[i][0] = avg(btValues);
			output[i][1] = avg(bfValues);
		}
		return output;
	}

	public double avg(long[] values) {
		double avg = 0;
		for(int i = 0; i < values.length; i++) {
			avg += (double)values[i] / values.length;
		}
		return avg;
	}
}
