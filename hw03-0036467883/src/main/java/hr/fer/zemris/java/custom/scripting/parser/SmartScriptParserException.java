package hr.fer.zemris.java.custom.scripting.parser;

/**
 * Iznimka nasljeđuje iz razreda RuntimeException i predstavlja iznimku za razred SmartScriptParser.
 * 
 * @author Ivan Jukić
 *
 */

public class SmartScriptParserException extends RuntimeException {
	
	/**
	 * Konstruktor prima tekst te vraća iznimku s danim tekstom.
	 * 
	 * @param exception tekst iznimke
	 */
	
	public SmartScriptParserException(String exception) {
		super(exception);
	}
}
