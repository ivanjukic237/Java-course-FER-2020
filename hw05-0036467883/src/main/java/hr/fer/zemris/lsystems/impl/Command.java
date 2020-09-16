package hr.fer.zemris.lsystems.impl;

import hr.fer.zemris.lsystems.Painter;

/**
 * Sučelje predstavlja model naredbe koje će aplikacija kornjača raditi.
 * 
 * @author Ivan Jukić
 *
 */

public interface Command {

	/**
	 * Pokreće određenu naredbu.
	 * 
	 * @param ctx primjerak razreda Context
	 * @param painter primjerak razreda Painter
	 */
	
	void execute(Context ctx, Painter painter);
}
