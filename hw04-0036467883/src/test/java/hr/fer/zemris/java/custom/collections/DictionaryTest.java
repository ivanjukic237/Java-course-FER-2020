package hr.fer.zemris.java.custom.collections;

import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.Test;

/**
 * Razred testira razred Dictionary.
 */

class DictionaryTest {

	/**
	 * Testira se je li novo kreirani stog prazan.
	 */
	
	@Test
	void testIsEmpty() {

		Dictionary<Object, Object> testDictionary = new Dictionary<>();
		
		assertTrue(testDictionary.isEmpty());
	}
	
	/**
	 * Testira vraća li metoda dobar broj elemenata stoga.
	 */
	
	@Test
	void testSize() {
		Dictionary<Object, Object> testDictionary = new Dictionary<>();
		assertEquals(testDictionary.size(), 0);
		
		testDictionary.put("a", "a");
		assertEquals(testDictionary.size(), 1);	
	}
	
	/**
	 * Testira vraća li se dobar element s metodom get.
	 */
	
	@Test
	void testGet() {
		Dictionary<Object, Object> testDictionary = new Dictionary<>();
		testDictionary.put("a", 5);

		assertEquals(testDictionary.get("a"), 5);	


	}
	
	/**
	 * Testira briše li se stog s metodom clear.
	 */
	
	@Test
	void testClear() {
		Dictionary<Object, Object> testDictionary = new Dictionary<>();
		testDictionary.put("a", "a");
		testDictionary.put("b", "b");
		testDictionary.put("c", "c");
		testDictionary.clear();
		
		assertTrue(testDictionary.isEmpty());
	}
	
	/**
	 * Testira baca li se iznimka ako se pokuša ubaciti null ključ.
	 */
	
	@Test
	void testNullKey() {
		Dictionary<Object, Object> testDictionary = new Dictionary<>();
		assertThrows(NullPointerException.class, () -> testDictionary.put(null, 2));
	}
	
	// Testiranje za različite objekte

	/**
	 * Testira se ubacivanje Stringa u kolekciju.
	 */
	
	@Test
	void testString() {
		Dictionary<String, String> testDictionary = new Dictionary<>();
		testDictionary.put("a", "b");

		assertEquals(testDictionary.get("a"), "b");
	}
	
	/**
	 * Testira se ubacivanje brojeva u kolekciju.
	 */
	
	@Test
	void testNumber() {
		Dictionary<Number, Number> testDictionary = new Dictionary<>();
		testDictionary.put(2, 3.5);
		assertEquals(testDictionary.get(2), 3.5);

	}

}
