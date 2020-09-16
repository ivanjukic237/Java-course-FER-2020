package hr.fer.zemris.java.custom.scripting.lexer;

/**
 * enum razred predstavlja sve tipove Tokena za razred Lexer. 
 * 
 * @author Ivan Jukić
 *
 */

public enum TokenType {
	TEXT,
	
	StartOfTag,
	EndOfTag,
	
	INTEGER,
	DOUBLE,
	VARIABLE,
	OPERATOR,
	FUNCTION,
	FOR,
	EQUALS,
	END,
	EOF
	
}
