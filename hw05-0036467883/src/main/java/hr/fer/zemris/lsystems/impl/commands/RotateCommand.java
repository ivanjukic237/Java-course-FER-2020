package hr.fer.zemris.lsystems.impl.commands;

import hr.fer.zemris.lsystems.Painter;
import hr.fer.zemris.lsystems.impl.Command;
import hr.fer.zemris.lsystems.impl.Context;
import hr.fer.zemris.lsystems.impl.TurtleState;

/**
 * Rotira se smjer gledanja kornjače za trenutno stanje.
 * 
 * @author Ivan Jukić
 *
 */

public class RotateCommand implements Command {
	private double angle;
	
	/**
	 * Postavlja se kut rotacije kornjače.
	 * 
	 * @param angle kut rotacije kornjače
	 */
	
	public RotateCommand(double angle) {
		this.angle = angle;
	}
	
	@Override
	public void execute(Context ctx, Painter painter) {
		TurtleState currentState = ctx.getCurrentState();
		currentState.setAngle(currentState.getAngle() + angle);
	}

}
