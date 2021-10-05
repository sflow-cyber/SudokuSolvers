import java.awt.BorderLayout;
import java.awt.Container;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.GridLayout;
import java.awt.TextField;
import java.awt.Toolkit;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.ItemEvent;
import java.awt.event.ItemListener;

import javax.swing.ButtonGroup;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JRadioButton;


public class MainFrame implements ActionListener, ItemListener{

	private JFrame frame;
	private Container contentPane;
	private JPanel panel1;
	private JPanel panel2;
	private JPanel panel3;
	private TextField[] tfs;
	private JRadioButton zwei;
	private JRadioButton drei;
	private JRadioButton vier;
	private JRadioButton fuenf;
	private JRadioButton sleicht;
	private JRadioButton mittel;
	private JRadioButton schwer;
	private JRadioButton leicht;
	private JRadioButton bf;
	private JRadioButton bt;
	private JRadioButton so;
	private JRadioButton cp;
	private Runtime runtime;
	private int[] savedPuzzle;

	public MainFrame()  {
		paintFrame();
	}

	private void paintFrame() {
		frame = new JFrame("Sudoku");
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		contentPane = frame.getContentPane();
		contentPane.setLayout(new BorderLayout());
		panel1 = new JPanel(new GridLayout(9,9));	// hier kommt das Sudoku Feld rein
		panel1.setPreferredSize(new Dimension(500,500));
		panel2 = new JPanel(new BorderLayout());		// Metapanel hier kommen die einstellungen rein
		panel2.setSize(new Dimension(450,350));
		panel3 = new JPanel(new GridLayout(1,3));		// hier kommen die Buttons rein
		tfs = new TextField[81];
		Font f = new Font("VerdanaT",Font.PLAIN,  60);
		for(int i = 0; i < tfs.length; i++) {
			tfs[i] = new TextField("");
			tfs[i].setFont(f);
			tfs[i].setEnabled(false);
			panel1.add(tfs[i]);
		}
		JPanel panel4 = new JPanel(new GridLayout(5,1));
		JLabel sizeLabel = new JLabel("Grid Size:");
		panel4.add(sizeLabel);
		zwei = new JRadioButton("4x4");

		zwei.setActionCommand("zwei");
		drei = new JRadioButton("9x9");

		drei.setActionCommand("drei");
		vier = new JRadioButton("16x16");

		vier.setActionCommand("vier");
		fuenf = new JRadioButton("25x25");

		fuenf.setActionCommand("fuenf");
		if(!zwei.isSelected() & !vier.isSelected() & !fuenf.isSelected())
			drei.setSelected(true);
		zwei.addItemListener(this);
		drei.addItemListener(this);
		vier.addItemListener(this);
		fuenf.addItemListener(this);
		ButtonGroup sizeGroup = new ButtonGroup();
		sizeGroup.add(zwei);
		sizeGroup.add(drei);
		sizeGroup.add(vier);
		sizeGroup.add(fuenf);
		panel4.add(zwei);
		panel4.add(drei);
		panel4.add(vier);
		panel4.add(fuenf);
		panel2.add(panel4,BorderLayout.PAGE_START);
		JPanel panel5 = new JPanel(new GridLayout(5,1));
		JLabel levelLabel = new JLabel("Level:");
		panel5.add(levelLabel);
		sleicht = new JRadioButton("very easy");
		leicht = new JRadioButton("easy");
		mittel = new JRadioButton("medium");
		schwer = new JRadioButton("hard");
		if(!leicht.isSelected() & !mittel.isSelected() & !sleicht.isSelected())
			mittel.setSelected(true);
		ButtonGroup levelGroup = new ButtonGroup();
		levelGroup.add(leicht);
		levelGroup.add(mittel);
		levelGroup.add(schwer);
		levelGroup.add(sleicht);
		panel5.add(sleicht);
		panel5.add(leicht);
		panel5.add(mittel);
		panel5.add(schwer);
		panel2.add(panel5,BorderLayout.CENTER);
		JPanel panel6 = new JPanel(new GridLayout(5,1));
		JLabel solverLabel = new JLabel("Method:");
		panel6.add(solverLabel);
		bf = new JRadioButton("Brute Force");
		bt = new JRadioButton("BruteForceLoop");
		so = new JRadioButton("Stochastic Optimization");
		cp = new JRadioButton("Constraint Programming");
		if(!bf.isSelected() & !so.isSelected() & !cp.isSelected())
			bt.setSelected(true);
		ButtonGroup solverGroup = new ButtonGroup();
		solverGroup.add(bf);
		solverGroup.add(bt);
		solverGroup.add(so);
		solverGroup.add(cp);
		panel6.add(bf);
		panel6.add(bt);
		panel6.add(so);
		panel6.add(cp);
		panel2.add(panel6,BorderLayout.PAGE_END);
		JButton button1 = new JButton("Create");
		button1.addActionListener(this);
		button1.setActionCommand("erzeugen");
		panel3.add(button1);
		JButton button2 = new JButton("Enter");
		button2.addActionListener(this);
		button2.setActionCommand("eingabe");
		panel3.add(button2);
		JButton button4 = new JButton("Remove");
		button4.addActionListener(this);
		button4.setActionCommand("loeschen");
		panel3.add(button4);
		JButton button3 = new JButton("Solve");
		button3.addActionListener(this);
		button3.setActionCommand("solve");
		panel3.add(button3);
		JButton button5 = new JButton("Unsolve");
		button5.addActionListener(this);
		button5.setActionCommand("unsolve");
		panel3.add(button5);
		JButton button6 = new JButton("Benchmark-Test");
		button6.addActionListener(this);
		button6.setActionCommand("test");
		panel3.add(button6);


		contentPane.add(panel1,BorderLayout.LINE_END);
		contentPane.add(panel2,BorderLayout.LINE_START);
		contentPane.add(panel3,BorderLayout.PAGE_END);
	}

	@Override
	public void actionPerformed(ActionEvent event) {
		String command = event.getActionCommand();
		int[] puzzleBoard = null;
		switch(command) {
		case "erzeugen":
			BoardBuilder bBuilder = new BoardBuilder();
			if(zwei.isSelected() && tfs.length == 16)
				puzzleBoard = bBuilder.build(2,0);
			else if(vier.isSelected())
				puzzleBoard = bBuilder.build(4,0);
			else if(fuenf.isSelected())
				puzzleBoard = bBuilder.build(5,0);
			else {
				if(sleicht.isSelected())
					puzzleBoard = bBuilder.build(3, 0);
				if(leicht.isSelected())
					puzzleBoard = bBuilder.build(3, 1);
				if(mittel.isSelected())
					puzzleBoard = bBuilder.build(3, 2);
				if(schwer.isSelected())
					puzzleBoard = bBuilder.build(3, 3);
			}
			for(int i = 0; i < tfs.length; i++) {
				tfs[i].setEnabled(false);
				if(!(""+puzzleBoard[i]).equals("0"))
					tfs[i].setText(""+puzzleBoard[i]); 
				else
					tfs[i].setText(""+(char)0);
				tfs[i].repaint();
			}
			break;
		case "eingabe":
			for(int i = 0; i < tfs.length; i++) {
				tfs[i].setEnabled(true);
				tfs[i].repaint();
			}
			break;
		case "loeschen":
			for(int i = 0; i < tfs.length; i++) {
				tfs[i].setText("" + (char)0);
				tfs[i].repaint();
			}
			break;
		case "solve":
			long startTime = 0;
			long stopTime = 0;
			runtime = Runtime.getRuntime();
			int[] puzzle = new int[tfs.length];
			savedPuzzle = new int[tfs.length];
			int[] sol = null;
			Solver solver = null;
			for(int i = 0; i < tfs.length; i++) {
				tfs[i].setEnabled(false);
				tfs[i].repaint();
				String content = tfs[i].getText();
				int intCont = 0;
				if(!content.equals("")) {
					try {
						intCont = Integer.parseInt(content);
					} catch (Exception e) {
						JOptionPane.showMessageDialog(frame, "Something went wrong reading the numbers");
					} 
				}
				if(intCont >= 0 && intCont <= (int)Math.sqrt(tfs.length)) {
					puzzle[i] = intCont;
					savedPuzzle[i] = intCont;
				}
			}
			if(bt.isSelected()) {
				solver = new BruteForceRecursive(puzzle);

			} else if(bf.isSelected()) {

				solver = new BruteForceLoop(puzzle);

			} else if(so.isSelected()) {
				//solver = new StochasticOpt(puzzle);
			} else {
				//solver = new ConstraintProg(puzzle);
			}
			startTime = System.nanoTime(); 
			sol = solver.execute(puzzle);
			stopTime = System.nanoTime();
			double maxMemory = solver.getMaxMemory();
			if(sol == null)
				sol = solver.getSolution();
			if(sol != null) {
				for(int i = 0; i < tfs.length; i++) {
					tfs[i].setText("" + sol[i]);
				}
			}
			JOptionPane.showMessageDialog(frame, "Method: " + (solver instanceof BruteForceLoop ? 
					"Brute Force" : solver instanceof BruteForceRecursive ? "Back Tracking" : "?") 
					+ "\nTime Elapsed (milli-seconds): " + (stopTime - startTime) / 1000000d
					+ "\nMax Memory Usage (MiB): " + maxMemory);
			break;
		case "unsolve":
			if(tfs.length == savedPuzzle.length) {
				for(int i = 0; i < tfs.length; i++) {
					if(savedPuzzle[i] != 0)
						tfs[i].setText(""+savedPuzzle[i]);
					else
						tfs[i].setText(""+(char)0);
					tfs[i].repaint();
				}
			}
		case "test":
			ResultFrame rf = new ResultFrame();
			rf.showFrame();
		}

		panel1.repaint();
		frame.repaint();
		// double currentMemory = ( (double)((double)(Runtime.getRuntime().totalMemory()/1024)/1024))- ((double)((double)(Runtime.getRuntime().freeMemory()/1024)/1024));
	}

	public void showFrame() {
		frame.setSize(1000, 650);
		Dimension d = Toolkit.getDefaultToolkit().getScreenSize();
		frame.setLocation((d.width-frame.getSize().width)/2, (d.height-frame.getSize().height)/2);
		frame.setVisible(true);
	}

	@Override
	public void itemStateChanged(ItemEvent e) {
		JRadioButton selBut = (JRadioButton)e.getItem();
		boolean wasEnabled = tfs[0].isEnabled();
		if(tfs.length == 81 && selBut == drei) {

		} else {
			Font f = null;
			if(selBut == zwei) {
				if(tfs.length != 16) {
					tfs = new TextField[16];
					f = new Font("VerdanaT",Font.PLAIN,  120);
					mittel.setSelected(true);
					leicht.setEnabled(false);
					sleicht.setEnabled(false);
					mittel.setEnabled(false);
					schwer.setEnabled(false);
				}
			} else if(selBut == drei) {
				if(tfs.length != 81) {
					tfs = new TextField[81];
					f = new Font("VerdanaT",Font.PLAIN, 60);
					sleicht.setEnabled(true);
					leicht.setEnabled(true);
					mittel.setEnabled(true);
					schwer.setEnabled(true);
				}
			} else if(selBut == vier) {
				if(tfs.length != 256) {
					tfs = new TextField[256];
					f = new Font("VerdanaT",Font.PLAIN,  32);
					mittel.setSelected(true);
					sleicht.setEnabled(false);
					leicht.setEnabled(false);
					mittel.setEnabled(false);
					schwer.setEnabled(false);
				}
			} else if(selBut == fuenf) {
				if(tfs.length != 625) {
					tfs = new TextField[625];
					f = new Font("VerdanaT",Font.PLAIN,  14);
					mittel.setSelected(true);
					sleicht.setEnabled(false);
					leicht.setEnabled(false);
					mittel.setEnabled(false);
					schwer.setEnabled(false);
				}	
			}
			contentPane.remove(panel1);
			panel1 = new JPanel(new GridLayout((int)Math.sqrt(tfs.length),(int)Math.sqrt(tfs.length)));
			panel1.setPreferredSize(new Dimension(500,500));
			for(int i = 0; i < tfs.length; i++) {
				tfs[i] = new TextField("");
				tfs[i].setFont(f);
				tfs[i].setEnabled(wasEnabled);
				panel1.add(tfs[i]);
			}

			contentPane.add(panel1,BorderLayout.LINE_END);
			contentPane.add(panel2,BorderLayout.LINE_START);
			contentPane.add(panel3,BorderLayout.PAGE_END);
			frame.revalidate();
			frame.repaint();
		}
	}
}

