package hr.fer.zemris.math;

/**
 *  Razred modelira 2D vektor u Kartezijevom koordinatnom sustavu čije su komponente realni brojevi x i y.
 * 
 * @author Ivan Jukić
 *
 */

public class Vector2D {

	private double x;
	private double y;

	/**
	 * Konstruktor stvara vektor s parametrima x i y.
	 * 
	 * @param x komponenta vektora
	 * @param y komponenta vektora
	 */
	
	public Vector2D(double x, double y) {
		this.x = x; 
		this.y = y;
	}
	
	/**
	 * Vraća x komponentu vektora
	 * 
	 * @return x komponentu vektora
	 */
	
	public double getX() {
		return this.x;
	}
	
	/**
	 * Vraća y komponentu vektora
	 * 
	 * @return  y komponentu vektora
	 */
	
	public double getY() {
		return this.y;
	}
	
	/**
	 * Translatira vektor za zadani vektor.
	 * 
	 * @param offset vektor translatacije
	 */
	
	public void translate(Vector2D offset) {
		this.x += offset.getX();
		this.y += offset.getY();
	}
	
	/**
	 * Vraća vektor koji se dobiva translatacijom za zadani vektor. Vektor razreda ostaje nepromijenjen.
	 * 
	 * @param offset vektor translacije
	 * @return vektor dobiven translacijom za zadani vektor
	 */
	
	public Vector2D translated(Vector2D offset) {
		return new Vector2D(this.x + offset.x, this.y + offset.y);
	}
	
	/**
	 * Metoda rotira vektor za zadani kut. 
	 * 
	 * @param angle kut rotacije vektora
	 */
	
	public void rotate(double angle) {
		this.x = Math.cos(angle) * this.x - Math.sin(angle) * this.y;
		this.y = Math.sin(angle) * this.x + Math.cos(angle) * this.y;
	}
	
	/**
	 * Metoda vraća vektor rotiran za zadani kut. Vektor razreda ostaje nepromijenjen.
	 * 
	 * @param angle kut rotacije vektora
	 * @return vektor rotiran za zadani kut
	 */
	
	public Vector2D rotated(double angle) {
		double x = Math.cos(angle) * this.x - Math.sin(angle) * this.y;
		double y = Math.sin(angle) * this.x + Math.cos(angle) * this.y;
		
		return new Vector2D(x, y);
	}
	
	/**
	 * Vektor se skalira za zadanu konstantu.
	 *  
	 * @param scaler broj kojim se skalira vektor
	 */
	
	public void scale(double scaler) {
		this.x *= scaler;
		this.y *= scaler;
	}
	
	/**
	 * Vraća vektor skaliran za zadanu konstantu.
	 * 
	 * @param scaler broj kojim se skalira vektor
	 * @return vektor skaliran za zadanu konstantu
	 */
	
	public Vector2D scaled(double scaler) {
		double x = this.x * scaler;
		double y = this.y * scaler;
		
		return new Vector2D(x, y);
	}

	/**
	 * Vraća kopiju vektora razreda.
	 * 
	 * @return kopija vektora razreda
	 */

	public Vector2D copy() {
		return new Vector2D(this.x, this.y);
	}
	
	public String toString() {
		return "(" + this.x + ", " + this.y + ")";
	}
	
}
