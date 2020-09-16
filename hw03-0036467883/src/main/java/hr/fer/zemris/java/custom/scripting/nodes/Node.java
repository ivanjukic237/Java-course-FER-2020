package hr.fer.zemris.java.custom.scripting.nodes;

import hr.fer.zemris.java.custom.collections.ArrayIndexedCollection;

/**
 * Model čvora koji predstavlja stablo u koje se mogu dodavati svi čvorovi tipa Node.
 * 
 * @author Ivan Jukić
 *
 */

public class Node {
	
	/**
	 * Struktura ArrayIndexedCollection koji sprema djecu ovog čvora.
	 */
	
	ArrayIndexedCollection collection;
	
	/**
	 * Dodaje zadan čvor u kolekciju čvorova.
	 * 
	 * @param child čvor koji se dodaje u kolekciju.
	 */
	
	public void addChildNode(Node child) {
		if(collection == null) {
			collection = new ArrayIndexedCollection();
		}
		this.collection.add(child);

		
	}
	
	/**
	 * Metoda vraća broj djece ovog čvora.
	 * 
	 * @return broj djece ovog čvora
	 */
	
	public int numberOfChildren() {
		return this.collection.size();
	}
	
	/**
	 * Metoda vraća dijete ovog čvora na određenom indeksu-
	 * 
	 * @param index indeks mjesta djeteta kojeg tražimo
	 * @return dijete čvora na određenom indeksu
	 */
	
	public Node getChild(int index) {
		return (Node) this.collection.get(index);
	}
}
