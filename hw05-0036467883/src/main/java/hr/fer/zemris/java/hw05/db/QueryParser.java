package hr.fer.zemris.java.hw05.db;

import java.util.ArrayList;
import java.util.List;

import hr.fer.zemris.java.hw05.db.lexer.*;

/**
 * Parser dobiva tokene od razreda Lexer za neki query, radi gramatičku obradu
 * tokena tako da vadi uvjete kombinacije tokena i te uvjete pamti u listu.
 * Jedan uvjet se sastoji od atributa, operatora i vrijednosti.
 * 
 * @author Ivan Jukić
 *
 */

public class QueryParser {

	/**
	 * Lista uvjeta koji će se vratiti.
	 */

	private List<ConditionalExpression> listOfConditionalExpressions = new ArrayList<>();

	/**
	 * Razred prima query String te poziva Lexer da vraća sve tokene iz query-a.
	 * Logika je sljedeća: prvi token mora biti tip varijable tj. atributa baze,
	 * zatim mora biti token tipa operator koji odlučuje što će se raditi s tim
	 * atributom. Nakon toga dolazi token vrijednosti za pojedini atribut. S tim
	 * dobivamo jedan uvjet te ga dodajemo u listu. Nakon toga može doći operator
	 * AND koji spaja taj uvjet i sljedeći. Broj uvjeta koji se mogu parsirati nije
	 * zadan.
	 * 
	 * @throws IllegalArgumentException ako tokeni ne dolaze u zadanom poretku
	 * @param queryStatement
	 */

	public QueryParser(String queryStatement) {
		Lexer lexer = new Lexer(queryStatement);
		Token token = lexer.nextToken();

		while (true) {
			IFieldValueGetter fieldValueGetter = null;
			if (token.getTokenType() == TokenType.EOF) {
				break;
			}
			if (token.getTokenType() == TokenType.VARIABLE) {
				if (token.getValue().equals("firstname")) {
					fieldValueGetter = FieldValueGetters.FIRST_NAME;
				} else if (token.getValue().equals("lastname")) {
					fieldValueGetter = FieldValueGetters.LAST_NAME;
				} else if (token.getValue().equals("jmbag")) {
					fieldValueGetter = FieldValueGetters.JMBAG;
				} else if (token.getValue().equals("finalgrade")) {
					fieldValueGetter = FieldValueGetters.FINAL_GRADE;
				} else {
					throw new IllegalArgumentException(token.getValue() + " je nepoznata varijabla.");
				}
			} else {
				throw new IllegalArgumentException(token.getValue() + " mora biti varijabla.");
			}

			token = lexer.nextToken();
			IComparisonOperator comparisonOperator = null;
			if (token.getTokenType() == TokenType.OPERATOR) {
				String text = token.getValue();
				if (text.equals(">")) {
					comparisonOperator = ComparisonOperators.GREATER;
				} else if (text.equals("<")) {
					comparisonOperator = ComparisonOperators.LESS;
				} else if (text.equals(">=")) {
					comparisonOperator = ComparisonOperators.GREATER_OR_EQUALS;
				} else if (text.equals("<=")) {
					comparisonOperator = ComparisonOperators.LESS_OR_EQUALS;
				} else if (text.equals("=")) {
					comparisonOperator = ComparisonOperators.EQUALS;
				} else if (text.toLowerCase().equals("like")) {
					comparisonOperator = ComparisonOperators.LIKE;
				} else {
					throw new IllegalArgumentException(text + " je nepoznat operator.");
				}
			} else {
				throw new IllegalArgumentException("Nakon varijable treba biti operator.");
			}
			token = lexer.nextToken();

			String stringLiteral = null;
			if (token.getTokenType() == TokenType.VALUE) {
				stringLiteral = token.getValue();
			} else {
				throw new IllegalArgumentException("Varijabli trebamo pridružiti neku vrijednost pod navodnicima.");
			}

			listOfConditionalExpressions
					.add(new ConditionalExpression(fieldValueGetter, stringLiteral, comparisonOperator));
			token = lexer.nextToken();
			if (token.getTokenType() == TokenType.EOF) {
				break;
			} else if (token.getTokenType() == TokenType.AND) {
				token = lexer.nextToken();
			} else {
				throw new IllegalArgumentException(token.getValue() + ": ilegalan operator.");
			}
		}
	}

	/**
	 * Vraća {@true} ako je query direktan tj. ako postoji samo jedan uvjet kojem je
	 * atribut JMBAG studenta, i operator je jednako.
	 * 
	 * @return {@true} ako je query direktan
	 */

	public boolean isDirectQuery() {
		if (listOfConditionalExpressions.size() == 1
				&& listOfConditionalExpressions.get(0).getComparisonOperator().equals(ComparisonOperators.EQUALS)
				&& listOfConditionalExpressions.get(0).getFieldGetter().equals(FieldValueGetters.JMBAG)) {
			return true;
		}
		return false;
	}

	/**
	 * Ako je query direktan, onda vraća JMBAG koji je zatražen u query-u.
	 * 
	 * @throws IllegalStateException ako query nije direktan
	 * @return traženi JMBAG u query-u
	 */

	public String getQueriedJMBAG() {
		if (isDirectQuery()) {
			return listOfConditionalExpressions.get(0).getStringLiteral();
		}
		throw new IllegalStateException("Query nije direktan.");

	}

	/**
	 * Vraća listu uvjeta dobiveni parsiranjem u query-a.
	 * 
	 * @return lista uvjeta dobiveni parsiranjem
	 */

	List<ConditionalExpression> getQuery() {
		return this.listOfConditionalExpressions;
	}

}
