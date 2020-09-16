package hr.fer.zemris.custom.collections;


/**
 * Sučelje predstavlja opis metoda za iterator po listi.
 * 
 * @author Ivan Jukić
 *
 * @param <E> tip elemenata liste
 */

public interface ElementsGetter<E> {

	/**
	 * Metoda provjerava postoji li sljedeći element u listi za vratiti.
	 * 
	 * @return true ako postoji sljedeći element.
	 */
	
	boolean hasNextElement();
	
	/**
	 * Metoda vraća sljedeći element u listi.
	 * 
	 * @return sljedeći element u listi.
	 */
	
	E getNextElement();
		
	
	/**
	 * Metoda nad svim preostalim elementima kolekcije poziva zadani procesor.
	 * 
	 * @param p processor koji poziva process nad preostalim elementima kolekcija
	 */
	
	default void processRemaining(Processor<E> p) {
		while(hasNextElement()) {
			p.process(getNextElement());
		}
	}
}
