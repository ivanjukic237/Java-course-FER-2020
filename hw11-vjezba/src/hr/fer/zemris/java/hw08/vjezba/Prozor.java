package hr.fer.zemris.java.hw08.vjezba;

import java.awt.BorderLayout;
import java.awt.HeadlessException;
import java.awt.event.ActionEvent;

import javax.swing.AbstractAction;
import javax.swing.Action;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JMenu;
import javax.swing.JMenuBar;
import javax.swing.JMenuItem;
import javax.swing.SwingUtilities;
import javax.swing.WindowConstants;

import hr.fer.zemris.java.tecaj_10.local.FormLocalizationProvider;
import hr.fer.zemris.java.tecaj_10.local.LocalizableAction;
import hr.fer.zemris.java.tecaj_10.local.LocalizationProvider;

public class Prozor extends JFrame {
	private static final long serialVersionUID = 1L;
	private FormLocalizationProvider flp;

	public Prozor() throws HeadlessException {
		setDefaultCloseOperation(WindowConstants.DISPOSE_ON_CLOSE);
		setLocation(500, 500);
		setSize(600, 600);
		setTitle("Demo");
		flp = new FormLocalizationProvider(LocalizationProvider.getInstance(), this);
		initGUI();
	}

	private void createActions() {
		setEN.putValue(Action.NAME, "English");
		setDE.putValue(Action.NAME, "Deutsch");
		setHR.putValue(Action.NAME, "Hrvatski");
	}

	private Action setEN = new AbstractAction() {

		private static final long serialVersionUID = 1L;

		@Override
		public void actionPerformed(ActionEvent e) {
			LocalizationProvider.getInstance().setLanguage("en");
			LocalizationProvider.getInstance().fire();
		}
	};

	private Action setDE = new AbstractAction() {

		private static final long serialVersionUID = 1L;

		@Override
		public void actionPerformed(ActionEvent e) {
			LocalizationProvider.getInstance().setLanguage("de");
			LocalizationProvider.getInstance().fire();
		}
	};

	private Action setHR = new AbstractAction() {

		private static final long serialVersionUID = 1L;

		@Override
		public void actionPerformed(ActionEvent e) {
			LocalizationProvider.getInstance().setLanguage("hr");
			LocalizationProvider.getInstance().fire();
		}
	};

	private void initGUI() {
		getContentPane().setLayout(new BorderLayout());
		JButton gumb = new JButton(new LocalizableAction("login", flp, "Ovo je kratak opis.") {

			private static final long serialVersionUID = 1L;

		});
		JButton gumb1 = new JButton(new LocalizableAction("logout", flp, "Ovo je kratak opis.") {

			private static final long serialVersionUID = 1L;

		});
		getContentPane().add(gumb, BorderLayout.CENTER);
		getContentPane().add(gumb1, BorderLayout.EAST);

		createActions();

		JMenuBar menuBar = new JMenuBar();

		JMenu languageMenu = new JMenu("Languages");
		languageMenu.add(new JMenuItem(setEN));
		languageMenu.add(new JMenuItem(setDE));
		languageMenu.add(new JMenuItem(setHR));

		menuBar.add(languageMenu);
		this.setJMenuBar(menuBar);

	}

	public static void main(String[] args) {
		if (args.length != 1) {
			System.err.println("Očekivao sam oznaku jezika kao argument!");
			System.err.println("Zadajte kao parametar hr ili en.");
			System.exit(-1);
		}
		final String jezik = args[0];
		SwingUtilities.invokeLater(new Runnable() {
			@Override
			public void run() {
				LocalizationProvider.getInstance().setLanguage(jezik);
				new Prozor().setVisible(true);
			}
		});
	}
}
