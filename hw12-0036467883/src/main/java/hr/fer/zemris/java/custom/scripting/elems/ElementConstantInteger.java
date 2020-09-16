package hr.fer.zemris.java.custom.scripting.elems;

/**
 * Predstavlja element koji ima vrijednost int. 
 * 
 * @author Ivan Jukić
 *
 */

public class ElementConstantInteger extends Element {
	
	private int value;
	
	/**
	 * Postavlja vrijednost double za element.
	 * 
	 * @param value postavlja vrijednost int
	 */
	
	public ElementConstantInteger(int value) {
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
