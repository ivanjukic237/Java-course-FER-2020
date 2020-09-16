package hr.fer.zemris.java.gui.calc.Buttons;

import java.awt.Container;

import javax.swing.JButton;

import hr.fer.zemris.java.gui.calc.Calculator;
import hr.fer.zemris.java.gui.calc.CalculatorInputException;
import hr.fer.zemris.java.gui.layouts.RCPosition;

/**
 * Creates the number buttons for the calculator. Buttons are from 0 to 9.
 * 
 * @author Ivan Jukić
 *
 */

public class NumberButtons {

	Container container;
	Calculator calc;

	/**
	 * Sets the container, Calculator object and Functions object for the
	 * calculator. Sets the number buttons.
	 * 
	 * @param container container
	 * @param calc      Calculator object
	 */

	public NumberButtons(Container container, Calculator calc) {
		this.container = container;
		this.calc = calc;
		setButtons();

	}

	/**
	 * Sets the number buttons.
	 */

	private void setButtons() {
		int number = 0;
		for (int i = 5; i >= 2; i--) {
			for (int j = 3; j <= 5; j++) {

				JButton numberButton = new JButton(number + "");
				numberButton.setFont(numberButton.getFont().deriveFont(30f));
				int num = number;
				numberButton.addActionListener(a -> {
					try {
						calc.insertDigit(num);
					} catch (CalculatorInputException ex) {

					}

				});
				container.add(numberButton, new RCPosition(i, j));
				if (i == 5) {
					i--;
					j = 2;
				}
				number++;
			}
		}
	}

}
