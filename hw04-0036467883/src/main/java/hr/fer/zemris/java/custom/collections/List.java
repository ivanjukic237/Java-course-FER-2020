package hr.fer.zemris.java.custom.collections;

/**
 * Sučelje predstavlja poopćavanje modela sučelja collection. Sadrži dodatne metode za
 * dobavljanje, ubacivanje, brisanje i traženje indeksa elementa u listi.
 * 
 * @author Ivan Jukić
 *
 * @param <E> tip elemenata liste
 */

public interface List<E> extends Collection<E> {

	/**
	 * Metoda vraća element liste na određenoj poziciji.
	 * 
	 * @param index indeks na kojem se traži element
	 * @return objekt na zadanom indeksu
	 */
	
	Object get(int index);
	
	/**
	 * Metoda ubacuje vrijednost u listu na određeno mjesto.
	 * 
	 * @param value vrijednost koja se ubacuje u listu
	 * @param position mjesto na koje se ubacuje vrijednost
	 */
	
	void insert(E value, int position);
	
	/**
	 * Metoda vraća indeks elementa u listi po nekom zadanom pravilu.
	 * 
	 * @param value vrijednost elementa kojeg se traži
	 * @return indeks u listi gdje je element
	 */
	
	int indexOf(Object value);
	
	/**
	 * Metoda briše element iz liste na određenoj poziciji.
	 * 
	 * @param index pozicija u listi s koje se briše element
	 */
	
	void remove(int index);
	
}
