package hr.fer.zemris.java.custom.scripting.elems;

/**
 * Predstavlja element koji ima vrijednost double. 
 * 
 * @author Ivan Jukić
 *
 */

public class ElementConstantDouble extends Element {
	
	private double value;
	
	/**
	 * Postavlja vrijednost double za element.
	 * 
	 * @param value postavlja vrijednost double
	 */
	
	public ElementConstantDouble(double value) {
		this.value = value;
	}
	
	/**
	 * Vraća vrijednost elementa kao String.
	 * 
	 * @return vrijednost elementa kao String
	 */
	
	@Override
	public String asText() {
		return this.value + "";
	}

}
