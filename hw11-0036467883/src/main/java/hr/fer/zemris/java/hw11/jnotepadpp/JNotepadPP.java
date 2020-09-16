package hr.fer.zemris.java.hw11.jnotepadpp;

import java.awt.BorderLayout;
import java.awt.event.ActionEvent;
import java.awt.event.KeyEvent;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.io.File;
import java.nio.file.Files;
import java.nio.file.Path;
import java.text.Collator;
import java.util.Arrays;
import java.util.Iterator;
import java.util.List;
import java.util.Locale;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import javax.swing.AbstractAction;
import javax.swing.Action;
import javax.swing.JButton;
import javax.swing.JFileChooser;
import javax.swing.JFrame;
import javax.swing.JMenu;
import javax.swing.JMenuBar;
import javax.swing.JMenuItem;
import javax.swing.JOptionPane;
import javax.swing.JTextArea;
import javax.swing.JToolBar;
import javax.swing.KeyStroke;
import javax.swing.SwingUtilities;
import javax.swing.UIManager;
import javax.swing.WindowConstants;
import javax.swing.text.DefaultEditorKit;

import hr.fer.zemris.java.hw11.localization.*;

/**
 * Class represents the frame of the notepad. It creates all of the menus,
 * buttons, toolbar and status bar. It creates the localized action listeners
 * for all of the buttons.
 * 
 * @author Ivan Jukić
 *
 */

public class JNotepadPP extends JFrame {

	private static final long serialVersionUID = 1L;
	/**
	 * Custom model for document handling.
	 */
	static DefaultMultipleDocumentModel multipleDocumentModel;
	/**
	 * Localization provider that handles localization of the buttons.
	 */
	private FormLocalizationProvider flp = new FormLocalizationProvider(LocalizationProvider.getInstance(), this);
	/**
	 * Static save button in the menu.
	 */
	private static JMenuItem saveButton;
	/**
	 * Static save button in the toolbar.
	 */
	private static JButton save;
	/**
	 * Static status bar.
	 */
	private static StatusBar statusBar;
	/**
	 * Static to uppercase button.
	 */
	private static JMenuItem toUppercaseButton;
	/**
	 * Static to lowercase button.
	 */
	private static JMenuItem toLowercaseButton;
	/**
	 * Static invert case button.
	 */
	private static JMenuItem invertCaseButton;
	/**
	 * Static collator used for changing cases in regards of the localized language.
	 */
	private static Collator collator;
	/**
	 * Static sort descending button.
	 */
	private static JMenuItem sortDescending;
	/**
	 * Static sort ascending button.
	 */
	private static JMenuItem sortAscending;
	/**
	 * Static unique button.
	 */
	private static JMenuItem unique;

	/**
	 * Constructor sets the size, location and initial title of the frame. It
	 * creates a window listener that checks unsaved documents in the notepad. It
	 * initializes the GUI elements.
	 */

	private static JMenuItem deleteRegularExpression;
	
	public JNotepadPP() {
		setDefaultCloseOperation(WindowConstants.DO_NOTHING_ON_CLOSE);
		setSize(800, 600);
		setLocationRelativeTo(null);
		setTitle("(unnamed) - JNotepad++");
		this.addWindowListener(new WindowAdapter() {

			@Override
			public void windowClosing(WindowEvent e) {
				checkExit();
			}
		});

		initGUI();

	}

	/**
	 * Returns the FormLocalizationProvider.
	 * 
	 * @return
	 */

	public FormLocalizationProvider getFlp() {
		return this.flp;
	}

	/**
	 * Checks if any of the open documents was modified and shows dialog option to
	 * user. It's used when the user wants to exit the application.
	 */

	private void checkExit() {
		boolean isModified = false;
		Iterator<SingleDocumentModel> iter = multipleDocumentModel.iterator();
		while (iter.hasNext()) {
			SingleDocumentModel nextModel = iter.next();
			if (nextModel.isModified()) {
				isModified = true;
				break;
			}
		}
		if (isModified) {
			Object[] options = { "Yes", "No", "Cancel" };
			JOptionPane p = new OptionsLocalizableAction("There are unsaved documents.\nDo you wish to save the changes?", flp, "Save");
			@SuppressWarnings("static-access")
			int choice = p.showOptionDialog(null,
					p.getMessage().toString(), "Save",
					JOptionPane.YES_NO_CANCEL_OPTION, JOptionPane.INFORMATION_MESSAGE, null, options, options[0]);
			if (choice == JOptionPane.YES_OPTION) {
				Iterator<SingleDocumentModel> iter1 = multipleDocumentModel.iterator();
				while (iter1.hasNext()) {
					DefaultSingleDocumentModel nextModel = (DefaultSingleDocumentModel) iter1.next();
					multipleDocumentModel.checkIfModifiedDialog(nextModel);
				}
				dispose();
			} else if (choice == JOptionPane.NO_OPTION) {
				dispose();
			}
		} else {
			dispose();
		}
	}

	/**
	 * Initializes the GUI elements. It creates the multiple document model, menus,
	 * toolbar and status bar. Layout used is BorderLayout.
	 */

	private void initGUI() {

		this.getContentPane().setLayout(new BorderLayout());
		multipleDocumentModel = new DefaultMultipleDocumentModel();
		multipleDocumentModel.setFrame(this);
		this.getContentPane().add(multipleDocumentModel, BorderLayout.CENTER);
		createMenus();
		createToolbars();
		createStatusBar();
	}

	/**
	 * Action used by new document buttons in menu and toolbar. It creates a new
	 * blank document.
	 */

	LocalizableAction newDocButtonLocalizableAction = new LocalizableAction("New", flp, "Adds a new blank document.") {

		private static final long serialVersionUID = 1L;

		@Override
		public void actionPerformed(ActionEvent e) {
			multipleDocumentModel.createNewDocument();

		}

	};

	/**
	 * Action used by the close buttons in menu, toolbar and by the x button on the
	 * tab. Closes the current opened tab.
	 */

	LocalizableAction closeCurrentDocLocalizableAction = new LocalizableAction("Close", flp,
			"Closes the current document.") {

		private static final long serialVersionUID = 1L;

		@Override
		public void actionPerformed(ActionEvent e) {

			multipleDocumentModel.closeDocument(multipleDocumentModel.getCurrentDocument());

		}

	};

	/**
	 * Action that saves the current opened document.
	 */

	LocalizableAction saveDocLocalizableAction = new LocalizableAction("Save", flp, "Saves the current document.") {

		private static final long serialVersionUID = 1L;

		@Override
		public void actionPerformed(ActionEvent e) {
			multipleDocumentModel.saveDocument(multipleDocumentModel.getCurrentDocument(), null);
		}

	};

	/**
	 * Action that opens a document with a specified path. If the path doesn't exits
	 * it shows a error message to the user.
	 */

	LocalizableAction loadDocAction = new LocalizableAction("Open...", flp, "Opens an existing document.") {

		private static final long serialVersionUID = 1L;

		@Override
		public void actionPerformed(ActionEvent e) {

			JFileChooser fc = new JFileChooser();
			fc.setDialogTitle("Open file");
			if (fc.showOpenDialog(JNotepadPP.this) != JFileChooser.APPROVE_OPTION) {
				return;
			}
			File fileName = fc.getSelectedFile();
			Path filePath = fileName.toPath();
			if (!Files.isReadable(filePath)) {
				Object[] options = { "OK" };
				JOptionPane.showOptionDialog(null, "File " + fileName.getAbsolutePath() + " doesn't exist!", "Error",
						JOptionPane.DEFAULT_OPTION, JOptionPane.ERROR_MESSAGE, null, options, options[0]);
				return;
			}
			multipleDocumentModel.loadDocument(filePath);

		}

	};

	/**
	 * Action that saves the current opened document on a specified path.
	 */

	LocalizableAction saveAsDocLocalizableAction = new LocalizableAction("Save As...", flp,
			"Saves a copy of the current document created under a specified path.") {

		private static final long serialVersionUID = 1L;

		@Override
		public void actionPerformed(ActionEvent e) {
			multipleDocumentModel.saveAsDocument(multipleDocumentModel.getCurrentDocument(), null);

		}

	};

	/**
	 * Action that exits the application. Action checks if any unsaved documents are
	 * left in the notepad.
	 */

	LocalizableAction exitLocalizableAction = new LocalizableAction("Exit", flp, "Exits the application.") {

		private static final long serialVersionUID = 1L;

		@Override
		public void actionPerformed(ActionEvent e) {
			checkExit();
		}

	};

	/**
	 * Action that checks the statistics of the current opened document. Statistics
	 * include the number of characters, number of non-blank characters and number
	 * of lines.
	 */

	LocalizableAction statisticsLocalizableAction = new LocalizableAction("Get Statistics", flp,
			"Gets the statistics of this document.") {

		private static final long serialVersionUID = 1L;

		@Override
		public void actionPerformed(ActionEvent e) {
			StatisticalInfo info = new StatisticalInfo(
					multipleDocumentModel.getCurrentDocument().getTextComponent().getText());
			Object[] options = { "OK" };
			JOptionPane.showOptionDialog(null,
					String.format("Your document has %d characters, %d non-blank characters and %d lines.",
							info.getNumberOfCharacters(), info.getNumberOfCharactersWithoutBlanks(),
							info.getNumberOfLines()),
					"Statistics", JOptionPane.DEFAULT_OPTION, JOptionPane.PLAIN_MESSAGE, null, options, options[0]);

		}

	};

	/**
	 * Action that cuts the selected text and puts it in the clipboard.
	 */

	LocalizableAction cutLocalizableAction = new LocalizableAction("Cut", flp, "Cuts the selected text.") {

		private static final long serialVersionUID = 1L;

	};

	/**
	 * Action that copies the selected text and puts it in the clipboard.
	 */

	LocalizableAction copyLocalizableAction = new LocalizableAction("Copy", flp, "Copies the selected text.") {

		private static final long serialVersionUID = 1L;

	};

	/**
	 * Action that pastes the text from the clipboard.
	 */

	LocalizableAction pasteLocalizableAction = new LocalizableAction("Paste", flp, "Pastes the text from clipboard.") {

		private static final long serialVersionUID = 1L;

	};

	/**
	 * Action that changes the selected text to upper case.
	 */

	LocalizableAction toUppercaseLocalizableAction = new LocalizableAction("To Upper Case", flp,
			"Changes the selected text to upper case.") {

		private static final long serialVersionUID = 1L;

		@Override
		public void actionPerformed(ActionEvent e) {
			setTextCase((x) -> x.toUpperCase());
		}
	};

	/**
	 * Action that changes the selected text to lower case.
	 */

	LocalizableAction toLowercaseLocalizableAction = new LocalizableAction("To Lower Case", flp,
			"Changes the selected text to lower case.") {

		private static final long serialVersionUID = 1L;

		@Override
		public void actionPerformed(ActionEvent e) {
			setTextCase((x) -> x.toLowerCase());
		}
	};

	/**
	 * Action that inverts case for selected text.
	 */

	LocalizableAction invertCaseLocalizableAction = new LocalizableAction("Invert Case", flp,
			"Inverts case for selected text.") {

		private static final long serialVersionUID = 1L;

		@Override
		public void actionPerformed(ActionEvent e) {
			setTextCase((x) -> invertCase(x));
		}
	};

	/**
	 * Action that sorts the selected text in ascending order.
	 */

	LocalizableAction sortAscendingLocalizableAction = new LocalizableAction("Sort Ascending", flp,
			"Sorts the selected text in ascending order.") {

		private static final long serialVersionUID = 1L;

		@Override
		public void actionPerformed(ActionEvent e) {
			sort(true);
		}
	};

	/**
	 * Action that sorts the selected text in descending order.
	 */

	LocalizableAction sortDescendingLocalizableAction = new LocalizableAction("Sort Descending", flp,
			"Sorts the selected text in descending order.") {

		private static final long serialVersionUID = 1L;

		@Override
		public void actionPerformed(ActionEvent e) {
			sort(false);
		}
	};

	/**
	 * Action that removes from selection all lines which are duplicates.
	 */

	LocalizableAction uniqueLocalizableAction = new LocalizableAction("Unique", flp,
			"Removes from selection all lines which are duplicates") {

		private static final long serialVersionUID = 1L;

		@Override
		public void actionPerformed(ActionEvent e) {
			removeDuplicateLines();
		}
	};

	LocalizableAction deleteRegularExpressionAction = new LocalizableAction("Delete lines which...", flp,
			"Removes lines which match the given regular expression.") {

	
				private static final long serialVersionUID = 1L;
				
				@Override
				public void actionPerformed(ActionEvent e) {
					removeWithRegularExpression();
				}
	};
			
	
	private void removeWithRegularExpression() {
		JTextArea textArea = multipleDocumentModel.getCurrentDocument().getTextComponent();

		String text = textArea.getText();
		String pattern = JOptionPane.showInputDialog("");
        if(pattern != null) {
        	String[] lines = text.split("\n");
    		
    		Pattern p = Pattern.compile(pattern, Pattern.CASE_INSENSITIVE);
    		for(int i = 0; i < lines.length; i++) {
    			Matcher m = p.matcher(lines[i]);
    			if(m.find()) {
    				lines[i] = null;
    			}
    		}
    		StringBuilder sb = new StringBuilder();
    		for(String line : lines) {
    			if(line != null) {
    				sb.append(line + "\n");
    			}
    		}
    		textArea.setText(sb.toString());
        }	
	}
	
	
	/**
	 * Method removes duplicate lines of the selected text. First instance of
	 * duplicate lines is not deleted.
	 */

	private void removeDuplicateLines() {
		JTextArea textArea = multipleDocumentModel.getCurrentDocument().getTextComponent();
		String text = textArea.getText();
		StatisticalInfo info = new StatisticalInfo(text);

		int startRow = info.calculateRowNumber(textArea.getCaret().getDot()) - 1;
		int endRow = info.calculateRowNumber(textArea.getCaret().getMark()) - 1;
		int temp = endRow;
		// endRow and startRow should be the same regardless of which way the text is
		// selected
		endRow = startRow > endRow ? startRow : endRow;
		startRow = endRow > startRow ? startRow : temp;

		String[] lines = text.split("\\r?\\n");
		String[] selectedLines = new String[endRow - startRow + 1];

		int counter = 0;
		for (int i = startRow; i <= endRow; i++) {
			selectedLines[counter] = lines[i];
			counter++;
		}

		for (int i = 0; i < selectedLines.length; i++) {
			if (selectedLines[i] != null) {
				for (int j = i + 1; j < selectedLines.length; j++) {
					if (selectedLines[i].equals(selectedLines[j])) {
						selectedLines[j] = null;
					}
				}
			}

		}
		counter = 0;
		for (int i = startRow; i <= endRow; i++) {

			lines[i] = selectedLines[counter];

			counter++;
		}
		StringBuilder sb = new StringBuilder();
		for (int i = 0; i < lines.length; i++) {
			if (lines[i] != null) {
				sb.append(lines[i] + "\n");
			}
		}

		textArea.setText(sb.toString());
	}

	/**
	 * Sorts the document text in ascending or descending order.
	 * 
	 * @param isAscending true if you want to sort in ascending order
	 */

	private void sort(boolean isAscending) {
		JTextArea textArea = multipleDocumentModel.getCurrentDocument().getTextComponent();
		String text = textArea.getText();
		StatisticalInfo info = new StatisticalInfo(text);

		int startRow = info.calculateRowNumber(textArea.getCaret().getDot()) - 1;
		int endRow = info.calculateRowNumber(textArea.getCaret().getMark()) - 1;
		int temp = endRow;
		// endRow and startRow should be the same regardless of which way the text is
		// selected
		endRow = startRow > endRow ? startRow : endRow;
		startRow = endRow > startRow ? startRow : temp;

		String[] lines = text.split("\\r?\\n");
		String[] selectedLines = new String[endRow - startRow + 1];

		int counter = 0;
		for (int i = startRow; i <= endRow; i++) {
			selectedLines[counter] = lines[i];
			counter++;
		}
		List<String> selectedList = Arrays.asList(selectedLines);
		selectedList.sort(collator);

		counter = 0;
		if (isAscending) {
			for (int i = startRow; i <= endRow; i++) {
				lines[i] = selectedList.get(counter);
				counter++;
			}
		} else {
			counter = selectedList.size() - 1;
			for (int i = startRow; i <= endRow; i++) {
				lines[i] = selectedList.get(counter);
				counter--;
			}
		}

		StringBuilder sb = new StringBuilder();
		for (int i = 0; i < lines.length; i++) {
			sb.append(lines[i] + "\n");
		}

		textArea.setText(sb.toString());

	}

	/**
	 * Helper functional interface to be used in inverting cases.
	 * 
	 * @author Ivan Jukić
	 *
	 */

	private interface setCase {
		String apply(String text);
	}

	/**
	 * Function inverts case for the given String.
	 * 
	 * @param text
	 * @return
	 */

	private String invertCase(String text) {
		int len = text.length();
		StringBuilder sb = new StringBuilder();
		for (int i = 0; i < len; i++) {
			Character ch = text.charAt(i);
			if (Character.isLowerCase(ch)) {
				sb.append(Character.toUpperCase(ch));
			} else {
				sb.append(Character.toLowerCase(ch));
			}
		}
		return sb.toString();
	}

	/**
	 * Changes the selected text in the document according to some function.
	 * 
	 * @param function invertCase(String), toUppercase(String) or
	 *                 toLowerCase(String) functions are used
	 */

	private void setTextCase(setCase function) {
		JTextArea textArea = multipleDocumentModel.getCurrentDocument().getTextComponent();
		String text = textArea.getText();
		int start = textArea.getCaret().getDot();
		int end = textArea.getCaret().getMark();
		String leftPart = text.substring(0, start);
		String rightPart = text.substring(end, text.length());
		String middlePart = text.substring(start, end);
		textArea.setText(leftPart + function.apply(middlePart) + rightPart);
	}

	/**
	 * Creates the status bar.
	 */

	private void createStatusBar() {
		statusBar = new StatusBar(multipleDocumentModel.getCurrentDocument(), this);
		this.getContentPane().add(statusBar, BorderLayout.PAGE_END);

	}

	/**
	 * Creates the toolbar with new document, close, open, save, save as, statistics
	 * and exit buttons.
	 */

	private void createToolbars() {
		JToolBar toolBar = new JToolBar();
		toolBar.setFloatable(true);

		JButton newDoc = new JButton(newDocButtonLocalizableAction);

		JButton close = new JButton(closeCurrentDocLocalizableAction);

		JButton open = new JButton(loadDocAction);

		save = new JButton(saveDocLocalizableAction);
		save.setEnabled(false);

		JButton statisticalInfo = new JButton(statisticsLocalizableAction);
		JButton exit = new JButton(exitLocalizableAction);

		JButton saveAs = new JButton(saveAsDocLocalizableAction);

		toolBar.add(newDoc);
		toolBar.add(open);
		toolBar.add(save);
		toolBar.add(saveAs);
		toolBar.add(close);
		toolBar.add(statisticalInfo);
		toolBar.add(exit);

		this.getContentPane().add(toolBar, BorderLayout.PAGE_START);
	}

	/**
	 * Creates the file, edit, tools, languages, statistics menus.
	 */

	private void createMenus() {
		JMenuBar menuBar = new JMenuBar();
		JMenu fileMenu = new JMenu(new LocalizableAction("File", flp, null) {

			private static final long serialVersionUID = 1L;

		});

		// new document button

		newDocButtonLocalizableAction.putValue(Action.ACCELERATOR_KEY, KeyStroke.getKeyStroke("control N"));
		newDocButtonLocalizableAction.putValue(Action.MNEMONIC_KEY, KeyEvent.VK_N);

		JMenuItem newDocumentButton = new JMenuItem(newDocButtonLocalizableAction);
		fileMenu.add(newDocumentButton);

		// close current document button

		closeCurrentDocLocalizableAction.putValue(Action.ACCELERATOR_KEY, KeyStroke.getKeyStroke("control W"));
		closeCurrentDocLocalizableAction.putValue(Action.MNEMONIC_KEY, KeyEvent.VK_W);

		JMenuItem closeCurrentDocument = new JMenuItem(closeCurrentDocLocalizableAction);
		fileMenu.add(closeCurrentDocument);

		// save document button

		saveDocLocalizableAction.putValue(Action.ACCELERATOR_KEY, KeyStroke.getKeyStroke("control S"));
		saveDocLocalizableAction.putValue(Action.MNEMONIC_KEY, KeyEvent.VK_S);

		saveButton = new JMenuItem(saveDocLocalizableAction);
		saveButton.setEnabled(false);

		fileMenu.add(saveButton);

		// load document button

		loadDocAction.putValue(Action.ACCELERATOR_KEY, KeyStroke.getKeyStroke("control O"));
		loadDocAction.putValue(Action.MNEMONIC_KEY, KeyEvent.VK_O);
		JMenuItem loadButton = new JMenuItem(loadDocAction);
		fileMenu.add(loadButton);

		// save as document

		saveAsDocLocalizableAction.putValue(Action.ACCELERATOR_KEY, KeyStroke.getKeyStroke("control alt S"));
		saveAsDocLocalizableAction.putValue(Action.MNEMONIC_KEY, KeyEvent.VK_S);
		JMenuItem saveAsButton = new JMenuItem(saveAsDocLocalizableAction);
		fileMenu.add(saveAsButton);

		// exit the application

		exitLocalizableAction.putValue(Action.ACCELERATOR_KEY, KeyStroke.getKeyStroke("alt F4"));
		exitLocalizableAction.putValue(Action.MNEMONIC_KEY, KeyEvent.VK_F4);
		JMenuItem exitButton = new JMenuItem(exitLocalizableAction);
		fileMenu.add(exitButton);

		menuBar.add(fileMenu);

		// edit menu
		JMenu editMenu = new JMenu(new LocalizableAction("Edit", flp, null) {

			private static final long serialVersionUID = 1L;

		});

		JMenuItem cutButton = new JMenuItem(cutLocalizableAction);
		cutButton.addActionListener(new DefaultEditorKit.CutAction());
		cutButton.setAccelerator(KeyStroke.getKeyStroke("control X"));
		cutButton.setMnemonic(KeyEvent.VK_X);

		JMenuItem copyButton = new JMenuItem(copyLocalizableAction);
		copyButton.addActionListener(new DefaultEditorKit.CopyAction());
		copyButton.setAccelerator(KeyStroke.getKeyStroke("control C"));
		copyButton.setMnemonic(KeyEvent.VK_C);

		JMenuItem pasteButton = new JMenuItem(pasteLocalizableAction);
		pasteButton.addActionListener(new DefaultEditorKit.PasteAction());
		pasteButton.setAccelerator(KeyStroke.getKeyStroke("control V"));
		pasteButton.setMnemonic(KeyEvent.VK_V);

		editMenu.add(cutButton);
		editMenu.add(copyButton);
		editMenu.add(pasteButton);
		menuBar.add(editMenu);

		// tools menu
		JMenu toolsMenu = new JMenu(new LocalizableAction("Tools", flp, null) {
			private static final long serialVersionUID = 1L;
		});

		JMenu changeCaseMenu = new JMenu(new LocalizableAction("Change Case", flp, null) {
			private static final long serialVersionUID = 1L;
		});

		JMenu sortMenu = new JMenu(new LocalizableAction("Sort", flp, null) {
			private static final long serialVersionUID = 1L;
		});

		toolsMenu.add(changeCaseMenu);
		toolsMenu.add(sortMenu);

		sortAscending = new JMenuItem(sortAscendingLocalizableAction);
		sortAscending.setEnabled(false);
		sortMenu.add(sortAscending);

		sortDescending = new JMenuItem(sortDescendingLocalizableAction);
		sortDescending.setEnabled(false);

		sortMenu.add(sortDescending);

		unique = new JMenuItem(uniqueLocalizableAction);
		toolsMenu.add(unique);
		unique.setEnabled(false);

		deleteRegularExpression = new JMenuItem(deleteRegularExpressionAction);
		toolsMenu.add(deleteRegularExpression);
		
		toUppercaseButton = new JMenuItem(toUppercaseLocalizableAction);
		toLowercaseButton = new JMenuItem(toLowercaseLocalizableAction);
		invertCaseButton = new JMenuItem(invertCaseLocalizableAction);
		toUppercaseButton.setEnabled(false);
		toLowercaseButton.setEnabled(false);
		invertCaseButton.setEnabled(false);

		changeCaseMenu.add(toUppercaseButton);
		changeCaseMenu.add(toLowercaseButton);
		changeCaseMenu.add(invertCaseButton);

		menuBar.add(toolsMenu);

		// languages menu
		JMenu languagesMenu = new JMenu(new LocalizableAction("Languages", flp, null) {

			private static final long serialVersionUID = 1L;

		});

		JMenuItem enLanguage = new JMenuItem(setEN);
		enLanguage.setText("English");
		JMenuItem hrLanguage = new JMenuItem(setHR);
		hrLanguage.setText("Hrvatski");
		JMenuItem deLanguage = new JMenuItem(setDE);
		deLanguage.setText("Deutsch");

		languagesMenu.add(enLanguage);
		languagesMenu.add(hrLanguage);
		languagesMenu.add(deLanguage);
		menuBar.add(languagesMenu);

		// statistics menu
		JMenu statisticMenu = new JMenu(new LocalizableAction("Statistics", flp, null) {

			private static final long serialVersionUID = 1L;

		});

		JMenuItem getStatistics = new JMenuItem(statisticsLocalizableAction);
		statisticMenu.add(getStatistics);
		menuBar.add(statisticMenu);

		this.setJMenuBar(menuBar);
	}

	/**
	 * Action that sets the language to English.
	 */

	private Action setEN = new AbstractAction() {

		private static final long serialVersionUID = 1L;

		@Override
		public void actionPerformed(ActionEvent e) {
			collator = Collator.getInstance(new Locale("en"));
			LocalizationProvider.getInstance().setLanguage("en");
			LocalizationProvider.getInstance().fire();
		}
	};

	/**
	 * Action that sets the language to German.
	 */

	private Action setDE = new AbstractAction() {

		private static final long serialVersionUID = 1L;

		@Override
		public void actionPerformed(ActionEvent e) {
			collator = Collator.getInstance(new Locale("de"));
			LocalizationProvider.getInstance().setLanguage("de");
			LocalizationProvider.getInstance().fire();
		}
	};

	/**
	 * Action that sets the language to Croatian.
	 */

	private Action setHR = new AbstractAction() {

		private static final long serialVersionUID = 1L;

		@Override
		public void actionPerformed(ActionEvent e) {
			collator = Collator.getInstance(new Locale("hr"));
			LocalizationProvider.getInstance().setLanguage("hr");
			LocalizationProvider.getInstance().fire();
		}
	};

	/**
	 * Custom listener for the SingleDocumentModel class. It checks a couple of
	 * things. If the document is modified, the save button is enabled, and if it's
	 * not the save button is disabled. If the document path is updated, the title
	 * changes to the path name. If the document caret changes places, the status
	 * bar is modified. If the selection is made on the text in the document, then
	 * the buttons which edit the selected text are enabled.
	 * 
	 * @author Ivan Jukić
	 *
	 */

	static class singleDocumentListener implements SingleDocumentListener {

		JMenuItem button;
		JFrame frame;

		/**
		 * Sets the frame used by this class.
		 * 
		 * @param frame frame used by this class
		 */

		public singleDocumentListener(JFrame frame) {
			this.frame = frame;
		}

		/**
		 * If the current document is modified, save buttons are enabled and the save
		 * icon is swapped.
		 */

		@Override
		public void documentModifyStatusUpdated(SingleDocumentModel model) {
			saveButton.setEnabled(model.isModified());
			save.setEnabled(model.isModified());
			try {
				((ButtonTabComponent) multipleDocumentModel.getTabComponentAt(multipleDocumentModel.getSelectedIndex()))
						.swapIconLabel(model.isModified());
			} catch (NullPointerException ex) {

			}

		}

		/**
		 * If the file path is updated, title and tab are edited to hold the path name
		 * and the tooltip text is edited to hold the full path.
		 */

		@Override
		public void documentFilePathUpdated(SingleDocumentModel model) {
			Path path = model.getFilePath();
			String name = path == null ? "(unnamed)" : model.getFilePath().toFile().getName();
			((ButtonTabComponent) multipleDocumentModel.getTabComponentAt(multipleDocumentModel.getSelectedIndex()))
					.changeTabName(name);
			((ButtonTabComponent) multipleDocumentModel.getTabComponentAt(multipleDocumentModel.getSelectedIndex()))
					.setToolTipText(path == null ? "(unnamed)" : model.getFilePath().toString());
			frame.setTitle(name + " - " + "JNotepad++");

		}

		/**
		 * If the current document caret is updated the status bar is modified.
		 */

		@Override
		public void documentCaretUpdated(SingleDocumentModel model) {
			statusBar.update(multipleDocumentModel.getCurrentDocument());

		}

		/**
		 * If the text selection is made in the current document, all buttons that edit
		 * the selected text are enabled.
		 */

		@Override
		public void documentSelectionMade(SingleDocumentModel model) {
			toUppercaseButton.setEnabled(true);
			toLowercaseButton.setEnabled(true);
			invertCaseButton.setEnabled(true);

			sortDescending.setEnabled(true);
			sortAscending.setEnabled(true);

			unique.setEnabled(true);
		}

		/**
		 * If no text selection is made, all buttons that edit the selected text are
		 * disabled.
		 */

		@Override
		public void documentSelectionNotMade(SingleDocumentModel model) {
			toUppercaseButton.setEnabled(false);
			toLowercaseButton.setEnabled(false);
			invertCaseButton.setEnabled(false);

			sortDescending.setEnabled(false);
			sortAscending.setEnabled(false);

			unique.setEnabled(false);

		}

	}

	/**
	 * Starts the application. Removes the boldMetal fon and sets the default
	 * language to English.
	 * 
	 * @param args command line argument (not used)
	 */

	public static void main(String[] args) {
		SwingUtilities.invokeLater(new Runnable() {
			public void run() {
				LocalizationProvider.getInstance().setLanguage("en");
				UIManager.put("swing.boldMetal", Boolean.FALSE);
				new JNotepadPP().setVisible(true);
			}
		});
	}
}
