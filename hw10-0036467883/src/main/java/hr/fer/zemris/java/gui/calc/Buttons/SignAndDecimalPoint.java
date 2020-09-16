package hr.fer.zemris.java.gui.calc.Buttons;

import java.awt.Container;

import javax.swing.JButton;
import javax.swing.JLabel;

import hr.fer.zemris.java.gui.calc.Calculator;
import hr.fer.zemris.java.gui.calc.CalculatorInputException;
import hr.fer.zemris.java.gui.layouts.RCPosition;

/**
 * Sets the sign change and decimal point buttons for the calculator. Sign
 * changes the sign of the current value on the screen. Decimal point button
 * adds the decimal point to the current value.
 * 
 * @author Ivan Jukić
 *
 */

public class SignAndDecimalPoint {

	Container container;
	Calculator calc;
	JLabel screen = new JLabel();

	/**
	 * Sets the container, Calculator object and Functions object for the
	 * calculator. Sets the sign change and decimal point buttons.
	 * 
	 * @param container container
	 * @param calc      Calculator object
	 */

	public SignAndDecimalPoint(Container container, Calculator calc) {
		super();
		this.container = container;
		this.calc = calc;
		setButtons();
	}

	/**
	 * Sets the buttons and their functionalities.
	 */

	private void setButtons() {
		JButton sign = new JButton("+/-");
		container.add(sign, new RCPosition(5, 4));
		sign.addActionListener(a -> {
			try {
				calc.swapSign();
			} catch (CalculatorInputException ex) {

			}

		});

		JButton decimalPoint = new JButton(".");
		container.add(decimalPoint, new RCPosition(5, 5));
		decimalPoint.addActionListener(a -> {
			try {
				calc.insertDecimalPoint();
			} catch (CalculatorInputException ex) {

			}

		});
	}
}
