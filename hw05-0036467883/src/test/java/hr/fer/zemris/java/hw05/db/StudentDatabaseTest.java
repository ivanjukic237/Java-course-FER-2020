package hr.fer.zemris.java.hw05.db;

import static org.junit.jupiter.api.Assertions.*;

import java.util.List;

import org.junit.jupiter.api.Test;

/**
 * Testovi za razred StudentDatabase.
 * 
 * @author Ivan Jukić
 */

class StudentDatabaseTest {
	/**
	 * Testna baza s par inputa.
	 */
	String testInput1 = "0000000001	Akšamović	Marin	2";
	String testInput2 = "0000000005	Brezović	Jusufadis	2";
	String testInput3 = "0000000049	Saratlija	Branimir	2";
	String[] studentRecords = {testInput1, testInput2, testInput3};
	
	StudentDatabase dataBase = new StudentDatabase(studentRecords);
	
	/**
	 * Testira se vadi li se dobar JMBAG iz baze.
	 */
	
	@Test
	void testForJmbag() {
		
		
		assertEquals(null, dataBase.forJMBAG("22"));
		assertEquals(new StudentRecord("0000000001", "Akšamović", "Marin", "2"), dataBase.forJMBAG("0000000001"));
		
	}
	
	/**
	 * Razred predstavlja filter koji će uvijek vratiti {@true}.
	 * 
	 * @author Ivan Jukić
	 *
	 */
	
	private class alwaysTrueFilter implements IFilter {

		@Override
		public boolean accepts(StudentRecord record) {
			return true;
		}
	}
	
	/**
	 * Razred predstavlja filter koji će uvijek vratiti {@false}.
	 * 
	 * @author Ivan Jukić
	 *
	 */
	
	private class alwaysFalseFilter implements IFilter {

		@Override
		public boolean accepts(StudentRecord record) {
			return false;
		}
		
	}
	
	/**
	 * Testira da se filtriraju svi studenti ako se koristi alwaysTrueFilter.
	 */
	
	@Test
	void testFilterTrue() {
		List<StudentRecord> listOfFiltered = dataBase.filter(new alwaysTrueFilter());
		assertEquals(new StudentRecord("0000000001", "Akšamović", "Marin", "2"), listOfFiltered.get(0));
		assertEquals(new StudentRecord("0000000005", "Brezović", "Jusufadis", "2"), listOfFiltered.get(1));
		assertEquals(new StudentRecord("0000000049", "Saratlija", "Branimir", "2"), listOfFiltered.get(2));

	}

	/**
	 * Testira da se nijedan student ne filtrira ako se koristi alwaysFalseFilter.
	 */
	
	@Test
	void testFilterFalse() {
		assertEquals(0, dataBase.filter(new alwaysFalseFilter()).size());
		
	}
}
