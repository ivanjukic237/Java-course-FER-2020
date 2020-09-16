package hr.fer.zemris.java.hw11.jnotepadpp;

/**
 * Listener for the single document.
 * 
 * @author Ivan Jukić
 *
 */

public interface SingleDocumentListener {
	
	/**
	 * Listener if the document modify status got updated.
	 * @param model single document model
	 */
	
	void documentModifyStatusUpdated(SingleDocumentModel model);

	/**
	 * Listener if the document file path got updated.
	 * @param model single document model
	 */
	
	void documentFilePathUpdated(SingleDocumentModel model);
	
	/**
	 * Listener if the document caret got updated.
	 * @param model single document model
	 */
	
	void documentCaretUpdated(SingleDocumentModel model);
	
	/**
	 * Listener if the document text selection got made.
	 * @param model single document model
	 */
	
	void documentSelectionMade(SingleDocumentModel model);
	
	/**
	 * Listener if the document text is not selected anymore.
	 * @param model single document model
	 */
	
	void documentSelectionNotMade(SingleDocumentModel model);

}
