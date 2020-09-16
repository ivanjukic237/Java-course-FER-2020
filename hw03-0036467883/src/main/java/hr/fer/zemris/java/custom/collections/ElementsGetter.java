package hr.fer.zemris.java.custom.collections;

/**
 * Sučelje predstavlja opis metoda za iterator po listi.
 * 
 * @author Ivan Jukić
 *
 */


public interface ElementsGetter {

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
	
	Object getNextElement();
		
	
	/**
	 * Metoda nad svim preostalim elementima kolekcije poziva zadani procesor.
	 * 
	 * @param p processor koji poziva process nad preostalim elementima kolekcija
	 */
	
	default void processRemaining(Processor p) {
		while(hasNextElement()) {
			p.process(getNextElement());
		}
	}
}
