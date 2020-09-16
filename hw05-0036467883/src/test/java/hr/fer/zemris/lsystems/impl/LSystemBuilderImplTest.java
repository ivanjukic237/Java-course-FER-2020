package hr.fer.zemris.lsystems.impl;

import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.Test;

import hr.fer.zemris.lsystems.LSystem;

/**
 * Razred za testiranje LSystemBuilderImpl.S
 * 
 * @author Ivan Jukić
 *
 */

class LSystemBuilderImplTest {
	
	/**
	 * Testira se generiranje L-sustava.
	 */
	
	@Test
	void testGenerateZeroth() {
		LSystemBuilderImpl systemBuilder = new LSystemBuilderImpl();
		systemBuilder.setAxiom("F").registerProduction('F', "F+F--F+F");
		LSystem lSystem = systemBuilder.build();
		assertEquals("F", lSystem.generate(0));
		
	}
	
	@Test
	void testGenerateFirst() {
		LSystemBuilderImpl systemBuilder = new LSystemBuilderImpl();
		systemBuilder.setAxiom("F").registerProduction('F', "F+F--F+F");
		LSystem lSystem = systemBuilder.build();
		assertEquals("F+F--F+F", lSystem.generate(1));
	}
	
	@Test
	void testGenerateSecond() {
		LSystemBuilderImpl systemBuilder = new LSystemBuilderImpl();
		systemBuilder.setAxiom("F").registerProduction('F', "F+F--F+F");
		LSystem lSystem = systemBuilder.build();
		assertEquals("F+F--F+F+F+F--F+F--F+F--F+F+F+F--F+F", lSystem.generate(2));
	}

}
