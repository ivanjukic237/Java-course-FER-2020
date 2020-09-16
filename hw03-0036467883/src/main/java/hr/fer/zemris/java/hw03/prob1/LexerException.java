package hr.fer.zemris.java.hw03.prob1;

/**
 * Iznimka nasljeđuje iz razreda RuntimeException i predstavlja iznimku za razred Lexer.
 * 
 * @author Ivan Jukić
 *
 */

public class LexerException extends RuntimeException {
	
	/**
	 * Konstruktor prima teks te vraća iznimku s danim tekstom.
	 * 
	 * @param exception tekst iznimke
	 */
	
	public LexerException(String exception) {
		super(exception);
	}
}
