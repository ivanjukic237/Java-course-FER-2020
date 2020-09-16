package hr.fer.zemris.java.hw11.jnotepadpp;

/**
 * Listener for the multiple document model.
 * 
 * @author Ivan Jukić
 *
 */

public interface MultipleDocumentListener {

	/**
	 * Listens if the current document gets changed.
	 * 
	 * @param previousModel previous opened document
	 * @param currentModel  current opened document
	 */

	void currentDocumentChanged(SingleDocumentModel previousModel, SingleDocumentModel currentModel);

	/**
	 * Listens if a new document got added.
	 * 
	 * @param model new document added
	 */

	void documentAdded(SingleDocumentModel model);

	/**
	 * Listens if a document gets removed.
	 * 
	 * @param model removed document
	 */

	void documentRemoved(SingleDocumentModel model);
}
