package hr.fer.zemris.java.gui.calc.Buttons;

import java.awt.Container;

import javax.swing.JButton;

import hr.fer.zemris.java.gui.calc.Calculator;
import hr.fer.zemris.java.gui.layouts.RCPosition;

/**
 * Models the equal button for the calculator. This button applies the binary
 * operaton on the active operand and current value. Position for the button is
 * (1, 6).
 * 
 * @author Ivan Jukić
 *
 */

public class Equal {

	Container container;
	Calculator calc;

	/**
	 * Sets the container and Calculator object for this class.
	 * 
	 * @param container container
	 * @param calc      Calculator object
	 */

	public Equal(Container container, Calculator calc) {
		this.container = container;
		this.calc = calc;
		setButton();
	}

	/**
	 * Sets the button for the current container. If IllegalStateException occurs
	 * (that happens if the active operand is not set), it is caught and the button
	 * does nothing.
	 */

	private void setButton() {
		JButton equalButton = new JButton("=");
		equalButton.addActionListener(a -> {
			try {
				calc.setValue(calc.getPendingBinaryOperation().applyAsDouble(calc.getActiveOperand(), calc.getValue()));
				calc.clearActiveOperand();
				calc.setPendingBinaryOperation(null);
			} catch (IllegalStateException ex) {

			}

		});
		container.add(equalButton, new RCPosition(1, 6));

	}
}
