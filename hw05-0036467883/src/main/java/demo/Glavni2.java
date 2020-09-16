package demo;

import hr.fer.zemris.lsystems.LSystem;
import hr.fer.zemris.lsystems.LSystemBuilderProvider;
import hr.fer.zemris.lsystems.gui.LSystemViewer;
import hr.fer.zemris.lsystems.impl.LSystemBuilderImpl;

/**
 * Program koji crta Koch krivulju. Koristi se u svrhu testiranja konfiguracije preko teksta.
 * 
 * @author Ivan Jukić
 *
 */

public class Glavni2 {
	
	/**
	 * Postavlja potrebne atribute za crtanje fraktala preko teksta.
	 * 
	 * @param provider generator L-sustava
	 * @return L-sustav
	 */
	
	private static LSystem createKochCurve2(LSystemBuilderProvider provider) {
		String[] data = new String[] {
			"origin 0.05 0.4",
			"angle 0",
			"unitLength 0.9",
			"unitLengthDegreeScaler 1.0 / 3.0",
			"",
			"command F draw 1",
			"command + rotate 60",
			"command - rotate -60",
			"",
			"axiom F",
			"",
			"production F F+F--F+F"
		};
		return provider.createLSystemBuilder().configureFromText(data).build();
	
	}
	
	/**
	 * Metoda pokreće aplikaciju crtanja fraktala.
	 * 
	 * @param args argumenti komandne linije koji se ne koriste u ovom primjeru
	 */
	
	public static void main(String[] args) {
		LSystemViewer.showLSystem(createKochCurve2(LSystemBuilderImpl::new));

	}
}
