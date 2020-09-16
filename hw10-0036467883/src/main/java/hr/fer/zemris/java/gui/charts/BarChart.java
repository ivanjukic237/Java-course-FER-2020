package hr.fer.zemris.java.gui.charts;

import java.util.List;

/**
 * Models a bar chart. It holds the information of the x axis description, y
 * axis description, minimum and maximum value for y, list of XYValues and the y
 * step on the y axis.
 * 
 * @author Ivan Jukić
 *
 */

public class BarChart {
	/**
	 * List that hold the XYValues.
	 */
	private List<XYValue> list;
	/**
	 * Description of the x axis.
	 */
	private String xDescription;
	/**
	 * Description of the y axis.
	 */
	private String yDescription;
	/**
	 * Minimum y value.
	 */
	private int yMin;
	/**
	 * Maximum y value.
	 */
	private int yMax;
	/**
	 * Step on y axis.
	 */
	private int yDiff;

	/**
	 * Creates a BarChart object with a given information.
	 * 
	 * @param list         list of XYValues points
	 * @param xDescription description of x axis
	 * @param yDescription description of y axis
	 * @param yMin         minimum y value
	 * @param yMax         maximum y value
	 * @param yDiff        step on y axis
	 * 
	 * @throws IllegalArgumentException if minimal value for y is less than 0
	 * @throws IllegalArgumentException if maximal value for y is less than the
	 *                                  minimal value for y
	 * @throws IllegalArgumentException if one of the values for y is less than the minimal value
	 * 
	 */

	public BarChart(List<XYValue> list, String xDescription, String yDescription, int yMin, int yMax, int yDiff) {
		this.list = list;
		this.xDescription = xDescription;
		this.yDescription = yDescription;
		if (yMin < 0) {
			throw new IllegalArgumentException("Minimal value for y is less than 0.");
		}
		if (yMax < yMin) {
			throw new IllegalArgumentException("Maximal value for y is less than the minimal value for y.");
		}
		this.yMin = yMin;
		this.yMax = yMax;
		this.yDiff = yDiff;
		for (XYValue value : list) {
			if (value.getY() < yMin) {
				throw new IllegalArgumentException("One of the values for y is less than the minimal value.");
			}
		}
	}

	/**
	 * Returns the list of XYValues.
	 * 
	 * @return list of XYValues
	 */
	
	public List<XYValue> getList() {
		return list;
	}

	/**
	 * Returns the x axis description.
	 * 
	 * @return x axis description
	 */
	
	public String getxDescription() {
		return xDescription;
	}

	/**
	 * Returns the y axis description.
	 * 
	 * @return y axis description
	 */
	
	public String getyDescription() {
		return yDescription;
	}

	/**
	 * Returns the minimum y value.
	 * 
	 * @return minimum y value
	 */
	
	public int getyMin() {
		return yMin;
	}

	/**
	 * Returns the maximum y value.
	 * 
	 * @return maximum y value.
	 */
	
	public int getyMax() {
		return yMax;
	}

	/**
	 * Returns the step y value.
	 * 
	 * @return step y value
	 */
	
	public int getyDiff() {
		return yDiff;
	}
}
