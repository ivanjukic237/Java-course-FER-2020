package hr.fer.zemris.java.custom.collections;

import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.Test;

/**
 * JUnit aplikacija koja testira razred LinkedListIndexedCollection.
 * 
 * @author Ivan Jukić
 */

class LinkedListIndexedCollectionTest {
	
	/**
	 * Testira kopira li se dobro kolekcija pomoću konstruktora.
	 */
	
	@Test
	void testConstructorCopyCollection() {
		//Provjera za ArrayIndexedCollection.
		ArrayIndexedCollection<String> testCollection = new ArrayIndexedCollection<>();
		testCollection.add("first");
		testCollection.add("second");
		testCollection.add("third");
		
		assertEquals(new LinkedListIndexedCollection<String>(testCollection).toString(), testCollection.toString());
		
		//Provjera za LinkedListIndexedCollection.
		LinkedListIndexedCollection<String> linkedCollection = new LinkedListIndexedCollection<>();
		linkedCollection.add("firstLinked");
		linkedCollection.add("secondLinked");
		linkedCollection.add("thirdLinked");
		
		assertEquals(new LinkedListIndexedCollection<String>(linkedCollection).toString(), linkedCollection.toString());

	}
	
	/**
	 * Testira dodavanje null u listu.
	 */
	
	@Test
	void testAddingNull() {
		assertThrows(NullPointerException.class, () -> new LinkedListIndexedCollection<Object>().add(null));
	}
	
	/**
	 * Testira dodavanje par objekata u listu.
	 */
	
	@Test
	void testAddingSomeObjects() {
		LinkedListIndexedCollection<Object> testCollection = new LinkedListIndexedCollection<>();
		testCollection.add("first");
		testCollection.add("second");
		testCollection.add(5);
		
		assertEquals(testCollection.toString(), "[first, second, 5]");
	}
	
	/**
	 * Testira dobavljanje objekata iz liste.
	 */
	
	@Test
	void testGet() {
		LinkedListIndexedCollection<Object> testCollection = new LinkedListIndexedCollection<>();
		testCollection.add("abc");
		testCollection.add(2);
		testCollection.add('a');
		assertEquals(testCollection.get(0), "abc");
		assertEquals(testCollection.get(1), 2);
		assertEquals(testCollection.get(2), 'a');
	}
	
	/**
	 * Testira da se vraća iznimka ako se pokuša dobaviti objekt liste na poziciji 
	 * koja je manja od nula ili veća od size-1
	 */
	
	@Test
	void testGetOutOfBounds() {
		LinkedListIndexedCollection<Object> testCollection = new LinkedListIndexedCollection<Object>();
		assertThrows(IndexOutOfBoundsException.class, () -> testCollection.get(-1));
		assertThrows(IndexOutOfBoundsException.class, () -> testCollection.get(testCollection.size()));
	}
	
	/**
	 * Testira vraća li se dobar index za određene elemente.
	 */
	
	@Test
	void testIndexOf() {
		LinkedListIndexedCollection<Object> testCollection = new LinkedListIndexedCollection<>();
		testCollection.add("abc");
		testCollection.add(2);
		testCollection.add('a');
		testCollection.add("abc");
		
		assertEquals(testCollection.indexOf("abc"), 0);
		assertEquals(testCollection.indexOf(2), 1);
		assertEquals(testCollection.indexOf('a'), 2);
		
		assertEquals(testCollection.indexOf("nothing"), -1);
		assertEquals(testCollection.indexOf(null), -1);
	}
	
	/**
	 * Testira da se vraća iznimka ako se pokuša izbrisati objekt na 
	 * poziciji manjoj od nula i većoj od size - 1
	 */
	
	@Test
	void testInvalidRemoveIndex() {
		assertThrows(IndexOutOfBoundsException.class, () ->  new LinkedListIndexedCollection<Object>().remove(-1));
		assertThrows(IndexOutOfBoundsException.class, () ->  new LinkedListIndexedCollection<Object>().remove(1));

	}
	
	/**
	 * Testira da se vraća točan boolean za to je li objekt u listi.
	 */
	
	@Test
	void testContains() {
		LinkedListIndexedCollection<Object> testCollection = new LinkedListIndexedCollection<>();
		testCollection.add("abc");
		testCollection.add(2);
		
		assertTrue(testCollection.contains(2));
		assertTrue(testCollection.contains("abc"));
		assertFalse(testCollection.contains(12));
	}
	
	/**
	 * Testira da se vraća dobro polje liste.
	 */
	
	@Test
	void testToArray() {
		LinkedListIndexedCollection<Object> testCollection = new LinkedListIndexedCollection<>();
		testCollection.add("abc");
		testCollection.add(2);
		Object[] testArray = testCollection.toArray();
		Object[] intendedArray = new Object[] {"abc", 2};
		for(int i = 0; i < testArray.length; i++) {
			assertEquals(testArray[i], intendedArray[i]);
		}
	}	
	
	/**
	 * Testira se brisanje elementa iz liste.
	 */
	
	@Test
	void testRemoveObject() {
		ArrayIndexedCollection<Object> testCollection = new ArrayIndexedCollection<>();
		
		testCollection.add("abc");
		testCollection.add(2);
		testCollection.add("abc");
		testCollection.add("abcd");

		//testira se da se briše prvi primjer objekta
		testCollection.remove("abc");
		assertEquals(testCollection.get(0), 2);
		assertEquals(testCollection.get(1), "abc");
		
		//testira se da se vraća true ako je obrisan i false ako nije obrisan element
		assertTrue(testCollection.remove("abc"));
		assertFalse(testCollection.remove("abcdef"));
		assertFalse(testCollection.remove(null));
		
	}
}
