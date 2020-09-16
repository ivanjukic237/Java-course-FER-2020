package hr.fer.zemris.java.hw11.localization;

import javax.swing.JOptionPane;

public class OptionsLocalizableAction extends JOptionPane {
	
	private static final long serialVersionUID = 1L;
	/**
	 * Key in properties documents from where the translated text is taken.
	 */
	private String key;
	/**
	 * Listener for the localization.
	 */
	private ILocalizationListener listener;
	/**
	 * Localization provider.
	 */
	private ILocalizationProvider lp;
	/**
	 * Key in properties documents from where the translated text is taken. Used for
	 * short text.
	 */
	@SuppressWarnings("unused")
	private String title;

	/**
	 * Adds the localization listener to the localization provider and updates the
	 * listeners.
	 * 
	 * @param key              Key in properties documents from where the translated
	 *                         text is taken.
	 * @param lp               localization provider
	 * @param shortDescription Key in properties documents from where the translated
	 *                         text is taken. Used for short text.
	 */

	public OptionsLocalizableAction(String key, ILocalizationProvider lp, String title) {
		this.key = key;
		this.lp = lp;
		this.title = title;
		this.listener = this::update;
		update();
		lp.addLocalizationListener(listener);

	}

	/**
	 * Updates the names and short descriptions with localized translateions for all
	 * listeners.
	 */

	private void update() {
		setMessage(lp.getString(key));
		
	}

	
}
