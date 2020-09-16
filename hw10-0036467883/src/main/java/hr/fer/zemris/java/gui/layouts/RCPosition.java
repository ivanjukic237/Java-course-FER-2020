package hr.fer.zemris.java.gui.layouts;

/**
 * Positions for the components for the CalcLayout.
 * 
 * @author Ivan Jukić
 *
 */

public class RCPosition {

	private int row;
	private int column;
	
	/**
	 * Sets the row and column position of the component.
	 * 
	 * @param row row position of the component
	 * @param column column position of the component
	 */
	
	public RCPosition(int row, int column) {
		super();
		this.row = row;
		this.column = column;
	}

	/**
	 * Returns the row position of the component.
	 * 
	 * @return row position of the component
	 */
	
	public int getRow() {
		return row;
	}

	/**
	 * Returns the column position of the component.
	 * 
	 * @return column position of the component
	 */
	
	public int getColumn() {
		return column;
	}
	
	
}
