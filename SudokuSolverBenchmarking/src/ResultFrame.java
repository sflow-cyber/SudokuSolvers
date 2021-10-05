import java.awt.BorderLayout;
import java.awt.Container;
import java.awt.Dimension;
import java.awt.Toolkit;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JScrollPane;


public class ResultFrame implements ActionListener {
	
	private JFrame frame;
	
	public ResultFrame() {
		frame = new JFrame("Sudoku Benchmarking Results");
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		Container contentPane = frame.getContentPane();
		contentPane.setLayout(new BorderLayout());
		JButton button1 = new JButton("Ok");
		button1.addActionListener(this);
		button1.setActionCommand("ok");
		contentPane.add(button1,BorderLayout.PAGE_END);
		JLabel label = new JLabel();
		AutoTest at = new AutoTest();
		double[][] processingTimes = at.getData();
		double[][] diffScArray = at.difficultyScaling();
		String text = "<html><body>Sudoku solver benchmarking ended with the following results:<br>" +
		"(Format: Average Time Elapsed in milli seconds / Average Memory Usage in MiB)<br><br>" +
		"1.000 random 9x9 sudokus were solved by all algorithms:<br>" +
		"Brute Force: " + Math.round((processingTimes[1][0] / 1000000d) * 10000) /10000d + " / " + Math.round((processingTimes[1][1] / (1024d * 1024)) * 10000) /10000d + "<br>" +
		"Backtracking: " + Math.round((processingTimes[0][0] / 1000000d) * 10000) /10000d + " / " + Math.round((processingTimes[1][0] / (1024d * 1024)) * 10000) /10000d + "<br>" +
		"Stochastic Optimization: " + "<br>" +
		"Constraint Programming: " + "<br><br>" +
		"Difficulty Scaling: <br> " +
		"1.000 random 9x9 sudokus from each difficulty class were solved by all algorithms:<br>"+
		"Very Easy:<br>"+
		"Brute Force: " + Math.round((diffScArray[0][1] / 1000000d) * 10000) /10000d + "/" + "<br>" +
		"Backtracking: " + Math.round((diffScArray[0][0] / 1000000d) * 10000) /10000d + "/" + "<br>" +
		"Stochastic Optimization: " + "<br>" +
		"Constraint Programming: " + "<br>" +
		"Easy:<br>" +
		"Brute Force: " + Math.round((diffScArray[1][1] / 1000000d) * 10000) /10000d + "/" + "<br>" +
		"Backtracking: " + Math.round((diffScArray[1][0] / 1000000d) * 10000) /10000d + "/" + "<br>" +
		"Stochastic Optimization: " + "<br>" +
		"Constraint Programming: " + "<br>" +
		"Medium:<br>" +
		"Brute Force: " + Math.round((diffScArray[2][1] / 1000000d) * 10000) /10000d + "/" + "<br>" +
		"Backtracking: " + Math.round((diffScArray[2][0] / 1000000d) * 10000) /10000d + "/" + "<br>" +
		"Stochastic Optimization: " + "<br>" +
		"Constraint Programming: " + "<br>" +
		"Hard:<br>" +
		"Brute Force: " + Math.round((diffScArray[3][1] / 1000000d) * 10000) /10000d + "/" + "<br>" +
		"Backtracking: " + Math.round((diffScArray[3][0] / 1000000d) * 10000) /10000d + "/" + "<br>" +
		"Stochastic Optimization: " + "<br>" +
		"Constraint Programming: " + "<br><br>" +
		"Size Scaling: <br> " +
		"1.000 random sudokus from each size class (*10 from size class 4x4) were solved by all algorithms:<br>"+
		"*Grid Size 4x4:<br>"+
		"Brute Force: " + Math.round((diffScArray[0][1] / 1000000d) * 10000) /10000d + "/" + "<br>" +
		"Backtracking: " + Math.round((diffScArray[0][0] / 1000000d) * 10000) /10000d + "/" + "<br>" +
		"Stochastic Optimization: " + "<br>" +
		"Constraint Programming: " + "<br>" +
		"Grid Size 9x9:<br>" +
		"Brute Force: " + Math.round((diffScArray[1][1] / 1000000d) * 10000) /10000d + "/" + "<br>" +
		"Backtracking: " + Math.round((diffScArray[1][0] / 1000000d) * 10000) /10000d + "/" + "<br>" +
		"Stochastic Optimization: " + "<br>" +
		"Constraint Programming: " + "<br>" +
		"Grid Size 16x16:<br>" +
		"Brute Force: " + Math.round((diffScArray[2][1] / 1000000d) * 10000) /10000d + "/" + "<br>" +
		"Backtracking: " + Math.round((diffScArray[2][0] / 1000000d) * 10000) /10000d + "/" + "<br>" +
		"Stochastic Optimization: " + "<br>" +
		"Constraint Programming: " + "<br>" +
		"Grid Size 25x25:<br>" +
		"Brute Force: " + Math.round((diffScArray[3][1] / 1000000d) * 10000) /10000d + "/" + "<br>" +
		"Backtracking: " + Math.round((diffScArray[3][0] / 1000000d) * 10000) /10000d + "/" + "<br>" +
		"Stochastic Optimization: " + "<br>" +
		"Constraint Programming: " +
		"</body></html>";
		label.setText(text.toString());
		JScrollPane scrollPane = new JScrollPane(label,JScrollPane.VERTICAL_SCROLLBAR_AS_NEEDED,JScrollPane.HORIZONTAL_SCROLLBAR_AS_NEEDED);
		contentPane.add(scrollPane, BorderLayout.CENTER);
	}
	
	public void showFrame() {
		frame.setSize(650, 650);
		Dimension d = Toolkit.getDefaultToolkit().getScreenSize();
		frame.setLocation((d.width-frame.getSize().width)/2, (d.height-frame.getSize().height)/2);
		frame.setVisible(true);
	}

	@Override
	public void actionPerformed(ActionEvent e) {
		String command = e.getActionCommand();
		if(command.equals("ok"))
			frame.dispose();
	}

}
