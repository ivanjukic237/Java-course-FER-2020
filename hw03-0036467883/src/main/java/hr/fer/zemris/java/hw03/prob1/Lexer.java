package hr.fer.zemris.java.hw03.prob1;

import hr.fer.zemris.java.custom.scripting.lexer.LexerException;

/**
 * Predstavlja lexer koji prima tekst i iz njega vraća tokene definirane u TokenType.
 * 
 * @author Ivan Jukić
 *
 */

public class Lexer {

	/**
	 * Polje znakova ulaznog teksta.
	 */
	
	private char[] data; //ulazni tekst
	private Token token; //trenutni token
	private int currentIndex; //indeks prvog neobrađenog znaka
	private LexerState state = LexerState.BASIC;
	
	/**
	 * Uzima ulazni tekst te vraća polje znakova od ulaznog teksta. Metoda miče razmake na početku i kraju ulaznog teksta.
	 * 
	 * @throws NullPointerException ako je ulazni tekst jednak null
	 * @param text polje znakova ulaznog teksta
	 */
	
	public Lexer(String text) {
		if(text == null) {
			throw new NullPointerException("Vrijednost teksta ne može biti null.");
		}else {
		data = text.trim().toCharArray();
		currentIndex = 0;
		}
	}
	
	/**
	 * Metoda parsira polje ulaznog teksta sve dok parser ne dođe do znaka koji nije slovo. Metoda prihvaća izlaznu sekvencu za znakove koji nisu slova.
	 * Ako vrijedi da je LexerState = LexerState.EXTENDED onda parser prihvaća svaki znak do praznine.
	 * 
	 * @throws LexerException ako ulaz završava na '\\' ili ako izlazna sekvenca nije ispravna
	 * @return token  TokenType.WORD
	 */
	
	public Token parseWord() {
		boolean isEscaped = false;
		StringBuilder valueOfToken = new StringBuilder();
				
		while(currentIndex < data.length) {
			if(data[currentIndex] == '\\') {
				//prvi uvjet provjerava da input ne završava na //, a drugi da je ispravna izlazna sekvenca
				if(currentIndex + 1 < data.length && !Character.isLetter(data[currentIndex + 1])) {
					isEscaped = true;
					currentIndex++;
				} else {
					throw new LexerException("Krivi izlazni simbol.");
				}
			}
			//preskače svaki whitespace character i prekida parsiranje
			if(Character.isWhitespace(data[currentIndex])){
				while(Character.isWhitespace(data[currentIndex])) {
					currentIndex++;
				}
				break;
				
			//ako je izlazni znak ispred riječi uzet će svaki simbol i svaki broj, a ne samo slovo
			} else if(isEscaped) {
				valueOfToken.append(data[currentIndex]);
				currentIndex++;
			} else if(Character.isLetter(data[currentIndex]) && !isEscaped) {
				valueOfToken.append(data[currentIndex]);
				currentIndex++;				
			} else {
				break;
			}
			isEscaped = false;
		}
		token = new Token(TokenType.WORD, valueOfToken.toString());
		return token;
	}
	
	/**
	 * Metoda parsira tekst i vraća određeni broj.
	 * 
	 * @throws LexerException ako je broj prevelik za tip long
	 * @return Token tipa NUMBER
	 */
	
	public Token parseNumber() {
		StringBuilder valueOfToken = new StringBuilder();
		
		while(currentIndex < data.length) {
			//preskače svaki whitespace character
			if(Character.isWhitespace(data[currentIndex])){
				while(Character.isWhitespace(data[currentIndex])) {
					currentIndex++;
				}
				break;
			}
			
			if(Character.isDigit(data[currentIndex])) {
				valueOfToken.append(data[currentIndex]);
				currentIndex++;
			} else {
				break;
			}
		}
			try {
				token = new Token(TokenType.NUMBER, Long.parseLong(valueOfToken.toString()));
				return token;
			}catch(NumberFormatException ex) {
				throw new LexerException("Broj je prevelik za tip Long.");
			}
	}
	
	/**
	 * Metoda mijenja stanje lexera.
	 */
	
	private void switchStates() {
		
		if(state.equals(LexerState.BASIC)) {
			setState(LexerState.EXTENDED);
		} else {				
			setState(LexerState.BASIC);
		}
	}
	
	/**
	 * Metoda parsira simbol. Ako je simbole jednak # mijenja stanje.
	 * 
	 * @return token čiji je tip SYMBOL 
	 */
	
	public Token parseSymbol() {
		if(data[currentIndex] == '#') {
			switchStates();
		}
		int tempIndex = currentIndex;
		currentIndex++;
		if(currentIndex + 1 < data.length) {
			if(Character.isWhitespace(data[currentIndex])){
				while(Character.isWhitespace(data[currentIndex])) {
					currentIndex++;
				}
			}
		}
		token = new Token(TokenType.SYMBOL, data[tempIndex]);
		return token;
	}
	
	/**
	 * Metoda vraća sljedeći Token u tekstu. Ako je završeno parsiranje vraća Token EOF.
	 * 
	 * @throws LexerException ako se traži token nakon tokena kraja teksta EOF
	 * @return
	 */
	
	public Token nextToken() {
		if(state == LexerState.BASIC) {
			if(token != null && token.getType() == TokenType.EOF) {
				throw new LexerException("Došli smo do kraja.");
				
			} else if(data.length == 0 || currentIndex >= data.length) {
				token = new Token(TokenType.EOF, null);
				return token;
				
			} else if(Character.isLetter(data[currentIndex]) || data[currentIndex] == '\\') {
				return parseWord();
				
			} else if(Character.isDigit(data[currentIndex])) {
				return parseNumber();
				
			}else {
				return parseSymbol();
			}
			
		} else {
			//provjerava je li kraj i vraća zadnji token EOF
			if(token != null && token.getType() == TokenType.EOF) {
				throw new LexerException("Došli smo do kraja.");
				
			} else if(data.length == 0 || currentIndex >= data.length) {
				token = new Token(TokenType.EOF, null);
				return token;
			} 
			if(currentIndex + 1 < data.length) {
				if(Character.isWhitespace(data[currentIndex])){
					while(Character.isWhitespace(data[currentIndex])) {
						currentIndex++;
					}
				}
			}
			
			if(data[currentIndex] == '#') {
				switchStates();
				token = new Token(TokenType.SYMBOL, data[currentIndex++]);
				return token;
			}
			
			StringBuilder tokenValue = new StringBuilder();
			while(currentIndex < data.length && !Character.isWhitespace(data[currentIndex]) && data[currentIndex] != '#') {
				
				tokenValue.append(data[currentIndex]);
				currentIndex++;
			
			}
			return new Token(TokenType.WORD, tokenValue.toString());
		}
	}
	
	/**
	 * Metoda mijenja stanje lexera na određeno stanje.
	 * 
	 * @param state stanje na koje želimo staviti lexer
	 */
	
	public void setState(LexerState state) {
		if(state == null) {
			throw new NullPointerException("Stanje ne smije biti null.");
		} else {
			this.state = state;
		}
	}
	
	/**
	 * Vraća trenutni Token lexera.
	 * 
	 * @return trenutni Token lexera
	 */
	
	public Token getToken() {
		return token;
	}
}
