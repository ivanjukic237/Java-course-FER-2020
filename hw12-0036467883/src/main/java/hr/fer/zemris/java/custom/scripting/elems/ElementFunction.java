package hr.fer.zemris.java.custom.scripting.elems;

/**
 * Predstavlja element koji je neka funkcija.
 * 
 * @author Ivan Jukić
 *
 */

public class ElementFunction extends Element {
	
	private String name;
	
	/**
	 * Postavlja ime funkcije.
	 * 
	 * @param name ime funkcije
	 */
	
	public ElementFunction(String name) {
		this.name = name;
	}
	
	/**
	 * Vraća ime funkcije.
	 * 
	 * @return ime funkcije
	 */
	
	@Override
	public String asText() {
		return this.name;
	}
}
