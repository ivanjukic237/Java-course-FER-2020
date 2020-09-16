package hr.fer.zemris.java.math;

import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.Test;

import hr.fer.zemris.math.Vector2D;

/**
 * Razred testira funkcionalnosti razreda Vector2D.
 * 
 * @author Ivan Jukić
 *
 */

class Vector2DTest {

	/**
	 * Testira se translacija.
	 */
	
	@Test
	void testTranslate() {

		Vector2D v = new Vector2D(2,2);
		v.translate(new Vector2D(-5, -5));
		
		assertEquals(v.getX(), -3);
		assertEquals(v.getY(), -3);
	}

	/**
	 * Testira vraća li se dobar translatirani vektor.
	 */
	
	@Test
	void testTranslated() {
		Vector2D v = new Vector2D(5,2);

		Vector2D vTranslated = v.translated(new Vector2D(4,4));
		assertEquals(vTranslated.getX(), 9);
		assertEquals(vTranslated.getY(), 6);
	}
	
	/**
	 * Testira rotaciju vektora.
	 */
	
	@Test
	void testRotate() {
		Vector2D v = new Vector2D(1,0);
		v.rotate(Math.toRadians(90));
		
		assertTrue(Math.abs(v.getX()) - 1E-6 < 0);
		assertTrue(Math.abs(v.getY()) - 1E-6 < 1);

	}
	
	/**
	 * Testira vraća li se dobar rotirani vektor.
	 */
	
	@Test
	void testRotated() {
		Vector2D v = new Vector2D(1,0);
		Vector2D vRotated = v.rotated(Math.toRadians(90));
		
		assertTrue(Math.abs(vRotated.getX()) - 1E-6 < 0);
		assertTrue(Math.abs(vRotated.getY()) - 1E-6 < 1);
	}
	
	/**
	 * Testira skalira li se dobro vektor.
	 */
	
	@Test
	void testScale() {
		Vector2D v = new Vector2D(6,-2);
		v.scale(-2);
		
		assertEquals(v.getX(), -12);
		assertEquals(v.getY(), 4);
	}
	
	/**
	 * Testira vraća li se dobro skalirani vektor.
	 */
	
	@Test 
	void testScaled() {
		Vector2D v = new Vector2D(1, 2);
		Vector2D vScaled = v.scaled(-2);
		
		assertEquals(vScaled.getX(), -2);
		assertEquals(vScaled.getY(), -4);
	}
	
	/**
	 * Testira kopira li se dobro vektor.
	 */
	
	@Test
	void copy() {
		Vector2D v = new Vector2D(1,0);
		Vector2D vCopied = v.copy();

		assertEquals(vCopied.getX(), 1);
		assertEquals(vCopied.getY(), 0);
	}
}
