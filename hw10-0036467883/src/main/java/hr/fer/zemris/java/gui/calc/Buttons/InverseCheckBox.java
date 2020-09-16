package hr.fer.zemris.java.gui.calc.Buttons;

import java.awt.Container;

import javax.swing.JCheckBox;

import hr.fer.zemris.java.gui.calc.Calculator;
import hr.fer.zemris.java.gui.layouts.RCPosition;

/**
 * Swaps the functions shown in the calculator. Which functions will be swapped
 * is defined in the class Functions.java.
 * 
 * @author Ivan Jukić
 *
 */

public class InverseCheckBox {

	Container container;
	Calculator calc;
	Functions functions;

	/**
	 * Sets the container, Calculator object and Functions object for the
	 * calculator. Sets the inverse checkbox.
	 * 
	 * @param container container
	 * @param calc      Calculator object
	 * @param functions Functions object
	 */

	public InverseCheckBox(Container container, Calculator calc, Functions functions) {
		this.container = container;
		this.calc = calc;
		this.functions = functions;
		setButton();
	}

	/**
	 * Sets the checkbok at the position (5, 7) and swaps the functions shown in the
	 * calculator.
	 */

	private void setButton() {
		JCheckBox inverseCheckBox = new JCheckBox("inv");
		container.add(inverseCheckBox, new RCPosition(5, 7));
		inverseCheckBox.addActionListener(a -> {
			Functions.swapStates();
			functions.setButtons();
			functions.setTexts();
		});
	}
}
