package hr.fer.zemris.java.gui.layouts;

import java.awt.Component;
import java.awt.Container;
import java.awt.Dimension;
import java.awt.Insets;
import java.awt.LayoutManager2;
import java.util.HashMap;
import java.util.Map;
import javax.swing.JComponent;

/**
 * Custom layout manager. It models a grid like structure where the first 5
 * spaces of the first row is merged together.
 * 
 * @author Ivan Jukić
 *
 */

public class CalcLayout implements LayoutManager2 {
	/**
	 * Vertical (and horizontal gap).
	 */
	private int vgap;
	/**
	 * Minimal and maximal width and height.
	 */
	private int minWidth = 0, minHeight = 0;
	/**
	 * Prefered width and height.
	 */
	private int preferredWidth = 0, preferredHeight = 0;
	/**
	 * Flag is size is known.
	 */
	private boolean sizeUnknown = true;
	/**
	 * Number of rows.
	 */
	private final int rowCount = 5;
	/**
	 * Number of columns.
	 */
	private final int columnCount = 7;
	/**
	 * Map where key is a component and value is the position of that component.
	 */
	private Map<Component, RCPosition> map = new HashMap<>();

	/**
	 * Creates the layout where the gap is 0.
	 */

	public CalcLayout() {
		this(0);
	}

	/**
	 * Creates the layout with the given gap.
	 * 
	 * @param vgap vertical/horizontal gap
	 */

	public CalcLayout(int vgap) {
		this.vgap = vgap;
	}

	/**
	 * Adds a layout component. Not used.
	 */

	public void addLayoutComponent(String name, Component comp) {

	}

	/**
	 * Removes a layoud component. Not used.
	 */

	public void removeLayoutComponent(Component comp) {

	}

	/**
	 * Setts sizes of the parent.
	 * 
	 * @param parent parent
	 */

	private void setSizes(Container parent) {
		int nComps = parent.getComponentCount();
		Dimension d = null;

		preferredWidth = 0;
		preferredHeight = 0;
		minWidth = 0;
		minHeight = 0;

		for (int i = 0; i < nComps; i++) {
			Component c = parent.getComponent(i);
			if (c.isVisible()) {
				d = c.getPreferredSize();

				// prva komponenta nema vgap pa ne dodajemo
				if (i > 0) {
					// zašto /2?
					preferredWidth += d.width / 2;
					preferredHeight += vgap;
				} else {
					preferredWidth = d.width;
				}

				preferredHeight += d.height;
				minWidth = Math.max(c.getMinimumSize().width, minWidth);
				minHeight = preferredHeight;
			}
		}

	}

	/**
	 * Sets the preferred layout size
	 * 
	 * @param parent parent
	 */

	public Dimension preferredLayoutSize(Container parent) {
		Dimension dim = new Dimension(0, 0);

		setSizes(parent);
		Insets insets = parent.getInsets();
		dim.width = preferredWidth + insets.left + insets.right;
		dim.height = preferredHeight + insets.top + insets.bottom;

		sizeUnknown = false;
		return dim;
	}

	/**
	 * Sets the minimum layout size.
	 * 
	 * @param parent parent
	 */

	public Dimension minimumLayoutSize(Container parent) {
		Dimension dim = new Dimension(0, 0);

		// Always add the container's insets!
		Insets insets = parent.getInsets();
		dim.width = minWidth + insets.left + insets.right;
		dim.height = minHeight + insets.top + insets.bottom;

		sizeUnknown = false;

		return dim;
	}

	/**
	 * Sets the positions of all components in the layout.
	 */

	public void layoutContainer(Container parent) {

		Insets insets = parent.getInsets();
		// if preferredLayoutSize or minimumLayoutSize were not called
		if (sizeUnknown) {
			setSizes(parent);
		}
		int componentCount = parent.getComponentCount();

		double availableWidth = parent.getWidth() - insets.left - insets.right - (columnCount - 1) * vgap;
		double availableHeight = parent.getHeight() - insets.bottom - insets.top - (rowCount - 1) * vgap;

		int widthOfComponent = (int) availableWidth / columnCount;
		int heightOfComponent = (int) availableHeight / rowCount;

		// differences will be between 0 to columnCount - 1 and 0 to rowCount - 1
		// (remainder of division with columnCount or rowCount)
		double widthDiff = availableWidth - widthOfComponent * columnCount;

		for (int i = 0; i < componentCount; i++) {
			JComponent component = (JComponent) parent.getComponent(i);
			RCPosition constraint = map.get(component);

			int constraintColumn = constraint.getColumn();
			int constraintRow = constraint.getRow();

			@SuppressWarnings("unused")
			int widthDiffForComp = 0;
			if (widthDiff < columnCount / 2 + 1) {
				if (constraintColumn % 2 == 0) {
					widthDiffForComp = Integer.min((int) widthDiff, constraintColumn / 2);
				} else {
					widthDiffForComp = Integer.min((int) widthDiff, constraintColumn / 2 + 1);
				}
			}

			int x = insets.left + (constraintColumn - 1) * (vgap + widthOfComponent);
			int y = insets.top + (constraintRow - 1) * (vgap + heightOfComponent);

			if (constraintColumn == 1 && constraintRow == 1) {
				component.setBounds(x, y, (widthOfComponent + vgap - 1) * 5, heightOfComponent);
			} else {
				component.setBounds(x, y, widthOfComponent, heightOfComponent);

			}

		}

	}

	/**
	 * Adds a layout component with the given constraints.
	 * 
	 * @throws NullPointerException     if the given constraint is null
	 * @throws IllegalArgumentException if the constraint is not a type of
	 *                                  RCPosition.
	 */

	public void addLayoutComponent(Component comp, Object constraints) {
		if (constraints == null) {
			throw new NullPointerException("Constraint can't be null.");
		}
		if (!(constraints instanceof RCPosition)) {
			throw new IllegalArgumentException("Constraints is not a type of RCPosition.");
		}
		map.put(comp, (RCPosition) constraints);

	}

	/**
	 * Sets the maximum layout size.
	 */
	
	public Dimension maximumLayoutSize(Container target) {
		return null;
	}

	/**
	 * Returns the x layout alignment. Always returns 0.
	 */
	
	public float getLayoutAlignmentX(Container target) {
		return 0;
	}

	/**
	 * Returns the y layout alignment. Always returns 0.
	 */
	
	public float getLayoutAlignmentY(Container target) {
		return 0;
	}

	/**
	 * Invalidates the layout. Not used.
	 */
	
	public void invalidateLayout(Container target) {

	}
}
