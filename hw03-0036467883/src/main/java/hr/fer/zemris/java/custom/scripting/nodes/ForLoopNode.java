package hr.fer.zemris.java.custom.scripting.nodes;

import hr.fer.zemris.java.custom.scripting.elems.*;

/**
 * Predstavlja čvor koji sadrži vrijednosti for petlje. Sadrži 4 člana od kojih prvi predstavlja varijablu po 
 * kojoj se vrti petlja, drugi predstavlja početni broj, a treći zadnji broj iteracije. Zadnji član može biti
 * null ili broj. Ako je broj onda predstavlja za koliko se mijenja varijabla svake iteracije.
 * 
 * @author Ivan Jukić
 *
 */

public class ForLoopNode extends Node {

	ElementVariable variable;
	Element startExpression;
	Element endExpression;
	Element stepExpression;
	
	/**
	 * Inicijaliziraju se vrijednosti for petlje. 
	 * 
	 * @param variable vrijednost varijable koja se iterira
	 * @param startExpression početna vrijednost varijable
	 * @param endExpression konačna vrijednost varijable
	 * @param stepExpression za koliko varijabla svake iteracije
	 */
	
	public ForLoopNode(ElementVariable variable, Element startExpression, Element endExpression, Element stepExpression) {
		this.variable = variable;
		this.startExpression = startExpression;
		this.endExpression = endExpression;
		this.stepExpression = stepExpression;
		
		
	}
	
	/**
	 * Vraća varijablu petlje
	 *  
	 * @return varijabla petlje
	 */
	
	public ElementVariable getVariable() {
		return variable;
	}
	
	/**
	 * Vraća početnu vrijednost petlje.
	 * 
	 * @return početna vrijednost petlje
	 */
	
	public Element getStartExpression() {
		return startExpression;
	}
	
	/**
	 * Vraća konačnu vrijednost petlje
	 * 
	 * @return konačna vrijednost petlje
	 */
	
	public Element getEndExpression() {
		return endExpression;
	}
	
	/**
	 * Vraća vrijednost za koliko se mijenja varijabla u petlji.
	 *  
	 * @return vrijednost za koliko se mijenja varijabla u petlji
	 */
	
	public Element stepExpression() {
		return stepExpression;
	}
	
	/**
	 * Vraća String reprezentaciju for petlje. Na početak stavlja {$ zatim zapisuje članove for petlje i zatvara
	 * petlju s $}. Nakon toga pretražuje tijelo for petlje, tj. djecu čvora te ih dodaje u String. Na kraju se
	 * dodaje {$END$} tag za zatvaranje petlje.
	 * 
	 * @return String reprezentacija for petlje
	 */
	
	@Override
	public String toString() {
		StringBuilder stringBuilder = new StringBuilder();
		stringBuilder.append("{$FOR ");
		stringBuilder.append(variable.asText() + " ");
		stringBuilder.append(startExpression.asText() + " ");
		stringBuilder.append(endExpression.asText() + " ");
		if(stepExpression == null) {
			stringBuilder.append("");
		} else {
			stringBuilder.append(stepExpression.asText() + " ");

		}
		stringBuilder.append("$}");
		if(this.collection != null) {
			int numberOfChildren = numberOfChildren();

			for(int i = 0; i < numberOfChildren; i++) {
				stringBuilder.append(getChild(i));
			}
		}
		
		stringBuilder.append("{$END$}");
		
		return stringBuilder.toString();
	}

}
