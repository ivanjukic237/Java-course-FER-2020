package hr.fer.zemris.lsystems.impl.commands;

import hr.fer.zemris.lsystems.Painter;
import hr.fer.zemris.lsystems.impl.Command;
import hr.fer.zemris.lsystems.impl.Context;
import hr.fer.zemris.lsystems.impl.TurtleState;
import hr.fer.zemris.math.Vector2D;

/**
 * Predstavlja naredbu koja crta zadanu duljinu linije u smjeru u kojem je kornjača okrenuta.
 * 
 * @author Ivan Jukić
 *
 */

public class DrawCommand implements Command {

	double step;
	
	/**
	 * Postavlja duljinu linije koja se crta.
	 * 
	 * @param step duljina linije koja se crta
	 */
	
	public DrawCommand(double step) {
		this.step = step;
	}
	
	/**
	 * Računa gdje će se linija nacrtati na ekranu. Početni položaj i smjer gledanja kornjače se dobiva iz 
	 * trenutnog stanja kornjače te se linija povlači za zadanu udaljenost.
	 * 
	 * @param ctx primjerak razreda Context
	 * @param painter primjerak razreda Painter
	 */
	
	@Override
	public void execute(Context ctx, Painter painter) {
		
		TurtleState currentState = ctx.getCurrentState();
		Vector2D position = currentState.getPosition();
		
		double angle = currentState.getAngle();
		Vector2D shiftVector = new Vector2D(step * Math.cos(Math.toRadians(angle)), step * Math.sin(Math.toRadians(angle)));
		
		painter.drawLine(
				position.getX(), 
				position.getY(), 
				position.getX() + shiftVector.getX(), 
				position.getY() + shiftVector.getY(), 
				ctx.getCurrentState().getColor(),
				1f
				);
		currentState.setPosition(new Vector2D(position.getX() + shiftVector.getX(), position.getY() + shiftVector.getY()));
	}

}
