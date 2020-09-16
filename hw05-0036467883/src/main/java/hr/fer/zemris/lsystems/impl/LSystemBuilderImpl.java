package hr.fer.zemris.lsystems.impl;

import java.awt.Color;

import hr.fer.zemris.custom.collections.Dictionary;
import hr.fer.zemris.lsystems.LSystem;
import hr.fer.zemris.lsystems.LSystemBuilder;
import hr.fer.zemris.lsystems.Painter;
import hr.fer.zemris.lsystems.impl.commands.*;
import hr.fer.zemris.math.Vector2D;

/**
 * Razred se koristi za generiranje L-sustava i pokretanje naredbi za pomicanje kornjače. Atributi L-sustava
 * se mogu postaviti direktno preko metoda ili pomoću tekstualne datoteke određenog formata. 
 * Primjer formata se može vidjeti u direktoriju src/main/resources.
 * Bitne komponente L-sustava su varijable koje se koriste, konstante, početak ili aksiom i pravila.
 * 
 * @author Ivan Jukić
 *
 */

public class LSystemBuilderImpl implements LSystemBuilder {

	/**
	 * Lista koji sadrži sva registrirana pravila koje se koriste u L-sustavu. 
	 */
	
	private Dictionary<Character, String> registeredProductions = new Dictionary<>();
	
	/**
	 * Lista sadrži sve registrirane naredbe tipa Command koje pokreću kornjaču.
	 */
	
	private Dictionary<Character, String> registeredActions = new Dictionary<>();;
	
	private double unitLength = 0.1;
	private double unitLengthDegreeScaler = 1;
	private Vector2D origin = new Vector2D(0, 0);
	private double angle = 0;
	private String axiom = "";
	
	/**
	 * Razred koji implementira LSystem i pokreće crtanje generiranih L-sustava. Metoda prima generiranu
	 * dubinu sustava te primijenjuje registrirane naredbe koje se nalaze u listi registeredActions na kornjaču.
	 * Ako naredba nije registrirana, preskoči je se. Pri crtanju linija metoda još skalira duljinu linije ovisno
	 * o dubini generiranog sustava.
	 * 
	 * @author Ivan Jukić
	 *
	 */
	
	private class CustomLSystem implements LSystem {

		@Override
		public void draw(int depth, Painter painter) {
			Context context = new Context();
			context.pushState(new TurtleState(origin.copy(), angle, Color.BLACK, unitLength));
			
			char[] generatedArray = generate(depth).toCharArray();
			
			for(char command : generatedArray) {
				String action = registeredActions.get(command);
				// ako je akcija registrirana tj. u dictu je
				if(action != null) {
					if(action.contains("rotate")) {
						try {
							double ang = Double.parseDouble(action.split("\\s+")[1]);
							new RotateCommand(ang).execute(context, painter);
						} catch(NumberFormatException ex) {
							throw new NumberFormatException("Akcija nije dobra.");
						}
					
					} else if(action.contains("draw")) {
						double depthScaler = 1;
						if(depth != 0) {
							depthScaler = Math.pow(unitLengthDegreeScaler, depth);
						}
						new DrawCommand(unitLength * depthScaler).execute(context, painter);
					
					} else if(command == ']') {
						new PopCommand().execute(context, painter);
					
					} else if(command == '[') {
						new PushCommand().execute(context, painter);
					
					} else if(action.contains("color")) {
						new ColorCommand(Color.decode("#" + action.split("\\s+")[1])).execute(context, painter);
						
					} else if(action.contains("scale")) {
						try {
							double scale = Double.parseDouble(action.split("\\s+")[1]);
							new ScaleCommand(scale).execute(context, painter);
						} catch(NumberFormatException ex) {
							throw new NumberFormatException("Akcija nije dobra.");
						}
					} else if(action.contains("skip")) {
						double depthScaler = 1;
						if(depth != 0) {
							depthScaler = Math.pow(unitLengthDegreeScaler, depth);
						}
						new SkipCommand(unitLength * depthScaler).execute(context, painter);
					}
				}
				
			}
		}

		/**
		 * Metoda generira L-sustav određenog aksioma, varijabli i pravila za određenu dubinu.
		 * 
		 * @param dubina koja se želi generirati za određeni L-sustav
		 * @return generirana dubina L-sustava
		 * 
		 */
		
		@Override
		public String generate(int depth) {
			if(depth == 0) {
				return axiom;
			}
			
			String output = axiom;
			for(int j = 0; j < depth; j++) {
				StringBuilder stringBuilder = new StringBuilder();
				for(int i = 0; i < output.length(); i++) {
					char currentLetter = output.substring(i, i + 1).charAt(0);
					if(registeredProductions.get(currentLetter) != null) {
						stringBuilder.append(registeredProductions.get(currentLetter));
					} else {
						stringBuilder.append(output.substring(i, i + 1));
					}
				}
				output = stringBuilder.toString();
			}
			return output;
		}
	}
	
	/**
	 * Vraća primjerak razreda CustomLSystem.
	 * 
	 * @return primjerak razreda CustomLSystem
	 */
	
	@Override
	public LSystem build() {
		return new CustomLSystem();
	}

	/**
	 * Metoda vadi sve potrebne podatke za inicijalizaciju L-sustava iz tekstualnog polja te ih postavlja.
	 * 
	 * @return primjerak ovog razreda
	 */
	
	@Override
	public LSystemBuilder configureFromText(String[] textArray) {
		String currentRow = "";
		try {
			for(int i = 0; i < textArray.length; i++) {
				 currentRow = textArray[i];
				if(!currentRow.equals("")){
					String[] rowArray = currentRow.split("\\s+");
					if(currentRow.contains("origin")) {
						
						double x = Double.parseDouble(rowArray[1]);
						double y = Double.parseDouble(rowArray[2]);
						this.origin = new Vector2D(x, y);
						
					} else if(currentRow.contains("angle")) {
						this.angle = Double.parseDouble(currentRow.split("\\s+")[1]);
						
					} else if(currentRow.contains("unitLength") && !currentRow.contains("Scaler")) {
						this.unitLength = Double.parseDouble(currentRow.split("\\s+")[1]);
						
					} else if(currentRow.contains("command")) {
						if((currentRow.contains("push") || currentRow.contains("pop"))){
							registerCommand(rowArray[1].charAt(0), rowArray[2]);
						} else {
							registerCommand(rowArray[1].charAt(0), rowArray[2] + " " + rowArray[3]);
						}
						
					} else if(currentRow.contains("axiom")) {
						axiom = rowArray[1];
						
					} else if(currentRow.contains("production")) {
						registerProduction(rowArray[1].charAt(0), rowArray[2]);
						
					} else if(currentRow.contains("unitLengthDegreeScaler")) {
						currentRow = currentRow.replace('/', ' ');
						rowArray = currentRow.split("\\s+");
						this.unitLengthDegreeScaler = Double.parseDouble(rowArray[1]) / Double.parseDouble(rowArray[2]);
					}
				}
			}
		} catch(ArrayIndexOutOfBoundsException | NumberFormatException ex) {
			throw new IllegalArgumentException("Greška u inputu u redu: " + currentRow);
		}
		return this;
	}

	/**
	 * Registrira jednu naredbu koja se koristi u L-sustavu. Stavlja je u listu registriranih
	 * naredbi.
	 * 
	 * @return primjerak ovog razreda
	 */
	
	@Override
	public LSystemBuilder registerCommand(char cmd, String action) {
		registeredActions.put(cmd, action);
		return this;
	}

	/**
	 * Registrira jedno pravilo koje se koristi u L-sustavu. Stavlja je u listu registriranih
	 * pravila.
	 * 
	 * @return primjerak ovog razreda
	 */
	
	@Override
	public LSystemBuilder registerProduction(char character, String rule) {
		registeredProductions.put(character, rule);
		return this;
	}

	/**
	 * Postavlja početni kut gledanja kornjače.
	 * 
	 * @return primjerak ovog razreda
	 */
	
	@Override
	public LSystemBuilder setAngle(double angle) {
		this.angle = angle;
		return this;
	}

	/**
	 * Postavlja aksiom L-sustava.
	 * 
	 * @return primjerak ovog razreda
	 */
	
	@Override
	public LSystemBuilder setAxiom(String axiom) {
		this.axiom = axiom;
		return this;
	}

	/**
	 * Postavlja točku otkud kornjača kreće.
	 * 
	 * @return primjerak ovog razreda
	 */
	
	@Override
	public LSystemBuilder setOrigin(double x, double y) {
		this.origin = new Vector2D(x, y);
		return this;
	}

	/**
	 * Postavlja duljinu linije koju kornjača povlači.
	 * 
	 * @return primjerak ovog razreda
	 */
	
	@Override
	public LSystemBuilder setUnitLength(double unitLength) {
		this.unitLength = unitLength;
		return this;
	}

	/**
	 * Postavlja skalar s kojim se množi duljina linije koju kornjača povlači.
	 * 
	 * @return primjerak ovog razreda
	 */
	
	@Override
	public LSystemBuilder setUnitLengthDegreeScaler(double unitLengthDegreeScaler) {
		this.unitLengthDegreeScaler = unitLengthDegreeScaler;
		return this;
	}

}
