package hr.fer.zemris.java.hw03.prob1;

/**
 * Razred predstavlja model Tokena koji sadrži vrijednost i tip TokenType.
 * 
 * @author Ivan Jukić
 *
 */

public class Token {
	TokenType type ;
	Object value ;

	/**
	 * Konstruktor inicijalizira razred s vrijednosti i tipom.
	 * 
	 * @param type tip tokena vrste TokenType
	 * @param value vrijednost sadržana u Tokenu
	 */
	
	public Token(TokenType type, Object value) {
		this.type = type;
		this.value = value;
	}
	
	/**
	 * Vraća vrijednost Tokena
	 * 
	 * @return vrijednost Tokena
	 */
	
	public Object getValue() {
		return value;
	}
	
	/**
	 * Vraća tip Tokena
	 * 
	 * @return tip Tokena
	 */
	
	public TokenType getType() {
		return type;
	}
}
