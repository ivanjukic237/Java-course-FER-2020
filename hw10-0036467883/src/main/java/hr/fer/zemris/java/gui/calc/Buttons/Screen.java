package hr.fer.zemris.java.gui.calc.Buttons;

import java.awt.Color;
import java.awt.Container;

import javax.swing.BorderFactory;
import javax.swing.JLabel;
import javax.swing.SwingConstants;

import hr.fer.zemris.java.gui.calc.CalcModel;
import hr.fer.zemris.java.gui.calc.CalcValueListener;
import hr.fer.zemris.java.gui.calc.Calculator;
import hr.fer.zemris.java.gui.layouts.RCPosition;

/**
 * Sets the screen label which shows the current value of the calculator.
 * 
 * @author Ivan Jukić
 *
 */

public class Screen extends JLabel {

	private static final long serialVersionUID = 1L;

	Container container;
	Calculator calc;
	JLabel screen = new JLabel();

	/**
	 * Sets the container, Calculator object and Functions object for the
	 * calculator. Sets the screen label.
	 * 
	 * @param container container
	 * @param calc      Calculator object
	 */

	public Screen(Container container, Calculator calc) {
		super();
		this.container = container;
		this.calc = calc;
		setScreen();
	}

	/**
	 * Sets the screen, its background, opaqueness, font, alignment, border and
	 * initial text.
	 */

	private void setScreen() {
		screen.setBackground(Color.YELLOW);
		screen.setOpaque(true);
		screen.setFont(screen.getFont().deriveFont(30f));
		calc.addCalcValueListener(new Listener());
		screen.setHorizontalAlignment(SwingConstants.RIGHT);
		screen.setBorder(BorderFactory.createLineBorder(Color.BLUE, 1));
		container.add(screen, new RCPosition(1, 1));
		screen.setText(calc.digits + "");
	}

	/**
	 * Custom value listener that edits the screen value when the method that is
	 * observed changes the current value.
	 * 
	 * @author Ivan Jukić
	 *
	 */

	public class Listener implements CalcValueListener {

		@Override
		public void valueChanged(CalcModel model) {
			screen.setText(calc.digits + "");

		}

	}
}
