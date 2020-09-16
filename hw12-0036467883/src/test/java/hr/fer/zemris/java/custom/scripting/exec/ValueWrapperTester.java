package hr.fer.zemris.java.custom.scripting.exec;

import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.Test;

/**
 * Test class that test the ValueWrapper class.
 * 
 * @author Ivan Jukić
 *
 */

class ValueWrapperTester {
	/**
	 * Testing addition
	 */
	@Test
	void testAdd() {
		ValueWrapper v1 = new ValueWrapper(null);
		ValueWrapper v2 = new ValueWrapper(null);
		v1.add(v2.getValue()); // v1 now stores Integer(0); v2 still stores null.
		assertEquals(Integer.valueOf(0), v1.getValue());
		assertEquals(null, v2.getValue());
	}

	/**
	 * Testing addition
	 */
	@Test
	void testAdd2() {
		ValueWrapper v3 = new ValueWrapper("1.2E1");
		ValueWrapper v4 = new ValueWrapper(Integer.valueOf(1));
		v3.add(v4.getValue()); // v3 now stores Double(13); v4 still stores Integer(1).
		assertEquals(Double.valueOf(13), v3.getValue());
		assertEquals(Integer.valueOf(1), v4.getValue());
	}

	/**
	 * Testing addition
	 */
	@Test
	void testAdd3() {
		ValueWrapper v5 = new ValueWrapper("12");
		ValueWrapper v6 = new ValueWrapper(Integer.valueOf(1));
		v5.add(v6.getValue()); // v5 now stores Integer(13); v6 still stores Integer(1).
		assertEquals(Integer.valueOf(13), v5.getValue());
		assertEquals(Integer.valueOf(1), v6.getValue());
	}

	/**
	 * Testing addition
	 */
	@Test
	void testAdd4() {
		ValueWrapper v7 = new ValueWrapper("Ankica");
		ValueWrapper v8 = new ValueWrapper(Integer.valueOf(1));
		// throws RuntimeException
		assertThrows(RuntimeException.class, () -> v7.add(v8.getValue()));
	}

	/**
	 * Testing comparation
	 */
	@Test
	void testComp() {
		ValueWrapper v5 = new ValueWrapper("0.2");
		ValueWrapper v6 = new ValueWrapper(Double.valueOf(0.2));
		ValueWrapper v7 = new ValueWrapper(Double.valueOf(-18.3));

		assertEquals(0, v5.numCompare(v6.getValue()));
		assertEquals(-1, v7.numCompare(v6.getValue()));
		assertEquals(1, v6.numCompare(v7.getValue()));

		ValueWrapper v1 = new ValueWrapper("2");
		ValueWrapper v2 = new ValueWrapper(Integer.valueOf(2));
		ValueWrapper v3 = new ValueWrapper(Integer.valueOf(-18));

		assertEquals(0, v1.numCompare(v2.getValue()));
		assertEquals(-1, v3.numCompare(v2.getValue()));
		assertEquals(1, v2.numCompare(v3.getValue()));

	}
}
