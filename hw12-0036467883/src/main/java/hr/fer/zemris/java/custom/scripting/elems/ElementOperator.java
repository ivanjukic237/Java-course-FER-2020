package hr.fer.zemris.java.custom.scripting.elems;

/**
 * Predstavlja element koji je neka aritmetička operacija.
 * 
 * @author Ivan Jukić
 *
 */

public class ElementOperator extends Element {
	
	private String symbol;
	
	/**
	 * Postavlja ime operacije.
	 * 
	 * @param symbol ime operacije
	 */
	
	public ElementOperator(String symbol) {
		this.symbol = symbol;
	}
	
	/**
	 * Vraća ime operacije.
	 * 
	 * @return ime operacije
	 */
	
	@Override
	public String asText() {
		return this.symbol;
	}

}
