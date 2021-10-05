import java.io.File;
import java.io.FileNotFoundException;
import java.util.Scanner;


public class BoardBuilder {
	private int[][] kids;
	private int[][] big;
	private int[][] giant;
	
	public BoardBuilder() {
		kids = new int[24][16];
		big = new int[10][256];
		giant = new int[10][625];
		
		kids[0] = new int[]{0,0,0,0,3,1,4,2,2,4,3,1,0,0,0,0};
		kids[1] = new int[]{3,0,0,2,2,0,0,4,1,0,0,3,4,0,0,1};
		kids[2] = new int[]{1,4,2,3,0,0,0,0,0,0,0,0,2,1,3,4};
		kids[3] = new int[]{0,4,2,0,2,0,0,3,1,0,0,4,0,3,1,0};
		kids[4] = new int[]{3,4,1,2,0,0,0,0,0,0,0,0,1,2,4,3};
		kids[5] = new int[]{0,1,3,0,0,4,1,0,0,2,4,0,0,3,2,0};
		kids[6] = new int[]{0,2,3,0,1,0,0,4,3,0,0,2,0,4,1,0};
		kids[7] = new int[]{4,0,0,3,2,0,0,4,3,0,0,1,1,0,0,2};
		kids[8] = new int[]{2,0,0,1,0,1,4,0,0,3,2,0,4,0,0,3};
		kids[9] = new int[]{4,0,0,1,3,0,0,4,1,0,0,2,2,0,0,3};
		kids[10] = new int[]{0,4,1,0,0,1,2,0,0,3,4,0,0,2,3,0};
		kids[11] = new int[]{3,0,0,4,2,0,0,1,4,0,0,3,1,0,0,2};
		kids[12] = new int[]{3,2,1,4,0,0,0,0,0,0,0,0,2,4,3,1};
		kids[13] = new int[]{2,4,3,1,0,0,0,0,0,0,0,0,3,1,4,2};
		kids[14] = new int[]{0,0,0,0,2,3,4,1,3,4,1,2,0,0,0,0};
		kids[15] = new int[]{4,0,0,3,0,3,2,0,0,4,3,0,3,0,0,2};
		kids[16] = new int[]{4,0,0,1,0,1,3,0,0,4,1,0,1,0,0,3};
		kids[17] = new int[]{2,0,0,4,0,1,2,0,0,4,3,0,3,0,0,1};
		kids[18] = new int[]{0,3,2,0,0,1,4,0,0,2,3,0,0,4,1,0};
		kids[19] = new int[]{2,0,0,1,4,0,0,2,1,0,0,3,3,0,0,4};
		kids[20] = new int[]{4,0,0,3,0,1,4,0,0,4,3,0,1,0,0,4};
		kids[21] = new int[]{0,0,0,0,1,4,2,3,3,2,1,4,0,0,0,0};
		kids[22] = new int[]{3,0,0,2,0,1,4,0,0,3,2,0,4,0,0,1};
		kids[23] = new int[]{0,3,2,0,1,0,0,3,2,0,0,4,0,4,1,0};
//		
//		big[0] = {};
//		big[1] = {};
//		big[2] = {};
//		big[3] = {};
//		big[4] = {};
//		big[5] = {};
//		big[6] = {};
//		big[7] = {};
//		big[8] = {};
//		big[9] = {};
//		
//		giant[0] = {};
//		giant[1] = {};
//		giant[2] = {};
//		giant[3] = {};
//		giant[4] = {};
//		giant[5] = {};
//		giant[6] = {};
//		giant[7] = {};
//		giant[8] = {};
//		giant[9] = {};
	}

	public int[] build(int size, int level) {
		if(size == 2) {
			return kids[(int)(Math.random()*24)];
		}
		if(size == 3) {
			int rand = (int)(Math.random() * 10000);
			File f = null;
			Scanner sc = null;
			// level 0 is very easy
			String fs = System.getProperty("file.separator");
			if(level == 0) {
				int rand2 = (int)(Math.random() * 2);
				if(rand2 == 0)
					f = new File("Boards" + fs + "45N" + fs + "0.txt");
				else
					f = new File("Boards" + fs + "40N" + fs + "1.txt");
			} else if(level == 1) { // level 1 is easy
				f = new File("Boards" + fs + "35N" + fs + "2.txt");
				
			} else if(level == 2) { // level 2 is medium
				f = new File("Boards" + fs + "30N" + fs + "3.txt");
			} else {	// level 3 is hard
				f = new File("Boards" + fs + "25N" + fs + "5.txt");
				
			}
			try {
				sc = new Scanner(f);
			} catch (FileNotFoundException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			for(int i = 0; i < rand; i++) {
				sc.nextLine();
			}
			String puzzle = sc.nextLine();
			int[] puzzleArray = new int[puzzle.length()];
			for(int i = 0; i < puzzle.length(); i++) {
				puzzleArray[i] = Integer.parseInt(""+puzzle.charAt(i));
			}
			try {
				sc.close();
			} catch(Exception e) {
				
			}
			return puzzleArray;
		}
		if(size == 4) {
			return big[(int)(Math.random()*10)];
		} 
		if(size == 5) {
			return giant[(int)(Math.random()*10)];
		}
		return null;
	}
}
