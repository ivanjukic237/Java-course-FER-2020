package hr.fer.zemris.java.gui.calc.prim;

import java.awt.BorderLayout;
import java.awt.Container;
import java.awt.Dimension;
import java.awt.GridLayout;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JList;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.SwingUtilities;
import javax.swing.WindowConstants;

/**
 * Class creates a window that show two scroll panes which generate prime
 * numbers when a button is pressed on both panes. Border layout is used where
 * the two panes are in the center and the button is at the bottom.
 * 
 * @author Ivan Jukić
 *
 */

public class PrimDemo extends JFrame {

	private static final long serialVersionUID = 1L;

	/**
	 * Sets the location, title and default closing operation. Initializes the GUI
	 * elements.
	 */

	public PrimDemo() {
		setLocation(20, 50);
		setTitle("Prim Demo");
		setDefaultCloseOperation(WindowConstants.DISPOSE_ON_CLOSE);

		initGUI();
	}

	/**
	 * Makes two croll panes and a button that generates prime numbers.
	 */

	private void initGUI() {
		Container cp = getContentPane();
		cp.setLayout(new BorderLayout());

		PrimListModel<Integer> model = new PrimListModel<>();

		JList<Integer> list1 = new JList<>(model);
		JList<Integer> list2 = new JList<>(model);
		model.add(1);
		JButton nextButton = new JButton("sljedeći");
		nextButton.addActionListener(e -> {
			model.add(model.next());
		});

		JPanel central = new JPanel(new GridLayout(1, 0));
		central.setPreferredSize(new Dimension(300, 200));

		central.add(new JScrollPane(list1));
		central.add(new JScrollPane(list2));

		cp.add(central, BorderLayout.CENTER);
		cp.add(nextButton, BorderLayout.PAGE_END);

	}

	/**
	 * Runs the window.
	 * 
	 * @param args command line arguments (not used)
	 */
	
	public static void main(String[] args) {

		SwingUtilities.invokeLater(() -> {
			JFrame frame = new PrimDemo();
			frame.pack();
			frame.setVisible(true);
		});
	}
}
