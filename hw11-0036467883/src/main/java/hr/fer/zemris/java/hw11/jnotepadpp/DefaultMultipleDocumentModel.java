package hr.fer.zemris.java.hw11.jnotepadpp;

import java.awt.event.MouseEvent;
import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.Iterator;
import java.util.LinkedList;
import javax.swing.plaf.*;

import hr.fer.zemris.java.hw11.jnotepadpp.JNotepadPP.singleDocumentListener;

import javax.swing.JComponent;
import javax.swing.JFileChooser;
import javax.swing.JOptionPane;
import javax.swing.JScrollPane;
import javax.swing.JTabbedPane;
import javax.swing.ToolTipManager;
import javax.swing.event.ChangeEvent;
import javax.swing.event.ChangeListener;

/**
 * Custom multiple document model. It is a JTabbedPane that holds all of the
 * open documents in tabs.
 * 
 * @author Ivan Jukić
 *
 */

public class DefaultMultipleDocumentModel extends JTabbedPane implements MultipleDocumentModel {

	private static final long serialVersionUID = 1L;
	/**
	 * List of all currently opened documents.
	 */
	private LinkedList<SingleDocumentModel> listOfDocuments = new LinkedList<>();
	/**
	 * Listener for the multiple document model.
	 */
	MultipleDocumentListener listener = new multipleListener();
	/**
	 * Current document.
	 */
	private SingleDocumentModel currentDocument = createNewDocument();
	/**
	 * Frame where this pane is embedded.
	 */
	private JNotepadPP frame;

	/**
	 * Constructor adds the change listener that notifies the listener that the
	 * current document is changed. Also it registers this class to be a shared
	 * instance.
	 */

	public DefaultMultipleDocumentModel() {
		// bug da se tab ne može kliknuti kada je setToolTip aktivan
		ToolTipManager.sharedInstance().registerComponent(this);
		this.addChangeListener(new ChangeListener() {

			@Override
			public void stateChanged(ChangeEvent e) {
				int index = getSelectedIndex();
				if (index != -1) {
					currentDocument = listOfDocuments.get(getSelectedIndex());
					listener.currentDocumentChanged(currentDocument, getCurrentDocument());

				} else {
					currentDocument = null;
				}
			}

		});
	}

	/**
	 * Sets the frame for this class.
	 * 
	 * @param frame
	 */

	public void setFrame(JNotepadPP frame) {
		this.frame = frame;
	}

	/**
	 * Sets the iterator for the list of documents.
	 */

	public Iterator<SingleDocumentModel> iterator() {
		return listOfDocuments.listIterator();
	}

	/**
	 * Creates a new blank document.
	 */

	public SingleDocumentModel createNewDocument() {
		return createDocument(null, "");
	}

	/**
	 * Creates a document with a specified path and text. Sets the text in the
	 * scroll pane and creates the tab label with the name of the document, the x
	 * button and the diskette icon.
	 * 
	 * @param path path for the document
	 * @param text text of the document
	 * @return currently created document
	 */

	private SingleDocumentModel createDocument(Path path, String text) {
		DefaultSingleDocumentModel document = new DefaultSingleDocumentModel(path, text);
		listOfDocuments.add(document);
		currentDocument = document;
		listener.documentAdded(document);

		JScrollPane scrollPane = new JScrollPane(document.getTextComponent());
		this.add(scrollPane);
		String name = path == null ? "(unnamed)" : path.toFile().getName();
		try {
			this.setTabComponentAt(listOfDocuments.size() - 1, new ButtonTabComponent(this, name));
		} catch (IOException e) {
			e.printStackTrace();
		}
		this.setSelectedIndex(listOfDocuments.size() - 1);
		JComponent tab = ((JComponent) this.getTabComponentAt(listOfDocuments.size() - 1));
		tab.setToolTipText(path == null ? "(unnamed)" : path.toString());
		if (frame != null) {
			frame.setTitle(name + " - JNotepad++");
		}
		ToolTipManager.sharedInstance().unregisterComponent(tab);
		getCurrentDocument().getTextComponent().requestFocusInWindow();
		return document;
	}

	/**
	 * Returns the tool tip text.
	 */

	@Override
	public String getToolTipText(MouseEvent e) {
		int index = ((TabbedPaneUI) ui).tabForCoordinate(this, e.getX(), e.getY());

		if (index != -1) {
			JComponent component = (JComponent) getTabComponentAt(index);
			return component.getToolTipText();
		}

		return super.getToolTipText(e);

	};

	/**
	 * Returns the currently opened document.
	 */

	public SingleDocumentModel getCurrentDocument() {
		return this.currentDocument;
	}

	/**
	 * Opens an already existing document. If the document is already opened in the
	 * pane, it switches to it. Shows the error message if there was a problem with
	 * reading the document.
	 * 
	 * @param path path of the document to open
	 */

	public SingleDocumentModel loadDocument(Path path) {

		for (SingleDocumentModel doc : listOfDocuments) {
			if (doc.getFilePath() != null && doc.getFilePath().equals(path)) {
				this.setSelectedIndex(listOfDocuments.indexOf(doc));
				return null;
			}
		}

		byte[] okteti = null;
		try {
			okteti = Files.readAllBytes(path);
		} catch (Exception ex) {
			Object[] options = { "OK" };
			JOptionPane.showOptionDialog(null, "Error while reading the file.", "Error", JOptionPane.DEFAULT_OPTION,
					JOptionPane.ERROR_MESSAGE, null, options, options[0]);
		}
		SingleDocumentModel document = createDocument(path, new String(okteti));
		listener.documentAdded(document);
		return document;
	}

	/**
	 * Saves a document to a specified path. Specified path is chosen by the user
	 * with a prompt.
	 * 
	 * @param model   model to be saved
	 * @param newPath path to be saved to
	 */

	public void saveAsDocument(SingleDocumentModel model, Path newPath) {
		Path path;
		JFileChooser jfc = new JFileChooser();
		jfc.setDialogTitle("Save As");
		if (jfc.showSaveDialog(DefaultMultipleDocumentModel.this) != JFileChooser.APPROVE_OPTION) {
			return;
		}
		path = jfc.getSelectedFile().toPath();
		if (checkIfDocOpened(model, path)) {
			return;
		}
		saveDocument(model, path);
	}

	/**
	 * Checks if the document is already opened.
	 * 
	 * @param model current model which we want to save
	 * @param path  path of the model where we want to save
	 * @return true if the document is already opened
	 */

	private boolean checkIfDocOpened(SingleDocumentModel model, Path path) {
		for (SingleDocumentModel doc : listOfDocuments) {
			if (doc.getFilePath() != null && doc.getFilePath().equals(path) && !doc.equals(model)) {
				Object[] options = { "OK" };
				JOptionPane.showOptionDialog(null, "The specified file is already opened in another tab.", "Error",
						JOptionPane.DEFAULT_OPTION, JOptionPane.ERROR_MESSAGE, null, options, options[0]);
				return true;
			}
		}
		return false;
	}

	/**
	 * Saves a document to a specified path. If the path doesn't exits, it works the
	 * same as save as, and if the path exists it saves it without asking for a
	 * path.
	 * 
	 * @param model
	 * @param newPath
	 */

	public void saveDocument(SingleDocumentModel model, Path newPath) {
		Path path;
		if (getCurrentDocument().getFilePath() == null) {
			JFileChooser jfc = new JFileChooser();
			jfc.setDialogTitle("Save");
			if (jfc.showSaveDialog(DefaultMultipleDocumentModel.this) != JFileChooser.APPROVE_OPTION) {
				return;
			}
			path = jfc.getSelectedFile().toPath();
			if (checkIfDocOpened(model, path)) {
				return;
			}
		} else {
			path = getCurrentDocument().getFilePath();
		}
		byte[] podatci = currentDocument.getTextComponent().getText().getBytes(StandardCharsets.UTF_8);

		try {
			Files.write(path, podatci);
		} catch (IOException e) {
			Object[] options = { "OK" };
			JOptionPane.showOptionDialog(null, "Error while writing to file.", "Error", JOptionPane.DEFAULT_OPTION,
					JOptionPane.ERROR_MESSAGE, null, options, options[0]);
			return;
		}
		currentDocument.setFilePath(path);
		currentDocument.setModified(false);
	}

	/**
	 * Checks if the document is modified while trying to save. If the document is
	 * modified it show an error message to the user and returns true.
	 * 
	 * @param model document to check if it's modified
	 * @return true if the document is modified
	 */

	public boolean checkIfModifiedDialog(SingleDocumentModel model) {
		if (model.isModified()) {
			Object[] options = { "Yes", "No", "Cancel" };
			Path path = model.getFilePath();
			String pathName = path == null ? "(unnamed)" : path.toFile().getName();
			int choice = JOptionPane.showOptionDialog(null, "Save file " + pathName + "?", "Save",
					JOptionPane.YES_NO_CANCEL_OPTION, JOptionPane.WARNING_MESSAGE, null, options, options[0]);

			if (choice == JOptionPane.YES_OPTION) {
				saveDocument(getCurrentDocument(), null);
				return true;
			}
			if (choice == JOptionPane.CANCEL_OPTION || choice == JOptionPane.CLOSED_OPTION) {
				return false;
			}
		}
		return true;
	}

	/**
	 * Closes specified document and removes it from the pane. If the chosen
	 * document to close is the last one in the notepad, the method creates a new
	 * blank document.
	 * 
	 * @param specified document
	 */

	public void closeDocument(SingleDocumentModel model) {
		int index = this.getSelectedIndex();
		if (index != -1) {
			if (checkIfModifiedDialog(getCurrentDocument())) {
				listOfDocuments.remove(getCurrentDocument());
				this.remove(index);
				if (listOfDocuments.size() != 0) {
					currentDocument = listOfDocuments.get(0);
					this.setSelectedIndex(0);

				} else {
					currentDocument = createNewDocument();
				}
			}

		}

	}

	/**
	 * {@inheritDoc}
	 */
	
	public void addMultipleDocumentListener(MultipleDocumentListener l) {
		this.listener = l;

	}

	/**
	 * {@inheritDoc}
	 */
	
	public void removeMultipleDocumentListener(MultipleDocumentListener l) {
		this.listener = null;
	}

	/**
	 * {@inheritDoc}
	 */
	
	public int getNumberOfDocuments() {
		return listOfDocuments.size();
	}

	/**
	 * {@inheritDoc}
	 */
	
	public SingleDocumentModel getDocument(int index) {
		return listOfDocuments.get(index);
	}

	/**
	 * Custom listener class for the multiple document model.
	 * 
	 * @author Ivan Jukić
	 *
	 */
	
	private class multipleListener implements MultipleDocumentListener {

		/**
		 * {@inheritDoc}
		 */
		
		@Override
		public void currentDocumentChanged(SingleDocumentModel previousModel, SingleDocumentModel currentModel) {
			((DefaultSingleDocumentModel) getCurrentDocument()).getListener()
					.documentModifyStatusUpdated(getCurrentDocument());
			try {
				((DefaultSingleDocumentModel) getCurrentDocument()).getListener()
						.documentFilePathUpdated(getCurrentDocument());
			} catch (NullPointerException ex) {

			}
			((DefaultSingleDocumentModel) getCurrentDocument()).getListener()
					.documentCaretUpdated(getCurrentDocument());

		}

		/**
		 * {@inheritDoc}
		 */
		
		@Override
		public void documentAdded(SingleDocumentModel model) {
			getCurrentDocument().addSingleDocumentListener(new singleDocumentListener(frame));

		}

		/**
		 * {@inheritDoc}
		 */
		
		@Override
		public void documentRemoved(SingleDocumentModel model) {

		}

	}

}
