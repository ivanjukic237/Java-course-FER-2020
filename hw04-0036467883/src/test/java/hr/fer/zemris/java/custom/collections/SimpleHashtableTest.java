package hr.fer.zemris.java.custom.collections;

import static org.junit.jupiter.api.Assertions.*;

import java.util.ConcurrentModificationException;
import java.util.Iterator;

import org.junit.jupiter.api.Test;

/**
 * Razred testira funkcionalnosti razreda SimpleHashtable. Za neke testove se koristi hash vrijednosti objekata pod
 * pretpostavkom da se koristi java 13.
 * 
 * @author Ivan Jukić
 *
 */

class SimpleHashtableTest {

	/**
	 * Metoda radi primjerak razreda i vraća ga u svrhu testiranja.
	 * 
	 * @return primjerak testnog razreda
	 */
	
	private SimpleHashtable<String, Integer> getTestHashtable() {
		SimpleHashtable<String, Integer> examMarks = new SimpleHashtable<>(2);
		examMarks.put("Ivana", 2);
		examMarks.put("Ante", 2);
		examMarks.put("Jasna", 2);
		examMarks.put("Kristina", 5);
		examMarks.put("Ivana", 5);
		return examMarks;
	}
	
	//*******************************************************************************
	// testiranje konstruktora
	//*******************************************************************************
	
	/**
	 * Testira da se baca iznimka ako se u konstruktor stavi kapacitet manji od 1.
	 */
	
	@Test
	void testCapacityLessThanOne() {
		assertThrows(IllegalArgumentException.class, () -> new SimpleHashtable<>(0));
	}

	/**
	 * Testira je li kapacitet promijenjen u potenciju broja dva u konstruktoru.
	 */
	
	@Test
	void testCapacityPowerOfTwo() {
		SimpleHashtable<Object, Object> table1 = new SimpleHashtable<>(15);
		assertEquals(16, table1.getCapacity());

		SimpleHashtable<Object, Object> table2 = new SimpleHashtable<>(255);
		assertEquals(256, table2.getCapacity());

		SimpleHashtable<Object, Object> table3 = new SimpleHashtable<>(256);
		assertEquals(256, table3.getCapacity());
	}

	/**
	 * Testira se kapacitet osnovnog konstruktora.
	 */
	
	@Test
	void testDefaultConstructorCapacity() {
		SimpleHashtable<Object, Object> table1 = new SimpleHashtable<>();
		assertEquals(16, table1.getCapacity());
	}
	
	//*******************************************************************************
	// Testiranje metoda provjere i dohvaćanja iz kolekcije
	//*******************************************************************************
	
	/**
	 * Testira se je li vrijednost ključa u kolekciji.
	 */
	
	@Test
	void testContainsKey() {
		SimpleHashtable<String, Integer> examMarks = getTestHashtable();
		assertTrue(examMarks.containsKey("Ivana"));
		assertFalse(examMarks.containsKey("abc"));
	}
	
	/**
	 * Testira se je li vrijednost u kolekciji.
	 */
	
	@Test
	void testContainsValue() {
		SimpleHashtable<String, Integer> examMarks = getTestHashtable();
		assertTrue(examMarks.containsValue(2));
		assertFalse(examMarks.containsValue(-15));
	}
	
	/**
	 * Testira vraćanje dobre vrijednosti za ključ.
	 */
	
	@Test
	void testGet() {
		SimpleHashtable<String, Integer> examMarks = getTestHashtable();
		assertEquals(2, examMarks.get("Ante"));
	}
	
	//*******************************************************************************
	// Testiranje metoda promjene kolekcije
	//*******************************************************************************
	
	/**
	 * Testira se stavljanja objekata u kolekciju.
	 */
	
	@Test
	void testPutNull() {
		SimpleHashtable<Object, Object> table1 = new SimpleHashtable<>();
		assertThrows(NullPointerException.class, () -> table1.put(null, "value"));
	}
	
	/**
	 * Testira se povećavanje kapaciteta kolekcije.
	 */
	
	@Test
	void testIncreaseCapacity() {
		SimpleHashtable<Object, Object> table1 = new SimpleHashtable<>(2);
		table1.put("Ivana", 2);
		table1.put("Ante", 2);
		
		assertEquals(4, table1.getCapacity());

	}
	
	/**
	 * Testira se stavljanje i dobavljanje elemenata kolekcije.
	 */
	
	@Test
	void testPutAndGetObject() {
		SimpleHashtable<Object, Object> table1 = new SimpleHashtable<>();
		table1.put("TestKey", "TestValue");
		
		assertEquals("TestValue", table1.get("TestKey"));
	}

	/**
	 * Testira se brisanje iz kolekcije.
	 */
	
	@Test 
	void testRemove() {
		SimpleHashtable<String, Integer> testTable = getTestHashtable();
		testTable.remove("Ivana");
		
		assertFalse(testTable.containsKey("Ivana"));	
	}
	
	/**
	 * Testira se metoda clear koja briše sve elemente iz kolekcije.
	 */
	
	@Test
	void testClear() {
		SimpleHashtable<String, Integer> testTable = getTestHashtable();
		testTable.clear();
		assertFalse(testTable.containsKey("Ivana"));
		assertFalse(testTable.containsKey("Ante"));
		assertFalse(testTable.containsKey("Jasna"));
		assertFalse(testTable.containsKey("Kristina"));
		
		assertEquals(0, testTable.size());

	}
	
	
	//*******************************************************************************
	// Testiranje iteratora (primjeri iz zadaće), koriste se hash vrijednosti jave 13
	//*******************************************************************************

	/**
	 * Testira se for-each petlja s iteratorom.
	 */
	
	@Test
	void testForEach() {
		SimpleHashtable<String, Integer> examMarks = getTestHashtable();

		int listOfValues[] = { 2, 5, 2, 5 };
		String listOfKeys[] = { "Ante", "Ivana", "Jasna", "Kristina" };
		int counter = 0;

		for (SimpleHashtable.TableEntry<String, Integer> pair : examMarks) {
			assertEquals(listOfValues[counter], pair.getValue());
			assertEquals(listOfKeys[counter], pair.getKey());

			counter++;
		}

	}

	/**
	 * Testira se korištenje dva iteratora u isto vrijeme.
	 */
	
	@Test
	void testForEachTwoIterators() {
		SimpleHashtable<String, Integer> examMarks = getTestHashtable();

		int listOfValues[] = { 2, 5, 2, 5 };
		String listOfKeys[] = { "Ante", "Ivana", "Jasna", "Kristina" };
		int counterOuter = 0;
		int counterInner = 0;
		for (SimpleHashtable.TableEntry<String, Integer> pair1 : examMarks) {
			for (SimpleHashtable.TableEntry<String, Integer> pair2 : examMarks) {
				assertEquals(listOfValues[counterOuter], pair1.getValue());
				assertEquals(listOfKeys[counterOuter], pair1.getKey());
				assertEquals(listOfValues[counterInner], pair2.getValue());
				assertEquals(listOfKeys[counterInner], pair2.getKey());
				counterInner++;
			}
			counterInner = 0;
			counterOuter++;
		}
	}

	/**
	 * Testira se brisanje u iteratoru.
	 */
	
	@Test
	void testRemoveInIterator() {
		SimpleHashtable<String, Integer> examMarks = getTestHashtable();

		Iterator<SimpleHashtable.TableEntry<String, Integer>> iter = examMarks.iterator();
		while (iter.hasNext()) {
			SimpleHashtable.TableEntry<String, Integer> pair = iter.next();
			if (pair.getKey().equals("Ivana")) {
				iter.remove(); // sam iterator kontrolirano uklanja trenutni element
			}
		}
		assertFalse(examMarks.containsKey("Ivana"));

	}

	/**
	 * Testira da se baca IllegalStateException ako se na isti element pozove 2 puta remove()
	 */

	@Test
	void testMoreRemovesInIterator() {
		SimpleHashtable<String, Integer> examMarks = getTestHashtable();

		Iterator<SimpleHashtable.TableEntry<String, Integer>> iter = examMarks.iterator();
		while (iter.hasNext()) {
			SimpleHashtable.TableEntry<String, Integer> pair = iter.next();
			if (pair.getKey().equals("Ivana")) {
				iter.remove();
				assertThrows(IllegalStateException.class, () -> iter.remove());
			}
		}
	}

	/**
	 * Provjerava baca li se iznimka ako se kolekcija mijenja izvan iteratora.
	 */
	
	// assert ne radi u if-u iz nekog razloga
	@Test
	void testOutsideModification() {
		SimpleHashtable<String, Integer> examMarks = getTestHashtable();

		Iterator<SimpleHashtable.TableEntry<String, Integer>> iter = examMarks.iterator();
		boolean hasNext = iter.hasNext();

		while (hasNext) {
			SimpleHashtable.TableEntry<String, Integer> pair = iter.next();
			if (pair.getKey().equals("Ivana")) {
				examMarks.remove("Ivana");
				break;
			}
			hasNext = iter.hasNext();
		}
		assertThrows(ConcurrentModificationException.class, () ->  iter.hasNext());
	}
	
	/**
	 * Provjerava se je li iterator obrisao sve elemente iz kolekcije.
	 */
	
	@Test
	void iteratorRemovesAllElements() {
		SimpleHashtable<String, Integer> examMarks = getTestHashtable();

		Iterator<SimpleHashtable.TableEntry<String,Integer>> iter = examMarks.iterator();
		while(iter.hasNext()) {
		iter.next();
		iter.remove();
		}
		
		assertEquals(0, examMarks.size());
	}
}
