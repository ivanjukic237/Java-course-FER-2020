package demo;

import hr.fer.zemris.lsystems.gui.LSystemViewer;
import hr.fer.zemris.lsystems.impl.LSystemBuilderImpl;

/**
 * Pokreće grafičku aplikaciju koja pokreće prikaz fraktala pomoću tekstualnih datoteka.
 * U projektu postoje primjeri u src/main/resources/examples.
 * 
 * @author Ivan Jukić
 *
 */

public class Glavni3 {

	/**
	 * Pokreće aplikaciju za prikaz fraktala.
	 * 
	 * @param args argumenti komandne linije (ne koriste se)
	 */
	
	public static void main(String[] args) {
		LSystemViewer.showLSystem(LSystemBuilderImpl::new);

	}

}
