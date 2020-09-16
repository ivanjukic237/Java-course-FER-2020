package hr.fer.zemris.lsystems.impl;

import hr.fer.zemris.custom.collections.ObjectStack;

/**
 * Razred se koristi za praćenje stanja kornjače te se koristi za crtanje L-sustava koji se 
 * dijele na više grana. 
 * 
 * @author Ivan Jukić
 *
 */

public class Context {

	/**
	 * Stog koji prati stanja kornjače. Kada se želi zapamtiti stanje kornjače, stavlja se stanje
	 * na stog. U slučaju da nema grananja sustava stog će imati samo jedan element. Ako ima 
	 * grananja onda se pamti stanje kada dolazi do grananja, te nakon što se završi crtanje grane
	 * se miče stanje sa stoga.
	 */
	
	public ObjectStack<TurtleState> states = new ObjectStack<>();
	
	/**
	 * Vraća trenutno stanje kornjače.
	 * 
	 * @return trenutno stanje kornjače
	 */
	
	public TurtleState getCurrentState() {
		return states.peek();
	}
	
	/**
	 * Postavlja trenutno stanje kornjače.
	 * 
	 * @param state trenutno stanje kornjače
	 */
	
	public void pushState(TurtleState state) {
		states.push(state);
	}
	
	/**
	 * Briše trenutno stanje kornjače.
	 */
	
	public void popState() {
		states.pop();
	}
}
