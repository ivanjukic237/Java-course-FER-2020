package hr.fer.zemris.java.hw05.db;

import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.Test;

/**
 * Testira se razred QueryParser.
 * 
 * @author Ivan Jukić
 *
 */

class QueryParserTest {

	/**
	 * Testira se da je lista prazna ako je query prazan.
	 */
	
	@Test
	void testEmptyString() {
		QueryParser parser = new QueryParser("");
		assertEquals(0, parser.getQuery().size());
	}
	
	/**
	 * Testira se da se baca iznimka ako se koristi ilegalna varijabla.
	 */
	
	@Test
	void testFirstArgumentIllegalVariable() {
		assertThrows(IllegalArgumentException.class, () -> new QueryParser("wrong variable"));
	}
	
	/**
	 * Testira da se baca iznimka ako prvi argument nije varijabla.
	 */
	
	@Test
	void testFirstArumentNotVariable() {
		assertThrows(IllegalArgumentException.class, () -> new QueryParser("\"some text\""));
	}
	
	/**
	 * Testira se query s jednim uvjetom.
	 */
	
	@Test
	void testOperator() {
		QueryParser parser = new QueryParser("lastname like \"abc\"");
		assertEquals(1, parser.getQuery().size());
	}
	
	/**
	 * Testira da se baca iznimka ako je ilegalan operator.
	 */
	
	@Test
	void testIllegalOperator() {
		assertThrows(IllegalArgumentException.class, () -> new QueryParser("lastname * \"abc\""));
	}

	/**
	 * Testira da se baca iznimka ako imamo varijablu i operator bez vrijednosti.
	 */
	
	@Test
	void testNoValue() {
		assertThrows(IllegalArgumentException.class, () -> new QueryParser("lastname ="));
	}
	
	/**
	 * Testira se da lista ima 2 člana ako imamo 2 uvjeta.
	 */
	
	@Test
	void testMoreThanOneExpression() {
		QueryParser parser = new QueryParser("lastname like \"abc\" and jmbag like \"a*b\"");
		assertEquals(2, parser.getQuery().size());
	}
	
	QueryParser qp1 = new QueryParser(" jmbag =\"0123456789\" ");
	
	/**
	 * Testira se je li query direktan.
	 */
	
	@Test
	void testIsDirectQuery() {
		
		assertTrue(qp1.isDirectQuery()); // true
	}
	
	/**
	 * Testira se vraćanje jmbaga za direktan query.
	 */
	
	@Test
	void testGetQueriedJmbag() {
		
		assertEquals("0123456789", qp1.getQueriedJMBAG()); 
	}
	
	/**
	 * Testira se da lista ima samo jedan član za direktan query.
	 */
	
	@Test
	void testSize() {
		
		assertEquals(1, qp1.getQuery().size()); 
	}
		
	QueryParser qp2 = new QueryParser("jmbag=\"0123456789\" and lastName>\"J\"");
	
	/**
	 * Testira se da metoda vraća false ako nije direktan query.
	 */
	
	@Test
	void testIsNotDirectQuery() {
		
		assertFalse(qp2.isDirectQuery()); 
	}
	
	/**
	 * Testira se da se baci iznimka ako se pokuša dobiti jmbag za query koji nije
	 * direktan.
	 */
	
	@Test
	void testGetQueriedJmbagThrows() {
		
		assertThrows(IllegalStateException.class,() -> qp2.getQueriedJMBAG()); 
	}
	
}
