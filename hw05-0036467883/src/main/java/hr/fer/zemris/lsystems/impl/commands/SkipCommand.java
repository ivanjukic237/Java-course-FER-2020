package hr.fer.zemris.lsystems.impl.commands;

import hr.fer.zemris.lsystems.Painter;
import hr.fer.zemris.lsystems.impl.Command;
import hr.fer.zemris.lsystems.impl.Context;
import hr.fer.zemris.lsystems.impl.TurtleState;
import hr.fer.zemris.math.Vector2D;

/**
 * Kornjača preskače određnu liniju i ne crta.
 * 
 * @author Ivan Jukić
 *
 */

public class SkipCommand implements Command {

	double step;
	
	/**
	 * Duljina koja se preskače.
	 * 
	 * @param step
	 */
	
	public SkipCommand(Double step) {
		this.step = step;
	}
	
	@Override
	public void execute(Context ctx, Painter painter) {
		
		TurtleState currentState = ctx.getCurrentState();
		Vector2D position = currentState.getPosition();
		
		double angle = currentState.getAngle();
		Vector2D shiftVector = new Vector2D(step * Math.cos(Math.toRadians(angle)), step * Math.sin(Math.toRadians(angle)));
		
		currentState.setPosition(new Vector2D(position.getX() + shiftVector.getX(), position.getY() + shiftVector.getY()));
		
	}

}
