package hr.fer.zemris.java.custom.collections;

/**
 * Sučelje Processor je model objekta koji može obavljati neku operaciju na dani objekt.
 * Razred se koristiti na način da se implementira i da se metoda process napiše za svaki 
 * taj razred.
 * 
 * @author Ivan Jukić
 *
 * @param <E> tip elemenata procesora
 */

public interface Processor<E> {
/**
 * Metoda process je prazna metoda koja predstavlja operaciju nad nekim objektom koju će obavljati razredi koji nasljeđuju ovaj razred.
 * 
 * @param value objekt nad kojem se vrši neka operacija
 */
	
	void process(E value);
	
}
