package hr.fer.zemris.java.hw05.db.lexer;

/**
 * Predstavlja tokene koje lexer vraća. Tokeni su definirani u razredu TokenType.
 * 
 * @author Ivan Jukić
 *
 */

public class Token {

	private TokenType tokenType;
	private String value;
	
	/**
	 * Postavlja vrstu i vrijednost tokena.
	 * 
	 * @param tokenType vrsta tokena
	 * @param value vrijednost tokena
	 */
	
	public Token(TokenType tokenType, String value) {
		this.tokenType = tokenType;
		this.value = value;
	}

	/**
	 * Vraća tip tokena.
	 * 
	 * @return tip tokena
	 */
	
	public TokenType getTokenType() {
		return tokenType;
	}

	/**
	 * Postavlja tip tokena.
	 * 
	 * @param tokenType tip tokena
	 */
	
	public void setTokenType(TokenType tokenType) {
		this.tokenType = tokenType;
	}

	/**
	 * Vraća vrijednost tokena
	 * 
	 * @return vrijednost tokena
	 */
	
	public String getValue() {
		return value;
	}

	/**
	 * Postavlja vrijednost tokena.
	 * 
	 * @param value vrijednost tokena
	 */
	
	public void setValue(String value) {
		this.value = value;
	}
	
	/**
	 * Vraća String reprezentaciju tokena.
	 */
	
	@Override
	public String toString() {
		return tokenType + " " +  value;
	}
}
