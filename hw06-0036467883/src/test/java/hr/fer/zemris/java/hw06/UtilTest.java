package hr.fer.zemris.java.hw06;

import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.Test;

/**
 * Class test the Util class.
 * 
 * @author Ivan Jukić
 *
 */

class UtilTest {
	byte[] output = {1, -82, 34};
	
	/**
	 * Checks the hextobyte method.
	 */
	
	@Test
	void testHexToByte() {
		byte[] byteOutputToCheck = Util.hextobyte("01aE22");
		for(int i = 0; i < output.length; i++) {
			assertEquals(output[i], byteOutputToCheck[i]);
		}
		
	}

	/**
	 * Checks the bytetohex method.
	 */
	
	@Test
	void testByteToHex() {
		String hexOutputToCheck = Util.bytetohex(output);
			assertEquals("01ae22", hexOutputToCheck);
		
		
	}
}
