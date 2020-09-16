package hr.fer.zemris.java.custom.collections;

/**
 * Razred Collection predstavlja model kolekcije i igra ulogu sučelja. 
 *
 * @author Ivan Jukić
 *
 */

public class Collection {
	
	/**
	 * Konstruktor stvara primjerak razreda.
	 */
	
	protected Collection() {
		
	}
	
	/**
	 * Provjerava je li lista prazna.
	 * 
	 * @return true ako je lista prazna
	 */
	
	public boolean isEmpty() {
		return size() == 0;
	}
	
	/**
	 * Vraća koliko članova ima lista.
	 * 
	 * @return broj članova liste
	 */
	
	public int size() {
		return 0;
	}
	
	/**
	 * Dodaje novi objekt na kraj liste.
	 * 
	 * @param value objekt koji se dodaje
	 */
	
	public void add(Object value) {
		
	}
	
	/**
	 * Provjerava postoji li određeni objekt u listi.
	 * 
	 * @param value objekt za kojeg se provjerava je li u listi
	 * @return true ako je objekt u listi
	 */
	
	public boolean contains(Object value) {
		return false;
	}
	
	/**
	 * Briše određeni objekt iz liste.
	 * 
	 * @param value objekt koji se briše iz liste.
	 * @return true ako je objekt izbrisan
	 */
	
	public boolean remove(Object value) {
		return false;
	}
	
	/**
	 * Vraća niz elementa liste kao objektno polje.
	 *  
	 * @return polje objekata liste
	 */
	
	public Object[] toArray() {
		throw new UnsupportedOperationException();
	}
	
	/**
	 * Metoda zove processor.process() za svaki element ove kolekcije.
	 * 
	 * @param processor
	 */
	
	public void forEach(Processor processor) {
		
	}
	
	/**
	 * Metoda dodaje sve elemente iz dane kolekcije u ovu. Druga kolekcija ostaje nepromijenjena
	 * 
	 * @param other kolekcija koja se kopira u ovu
	 */
	
	public void addAll(Collection other) {
		
		class LocalProcessor extends Processor {
			@Override
			public void process(Object value) {
				add(value);
			}
		}
		
		other.forEach(new LocalProcessor());
	}
	
	/**
	 * Metoda briše sve elemente liste.
	 */
	
	public void clear() {
		
	}
}
