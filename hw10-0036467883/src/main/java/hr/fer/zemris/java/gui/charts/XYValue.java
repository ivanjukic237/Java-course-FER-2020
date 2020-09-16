package hr.fer.zemris.java.gui.charts;

/**
 * Object that models a pair of numbers (x, y).
 * 
 * @author Ivan Jukić
 *
 */

public class XYValue {

	private int x;
	private int y;
	
	/**
	 * Returns the x value of the pair.
	 * 
	 * @return x value
	 */
	
	public int getX() {
		return x;
	}

	/**
	 * Returns the y value of the pair.
	 * 
	 * @return y value of the pair.
	 */
	
	public int getY() {
		return y;
	}

	/**
	 * Sets the (x, y) pair.
	 * 
	 * @param x x value
	 * @param y y value
	 */
	
	public XYValue(int x, int y) {
		super();
		this.x = x;
		this.y = y;
	}
	
}
