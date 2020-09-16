package hr.fer.zemris.java.hw11.jnotepadpp;

import java.nio.file.Path;

/**
 * Model of the opened documents in the notepad.
 * 
 * @author Ivan Jukić
 *
 */

public interface MultipleDocumentModel extends Iterable<SingleDocumentModel> {

	/**
	 * Creates a new document.
	 * 
	 * @return the current opened document
	 */

	SingleDocumentModel createNewDocument();

	/**
	 * Returns the current document.
	 * 
	 * @return the current opened document
	 */

	SingleDocumentModel getCurrentDocument();

	/**
	 * Opens an existing document.
	 * 
	 * @return the current opened document
	 */

	SingleDocumentModel loadDocument(Path path);

	/**
	 * Saves the current document.
	 * 
	 * @return the current opened document
	 */

	void saveDocument(SingleDocumentModel model, Path newPath);

	/**
	 * Closes the current document.
	 * 
	 */

	void closeDocument(SingleDocumentModel model);

	/**
	 * Adds the multiple document listener.
	 * 
	 */

	void addMultipleDocumentListener(MultipleDocumentListener l);

	/**
	 * Removes the multiple document listener.
	 * 
	 */

	void removeMultipleDocumentListener(MultipleDocumentListener l);

	/**
	 * Returns the number of documents opened.
	 * 
	 * @return number of documents opened
	 */

	int getNumberOfDocuments();

	/**
	 * Returns the document at the specified index.
	 * 
	 * @param index of the document
	 * @return document at the specified index
	 */

	SingleDocumentModel getDocument(int index);
}
