package hr.fer.zemris.java.gui.calc.Buttons;

import java.awt.Container;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.function.DoubleUnaryOperator;

import javax.swing.JButton;

import hr.fer.zemris.java.gui.calc.Calculator;
import hr.fer.zemris.java.gui.layouts.RCPosition;

/**
 * Model of available functions for the calculator. Model has one static boolean
 * flag that decides which set of functions will be shown. There are two sets of
 * functions. First set contains: log, ln, sin, cos, tan, ctg, x^n and 1/x.
 * Second set contains: 10^x, e^x, x^(1/n), arcsin, arccos, arctg, arcctg and
 * 1/x. 1/x function is shared between two sets. Flag is switched by the class
 * InverseCheckBox (inv buton in the calculator). Buttons are the same, only the
 * funcionality changes.
 * 
 * @author Ivan Jukić
 *
 */

public class Functions {

	Container container;
	Calculator calc;
	// list of function buttons
	JButton inverseButton = new JButton("1/x");
	JButton logButton = new JButton("log");
	JButton lnButton = new JButton("ln");
	JButton sinButton = new JButton("sin");
	JButton cosButton = new JButton("cos");
	JButton tanButton = new JButton("tan");
	JButton ctgButton = new JButton("ctg");
	JButton powButton = new JButton("x^n");
	// flag for inversing functions
	static boolean isInverse = false;

	/**
	 * List of function buttons.
	 */

	ArrayList<JButton> list = new ArrayList<>();

	/**
	 * Sets the container and Calculator object for the class. Sets the positions of
	 * all the buttons.
	 * 
	 * @param container container
	 * @param calc      Calculator object
	 */

	public Functions(Container container, Calculator calc) {
		this.container = container;
		this.calc = calc;
		container.add(inverseButton, new RCPosition(2, 1));
		container.add(logButton, new RCPosition(3, 1));
		container.add(lnButton, new RCPosition(4, 1));
		container.add(sinButton, new RCPosition(2, 2));
		container.add(cosButton, new RCPosition(3, 2));
		container.add(tanButton, new RCPosition(4, 2));
		container.add(ctgButton, new RCPosition(5, 2));
		container.add(powButton, new RCPosition(5, 1));

		inverseButton.addActionListener(new Listener((d) -> 1 / d, (d) -> 1 / d));

		list.addAll(Arrays.asList(logButton, lnButton, sinButton, cosButton, ctgButton, powButton));
		setButtons();
		setTexts();
	}

	/**
	 * Removes actio listeners for all buttons.
	 */

	private void removeActionListeners() {
		for (JButton currentButton : list) {
			for (ActionListener al : currentButton.getActionListeners()) {
				currentButton.removeActionListener(al);
			}
		}
	}

	/**
	 * Removes action listeners for all buttons and sets new action listeners for
	 * the buttons.
	 */

	public void setButtons() {
		removeActionListeners();
		logButton.addActionListener(new Listener((d) -> Math.log10(d), (d) -> Math.pow(10, d)));

		lnButton.addActionListener(new Listener((d) -> Math.log(d), (d) -> Math.pow(Math.E, d)));

		sinButton.addActionListener(new Listener((d) -> Math.sin(d), (d) -> Math.asin(d)));

		cosButton.addActionListener(new Listener((d) -> Math.cos(d), (d) -> Math.acos(d)));

		tanButton.addActionListener(new Listener((d) -> Math.tan(d), (d) -> Math.atan(d)));

		ctgButton.addActionListener(new Listener((d) -> 1 / Math.tan(d), (d) -> Math.atan(1 / d)));

		powButton.addActionListener(a -> {
			if (!calc.isActiveOperandSet()) {
				calc.setActiveOperand(calc.getValue());
				if (!isInverse) {
					calc.setPendingBinaryOperation((d1, d2) -> Math.pow(d1, d2));
				} else {
					calc.setPendingBinaryOperation((d1, d2) -> Math.pow(d1, 1 / d2));
				}
				calc.clear();
			}
		});
	}

	/**
	 * Sets text for buttons.
	 */

	public void setTexts() {
		if (!isInverse) {
			logButton.setText("log");
			lnButton.setText("ln");
			sinButton.setText("sin");
			cosButton.setText("cos");
			tanButton.setText("tan");
			ctgButton.setText("ctg");
			powButton.setText("x^n");

		} else {
			logButton.setText("10^x");
			lnButton.setText("e^x");
			powButton.setText("x^(1/n)");
			sinButton.setText("arcsin");
			cosButton.setText("arccos");
			tanButton.setText("arctan");
			ctgButton.setText("arcctg");
		}
	}

	public static void swapStates() {
		isInverse = !isInverse;
	}

	/**
	 * Models a custom listener that chooses between two DoubleUnaryOperators that
	 * depends on the flag.
	 * 
	 * @author Ivan Jukić
	 *
	 */

	public class Listener implements ActionListener {

		DoubleUnaryOperator operator1;
		DoubleUnaryOperator operator2;

		/**
		 * Sets two unary operators.
		 * 
		 * @param operator1 first unary operator
		 * @param operator2 second unary operator (when the inv button is activated)
		 */
		
		public Listener(DoubleUnaryOperator operator1, DoubleUnaryOperator operator2) {
			this.operator1 = operator1;
			this.operator2 = operator2;
		}

		/**
		 * If the inversed flag is triggered, operator 2 is chosen, else operator1 is chosen.
		 */
		
		@Override
		public void actionPerformed(ActionEvent e) {
			if (!isInverse) {
				calc.setValue(operator1.applyAsDouble(calc.getValue()));
			} else {
				calc.setValue(operator2.applyAsDouble(calc.getValue()));
			}
		}

	}

}
