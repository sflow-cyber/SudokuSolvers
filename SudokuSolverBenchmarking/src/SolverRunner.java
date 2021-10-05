import javax.swing.SwingUtilities;


public class SolverRunner {

	public static void main(String[] args) {

		MainFrame mf = new MainFrame();
		SwingUtilities.invokeLater(new Runnable(){
			public void run(){
				mf.showFrame();
			}
		});
	}
}
