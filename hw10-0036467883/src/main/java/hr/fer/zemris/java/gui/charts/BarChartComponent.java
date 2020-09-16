package hr.fer.zemris.java.gui.charts;

import java.awt.BasicStroke;
import java.awt.Color;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Insets;
import java.awt.geom.AffineTransform;
import javax.swing.JComponent;

/**
 * Creates the bar chart component.
 * 
 * @author Ivan Jukić
 *
 */

public class BarChartComponent extends JComponent {

	private static final long serialVersionUID = 1L;

	private BarChart barChart;
	private int space = 60;
	private int xLength;
	private int yLength;
	private final int ARR_SIZE = 4;
	Insets ins;

	/**
	 * Sets the BarChart object.
	 * 
	 * @param barChart BarChart object.
	 */
	
	public BarChartComponent(BarChart barChart) {
		super();
		this.barChart = barChart;
	}

	
	/**
	 * Paints all of the components for the bar chart.
	 */
	
	public void paint(Graphics g) {
		ins = getInsets();
		drawAxesLabel(g);
		
		g.drawString(barChart.getxDescription(), getWidth() / 2, getHeight() - ins.bottom - 20);

		// x axis
		drawArrow(g, space + ins.left, getHeight() - ins.bottom - space, getWidth() - ins.right - space,
				getHeight() - ins.bottom - space);
		// y axis
		drawArrow(g, space + ins.left, getHeight() - ins.bottom - space, space + ins.left, ins.top + space);
		xLength = Math.abs(getWidth() - ins.right - space - space + ins.left);
		yLength = Math.abs(ins.top - getHeight() - ins.bottom + 2 * space);
		drawLines(g);
	}

	/**
	 * Draws the lines on the y and x axes for the given BarChart object.
	 * 
	 * @param g graphics g
	 */
	
	void drawLines(Graphics g) {
		Graphics2D g2d = (Graphics2D) g;
		int yMax = barChart.getyMax();
		int yMin = barChart.getyMin();
		int diff = yMax - yMin;
		int step = barChart.getyDiff();
		while (diff % step != 0) {
			yMax++;
			diff = yMax - yMin;
		}
		int stepOnYAxis = (yLength - 5) / (diff / step);
		int counter = 0;
		for (int i = yMin; i <= yMax; i = i + step) {
			int x1 = space - 5 + ins.left;
			int y1 = getHeight() - ins.bottom - space - stepOnYAxis * counter;
			int x2 = getWidth() - ins.right - space - 5;
			int y2 = getHeight() - ins.bottom - space - stepOnYAxis * counter;
			g2d.drawLine(x1, y1, x2, y2);
			//max string width for y axis labels
			int textWidth = g.getFontMetrics().stringWidth(yMax + "");
			g2d.drawString(i + "", x1 - textWidth, y1 + 5);
			counter++;
		}

		int numberOfCharts = barChart.getList().size();
		int stepOnXAxis = (xLength - 5) / numberOfCharts;
		int xOrigin = space + ins.left;
		
		for (int i = 0; i <= numberOfCharts; i++) {
			int x1 = xOrigin + stepOnXAxis * i;
			int y1 = getHeight() - ins.bottom - space;
			int y2 = ins.top + space;
			g2d.setColor(Color.black);
			
			g2d.drawLine(x1, y1, x1, y2);
			if(i != numberOfCharts) {
				int yValueHeight = barChart.getList().get(i).getY();
				g2d.setColor(Color.blue);
				g2d.setStroke(new BasicStroke(2));
				g2d.fillRect(x1, y1 - stepOnYAxis * (yValueHeight - yMin) / step, stepOnXAxis, stepOnYAxis * (yValueHeight - yMin) / step);
				g2d.setColor(Color.black);
				g2d.drawString(barChart.getList().get(i).getX() + "", x1 + 15, 15 + y1);
			}
			
		}
	}

	/**
	 * Draws the axis arrow.
	 * 
	 * @param g1 graphics 
	 * @param x1 x coordinate of the start
	 * @param y1 y coordinate of the start
	 * @param x2 x coordinate of the end
	 * @param y2 y coordinate of the end
	 */
	
	void drawArrow(Graphics g1, int x1, int y1, int x2, int y2) {
		Graphics2D g = (Graphics2D) g1.create();

		double dx = x2 - x1, dy = y2 - y1;
		double angle = Math.atan2(dy, dx);
		int len = (int) Math.sqrt(dx * dx + dy * dy);
		AffineTransform at = AffineTransform.getTranslateInstance(x1, y1);
		at.concatenate(AffineTransform.getRotateInstance(angle));
		g.transform(at);

		// Draw a horizontal arrow starting in (0, 0)
		g.drawLine(0, 0, len, 0);
		g.fillPolygon(new int[] { len, len - ARR_SIZE, len - ARR_SIZE, len }, new int[] { 0, -ARR_SIZE, ARR_SIZE, 0 },
				4);
	}
	
	/**
	 * Draws the x axis labels.
	 * 
	 * @param g graphics g
	 */
	
	void drawAxesLabel(Graphics g) {
		Graphics2D g2d = (Graphics2D) g;
		AffineTransform defaultAt = g2d.getTransform();
		AffineTransform at = new AffineTransform();
		at.rotate(-Math.PI / 2);
		g2d.setTransform(at);
		g2d.drawString(barChart.getyDescription(), (-getHeight() / 2)-50, 20);
		g2d.setTransform(defaultAt);

	}
}
