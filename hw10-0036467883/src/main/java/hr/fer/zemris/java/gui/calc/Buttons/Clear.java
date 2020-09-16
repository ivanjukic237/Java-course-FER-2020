package hr.fer.zemris.java.gui.calc.Buttons;

import java.awt.Container;

import javax.swing.JButton;

import hr.fer.zemris.java.gui.calc.Calculator;
import hr.fer.zemris.java.gui.layouts.RCPosition;

/**
 * Models the clear button and the reset button for the calculator. Clear button clears
 * the current value, and reset resets the calculator to its initial state.
 * 
 * @author Ivan Jukić
 *
 */

public class Clear {

	Container container;
	Calculator calc;

	/**
	 * Sets the container and Calculator object for this class.
	 * 
	 * @param container container
	 * @param calc Calculator object
	 */
	
	public Clear(Container container, Calculator calc) {
		super();
		this.container = container;
		this.calc = calc;
		setButton();
	}

	/**
	 * Sets the clear and reset button for the given container.
	 */
	
	private void setButton() {
		JButton clear = new JButton("clr");
		container.add(clear, new RCPosition(1, 7));
		clear.addActionListener(a -> {
			calc.clearButton();
		});
		JButton clearAll = new JButton("reset");
		container.add(clearAll, new RCPosition(2, 7));

		clearAll.addActionListener(a -> {
			calc.clearAll();
		});
	}

}
