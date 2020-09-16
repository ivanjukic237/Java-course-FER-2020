package hr.fer.zemris.java.hw11.jnotepadpp;

import java.awt.BasicStroke;
import java.awt.Color;
import java.awt.Component;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Image;
import java.awt.event.*;
import java.io.IOException;
import java.io.InputStream;

import javax.swing.AbstractButton;
import javax.swing.BorderFactory;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.plaf.basic.BasicButtonUI;

/**
 * Panel used to represend the tab in the multiple document model tabbed pane.
 * It contains the diskette icon that changes in regards of modify state of the
 * document, name of the document and the x button that closes the current tab.
 * 
 * @author Ivan Jukić
 *
 */

public class ButtonTabComponent extends JPanel {

	private static final long serialVersionUID = 1L;

	/**
	 * Pane where this is going to be embedded
	 */

	private final DefaultMultipleDocumentModel pane;
	/**
	 * Icon for modified document.
	 */
	private ImageIcon modifiedIcon;
	/**
	 * Icon for not modified document.
	 */
	private ImageIcon notModifiedIcon;
	/**
	 * Label that holds the icon.
	 */
	private JLabel iconLabel;
	/**
	 * Label that holds the document name.
	 */
	private JLabel label;
	/**
	 * X button to close the current tab.
	 */
	private JButton xButton;

	/**
	 * Creates the icons, which are going to be swapped. Creates the name label, and
	 * the label which holds the x button.
	 * 
	 * @param pane pane where this is going to be embededd
	 * @param name name of the document to be shown on the tab
	 * @throws IOException if the icon is not found
	 */

	public ButtonTabComponent(final DefaultMultipleDocumentModel pane, String name) throws IOException {
		super(new FlowLayout(FlowLayout.LEFT, 0, 0));
		this.pane = pane;
		setOpaque(false);
		modifiedIcon = getIcon("icons/save-blue.png");
		notModifiedIcon = getIcon("icons/save-white.png");

		this.iconLabel = new JLabel(notModifiedIcon);
		iconLabel.setBorder(BorderFactory.createEmptyBorder(0, 0, 0, 5));
		add(iconLabel);

		// labela s nazivom datoteke
		this.label = new JLabel(name);
		add(label);
		label.setBorder(BorderFactory.createEmptyBorder(0, 0, 0, 10));

		// x gumb
		this.xButton = new TabButton();
		add(xButton);
		setBorder(BorderFactory.createEmptyBorder(2, 0, 0, 0));
	}

	/**
	 * Swaps the icon label.
	 * 
	 * @param isModified if it's true it swaps to modifiedIcon, if false it swaps to
	 *                   notModifiedIcon
	 */

	public void swapIconLabel(boolean isModified) {
		if (!isModified) {
			this.iconLabel.setIcon(notModifiedIcon);
		} else {
			this.iconLabel.setIcon(modifiedIcon);
		}
	}

	/**
	 * Changes the tab name
	 * 
	 * @param name name to be changed
	 */

	public void changeTabName(String name) {
		label.setText(name);
	}

	/**
	 * Makes an icon from the given specified path of the png pictures.
	 * 
	 * @param path path of the pictures
	 * @return image icon
	 * @throws IOException if the specified path was not found
	 */

	private ImageIcon getIcon(String path) throws IOException {
		InputStream is = this.getClass().getResourceAsStream(path);
		if (is == null) {
			throw new NullPointerException("Input stream is null.");
		}
		ImageIcon image = null;
		byte[] bytes;
		try {
			bytes = is.readAllBytes();
			image = new ImageIcon(bytes);
			image = new ImageIcon(image.getImage().getScaledInstance(20, 20, Image.SCALE_DEFAULT));
			is.close();

		} catch (IOException e) {
			e.printStackTrace();
		}
		return image;
	}

	/**
	 * Creates the x button for the tab.
	 * 
	 * @author Ivan Jukić
	 *
	 */

	private class TabButton extends JButton implements ActionListener {

		private static final long serialVersionUID = 1L;

		/**
		 * Paints the x button. Sets the listeners, borders and preferred sizes.
		 */

		public TabButton() {

			int size = 17;
			setOpaque(false);
			setPreferredSize(new Dimension(size, size));
			setUI(new BasicButtonUI());
			// transparent
			setContentAreaFilled(false);
			setFocusable(false);
			setBorder(BorderFactory.createEtchedBorder());
			setBorderPainted(false);
			addMouseListener(buttonMouseListener);
			setRolloverEnabled(true);
			addActionListener(this);

		}

		/**
		 * Closes the current document.
		 */

		@Override
		public void actionPerformed(ActionEvent e) {
			pane.closeDocument(pane.getCurrentDocument());

		}

		/**
		 * Paints the x component.
		 */

		@Override
		protected void paintComponent(Graphics g) {
			super.paintComponent(g);
			Graphics2D g2 = (Graphics2D) g.create();
			// shift the image for pressed buttons
			if (getModel().isPressed()) {
				g2.translate(1, 1);
			}
			g2.setStroke(new BasicStroke(1));
			g2.setColor(Color.BLACK);
			if (getModel().isRollover()) {
				g2.setColor(Color.MAGENTA);
			}
			int delta = 4;
			g2.drawLine(delta, delta, getWidth() - delta - 1, getHeight() - delta - 1);
			g2.drawLine(getWidth() - delta - 1, delta, delta, getHeight() - delta - 1);
			g2.dispose();
		}
	}

	/**
	 * Paints the x button border in a different color when the mouse hovers over
	 * it.
	 */

	private final MouseListener buttonMouseListener = new MouseAdapter() {
		public void mouseEntered(MouseEvent e) {
			Component component = e.getComponent();
			if (component instanceof AbstractButton) {
				AbstractButton button = (AbstractButton) component;
				button.setBorderPainted(true);
			}
		}

		/**
		 * Returns the color of the x button border to normal if the mouse stops
		 * hovering over it.
		 */

		public void mouseExited(MouseEvent e) {
			Component component = e.getComponent();
			if (component instanceof AbstractButton) {
				AbstractButton button = (AbstractButton) component;
				button.setBorderPainted(false);
			}
		}
	};
}
