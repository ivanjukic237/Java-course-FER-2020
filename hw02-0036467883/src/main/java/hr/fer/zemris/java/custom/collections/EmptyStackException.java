package hr.fer.zemris.java.custom.collections;

/**
 * Razred nasljeđuje RuntimeException i predstavlja iznimku kada je stog prazan.
 * 
 * @author Ivan Jukić
 *
 */
public class EmptyStackException extends RuntimeException {
/**
 * Konstruktor EmptyStackException baca iznimku kada je stog prazan.
 *
 * @param exception poruka korisniku da je stog prazan
 */
	public EmptyStackException(String exception) {
		super(exception);
	}
	    
}
