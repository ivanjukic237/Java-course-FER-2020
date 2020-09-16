package hr.fer.zemris.java.hw05.db.lexer;

/**
 * Razred predstavlja popis tipova tokena.
 * 
 * @author Ivan Jukić
 *
 */

public enum TokenType {
	//tekst pod navodicima
	VALUE,
	//operator
	OPERATOR,
	//varijabla koja nije tekst
	VARIABLE,
	AND,
	//end of query
	EOF
}
