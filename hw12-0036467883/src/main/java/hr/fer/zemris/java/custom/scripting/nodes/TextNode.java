package hr.fer.zemris.java.custom.scripting.nodes;

/**
 * Razred predstavlja čvor koji sadrži tekst dobiven od lexera u stanju TEXT.
 * 
 * @author Ivan Jukić
 *
 */

public class TextNode extends Node {

	String text;
	
	/**
	 * Inicijalizira tekst čvora.
	 * 
	 * @param text
	 */
	
	public TextNode(String text) {
		this.text = text;
	}
	
	/**
	 * Vraća tekst čvora.
	 * 
	 * @return tekst čvora
	 */
	
	public String getText() {
		return this.text;
	}
	
	/**
	 * Vraća tekst čvora i Override-a toString().
	 * 
	 * @return tekst čvora
	 */
	
	@Override
	public String toString() {
		return this.text;
	}

	@Override
	public void accept(INodeVisitor visitor) {
		visitor.visitTextNode(this);
		
	}
}
