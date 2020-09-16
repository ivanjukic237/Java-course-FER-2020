package hr.fer.zemris.java.gui.calc;

import java.util.Stack;
import java.util.function.DoubleBinaryOperator;

/**
 * Model of a simple calculator with all functionalities.
 * 
 * @author ivanjukic
 *
 */

public class Calculator implements CalcModel {
	/**
	 * Stack object for saving values of the calculator. Buttons for working with
	 * this object are made in StackOperations class.
	 */
	public Stack<Double> stack = new Stack<>();
	/**
	 * True if the value on the screen is editable.
	 */
	private boolean isEditable = true;
	/**
	 * True if the value shown on screen is negative.
	 */
	private boolean isNegative = false;
	/**
	 * String of digits shown on the screen.
	 */
	public String inputedDigits = "";
	/**
	 * Number representation of the digits on the screen.
	 */
	public double digits = 0;
	/**
	 * Frozen value.
	 */
	String frozenValue = null;
	/**
	 * Active operand saved after pressing one of the function buttons.
	 */
	private double activeOperand = 0;
	/**
	 * Next operation to be made on the active operand and current value.
	 */
	private DoubleBinaryOperator operator = null;
	/**
	 * Listener that notifies the observer (in this case the screen) when one of the
	 * buttons changes the value on the screen.
	 */
	CalcValueListener listener;
	/**
	 * True if operand is set.
	 */
	private boolean isOperandSet = false;

	/**
	 * Adds a value listener for the calculator.
	 */

	@Override
	public void addCalcValueListener(CalcValueListener l) {
		this.listener = l;
	}

	/**
	 * Removes a given value listener.
	 * 
	 * @param l listener to be removed
	 */

	@Override
	public void removeCalcValueListener(CalcValueListener l) {
		this.listener = null;
	}

	/**
	 * Returns the current value of the calculator.
	 * 
	 * @return digits current value of the calculator
	 */

	@Override
	public double getValue() {
		return digits;
	}

	/**
	 * Sets the current value of the calculator. Updates the screen.
	 * 
	 * @param value new current value in the calculator
	 */

	@Override
	public void setValue(double value) {

		this.digits = value;
		this.inputedDigits = value + "";
		isEditable = false;
		if (listener != null) {
			listener.valueChanged(this);
		}
	}

	/**
	 * Is the value editable.
	 * 
	 * @return true if the value is editable
	 */

	@Override
	public boolean isEditable() {
		return isEditable;
	}

	/**
	 * Sets if the value is editable or not.
	 */

	public void setEditable() {
		isEditable = true;
	}

	/**
	 * Clears the inputedDigits value, sets current value to 0 and sets the state of
	 * the value to be editable.
	 */

	@Override
	public void clear() {
		inputedDigits = "";
		digits = 0;
		isEditable = true;
	}

	/**
	 * Clears the inputedDigits value, sets current value to 0 and sets the state of
	 * the value to be editable. Updates the screen.
	 */

	public void clearButton() {
		inputedDigits = "";
		digits = 0;
		isEditable = true;
		if (listener != null) {
			listener.valueChanged(this);
		}
	}

	/**
	 * Clears the inputedDigits value, sets current value to 0, sets the state of
	 * the value to be editable, sets the acrive operand to 0 and resets the
	 * operator. Updates the screen.
	 */

	@Override
	public void clearAll() {
		inputedDigits = "";
		digits = 0;
		isEditable = true;
		activeOperand = 0;
		isOperandSet = false;
		operator = null;
		if (listener != null) {
			listener.valueChanged(this);
		}

	}

	/**
	 * Swaps the sign for the current value. Updates the screen.
	 * 
	 * @throws CalculatorInputException if the model is not editable when this
	 *                                  method is called
	 */

	@Override
	public void swapSign() throws CalculatorInputException {
		if (!isEditable) {
			throw new CalculatorInputException("Model is not editable.");
		} else {
			this.digits = this.digits * -1;
			if (inputedDigits.startsWith("-")) {
				inputedDigits = inputedDigits.replace("-", "");
			} else {
				inputedDigits = "-" + inputedDigits;
			}
			this.isNegative = !this.isNegative;

			frozenValue = null;
			if (listener != null) {
				listener.valueChanged(this);
			}
		}

	}

	/**
	 * Inserts a decimal point if there is not one there. Updates the screen.
	 * 
	 * @throws CalculatorInputException if the decimal point already exists in the
	 *                                  value.
	 */

	@Override
	public void insertDecimalPoint() throws CalculatorInputException {
		frozenValue = null;
		if (inputedDigits.contains(".") || inputedDigits.equals("") || inputedDigits.equals("-")) {
			throw new CalculatorInputException("Decimal point already exists.");
		}
		inputedDigits += ".";
		if (listener != null) {
			listener.valueChanged(this);
		}

	}

	/**
	 * Inserts a digit at the end of the value. Updates the screen.
	 * 
	 * @throws CalculatorInputException if the model is not editable when this
	 *                                  method is called
	 * @throws CalculatorInputException if new number could not be parsed
	 * @throws IllegalArgumentException if the given number to be parsed is bigger
	 *                                  than Double.MAX_VALUE
	 */

	@Override
	public void insertDigit(int digit) throws CalculatorInputException, IllegalArgumentException {
		if (!isEditable) {
			throw new CalculatorInputException("Model is not editable.");
		}
		frozenValue = null;
		if (digit != 0 && inputedDigits.equals("0")) {
			inputedDigits = "";
		}
		String temp = inputedDigits;

		temp += digit;

		try {
			double digits = Double.parseDouble(temp);
			if (digits > Double.MAX_VALUE) {
				throw new CalculatorInputException("Number is too big.");
			}
			if (!(inputedDigits.equals("0") && digits == 0)) {
				inputedDigits = temp;
			}

			this.digits = digits;
		} catch (NumberFormatException ex) {
			throw new CalculatorInputException("Number can't be parsed");

		}
		if (listener != null) {
			listener.valueChanged(this);
		}

	}

	/**
	 * Checks if active operand is set.
	 * 
	 * @return true if active operand is set
	 */

	@Override
	public boolean isActiveOperandSet() {
		return isOperandSet;
	}

	/**
	 * Returns the active operand.
	 * 
	 * @return IllegalStateException if the operand is not set
	 */

	@Override
	public double getActiveOperand() throws IllegalStateException {
		if (!isOperandSet) {
			throw new IllegalStateException("Operand is not set.");
		} else {
			return activeOperand;

		}
	}

	/**
	 * Sets the active operand.
	 * 
	 * @param activeOperand active operand to be set
	 */

	@Override
	public void setActiveOperand(double activeOperand) {
		this.activeOperand = activeOperand;
		isOperandSet = true;

	}

	/**
	 * Clears the active operand.
	 */

	@Override
	public void clearActiveOperand() {
		this.activeOperand = 0;
		isOperandSet = false;

	}

	/**
	 * Returns the current binary operator.
	 */

	@Override
	public DoubleBinaryOperator getPendingBinaryOperation() {
		return this.operator;
	}

	/**
	 * Sets the binary operator.
	 * 
	 * @param binary operator to be set
	 */

	@Override
	public void setPendingBinaryOperation(DoubleBinaryOperator op) {
		this.operator = op;

	}

	/**
	 * Returns the string representation of the current value.
	 */

	public String toString() {
		if (frozenValue != null) {
			return frozenValue;
		}
		if (inputedDigits.equals("-")) {
			return "-0";
		} else if (inputedDigits.equals("")) {
			return "0";
		} else {
			if (digits == Double.POSITIVE_INFINITY) {
				return "Infinity";
			} else if (digits == Double.NEGATIVE_INFINITY) {
				return "-Infinity";
			} else if (digits == Double.NaN) {
				return "NaN";
			} else {
				return inputedDigits;
			}
		}

	}

}
