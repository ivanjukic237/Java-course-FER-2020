package hr.fer.zemris.java.hw05.db.lexer;

/**
 * Lexer radi leksičku analizu ulaznog teksta tj. vraća tokene za određeni query
 * za bazu podataka studenata. Lexer može vratiti tokene tipa defenirani u enum
 * razredu TokenType: VARIABLE koji predstavlja atribut, OPERATOR koji
 * predstavlja operator, VALUE koji predstavlja vrijednost s kojom se radi u
 * bazi, AND koji predstavlja logički i i EOF token koji predstavlja kraj
 * query-a. Lexer se može naći u 3 stanja definirana u enum razredu LexerState.
 * 
 * @author Ivan Jukić
 *
 */

public class Lexer {

	/**
	 * Polje znakova ulaznog teksta (query-a).
	 */

	private char[] data;

	private int currentIndex = 0;
	private LexerState lexerState = LexerState.TEXT;

	/**
	 * Konstruktor prima ulazni tekst, radi polje znakova i postavlja ga u razredu.
	 * 
	 * @throws NullPointerException ako je ulazni tekst null referenca
	 * @param inputText ulazni tekst nad kojim se radi leksička analiza
	 */

	public Lexer(String inputText) {
		if (inputText == null) {
			throw new NullPointerException("Ulazni text ne može biti null.");
		}
		this.data = inputText.strip().toCharArray();
	}

	/**
	 * Metoda vraća sljedeći token u tekstu. Ako je neki izraz pod navodnicima
	 * mijenja stanje lexera u stanje VALUES gdje vadi tokene tipa VALUE. Ako je
	 * izraz riječ onda mijenja stanje lexera u stanje TEXT koji vraća token
	 * atributa i token operatora AND. U suprotnom mijenja stanje lexera u stanje
	 * OPERATORS gdje se vraćaju tokeni operatora.
	 * 
	 * @return sljedeći token u tekstu
	 */

	public Token nextToken() {

		if (currentIndex >= data.length) {
			return new Token(TokenType.EOF, "EOF");
		}
		if (data[currentIndex] == ' ') {
			skipSpaces();
		}
		// ako je tekst pod navodnicima onda je to vrijednost koju pridružujemo
		// varijablama
		if (data[currentIndex] == '\"') {
			lexerState = LexerState.VALUES;
			String text = parseText();
			return new Token(TokenType.VALUE, text);

		} else if (!Character.isAlphabetic(data[currentIndex]) && !Character.isDigit(data[currentIndex])) {
			lexerState = LexerState.OPERATORS;
			String text = parseText();
			return new Token(TokenType.OPERATOR, text);
		} else {
			// tekst može biti ili operator ili varijabla
			lexerState = LexerState.TEXT;
			String text = parseText();
			String textToCheck = text.toLowerCase();
			if (textToCheck.toLowerCase().equals("and")) {
				return new Token(TokenType.AND, text);
			} else if (textToCheck.toLowerCase().equals("like")) {
				return new Token(TokenType.OPERATOR, text);
			} else {
				return new Token(TokenType.VARIABLE, text.toLowerCase());
			}
		}
	}

	/**
	 * Pomoćna metoda koja preskače prazne znakove u polju data.
	 */

	private void skipSpaces() {
		if (data[currentIndex] == ' ') {
			while (data[currentIndex] == ' ') {
				currentIndex++;
			}
		}
	}

	/**
	 * Parsira polje vraća izraze kao Stringove.
	 * 
	 * @throws IllegalArgumentException ako vrijednost nije zatvorena s navodnicima
	 * @return izraz kao String
	 */

	private String parseText() {

		StringBuilder stringBuilder = new StringBuilder();

		if (lexerState == LexerState.VALUES) {
			currentIndex++;
			while (data[currentIndex] != '"') {
				stringBuilder.append(data[currentIndex]);
				currentIndex++;
				if (currentIndex >= data.length) {
					throw new IllegalArgumentException("Vrijednost nije zatvorena s navodnicima.");
				}
			}
			currentIndex++;

		} else if (lexerState == LexerState.TEXT) {
			skipSpaces();
			while (data[currentIndex] != ' ' && Character.isAlphabetic(data[currentIndex])) {
				stringBuilder.append(data[currentIndex]);
				currentIndex++;
				if (currentIndex >= data.length) {
					break;
				}
			}
		} else {
			while (data[currentIndex] == '<' || data[currentIndex] == '>'
					|| data[currentIndex] == '=' && data[currentIndex] != ' ') {
				stringBuilder.append(data[currentIndex]);
				currentIndex++;
				if (currentIndex >= data.length) {
					break;
				}
			}
		}
		return stringBuilder.toString().strip();
	}

}
