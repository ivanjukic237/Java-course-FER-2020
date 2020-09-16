package hr.fer.zemris.java.hw05.db;

import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.Test;

/**
 * Razred za testiranje razreda FieldValueGetters.
 * 
 * @author Ivan Jukić
 *
 */

class FieldValueGettersTest {
	
	 StudentRecord record = new StudentRecord("jmbag", "prezime", "ime", "ocjena");

	 /**
	  * Testira se dohvaćanje JMBAG-a.
	  */
	 
	@Test
	void testJMBAG() {
		assertEquals("jmbag", FieldValueGetters.JMBAG.get(record));
	}

	/**
	 * Testira se dohvaćanje prezimena.
	 */
	
	@Test
	void testLastName() {
		assertEquals("prezime", FieldValueGetters.LAST_NAME.get(record));
	}
	
	/**
	 * Testira se dohvaćanje imena.
	 */
	
	@Test
	void testName() {
		assertEquals("ime", FieldValueGetters.FIRST_NAME.get(record));
	}
	
	/**
	 * Testira se dohvaćanje ocjene.
	 */
	
	@Test
	void testFinalGrade() {
		assertEquals("ocjena", FieldValueGetters.FINAL_GRADE.get(record));
	}
}
