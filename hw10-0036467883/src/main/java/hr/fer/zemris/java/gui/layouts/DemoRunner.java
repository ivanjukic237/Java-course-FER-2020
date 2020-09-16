package hr.fer.zemris.java.gui.layouts;

import java.awt.Container;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.SwingUtilities;
import javax.swing.WindowConstants;

/**
 * Demo runner that shows all button in the CalcLayout.
 * 
 * @author ivanjukic
 *
 */

public class DemoRunner extends JFrame {
	private static final long serialVersionUID = 1L;

	/**
	 * Sets the location, size, title and default close operation for the window.
	 * Initializes the GUI elements.
	 */

	public DemoRunner() {
		setLocation(300, 300);
		setSize(700, 500);
		setTitle("Calculator");
		setDefaultCloseOperation(WindowConstants.DISPOSE_ON_CLOSE);

		initGUI();
	}

	/**
	 * Initializes all of the possible GUI elements.
	 */
	
	private void initGUI() {
		Container p = getContentPane();
		p.setLayout(new CalcLayout(5));
		for (int i = 1; i < 8; i++) {
			for (int j = 1; j < 6; j++) {
				if ((i == 2 || i == 3 || i == 4 || i == 5) && j == 1) {

				} else {
					p.add(new JButton(j + ", " + i), new RCPosition(j, i));

				}
			}
		}

	}

	/**
	 * Starts the window.
	 * 
	 * @param args command line arguments (not used)
	 */
	
	public static void main(String[] args) {

		SwingUtilities.invokeLater(() -> {
			JFrame frame = new DemoRunner();
			frame.setVisible(true);
		});
	}
}
