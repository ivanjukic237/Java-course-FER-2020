package hr.fer.zemris.java.custom.scripting.lexer;

/**
 * Iznimka nasljeđuje iz razreda RuntimeException i predstavlja iznimku za razred Lexer.
 * 
 * @author Ivan Jukić
 *
 */

public class LexerException extends RuntimeException {
		
	private static final long serialVersionUID = 1L;

	/**
	 * Konstruktor prima tekst te vraća iznimku s danim tekstom.
	 * 
	 * @param exception tekst iznimke
	 */
	
	public LexerException(String exception) {
		super(exception);
	}
}


