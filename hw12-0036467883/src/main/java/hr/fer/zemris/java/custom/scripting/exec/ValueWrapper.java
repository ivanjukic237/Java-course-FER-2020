package hr.fer.zemris.java.custom.scripting.exec;

/**
 * Wraps an object like String, Integer or Double and does some operations on
 * it. Operations available are addition, subtraction, division, multiplication
 * and comparation.
 * 
 * @author Ivan Jukić
 *
 */

public class ValueWrapper {

	/**
	 * Wrapped value.
	 */

	private Object value;

	/**
	 * Sets the value.
	 * 
	 * @param initialValue given value
	 */

	public ValueWrapper(Object initialValue) {
		this.value = initialValue;
	}

	@Override
	public String toString() {
		return this.value.toString();
	}

	/**
	 * Adds this value to the given value.
	 * 
	 * @param incValue value to add to this value
	 */

	public void add(Object incValue) {
		value = compute(incValue, (x, y) -> x = x + y, (x, y) -> x = x + y);
	}

	/**
	 * Subtract this value to the given value.
	 * 
	 * @param incValue value to Subtract to this value
	 */

	public void subtract(Object decValue) {
		value = compute(decValue, (x, y) -> x = x - y, (x, y) -> x = x - y);

	}

	/**
	 * Multiplies this value with the given value.
	 * 
	 * @param incValue value to Multipliy to this value
	 */

	public void multiply(Object mulValue) {
		value = compute(mulValue, (x, y) -> x = x * y, (x, y) -> x = x * y);

	}

	/**
	 * Divides this value to the given value.
	 * 
	 * @param incValue value to divide to this value
	 */

	public void divide(Object divValue) {
		value = compute(divValue, (x, y) -> x = x / y, (x, y) -> x = x / y);

	}

	/**
	 * Compares a given value to this value.
	 * 
	 * @param withValue value to compare to this value
	 * @return -1 if this value is less than the given value, 0 if they are the same
	 *         and 1 if this value is larger than the given value.
	 */

	public int numCompare(Object withValue) {
		Object comp = compute(withValue, (x, y) -> x >= y ? (x == y ? 0.0 : 1.0) : -1.0,
				(x, y) -> x >= y ? (x == y ? 0 : 1) : -1);
		int comparator;
		if (comp instanceof Integer) {
			comparator = (Integer) comp;
		} else {
			comparator = ((Double) comp).intValue();
		}
		if (comparator == 0) {
			return 0;
		} else if (comparator == 1) {
			return 1;
		} else {
			return -1;
		}

	}

	/**
	 * Returns the wrapped value.
	 * 
	 * @return the wrapped value
	 */

	public Object getValue() {
		return value;
	}

	/**
	 * Sets the wrapped value.
	 * 
	 * @param value value to set the wrapped value to
	 */

	public void setValue(Object value) {
		this.value = value;
	}

	/**
	 * Creates a string from the given object. Only Integers, Doubles and Strings
	 * are supported.
	 * 
	 * @param num object to turn to a string
	 * @return string of the given object
	 * @throws RuntimeException if input object is not valid.
	 */

	public String toStringFromObject(Object num) {
		if (num == null) {
			return "0";
		}
		if (num instanceof String || num instanceof Integer || num instanceof Double) {
			return num.toString();
		} else {
			throw new RuntimeException("Input object is not valid.");
		}
	}

	/**
	 * Operation on two given double numbers.
	 * 
	 * @author Ivan Jukić
	 *
	 */

	private interface DoubleOperation {
		double calculate(double x, double y);
	}

	/**
	 * Operation on two given integer numbers.
	 * 
	 * @author Ivan Jukić
	 *
	 */

	private interface IntegerOperation {

		int calculate(int x, int y);
	}

	/**
	 * Checks if a given object is integer.
	 * 
	 * @param num object to check if it's an integer
	 * @return true if the given object is integer
	 */

	private boolean isInteger(Object num) {
		try {
			Integer.parseInt(toStringFromObject(num));
			Integer.parseInt(toStringFromObject(value));
			return true;
		} catch (NumberFormatException ex) {
			return false;
		}
	}

	/**
	 * Does a operation on a given objects and the wrapped value.
	 * 
	 * @param num              does operation on this object
	 * @param doubleOperation  some operation on doubles
	 * @param integerOperation some operation on integers
	 * @return object calculated from given objects
	 * @throws RuntimeException if the input object is not valid.
	 */

	private Object compute(Object num, DoubleOperation doubleOperation, IntegerOperation integerOperation) {
		if (isInteger(num)) {
			Integer numInteger = Integer.parseInt(toStringFromObject(num));
			Integer valueInteger = Integer.parseInt(toStringFromObject(value));
			return integerOperation.calculate(valueInteger, numInteger);

		} else {
			try {
				Double numDouble = Double.parseDouble(toStringFromObject(num));
				Double valueDouble = Double.parseDouble(toStringFromObject(value));
				return doubleOperation.calculate(valueDouble, numDouble);
			} catch (NumberFormatException ex1) {
				throw new RuntimeException("Input object is not valid.");
			}
		}
	}

}
