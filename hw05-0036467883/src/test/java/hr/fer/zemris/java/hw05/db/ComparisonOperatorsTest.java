package hr.fer.zemris.java.hw05.db;

import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.Test;

/**
 * Testiraju se operatori usporedbe.
 * 
 * @author Ivan Jukić
 *
 */

class ComparisonOperatorsTest {

	/**
	 * Testira se operator < manje.
	 */
	
	@Test
	void testLess() {
		IComparisonOperator oper = ComparisonOperators.LESS;
		assertTrue(oper.satisfied("Ana", "Ivana"));
		assertFalse(oper.satisfied("2", "1"));
	}

	/**
	 * Testira se operator <= manje ili jednako.
	 */
	
	@Test
	void testLessOrEquals() {
		IComparisonOperator oper = ComparisonOperators.LESS_OR_EQUALS;
		assertTrue(oper.satisfied("Ana", "Ivana"));
		assertFalse(oper.satisfied("2", "1"));
		assertTrue(oper.satisfied("Ivana", "Ivana"));

	}
	
	/**
	 * Testira se operator > veće.
	 */
	
	@Test
	void testGreater() {
		IComparisonOperator oper = ComparisonOperators.GREATER;
		assertFalse(oper.satisfied("Ana", "Ivana"));
		assertTrue(oper.satisfied("2", "1"));
	}
	
	/**
	 * Testira se operator >= veće ili jednako.
	 */
	
	@Test
	void testGreaterOrEquals() {
		IComparisonOperator oper = ComparisonOperators.GREATER_OR_EQUALS;
		assertTrue(oper.satisfied("Ana", "Ana"));
		assertFalse(oper.satisfied("Ana", "Ivana"));
		assertTrue(oper.satisfied("2", "1"));
	}
	
	/**
	 * * Testira se operator = jednako.
	 */
	
	@Test
	void testEquals() {
		IComparisonOperator oper = ComparisonOperators.EQUALS;
		assertTrue(oper.satisfied("Ana", "Ana"));
		assertFalse(oper.satisfied("Ana", "Ivana"));
	}
	
	/**
	 * * Testira se operator != nejednako.
	 */
	
	@Test
	void testNotEquals() {
		IComparisonOperator oper = ComparisonOperators.NOT_EQUALS;
		assertFalse(oper.satisfied("Ana", "Ana"));
		assertTrue(oper.satisfied("Ana", "Ivana"));
	}
	
	/**
	 * Testira se operator LIKE.
	 */
	
	@Test
	void testLike() {
		IComparisonOperator oper = ComparisonOperators.LIKE;
		//bez operatora
		assertTrue(oper.satisfied("Zagreb", "Zagreb"));
		assertFalse(oper.satisfied("Zagreb", "Bjelovar"));
		
		//operator na kraju
		assertTrue(oper.satisfied("Zagreb", "*reb"));
		assertTrue(oper.satisfied("Zagreb", "*b"));
		assertTrue(oper.satisfied("Zagreb", "*Zagreb"));
		assertFalse(oper.satisfied("Zagreb", "*ab"));
		
		//operator na početku
		assertTrue(oper.satisfied("Zagreb", "Za*"));
		assertTrue(oper.satisfied("Zagreb", "Z*"));
		assertTrue(oper.satisfied("Zagreb", "Zagreb*"));
		assertFalse(oper.satisfied("Zagreb", "Zb*"));

		//operator u sredini
		assertTrue(oper.satisfied("Zagreb", "Zag*b"));
		assertTrue(oper.satisfied("Zagreb", "Z*b"));
		assertTrue(oper.satisfied("Za", "Za*Za"));
		assertFalse(oper.satisfied("Z", "Za*Za"));
		assertFalse(oper.satisfied("Zagreb", "Za*zag"));
		assertFalse(oper.satisfied("Zagreb", "Zbg*eb"));
	}
	
	/**
	 * Testira da se baca iznimka ako imamo više od jednog wildcard znakova u LIKE operatoru.
	 */
	
	@Test
	void testMoreThanOneWildcard() {
		IComparisonOperator oper = ComparisonOperators.LIKE;

		assertThrows(IllegalArgumentException.class, () -> oper.satisfied("Zagreb", "re*b*"));
	}
}
