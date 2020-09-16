package hr.fer.zemris.java.custom.collections;

/**
 * Sučelje predstavlja model neke kolekcije.
 *
 * @author Ivan Jukić
 * 
 * @param <E> tip elemenata kolekcije
 *
 */

public interface Collection<E> {

	/**
	 * Provjerava je li lista prazna.
	 * 
	 * @return true ako je lista prazna
	 */

	default boolean isEmpty() {
		return size() == 0;
	}

	/**
	 * Vraća koliko članova ima lista.
	 * 
	 * @return broj članova liste
	 */

	int size();

	/**
	 * Dodaje novi objekt na kraj liste.
	 * 
	 * @param value objekt koji se dodaje
	 */

	void add(E e);

	/**
	 * Provjerava postoji li određeni objekt u listi.
	 * 
	 * @param value objekt za kojeg se provjerava je li u listi
	 * @return true ako je objekt u listi
	 */

	boolean contains(Object value);

	/**
	 * Briše određeni objekt iz liste.
	 * 
	 * @param value objekt koji se briše iz liste.
	 * @return true ako je objekt izbrisan
	 */

	boolean remove(Object value);

	/**
	 * Vraća niz elementa liste kao objektno polje.
	 * 
	 * @return polje objekata liste
	 */

	Object[] toArray();

	/**
	 * Metoda zove processor.process() za svaki element ove kolekcije.
	 * 
	 * @param processor
	 */

	default void forEach(Processor<? super E> processor) {
		ElementsGetter<E> elementsGetter = createElementsGetter();

		while (elementsGetter.hasNextElement()) {
			processor.process(elementsGetter.getNextElement());
		}
	}

	/**
	 * Metoda dodaje sve elemente iz dane kolekcije u ovu. Druga kolekcija ostaje
	 * nepromijenjena
	 * 
	 * @param other kolekcija koja se kopira u ovu
	 */

	default void addAll(Collection<? extends E> other) {
		if (other != null) {
			
			other.forEach(new Processor<E>() {
				@Override
				public void process(E value) {
					add(value);
				}
			});

		}
	}

	/**
	 * Metoda briše sve elemente liste.
	 */

	void clear();

	/**
	 * Kreira primjerak razreda ElementsGetter koji služi kao iterator.
	 * 
	 * @return objekt razreda ElementsGetter
	 */

	ElementsGetter<E> createElementsGetter();

	/**
	 * Metoda dodaje iz kolekcije sve elemente koji ispunjavaju zadani uvjet.
	 * 
	 * @param col    kolekcija iz koje dodajemo sve elemente koji ispunjavaju zadani
	 *               uvjet
	 * @param tester testira objekt ispunjava li zadani uvjet
	 */

	default void addAllSatisfying(Collection<E> col, Tester<E> tester) {
		if (col != null) {

			col.forEach(new Processor<E>() {
				public void process(E value) {
					if (tester.test(value)) {
						add(value);
					}
				}
			});

		}
	}

}
