package hr.fer.zemris.java.gui.calc.Buttons;

import java.awt.Container;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.function.DoubleBinaryOperator;

import javax.swing.JButton;

import hr.fer.zemris.java.gui.calc.Calculator;
import hr.fer.zemris.java.gui.layouts.RCPosition;

/**
 * Sets the operations buttons for the calculator. Operations are *, /, + and -.
 * 
 * @author Ivan Jukić
 *
 */

public class Operations {

	Container container;
	Calculator calc;

	/**
	 * Sets the container, Calculator object and Functions object for the
	 * calculator. Sets the operations buttons.
	 * 
	 * @param container container
	 * @param calc      Calculator object
	 */

	public Operations(Container container, Calculator calc) {
		this.container = container;
		this.calc = calc;
		setButtons();
	}

	/**
	 * Sets the operations buttons and their functionalities.
	 */

	private void setButtons() {
		JButton divisionButton = new JButton("/");
		divisionButton.addActionListener(new listener((d1, d2) -> d1 / d2));

		JButton multiplicationButton = new JButton("*");
		multiplicationButton.addActionListener(new listener((d1, d2) -> d1 * d2));

		JButton subtractionButton = new JButton("-");
		subtractionButton.addActionListener(new listener((d1, d2) -> d1 - d2));

		JButton aditionButton = new JButton("+");
		aditionButton.addActionListener(new listener((d1, d2) -> d1 + d2));

		container.add(divisionButton, new RCPosition(2, 6));
		container.add(multiplicationButton, new RCPosition(3, 6));
		container.add(subtractionButton, new RCPosition(4, 6));
		container.add(aditionButton, new RCPosition(5, 6));

	}

	/**
	 * Custom listener class that gets the binary operator and sets that operator as
	 * a current operator in the calculator.
	 * 
	 * @author Ivan Jukić
	 *
	 */

	public class listener implements ActionListener {
		DoubleBinaryOperator operator;

		/**
		 * Sets the operator for this class.
		 * 
		 * @param operator operator to be set
		 */

		public listener(DoubleBinaryOperator operator) {
			this.operator = operator;
		}

		/**
		 * If the active operand was not set in the calculator it sets the active
		 * operand, active oprator and clears the value.
		 */

		@Override
		public void actionPerformed(ActionEvent e) {
			if (!calc.isActiveOperandSet()) {
				calc.setActiveOperand(calc.getValue());
				calc.setPendingBinaryOperation(operator);
				calc.clear();
			}

		}

	}
}
