package hr.fer.zemris.java.custom.scripting.nodes;

import hr.fer.zemris.java.custom.scripting.elems.Element;

/**
 * Čvor koji predstavlja elemente funkcije jednako.
 * 
 * @author Ivan Jukić
 *
 */

public class EchoNode extends Node {

	/**
	 * Polje koje sadrži elemente čvora.
	 */
	
	Element[] elements;
	
	/**
	 * Postavlja polje elemenata čvora.
	 * 
	 * @param elements polje elemenata čvora.
	 */
	
	public EchoNode(Element[] elements) {
		this.elements = elements;
	}
	
	/**
	 * Vraća polje elemenata čvora.
	 *  
	 * @return polje elemenata čvora
	 */
	
	public Element[] getElements() {
		return elements;
	}

	/**
	 * Vraća String reprezentaciju čvora. Stavlja potrebne znakove na početak i kraj.
	 * 
	 * @return String reprezentaciju čvora
	 */
	
	@Override
	public String toString() {
		StringBuilder stringBuilder = new StringBuilder();
		
		stringBuilder.append("{$");
		for(Element element: elements) {
			if(element == null) {
				break;
			}
			stringBuilder.append(element.asText() + " ");
		}
		stringBuilder.append("$}");
		return stringBuilder.toString();
	}
}
