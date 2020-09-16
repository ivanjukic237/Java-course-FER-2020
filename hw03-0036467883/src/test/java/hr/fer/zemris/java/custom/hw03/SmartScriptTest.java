package hr.fer.zemris.java.custom.hw03;

import static org.junit.jupiter.api.Assertions.*;
import org.junit.jupiter.api.Test;

import hr.fer.zemris.java.custom.collections.EmptyStackException;
import hr.fer.zemris.java.custom.scripting.lexer.Lexer;
import hr.fer.zemris.java.custom.scripting.lexer.LexerException;
import hr.fer.zemris.java.custom.scripting.nodes.DocumentNode;
import hr.fer.zemris.java.custom.scripting.parser.SmartScriptParser;
import hr.fer.zemris.java.custom.scripting.parser.SmartScriptParserException;

import java.io.IOException;
import java.io.InputStream;
import java.nio.charset.StandardCharsets;

/**
 * Testira se razred hr.fer.zemris.java.custom.scripting.parser.SmartScriptParser.
 * 
 * @author Ivan Jukić
 *
 */
class SmartScriptTest {

	/**
	 * Metoda čita testne datoteke i vraća njihovu String reprezentaciju.
	 * 
	 * @throws IOException ako se dogodi greška pri čitanju datoteke
	 * @param n broj testnog dokumenta koji se čita
	 * @return String reprezentaciju testnih dokumenata
	 */
	
	private String readExample(int n) {
		String path = "SmartScriptParserTestDocuments/doc"+n+".txt";
		  try(InputStream is = this.getClass().getClassLoader().getResourceAsStream(path)) {
		    if(is==null) throw new RuntimeException("Datoteka extra/primjer"+n+".txt je nedostupna.");
			    byte[] data = this.getClass().getClassLoader().getResourceAsStream(path).readAllBytes();
			    String text = new String(data, StandardCharsets.UTF_8);
			    return text;
		  } catch(IOException ex) {
			  throw new RuntimeException("Greška pri čitanju datoteke.", ex);
		  }
		}
	
	/**
	 * Provjerava hoće li SmartScriptParser vratiti jednako stablo kao originalno. 
	 * 
	 * @param docBody ulazni tekst iz testnih dokumenata
	 * @return true ako su stabla ulaznog dokumenta i procesiranog dokumenta jednaka
	 */
	
	private boolean testIfSame(String docBody) {

		SmartScriptParser parser = new SmartScriptParser(docBody);
		DocumentNode document = parser.getDocumentNode();
		
		String originalDocumentBody = document.toString();
		
		SmartScriptParser parser2 = new SmartScriptParser(originalDocumentBody);
		
		DocumentNode document2 = parser2.getDocumentNode();
		// now document and document2 should be structurally identical trees

		return document.equals(document2); // ==> "same" must be true
	}
	
	/**
	 * Provjerava vraća li se dobro stablo za dokument koji se sastoji od for petlje, funkcije i teksta.
	 */
	@Test
	public void testForFunctionText() {
		assertTrue(testIfSame(readExample(1)));

	}
	
	/**
	 * Provjerava se samo za jedan tekst Token
	 */
	@Test
	public void testOneTextToken() {
		
		assertTrue(testIfSame(readExample(2)));

	}
	
	/**
	 * Provjerava se za tekst i funkciju.
	 */
	
	@Test
	public void testTextFunction() {
		
		assertTrue(testIfSame(readExample(3)));

	}
	
	// Testiranje da for petlje daju dobar izraz na kraju. Koriste se doc4.txt-doc6.txt
	
	/**
	 *  Prazno tijelo for petlje
	 */
	@Test
	public void emptyBodyForLoop() {
		assertTrue(testIfSame(readExample(4)));

	}
	
	/**
	 *  Tijelo for petlje je tekst i funkcija, numerički tokeni imaju navodnike
	 */
	@Test
	public void functionTextInBodyForLoop() {
		assertTrue(testIfSame(readExample(5)));

	}
	
	/**
	 *  prvi i treći token su varijable i stepExpression ne postoji
	 */
	@Test
	public void noStepExpressionForLoop() {
		assertTrue(testIfSame(readExample(6)));

	}
	
	/**
	 *  Nedovoljan broj Tokena u for petlji
	 */
	@Test
	public void testNotEnoughVariablesInForLoop() {
		assertThrows(SmartScriptParserException.class,() -> new SmartScriptParser(readExample(7)));
	}
	
	/**
	 *  Previše Tokena u petlji
	 */
	@Test
	public void testTooManyVariablesInForLoop() {
		assertThrows(SmartScriptParserException.class,() -> new SmartScriptParser(readExample(8)));
	}
	
	/**
	 *  Prvi Token nije tipa VARIABLE
	 */
	@Test
	public void firstNotVariableInForLoop() {
		assertThrows(SmartScriptParserException.class,() -> new SmartScriptParser(readExample(9)));
	}
	
	/**
	 *  Petlja nije zatvorena
	 */
	@Test
	public void forLoopNotClosed() {
		assertThrows(SmartScriptParserException.class,() -> new SmartScriptParser(readExample(10)));

	}
	
	/**
	 *  Previše end tokena
	 */
	@Test
	public void tooManyEndTokens() {
		assertThrows(EmptyStackException.class,() -> new SmartScriptParser(readExample(11)));

	}
}