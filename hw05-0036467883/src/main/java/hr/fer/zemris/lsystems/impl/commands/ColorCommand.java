package hr.fer.zemris.lsystems.impl.commands;

import java.awt.Color;

import hr.fer.zemris.lsystems.Painter;
import hr.fer.zemris.lsystems.impl.Command;
import hr.fer.zemris.lsystems.impl.Context;

/**
 * Predstavlja naredbu u kojoj se postavlja boja linija.
 * 
 * @author Ivan Jukić
 *
 */

public class ColorCommand implements Command {

	Color color;
	
	/**
	 * Postavlja se boja koja će se mijenjati.
	 * 
	 * @param color boja u koju želimo promijeniti linije
	 */
	
	public ColorCommand(Color color) {
		this.color = color;
	}
	
	
	@Override
	public void execute(Context ctx, Painter painter) {

		ctx.getCurrentState().setColor(color);
		
	}

}
