package hr.fer.zemris.java.custom.scripting.nodes;

import java.util.ArrayList;

/**
 * Model čvora koji predstavlja stablo u koje se mogu dodavati svi čvorovi tipa
 * Node.
 * 
 * @author Ivan Jukić
 *
 */

public abstract class Node {

	/**
	 * Struktura ArrayIndexedCollection koji sprema djecu ovog čvora.
	 */

	ArrayList<Object> collection;

	public abstract void accept(INodeVisitor visitor);

	/**
	 * Dodaje zadan čvor u kolekciju čvorova.
	 * 
	 * @param child čvor koji se dodaje u kolekciju.
	 */

	public void addChildNode(Node child) {
		if (collection == null) {
			collection = new ArrayList<>();
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
