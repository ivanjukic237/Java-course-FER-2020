package demo;

import hr.fer.zemris.lsystems.LSystem;
import hr.fer.zemris.lsystems.LSystemBuilderProvider;
import hr.fer.zemris.lsystems.gui.LSystemViewer;
import hr.fer.zemris.lsystems.impl.LSystemBuilderImpl;

/**
 * Program koji crta Koch krivulju.
 * 
 * @author Ivan Jukić
 *
 */

public class Glavni1 {

	/**
	 * Postavlja potrebne atribute za crtanje fraktala.
	 * 
	 * @param provider generator L-sustava
	 * @return L-sustav
	 */
	
	private static LSystem createKochCurve(LSystemBuilderProvider provider) {
		return provider.createLSystemBuilder()
				.registerCommand('F', "draw 1")
				.registerCommand('+', "rotate 60")
				.registerCommand('-', "rotate -60")
				.setOrigin(0.05, 0.4)
				.setAngle(0)
				.setUnitLength(0.9)
				.setUnitLengthDegreeScaler(1.0/3.0)
				.registerProduction('F', "F+F--F+F")
				.setAxiom("F")
				.build();


		}
	
	/**
	 * Metoda pokreće aplikaciju crtanja fraktala.
	 * 
	 * @param args argumenti komandne linije koji se ne koriste u ovom primjeru
	 */
	
	public static void main(String[] args) {
		LSystemViewer.showLSystem(createKochCurve(LSystemBuilderImpl::new));
		
	}

}
