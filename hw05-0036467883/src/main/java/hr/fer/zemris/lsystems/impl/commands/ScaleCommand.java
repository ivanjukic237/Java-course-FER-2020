package hr.fer.zemris.lsystems.impl.commands;

import hr.fer.zemris.lsystems.Painter;
import hr.fer.zemris.lsystems.impl.Command;
import hr.fer.zemris.lsystems.impl.Context;

/**
 * Efektivnu duljinu pomaka ažurira tako da je pomnoži danim faktorom; npr. "scale 0.75"
 * 
 * @author Ivan Jukić
 *
 */

public class ScaleCommand implements Command {

	private double factor;
	
	/**
	 * Postavlja faktor skaliranje efektivne duljine.
	 * 
	 * @param factor
	 */
	
	public ScaleCommand(double factor) {
		this.factor = factor;
	}
	
	@Override
	public void execute(Context ctx, Painter painter) {

		ctx.getCurrentState().setStep(ctx.getCurrentState().getStep() * factor);
		
	}

}
