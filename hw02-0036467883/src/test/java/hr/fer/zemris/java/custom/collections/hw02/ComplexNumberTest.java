package hr.fer.zemris.java.custom.collections.hw02;

import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.Test;

import hr.fer.zemris.java.custom.collections.hw02.ComplexNumber;

class ComplexNumberTest {

	/**
	 * Testira se da konstuktor stvori pravilan kompleksni broj. 
	 */
	
	@Test
	void testComplexNumber() {
		ComplexNumber complexNumber = new ComplexNumber(2.2, 0);

		assertEquals(complexNumber.getReal(), 2.2);
		assertEquals(complexNumber.getImaginary(), 0);
		
	}
	
	/**
	 * Testira se da metoda parse vraća dobar kompleksni broj za razne ulazne Stringove.
	 */
	
	@Test
	void testParse() {
		
		
		assertEquals(ComplexNumber.parse("2 + 3i").getReal(), 2);
		assertEquals(ComplexNumber.parse("2 + 3i").getImaginary(), 3);

		assertEquals(ComplexNumber.parse("+2").getReal(), 2);
		assertEquals(ComplexNumber.parse("2").getImaginary(), 0);

		assertEquals(ComplexNumber.parse("i").getReal(), 0);
		assertEquals(ComplexNumber.parse("i").getImaginary(), 1);
		
		assertEquals(ComplexNumber.parse("    -22  -   3i  ").getReal(), -22);
		assertEquals(ComplexNumber.parse("    -22  -   3i  ").getImaginary(), -3);

	}
	
	/**
	 * Testira da se bacaju dobre iznimke za nepravilne unose.
	 */
	
	@Test
	void testParseExceptions() {
		// Testira pokušaj parsiranja praznog stringa i null reference
		assertThrows(NullPointerException.class, () -> ComplexNumber.parse(""));
		assertThrows(NullPointerException.class, () -> ComplexNumber.parse(null));
		
		// Testira pokušaj parsiranja nepravilnih Stringova
		assertThrows(NumberFormatException.class, () -> ComplexNumber.parse("2 +"));
		assertThrows(NumberFormatException.class, () -> ComplexNumber.parse("2 +-3i"));
		assertThrows(NumberFormatException.class, () -> ComplexNumber.parse("-"));
		assertThrows(NumberFormatException.class, () -> ComplexNumber.parse("2 +abci"));
		assertThrows(NumberFormatException.class, () -> ComplexNumber.parse("abci"));
		assertThrows(NumberFormatException.class, () -> ComplexNumber.parse("--2 +-7i"));
		assertThrows(NumberFormatException.class, () -> ComplexNumber.parse("2 -+5i"));
		assertThrows(NumberFormatException.class, () -> ComplexNumber.parse("2 + i20"));
		assertThrows(NumberFormatException.class, () -> ComplexNumber.parse("a + 20i"));
		assertThrows(NumberFormatException.class, () -> ComplexNumber.parse("2 + 2ai"));

		assertThrows(NumberFormatException.class, () -> ComplexNumber.parse("-2i + 20i"));
		assertThrows(NumberFormatException.class, () -> ComplexNumber.parse("i + i"));
		assertThrows(NumberFormatException.class, () -> ComplexNumber.parse("i+ "));




	}
	
	/**
	 * Testira se da se dobije pravilan kompleksan broj, tj. da je imaginarni dio nula, a realni dio jednak argumentu.
	 */
	
	@Test
	void testFromReal() {
		assertEquals(ComplexNumber.fromReal(-5).getReal(), -5);
		assertEquals(ComplexNumber.fromReal(-5).getImaginary(), 0);

	}

	/**
	 * Testira se da se dobije pravilan kompleksan broj, tj. da je realni dio nula, a imaginarni dio jednak argumentu.
	 */
	
	@Test
	void testFromImaginary() {
		assertEquals(ComplexNumber.fromImaginary(-5).getReal(), 0);
		assertEquals(ComplexNumber.fromImaginary(-5).getImaginary(), -5);
	}

	/**
	 * Testira se da se dobije pravilan kompleksni broj preko modula i kuta kompleksnog broja.
	 */
	
	@Test
	void testFromMagnitudeAndAngle() {
		assertTrue(Math.abs(ComplexNumber.fromMagnitudeAndAngle(2, 1).getReal() - 1.0806046117362795) < 1E-6);
		assertTrue(Math.abs(ComplexNumber.fromMagnitudeAndAngle(2, 1).getImaginary() - 1.682941969615793) < 1E-6);
	}

	/**
	 * Testira se metoda koja vraća realni dio kompleksnog broja.
	 */
	
	@Test
	void testGetReal() {
		assertEquals(new ComplexNumber(2, 0).getReal(), 2);	
	}

	/**
	 * Testira se metoda koja vraća imaginarni dio kompleksnog broja.
	 */
	
	@Test
	void testGetImaginary() {
		assertEquals(new ComplexNumber(2, 0).getImaginary(), 0);
	}

	/**
	 * Testira se vraćanje ispravnog modula kompleksnog broja.
	 */
	
	@Test
	void testGetMagnitude() {
		assertTrue(Math.abs(new ComplexNumber(2.5, 3).getMagnitude() - 3.905124837953327) < 1E-6);
	}

	/**
	 * Testira se vraćanje ispravnog kuta kompleksnog broja.
	 */
	
	@Test
	void testGetAngle() {
		assertTrue(Math.abs(new ComplexNumber(1, 1).getAngle() - 0.7853981633974483) < 1E-6);
	}

	/**
	 * Testira se zbrajanje kompleksnih brojeva.
	 */
	
	@Test
	void testAdd() {
		assertEquals(ComplexNumber.parse("2-3i").add(ComplexNumber.parse("3+4i")).getReal(), 5);
		assertEquals(ComplexNumber.parse("2-3i").add(ComplexNumber.parse("3+4i")).getImaginary(), 1);
	}

	/**
	 * Testira se oduzimanje kompleksnih brojeva.
	 */
	
	@Test
	void testSub() {
		assertEquals(ComplexNumber.parse("2-3i").sub(ComplexNumber.parse("3+4i")).getReal(), -1);
		assertEquals(ComplexNumber.parse("2-3i").sub(ComplexNumber.parse("3+4i")).getImaginary(), -7);	
	}

	/**
	 * Testira se množenje kompleksnih brojeva.
	 */
	
	@Test
	void testMul() {
		assertEquals(ComplexNumber.parse("2-3i").mul(ComplexNumber.parse("3+4i")).getReal(), 18);
		assertEquals(ComplexNumber.parse("2-3i").mul(ComplexNumber.parse("3+4i")).getReal(), 18);

	}

	/**
	 * Testira se dijeljenje kompleksnih brojeva.
	 */
	
	@Test
	void testDiv() {
		assertEquals(ComplexNumber.parse("2+3i").div(ComplexNumber.parse("2+2i")).getReal(), 1.25);
		assertEquals(ComplexNumber.parse("2+3i").div(ComplexNumber.parse("2+2i")).getImaginary(), 0.25);

	}

	/**
	 * Testira se potenciranje kompleksnih brojeva.
	 */
	
	@Test
	void testPower() {
		assertTrue(Math.abs(new ComplexNumber(0, 1).power(2).getReal() + 1) < 1E-6);
		assertTrue(Math.abs(new ComplexNumber(0, 1).power(2).getImaginary()) < 1E-6);

	}

	/**
	 * Testira se korjenovanje kompleksnih brojeva.
	 */
	
	@Test
	void testRoot() {
		
		ComplexNumber[] listOfRoots = new ComplexNumber(0, 1).root(4);
		ComplexNumber[] listOfSolutions = {
				ComplexNumber.parse("0.9239+0.3827i"),
				ComplexNumber.parse("-0.3827+0.9239i"),
				ComplexNumber.parse("-0.9239-0.3827i"),
				ComplexNumber.parse("0.3827-0.9239i"),
				};
		for(int i = 0; i < listOfSolutions.length; i++) {
			assertTrue(Math.abs(listOfRoots[i].getReal() - listOfSolutions[i].getReal()) < 1E-4);
			assertTrue(Math.abs(listOfRoots[i].getImaginary() - listOfSolutions[i].getImaginary()) < 1E-4);

		}
	}

	/**
	 * Testira se vraćanje kompleksnog broja kao String.
	 */
	
	@Test
	void testToString() {
		assertTrue(ComplexNumber.parse("2+ 5i").toString().equals("2.0+5.0i"));
		assertTrue(ComplexNumber.parse("-2+ 5i").toString().equals("-2.0+5.0i"));
		assertTrue(ComplexNumber.parse("+2+ 5i").toString().equals("2.0+5.0i"));
		assertTrue(ComplexNumber.parse("2- 5i").toString().equals("2.0-5.0i"));
		assertTrue(ComplexNumber.parse("+5i").toString().equals("5.0i"));
		assertTrue(ComplexNumber.parse("-i").toString().equals("-i"));
		assertTrue(ComplexNumber.parse("i").toString().equals("i"));
		
	}

}
