package hr.fer.zemris.java.gui.calc.Buttons;

import java.awt.Container;

import javax.swing.JButton;
import javax.swing.JLabel;

import hr.fer.zemris.java.gui.calc.Calculator;
import hr.fer.zemris.java.gui.layouts.RCPosition;

/**
 * Creates the stack operations button pop and push for the calculator. Pop
 * takes the last object in the stack, returns it and removes it from the stack.
 * Push puts the value to the end of the stack.
 * 
 * @author Ivan Jukić
 *
 */

public class StackOperations {

	Container container;
	Calculator calc;
	JLabel screen = new JLabel();

	/**
	 * Sets the container, Calculator object and Functions object for the
	 * calculator. Sets the pop and push buttons.
	 * 
	 * @param container container
	 * @param calc      Calculator object
	 */

	public StackOperations(Container container, Calculator calc) {
		super();
		this.container = container;
		this.calc = calc;
		setButtons();
	}

	/**
	 * Sets the push and pop buttons and their functionalities.
	 */

	private void setButtons() {
		JButton pushButton = new JButton("push");
		container.add(pushButton, new RCPosition(3, 7));
		pushButton.addActionListener(a -> {
			calc.stack.push(calc.getValue());
		});
		JButton popButton = new JButton("pop");
		container.add(popButton, new RCPosition(4, 7));
		popButton.addActionListener(a -> {
			calc.setValue(calc.stack.pop());
		});

	}
}
