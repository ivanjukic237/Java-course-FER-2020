package hr.fer.zemris.java.custom.scripting.elems;

/**
 * Predstavlja vrijednost elementa koji je varijabla.
 * 
 * @author Ivan Jukić
 *
 */

public class ElementVariable extends Element {
	
	private String name;
	
	/**
	 * Postavlja ime varijable.
	 * 
	 * @param name ime varijable.
	 */
	
	public  ElementVariable(String name) {
		this.name = name;
	}
	
	/**
	 * Vraća ime varijable.
	 * 
	 * @return ime varijable
	 */
	
	@Override
	public String asText() {
		return this.name;
	}
}
