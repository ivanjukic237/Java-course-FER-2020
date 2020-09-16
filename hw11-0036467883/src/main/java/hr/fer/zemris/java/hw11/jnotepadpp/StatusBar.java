package hr.fer.zemris.java.hw11.jnotepadpp;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.time.Instant;
import java.time.ZoneId;
import java.time.format.DateTimeFormatter;
import java.util.Locale;

import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.SwingConstants;
import javax.swing.SwingUtilities;
import javax.swing.border.BevelBorder;
import javax.swing.border.MatteBorder;

/**
 * Status bar used by the notepad. It holds three tabs. First is the length of
 * the text, holds the current caret position and the size of the selected text.
 * Third one holds the current date and time.
 * 
 * @author Ivan Jukić
 *
 */

public class StatusBar extends JLabel {

	private static final long serialVersionUID = 1L;
	/**
	 * Current opened document model.
	 */
	SingleDocumentModel model;
	/**
	 * Length of the text.
	 */
	private int length;
	/**
	 * Row number of the caret.
	 */
	private int rowNumber;
	/**
	 * Column number of the caret.
	 */
	private int columnNumber;
	/**
	 * StatisticalInfo object used for calculating the length and row/column
	 * numbers.
	 */
	private StatisticalInfo info;
	/**
	 * First label that holds the length of the text.
	 */
	private JLabel linesLabel;
	/**
	 * Label that holds the position of the caret in the text.
	 */
	private JLabel positionLabel;
	/**
	 * Number of selected characters.
	 */
	private int selected;

	/**
	 * Creates the labels and adds it in the status bar.
	 * 
	 * @param model current document model
	 * @param frame frame that this status bar will be inputted
	 */

	public StatusBar(SingleDocumentModel model, JFrame frame) {
		this.model = model;
		linesLabel = new JLabel();
		linesLabel.setPreferredSize(new Dimension(frame.getWidth() / 3, 20));
		linesLabel.setBorder(new MatteBorder(0, 0, 0, 1, Color.GRAY));

		positionLabel = new JLabel("", SwingConstants.LEFT);
		positionLabel.setPreferredSize(new Dimension(frame.getWidth() / 3, 20));
		positionLabel.setBorder(new MatteBorder(0, 0, 0, 1, Color.GRAY));

		update(model);
		setBorder(new BevelBorder(BevelBorder.LOWERED));
		setPreferredSize(new Dimension(frame.getWidth(), 20));
		setLayout(new FlowLayout(FlowLayout.LEFT, 5, 0));

		add(linesLabel);
		add(positionLabel);
		add(new Sat());

	}

	/**
	 * Updates the values of the labels for the current opened document.
	 * 
	 * @param model current opened document
	 */

	public void update(SingleDocumentModel model) {
		this.info = new StatisticalInfo(model.getTextComponent().getText());
		length = info.getNumberOfCharacters();
		linesLabel.setText("length: " + length);
		int caretPosition = model.getTextComponent().getCaret().getDot();
		rowNumber = info.calculateRowNumber(caretPosition);
		columnNumber = info.calculateColumnNumber(caretPosition);
		selected = Math.abs(model.getTextComponent().getCaret().getMark() - caretPosition);
		positionLabel.setText(String.format("Ln: %d   Col: %d   Sel: %d", rowNumber, columnNumber, selected));

	}

	/**
	 * Label that holds the current date and time. Format for the date and time is
	 * yyyy/MM/dd HH:mm:ss.
	 * 
	 * @author Ivan Jukić
	 *
	 */

	static class Sat extends JLabel {

		private static final long serialVersionUID = 1L;

		/**
		 * Text for the label.
		 */
		volatile String vrijeme;
		/**
		 * If the thread is requested to stop.
		 */
		volatile boolean stopRequested;
		/**
		 * Formatter for the current time with default locale and zone.
		 */
		DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy/MM/dd     HH:mm:ss")
				.withLocale(Locale.getDefault()).withZone(ZoneId.systemDefault());

		/**
		 * Creates a new thread that gets updated ever 0.5 s. It repaints the label
		 * every time it's updated.
		 */

		public Sat() {
			updateTime();

			Thread t = new Thread(() -> {
				while (true) {
					try {
						Thread.sleep(500);
					} catch (Exception ex) {
					}
					if (stopRequested)
						break;
					SwingUtilities.invokeLater(() -> {
						updateTime();
					});
				}
			});
			t.setDaemon(true);
			t.start();
		}

		/**
		 * Repaints the label with the new time.
		 */

		private void updateTime() {
			vrijeme = formatter.format(Instant.now());
			setText(vrijeme);
			repaint();
		}
	}

}
