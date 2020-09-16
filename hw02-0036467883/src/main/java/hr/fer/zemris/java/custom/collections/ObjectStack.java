package hr.fer.zemris.java.custom.collections;

/**
 * Razred ObjectStack predstavlja strukturu podataka stog. Neki objekt se može pojaviti više puta, ali spremanje null
 * reference nije dozvoljeno. Razred nasljeđuje ArrayIndexedCollection.
 * 
 * @author Ivan Jukić
 *
 */

public class ObjectStack extends ArrayIndexedCollection {
	
	/**
	 * Metoda push dodaje objekt na kraj stoga. Metoda je ekvivalentna metodi add u ArrayIndexedCollection.
	 * 
	 * @param value objekt koji se dodaje na kraj stoga
	 */
	
	public void push(Object value) {
		this.add(value);
	}
	
	/**
	 * Metoda pop vraća objekt s kraja stoga te ga briše iz stoga.
	 * 
	 * @throws EmptyStackException ako je stog prazan kada se metoda poziva
	 * @return objekt s kraja stoga
	 */
	
	public Object pop() {
		if(this.isEmpty()) {
			throw new EmptyStackException("Stog je prazan.");
		}else {
			Object lastObject = this.get(size() - 1);
			this.remove(size() - 1);
			return lastObject;
		}
	}
	
	/**
	 * Metoda peek vraća objekt s kraja stoga.
	 * 
	 * @throws EmptyStackException ako je stog prazan kada se metoda poziva
	 * @return objekt s kraja stoga
	 */
	
	public Object peek() {
		if(this.isEmpty()) {
			throw new EmptyStackException("Stog je prazan.");
		}else {
			return this.get(this.size() - 1);
		}
	}
}
