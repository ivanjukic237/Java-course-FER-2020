package hr.fer.zemris.custom.collections;

/**
 * Razred ObjectStack predstavlja strukturu podataka stog. Neki objekt se može pojaviti više puta, ali spremanje null
 * reference nije dozvoljeno. Razred nasljeđuje ArrayIndexedCollection.
 * 
 * @author Ivan Jukić
 *
 * @param <E> tip elemenata stoga
 */

public class ObjectStack<E> {
	
	/**
	 * Stog sadrži internu kolekciju ArrayIndexedCollection koja služi za pohranu elemenata.
	 */
	
	private ArrayIndexedCollection<E> collection = new ArrayIndexedCollection<>();
	
	/**
	 * Metoda push dodaje objekt na kraj stoga. Metoda je ekvivalentna metodi add u ArrayIndexedCollection.
	 * 
	 * @param value objekt koji se dodaje na kraj stoga
	 */
	
	public void push(E value) {
		this.collection.add(value);
	}
	
	/**
	 * Metoda pop vraća objekt s kraja stoga te ga briše iz stoga.
	 * 
	 * @throws EmptyStackException ako je stog prazan kada se metoda poziva
	 * @return objekt s kraja stoga
	 */
	
	@SuppressWarnings("unchecked")
	public E pop() {
		if(this.collection.isEmpty()) {
			throw new EmptyStackException("Stog je prazan.");
		}else {
			Object lastObject = this.collection.get(this.collection.size() - 1);
			this.collection.remove(this.collection.size() - 1);
			return (E) lastObject;
		}
	}
	
	/**
	 * Metoda peek vraća objekt s kraja stoga.
	 * 
	 * @throws EmptyStackException ako je stog prazan kada se metoda poziva
	 * @return objekt s kraja stoga
	 */
	
	@SuppressWarnings("unchecked")
	public E peek() {
		if(this.collection.isEmpty()) {
			throw new EmptyStackException("Stog je prazan.");
		}else {
			return (E) this.collection.get(this.collection.size() - 1);
		}
	}
	
	/**
	 * Metoda vraća članove stoga kao String.
	 * 
	 * @return članovi polja kao String.
	 */
	
	@Override
	public String toString() {
		return this.collection.toString();
	}
}
