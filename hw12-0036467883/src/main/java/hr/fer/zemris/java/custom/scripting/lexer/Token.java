package hr.fer.zemris.java.custom.scripting.lexer;

/**
 * Razred predstavlja model Tokena koji sadrži vrijednost i tip TokenType.
 * 
 * @author Ivan Jukić
 *
 */

public class Token {

	TokenType type;
	Object value;
	
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
	
	/**
	 * Vraća String reprezentaciju Tokena kao "tip vrijednost"
	 * 
	 * @return String reprezentacija Tokena
	 */
	
	@Override
	public String toString() {
		return "Tip: " + type.toString() + ", Vrijednost: " + value;
	}
	

}
