package hr.fer.zemris.java.custom.collections;

/**
 * Sučelje predstavlja model koji prima neki objekt i ispituje je li taj objekt 
 * prihvatljiv ili nije.
 * 
 * @author Ivan Jukić
 *
 * @param <E> tip elemenata testera
 */

public interface Tester<E> {
	
	/**
	 * Provjerava zadovoljava li neki objekt zadani uvjet.
	 * 
	 * @param obj objekt za kojeg testiramo zadani uvjet
	 * @return true ako za objekt vrijedi zadani uvjet
	 */
	
	boolean test(Object obj);
	
}
