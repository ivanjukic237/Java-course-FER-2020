package hr.fer.zemris.java.custom.scripting.exec;

import java.text.DecimalFormat;
import java.util.Stack;

import hr.fer.zemris.java.webserver.RequestContext;

/**
 * Class contains functions that do some calculations on some objects in a
 * stack.
 * 
 * @author Ivan Jukić
 *
 */

public class Functions {

	/**
	 * Stack of objects.
	 */
	private Stack<Object> stack;
	/**
	 * Context to write the function values to.
	 */
	private RequestContext requestContext;

	/**
	 * Creates the Functions object.
	 * 
	 * @param stack          stack of object to do calculations on
	 * @param requestContext context
	 */

	public Functions(Stack<Object> stack, RequestContext requestContext) {
		this.stack = stack;
		this.requestContext = requestContext;
	}

	/**
	 * Returns the String value of the given object.
	 * 
	 * @param obj object
	 * @return the String value of the given object
	 */

	private String getValue(Object obj) {
		ValueWrapper wrapper = new ValueWrapper(obj);
		String value = wrapper.toString();
		return value;
	}

	/**
	 * Sin function.
	 */

	public void sin() {
		Object obj = stack.pop();

		stack.push(Math.sin(Math.toRadians(Double.parseDouble(getValue(obj)))));
	}

	/**
	 * Sets the decimal format of a number.
	 */

	public void decfmt() {
		Object first = stack.pop();
		Object second = stack.pop();
		DecimalFormat df = new DecimalFormat(first.toString());
		stack.push(String.format("%s", df.format(second)));
	}

	/**
	 * Duplicates an object.
	 */

	public void dup() {
		Object duplicate = stack.pop();
		stack.push(duplicate);
		stack.push(duplicate);
	}

	/**
	 * Swaps two objects in the stack.
	 */

	public void swap() {
		Object first = stack.pop();
		Object second = stack.pop();
		stack.push(first);
		stack.push(second);
	}

	/**
	 * Sets the mime type for the context.
	 */

	public void setMimeType() {
		requestContext.setMimeType(stack.pop().toString());
	}

	/**
	 * Returns the parameter.
	 */

	public void paramGet() {
		// default value
		Object defValue = stack.pop();
		Object name = stack.pop();
		// value we want
		Object value = requestContext.getParameter(name.toString());
		stack.push(value == null ? defValue : value);
	}

	/**
	 * Returns the persistent parameter.
	 */

	public void pparamGet() {
		// default value
		Object defValue = stack.pop();
		Object name = stack.pop();
		// value we want
		Object value = requestContext.getPersistentParameter(name.toString());
		stack.push(value == null ? defValue : value);
	}

	/**
	 * Returns the temporary parameter.
	 */

	public void tparamGet() {
		// default value
		Object defValue = stack.pop();
		Object name = stack.pop();
		// value we want
		Object value = requestContext.getTemporaryParameter(name.toString());
		stack.push(value == null ? defValue : value);
	}

	/**
	 * Sets the persistent parameter.
	 */

	public void pparamSet() {
		Object name = stack.pop();
		Object value = stack.pop();
		requestContext.setPersistentParameter(name.toString(), value.toString());
	}

	/**
	 * Sets the temporary parameter.
	 */

	public void tparamSet() {
		Object name = stack.pop();
		Object value = stack.pop();
		requestContext.setTemporaryParameter(name.toString(), value.toString());

	}

	/**
	 * Deletes the temporary parameter.
	 */

	public void tparamDel() {
		Object name = stack.pop();
		requestContext.getTemporaryParameterNames().remove(name);
	}

	/**
	 * Deletes the persistent parameter.
	 */

	public void pparamDel() {
		Object name = stack.pop();
		requestContext.getParameterNames().remove(name);
	}

}
