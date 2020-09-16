package hr.fer.zemris.java.hw06.shell;

/**
 * Custom chell exception. Occurs when some error happens with user input or
 * output.
 * 
 * @author Ivan Jukić
 *
 */

public class ShellIOException extends RuntimeException {

	/**
	 * Default UID.
	 */
	private static final long serialVersionUID = 1L;

	/**
	 * Method throws the exception with the given message.
	 * 
	 * @param exception message for the exception
	 */

	public ShellIOException(String exception) {
		super(exception);
	}

}
