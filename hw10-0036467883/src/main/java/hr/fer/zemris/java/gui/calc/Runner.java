package hr.fer.zemris.java.gui.calc;

import java.awt.Container;

import javax.swing.JFrame;
import javax.swing.SwingUtilities;
import javax.swing.WindowConstants;

import hr.fer.zemris.java.gui.calc.Buttons.*;
import hr.fer.zemris.java.gui.layouts.CalcLayout;

/**
 * Starts the calculator user interface. Initializes the location and the
 * preffered size of the window.
 * 
 * @author Ivan Jukić
 *
 */

public class Runner extends JFrame {

	/**
	 * Calculator object for which the user interface will be shown.
	 */

	private static Calculator calc = new Calculator();

	private static final long serialVersionUID = 1L;

	/**
	 * Sets the location of the window, sets the size, title and default close
	 * operation. Initializes the GUI elements.
	 */

	public Runner() {
		setLocation(300, 300);
		setSize(700, 500);
		setTitle("Calculator");
		setDefaultCloseOperation(WindowConstants.DISPOSE_ON_CLOSE);

		initGUI();
	}

	/**
	 * Initializes all the buttons for the calculator.
	 */
	
	private void initGUI() {
		Container p = getContentPane();
		p.setLayout(new CalcLayout(2));

		new Screen(p, calc);
		new NumberButtons(p, calc);
		new Clear(p, calc);
		new SignAndDecimalPoint(p, calc);
		new Operations(p, calc);
		new Equal(p, calc);
		Functions functions = new Functions(p, calc);
		new StackOperations(p, calc);
		new InverseCheckBox(p, calc, functions);
	}

	/**
	 * Starts the window.
	 * 
	 * @param args command line arguments (not used)
	 */
	
	public static void main(String[] args) {

		SwingUtilities.invokeLater(() -> {
			JFrame frame = new Runner();
			frame.setVisible(true);
		});

	}
}
