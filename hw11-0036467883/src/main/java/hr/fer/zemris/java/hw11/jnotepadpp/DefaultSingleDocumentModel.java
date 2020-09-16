package hr.fer.zemris.java.hw11.jnotepadpp;

import java.nio.file.Path;

import javax.swing.JTextArea;
import javax.swing.event.CaretEvent;
import javax.swing.event.CaretListener;
import javax.swing.event.DocumentEvent;
import javax.swing.event.DocumentListener;

/**
 * Custom single document model for the notepad.
 * 
 * @author Ivan Jukić
 *
 */

public class DefaultSingleDocumentModel implements SingleDocumentModel {
	/**
	 * File path of the document.
	 */
	private Path filePath;
	/**
	 * Text area of the document.
	 */
	private JTextArea textArea;
	/**
	 * Flag if the document is modified.
	 */
	private boolean isModified = false;
	/**
	 * Listener for the document.
	 */
	private SingleDocumentListener listener = null;

	/**
	 * Creates a new single document with specified file path and text. Sets the
	 * document listener so if the document is modified it changes the modified
	 * flag. Sets the new caret listener that notifies the listeners that the caret
	 * position got changed.
	 * 
	 * @param filePath path of the document
	 * @param text     text of the document
	 */

	public DefaultSingleDocumentModel(Path filePath, String text) {
		this.filePath = filePath;
		this.textArea = new JTextArea(text);

		textArea.getDocument().addDocumentListener(new DocumentListener() {

			@Override
			public void insertUpdate(DocumentEvent e) {
				setModified(true);

			}

			@Override
			public void removeUpdate(DocumentEvent e) {
				setModified(true);

			}

			@Override
			public void changedUpdate(DocumentEvent e) {
				setModified(true);

			}

		});
		textArea.addCaretListener(new CaretListener() {

			@Override
			public void caretUpdate(CaretEvent e) {
				listener.documentCaretUpdated(DefaultSingleDocumentModel.this);
				if (e.getMark() - e.getDot() != 0) {
					listener.documentSelectionMade(DefaultSingleDocumentModel.this);
				} else {
					listener.documentSelectionNotMade(DefaultSingleDocumentModel.this);
				}
			}

		});
	}

	/**
	 * Returns the text area of the document.
	 */

	public JTextArea getTextComponent() {
		return textArea;
	}

	/**
	 * Returns the file path of the document.
	 */

	public Path getFilePath() {
		return filePath;
	}

	/**
	 * Sets the file path of the document. Notifies the listener that the file path
	 * got updated.
	 */

	public void setFilePath(Path path) {
		this.filePath = path;
		listener.documentFilePathUpdated(this);

	}

	/**
	 * Flag that is true if the document got modified.
	 */

	public boolean isModified() {
		return isModified;

	}

	/**
	 * Sets the modified flag. Notifies the listener that the status got updated.
	 */

	public void setModified(boolean modified) {
		this.isModified = modified;
		listener.documentModifyStatusUpdated(this);

	}

	/**
	 * Returns the listener for this document.
	 * @return listener for this document
	 */
	
	public SingleDocumentListener getListener() {
		return this.listener;
	}

	/**
	 * Adds the listener for this document.
	 */
	
	public void addSingleDocumentListener(SingleDocumentListener l) {
		this.listener = l;
	}

	/**
	 * removes the listener for this document.
	 */
	
	public void removeSingleDocumentListener(SingleDocumentListener l) {
		this.listener = null;
	}

}
