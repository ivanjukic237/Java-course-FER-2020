package hr.fer.zemris.java.custom.scripting.elems;

/**
 * Predstavlja element koji je riječ.
 * 
 * @author Ivan Jukić
 *
 */

public class ElementString extends Element {
	private String value;

	/**
	 * Postavlja vrijednost riječi.
	 * 
	 * @param value vrijednost riječi
	 */
	
	public ElementString(String value) {
		this.value = value;
	}
	
	/**
	 * Vraća vrijednost riječi.
	 * 
	 * @return vrijednost riječi
	 */
	
	@Override
	public String asText() {
		return this.value;
	}
}
