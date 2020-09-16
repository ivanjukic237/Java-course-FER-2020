package hr.fer.zemris.java.hw11.jnotepadpp;

import java.nio.file.Path;

import javax.swing.JTextArea;

/**
 * Model for the single document in the notepad.
 * 
 * @author Ivan Jukić
 *
 */

public interface SingleDocumentModel {

	/**
	 * Gets the text component for the document.
	 * 
	 * @return text component for the document
	 */

	JTextArea getTextComponent();

	/**
	 * Gets the file path for the document.
	 * 
	 * @return file path for the document
	 */

	Path getFilePath();

	/**
	 * Sets the file path for the document.
	 * 
	 * @param path file path for the document
	 */

	void setFilePath(Path path);

	/**
	 * Checks if the document is modified.
	 * 
	 * @return true if the document was modified
	 */

	boolean isModified();

	/**
	 * Sets the modified status for the document
	 * 
	 * @param modified true if the document was modified
	 */

	void setModified(boolean modified);

	/**
	 * Adds a single document listener for this document.
	 * 
	 * @param l single document listener
	 */

	void addSingleDocumentListener(SingleDocumentListener l);

	/**
	 * Removes a single document listener for this document.
	 * 
	 * @param l single document listener
	 */

	void removeSingleDocumentListener(SingleDocumentListener l);
}
