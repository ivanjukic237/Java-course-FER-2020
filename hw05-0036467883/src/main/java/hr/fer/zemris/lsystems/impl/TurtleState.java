package hr.fer.zemris.lsystems.impl;

import java.awt.Color;

import hr.fer.zemris.math.Vector2D;

/**
 * Predstavlja model kornjače koja se kreće u ravnini. Sadrži radij-vektor trenutne pozicije, smjer u kojem
 * gleda, duljina linije koju povlači i boju linije.
 * 
 * @author Ivan Jukić
 *
 */

public class TurtleState {

	private Vector2D position;
	private double angle;
	private Color color;
	private double step;
	
	/**
	 * Postavlja vrijednosti trenutne pozicije, kuta, boje i pomaka kornjače.
	 * 
	 * @param position trenutna pozicija kornjače
	 * @param angle kut smjera u kojem kornjača gleda
	 * @param color boja linije koju kornjača povlači
	 * @param step duljina linije koju kornjača prolazi
	 */
	
	public TurtleState(Vector2D position, double angle, Color color, double step) {
		this.position = position;
		this.angle = angle;
		this.color = color;
		this.step = step;
	}
	
	/**
	 * Vraća kopiju trenutnog stanja kornjače.
	 * 
	 * @return
	 */
	
	public TurtleState copy() {
		return new TurtleState(position, angle, color, step);
	}
	
	/**
	 * Vraća radij-vektor trenutne pozicije kornjače.
	 * 
	 * @return radij-vektor trenutne pozicije kornjače
	 */
	
	public Vector2D getPosition() {
		return this.position;
	}
	
	/**
	 * Postavlja radij-vektor trenutne pozicije kornjače.
	 * 
	 * @param radij-vektor trenutne pozicije kornjače
	 */
	
	public void setPosition(Vector2D position) {
		this.position = position;
	}
	
	/**
	 * Vraća boju linije koju kornjača povlači.
	 * 
	 * @return boja linije kju kornjača povlači
	 */
	
	public Color getColor() {
		return this.color;
	}
	
	/**
	 * Postavlja boju linije koju kornjača povlači.
	 * 
	 * @param boja linije kju kornjača povlači
	 */
	
	public void setColor(Color color) {
		this.color = color;
	}
	
	/**
	 * Vraća duljinu linije koju kornjača povlači.
	 * 
	 * @return boja linije kju kornjača povlači
	 */
	
	public double getStep() {
		return this.step;
	}
	
	/**
	 * Postavlja duljinu linije koju kornjača povlači.
	 * 
	 * @param boja linije kju kornjača povlači
	 */
	
	public void setStep(double step) {
		this.step = step;
	}
	
	/**
	 * Vraća kut smjera kornjače.
	 * 
	 * @return kut smjera kornjače
	 */
	
	public double getAngle() {
		return this.angle;
	}
	
	/**
	 * Postavlja kut smjera kornjače.
	 * 
	 * @param angle kut smjera kornjače
	 */
	
	public void setAngle(double angle) {
		this.angle = angle;
	}
}
