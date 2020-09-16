package hr.fer.zemris.java.custom.scripting.lexer;

import static org.junit.jupiter.api.Assertions.*;
import org.junit.jupiter.api.Test;

import hr.fer.zemris.java.custom.scripting.parser.SmartScriptParserException;

import java.io.IOException;
import java.io.InputStream;
import java.nio.charset.StandardCharsets;

/**
 * Testovi za razred hr.fer.zemris.java.custom.scripting.lexer.Lexer.
 *
 * @author Ivan Jukić
 */

class LexerTest {
	
	/**
	 * Testiramo da će se dobar function Token vratiti
	 */
	@Test
	public void functionToken() {	
		
		Lexer lexer = new Lexer("{$= @abc_d2$}");
		
		assertEquals(lexer.nextToken().type, TokenType.StartOfTag);
		assertEquals(lexer.nextToken().type, TokenType.EQUALS);
		assertEquals(lexer.nextToken().type, TokenType.FUNCTION);
	}
	
	/**
	 * Testiramo da će se program srušiti ako nakon @ bude broj
	 */
	@Test
	public void illegalFunctionName() {	
		
		Lexer lexer = new Lexer("{$= @2abc_d2$}");
		lexer.nextToken();
		lexer.nextToken();
		assertThrows(LexerException.class, () -> lexer.nextToken());
		
	}
	// Sljedećih 9 testova uzima kao argument dokumente iz src/test/resources/extra
	/**
	 *  Testiramo da će lexer vratiti samo jedan text Token
	 */
	@Test
	public void oneTextToken() {
		String text = readExample(1);
		Lexer lexer = new Lexer(text);
		assertEquals(lexer.nextToken().type, TokenType.TEXT);
		assertEquals(lexer.nextToken().type, TokenType.EOF);
	}
	
	/**
	 *  Testiramo da radi escape (\) za {
	 */
	@Test
	public void escapeForBracket() {
		String text = readExample(2);
		Lexer lexer = new Lexer(text);
		assertEquals(lexer.nextToken().type, TokenType.TEXT);
		assertEquals(lexer.nextToken().type, TokenType.EOF);

	}
	
	/**
	 *  Testiramo da radi escape (\) za { i \
	 */

	@Test
	public void escapeForBracketAndBackslash() {
		String text = readExample(3);
		Lexer lexer = new Lexer(text);
		assertEquals(lexer.nextToken().type, TokenType.TEXT);
		assertEquals(lexer.nextToken().type, TokenType.EOF);

	}
	
	/**
	 *  Provjeravamo da se tekst ruši ako ima ilegalan escape
	 */
	@Test
	public void illegaleEscape1() {
		String text = readExample(4);

		assertThrows(LexerException.class, () -> new Lexer(text).nextToken());


	}
	
	/**
	 *  Provjeravamo da se tekst ruši ako ima ilegalan escape
	 */
	@Test
	public void illegalEscape2() {
		String text = readExample(5);

		assertThrows(LexerException.class, () -> new Lexer(text).nextToken());
	}
	
	/**
	 * Provjeravamo da se pogrešno otvaranje taga vodi kao tekst
	 */
	@Test
	public void wrongTagStartGivesText() {
		String text = readExample(6);
		Lexer lexer = new Lexer(text);
		assertEquals(lexer.nextToken().type, TokenType.TEXT);
		assertEquals(lexer.nextToken().type, TokenType.EOF);

	}
	
	/**
	 * Provjerava da se primjer mora srušiti jer imamo ilegalan "\\n" u tekstu (po pravilu možemo imati samo "\\\\" i "\\{")
	 */
	@Test
	// 
	public void illegalEscapeInText() {
		String text = readExample(7);
		
		assertThrows(LexerException.class, () -> new Lexer(text).nextToken());
		


	}
	/**
	 *Provjerava još jednom escape za { u tekstu
	 * */
	@Test
	public void escapeBracketInText() {
		String text = readExample(8);
		Lexer lexer = new Lexer(text);
		assertEquals(lexer.nextToken().type, TokenType.TEXT);
		assertEquals(lexer.nextToken().type, TokenType.EOF);

	}
	
	/**
	 *Provjerava da se ruši ako imamo ilegalni escape u tagu 
	 */
	@Test
	public void illegalEscapeInTag() {
		String text = readExample(9);
		assertThrows(LexerException.class, () -> new Lexer(text).nextToken());

	}
	
	/**
	 * Metoda čita testne datoteke i vraća njihovu String reprezentaciju.
	 * 
	 * @throws IOException ako se dogodi greška pri čitanju datoteke
	 * @param n broj testnog dokumenta koji se čita
	 * @return String reprezentaciju testnih dokumenata
	 */
	
	private String readExample(int n) {
		  try(InputStream is = this.getClass().getClassLoader().getResourceAsStream("extra/primjer"+n+".txt")) {
		    if(is==null) throw new RuntimeException("Datoteka extra/primjer"+n+".txt je nedostupna.");
			    byte[] data = this.getClass().getClassLoader().getResourceAsStream("extra/primjer"+n+".txt").readAllBytes();
			    String text = new String(data, StandardCharsets.UTF_8);
			    return text;
		  } catch(IOException ex) {
			  throw new RuntimeException("Greška pri čitanju datoteke.", ex);
		  }
		}
	

}
