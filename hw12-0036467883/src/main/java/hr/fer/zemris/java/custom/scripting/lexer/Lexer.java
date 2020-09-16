package hr.fer.zemris.java.custom.scripting.lexer;

import hr.fer.zemris.java.custom.scripting.demo.SmartScriptEngineDemo1;

/**
 * Jednostavan lexer koji prolazi kroz tekst i vraća definirane Tokene. Lexer ima dva stanja: TAG i TEXT.
 * Ako lexer u parsiranju dođe do niza znakova "{$" onda se prebaci u stanje TAG gje vraća svaki pojedini član
 * taga u obliku Tokena. Inače je lexer u stanju TEXT i parsira sve znakove i vraća ih kao Token TEXT. 
 * 
 * @author Ivan Jukić
 *
 */

public class Lexer {
	
	/**
	 * Char polje koje sadrži simbole ulaznog teksta.
	 */
	private char[] data;
	
	private int currentIndex;
	private Token token;
	private LexerState state = LexerState.TEXT;
	
	/**
	 * Konstruktor koji ulazni tekst pretvara u char polje.
	 * 
	 * @throws NullPointerException ako je ulazni tekst null
	 * @param text ulazni tekst koji lexer analizira
	 */
	
	public Lexer(String text) {
		if(text == null) {
			throw new NullPointerException("Ulazni tekst ne može biti null.");
		} else {
			data = text.strip().toCharArray();
			currentIndex = 0;
		}
	}
	
	/**
	 * Metoda vraća sljedeći Token u nizu. Vrste tokena su definirane u enum razredu TokenType. 
	 * Ako naiđe na prazninu, a u stanju je TAG, preskoči sve praznine i navodnike i ponovno pokreće nextToken() metodu.
	 * Ako je naiđe na niz znakova "{$" prebacuje stanje lexera u stanje TAG i vraća Token StartOfTag, a inače djeluje
	 * u stanju TEXT. Ako je naiđe na niz znakova "$}" prebacuje stanje lexera u stanje TAG i vraća Token EndOfTag.
	 * Kada više nema Tokena za vratiti, vraća Token EOF.
	 * 
	 * @throws LexerException ako se traži token nakon tokena kraja teksta EOF
	 * @throws LexerException ako se pokušava otvoriti tag prije nego što je prošli tag zatvoren
	 * @throws LexerException ako se pokuša zatvoriti tag a nijedan tag nije otvoren
	 * @return vraća određeni Token
	 */
	
	public Token nextToken() {
		if(token != null && token.getType() == TokenType.EOF) {
			throw new LexerException("Došli smo do kraja.");
			
		} else if(data.length == 0 || currentIndex >= data.length) {
			token = new Token(TokenType.EOF, null);
			return token;
			
		} else if(data[currentIndex] == '{' && data[currentIndex + 1] == '$') {
			if(this.state == LexerState.TAG) {
				throw new LexerException("Pokušava se otvoriti tag prije nego što je prošli tag zatvoren.");
			} else {
				this.state = LexerState.TAG;
				currentIndex += 2;
				return new Token(TokenType.StartOfTag, "start");
			}
			
		} else if(data[currentIndex] == '$' && data[currentIndex + 1] == '}') {
			if(this.state == LexerState.TEXT) {
				throw new LexerException("Ne može se zatvoriti tag koji nije otvoren.");
			} else {
				this.state = LexerState.TEXT;
				currentIndex += 2;
				return new Token(TokenType.EndOfTag, "end");
			}
		} else if(Character.isWhitespace(data[currentIndex])) {
			skipSpaces();
			return nextToken();
		} else if(data[currentIndex] == '"') {
			currentIndex++;
			StringBuilder sb = new StringBuilder();
			while(data[currentIndex] != '"') {
				sb.append(data[currentIndex]);
				currentIndex++;
				if(currentIndex >= data.length) {
					throw new LexerException("Quotes were not closed.");
				}
			}
			currentIndex++;
			return new Token(TokenType.TEXT, sb.toString());
		}
		
		else if(data[currentIndex] == ' ') {
			skipSpaces();
			return nextToken();
		} else {
			return parseText();
		}
		
	}
	
	/**
	 * Metoda parsira tekst u dva stanja. Ako je u TEXT stanju onda parsira sve znakove i vraća Token tipa TEXT. 
	 * Ako je u stanju TAG onda vraća Tokene za svaki član taga. 
	 * 
	 * Za stanje TEXT vrijede i escape znak '\' koji vrijedi samo za znakove '\' i '{', a za stanje TAG vrijedi
	 * samo za znakove '\' i '"'. Nalaženje Tokena u tekstu ne ovisi o velikom i malom slovu.
	 * 
	 * U stanju TAG imamo Tokene: FOR - ako je prva riječ u tagu jednaka riječi for; END - ako je jedina riječ u tagu
	 * jednaka riječi end; EQUALS - ako je prvi znak u tagu jednak '='; VARIABLE - ako naiđe na tekst koji počinje sa
	 * slovom, a može imati i brojeve i znak '_'; FUNCTION - ima formu da je prvi znak @ pa onda slova, brojevi ili znak
	 * '_'. Ne prihvaća da je prvi znak nakon @ jednak praznini ili da je broj; DOUBLE - vraća broj koji predstavlja Double;
	 * INTEGER - vraća broj koji predstavlja Integer. Za DOUBLE i INTEGER prihvaća i prikaz s navodnicima npr. "-2.0" ili "5".
	 * 
	 * @throws LexerException ako izrazi nisu napisani kako je definirano
	 * @return parsirani token određenog tipa TokenType
	 */
	
	public Token parseText() {
		StringBuilder currentText = new StringBuilder();
		//TEXT state
		if(state.equals(LexerState.TEXT)) {
			 
			while(currentIndex < data.length) {
				
				if(data[currentIndex] == '\\' && (data[currentIndex + 1] == '\\' || data[currentIndex + 1] == '{')) {
					currentText.append(data[currentIndex + 1]);
					currentIndex += 2;
				} else if(data[currentIndex] == '\\' && (data[currentIndex + 1] != '\\' && data[currentIndex + 1] != '{')) {
					throw new LexerException("Izlazna sekvenca ne vrijedi za " + data[currentIndex + 1]);
				} else if(data[currentIndex] == '{' && data[currentIndex + 1] == '$') {
					break;
				} else {
					currentText.append(data[currentIndex]);
					currentIndex++;	
				}
			}
			token = new Token(TokenType.TEXT, currentText.toString());
			return token;
			//TAG state
		} else {
			if(Character.isAlphabetic(data[currentIndex]) || data[currentIndex] == '\\') {
			while(currentIndex < data.length && (Character.isAlphabetic(data[currentIndex]) || Character.isDigit((data[currentIndex])) || data[currentIndex] == '_' || data[currentIndex] == '\\')) {
				// ako je iza izlaznog simbola broj onda prekida parsiranje
				if(data[currentIndex] == '\\' && (data[currentIndex + 1] == '\\' || data[currentIndex + 1] == '"') && Character.isDigit(data[currentIndex + 1])){
					break;
				// ako je iza izlaznog simbola \ ili " onda ga dodaje u riječ, inače vraća iznimku
				} else if(data[currentIndex] == '\\' && (data[currentIndex + 1] == '\\' || data[currentIndex + 1] == '"')) {
					currentText.append(data[currentIndex]);
					currentIndex += 2;
				} else if(data[currentIndex] == '\\' && (data[currentIndex + 1] != '\\' || data[currentIndex] != '"')) {
					throw new LexerException("Kriva izlazna sekvenca.");
				} else {
					currentText.append(data[currentIndex]);
					currentIndex++;
				}
			}
			String word = currentText.toString();
			
			if(word.toLowerCase().equals("for")){
				token = new Token(TokenType.FOR, word);
				return token;
			} else if(word.toLowerCase().equals("end")) {
				token = new Token(TokenType.END, word);
				return token;
			} else {
				token = new Token(TokenType.VARIABLE, word);
				return token;
			}
			
		} else if(Character.isDigit((data[currentIndex])) || data[currentIndex] == '-' && Character.isDigit(data[currentIndex + 1])){
			if(data[currentIndex] == '"') {
				currentIndex++;
			}
			//ako je minus prvi simbol
			if(data[currentIndex] == '-') {
				currentText.append("-");
				currentIndex++;
			}
			while(Character.isDigit((data[currentIndex])) || data[currentIndex] == '.') {
				currentText.append(data[currentIndex]);
				currentIndex++;
			}
			String stringOfNumber = currentText.toString();
			
			if(stringOfNumber.contains(".")) {
				try {
					token = new Token(TokenType.DOUBLE, Double.parseDouble(stringOfNumber));
					return token;
				} catch(IllegalArgumentException ex) {
					System.out.println("Broj nije Double.");
				}
			} else {
				try {
					token = new Token(TokenType.INTEGER, Integer.parseInt(stringOfNumber));
					return token;
				} catch(IllegalArgumentException ex) {
					System.out.println("Broj nije Integer.");
				}
			}
		} 
		
		if(data[currentIndex] == '@' && Character.isAlphabetic(data[currentIndex + 1])) {
			currentText.append(data[currentIndex]);
			currentIndex++;
			while( Character.isAlphabetic(data[currentIndex]) || Character.isDigit((data[currentIndex])) || data[currentIndex] == '_') {
				currentText.append(data[currentIndex]);
				currentIndex++;
				if(currentIndex >= data.length) {
					break;
				}
			}
			
			token = new Token(TokenType.FUNCTION, currentText.toString());
			return token;
			
		} else if(data[currentIndex] == '@' && (Character.isWhitespace(data[currentIndex + 1]) || Character.isDigit(data[currentIndex + 1]))) {
			throw new LexerException("Funkcija nije ispravno napisana");
		}
		
		if(!Character.isAlphabetic(data[currentIndex]) && !Character.isDigit((data[currentIndex]))) {
			char currentSymbol = data[currentIndex];
			if(data[currentIndex] == '=') {
				currentIndex++;
				token = new Token(TokenType.EQUALS, currentSymbol + "");
				return token;
			} else if(data[currentIndex] == '*' || data[currentIndex] == '/' || data[currentIndex] == '+' || data[currentIndex] == '-' || data[currentIndex] == '^') {
				currentIndex++;
				token = new Token(TokenType.OPERATOR, currentSymbol + "");
				return token;
			}
				
		}
		throw new LexerException("Izraz nije dobro napisan.");
			
		}
	}
	
	/**
	 * Metoda preskače praznine i navodnike u tekstu. Koristi se samo ako navodnik nije escapean i samo u TAG stanju.
	 */
	
	private void skipSpaces() {
		while(Character.isWhitespace(data[currentIndex])) {
			currentIndex++;
		}
	}
	public static void main(String[] args) {
		String documentBody = SmartScriptEngineDemo1.read("src\\main\\resources\\fibonacci.smscr");
		Lexer l = new Lexer(documentBody);
		/*for(int i = 0; i < l.data.length; i++) {
			System.out.print(l.data[i]);
		}*/
		for(int i = 0; i < 100; i++) {
			System.out.println(l.nextToken());

		}
		
		
	}
}
