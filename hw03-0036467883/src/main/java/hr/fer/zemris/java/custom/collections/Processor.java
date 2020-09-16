package hr.fer.zemris.java.custom.collections;
/**
 * Razred Processor je model objekta koji može obavljati neku operaciju na dani objekt.
 * Razred se koristiti na način da se naslijedi i da se metoda process napiše za svaki 
 * taj razred.
 * 
 * @author Ivan Jukić
 *
 */
public interface Processor {
/**
 * Metoda process je prazna metoda koja predstavlja operaciju nad nekim objektom koju će obavljati razredi koji nasljeđuju ovaj razred.
 * 
 * @param value objekt nad kojem se vrši neka operacija
 */
	public void process(Object value);
	
}
