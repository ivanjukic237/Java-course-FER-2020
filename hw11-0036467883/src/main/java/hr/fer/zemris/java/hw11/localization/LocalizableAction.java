package hr.fer.zemris.java.hw11.localization;

import java.awt.event.ActionEvent;
import javax.swing.AbstractAction;

/**
 * Class that updates the names and short descriptions of all listeners.
 * 
 * @author Ivan Jukić
 *
 */

public abstract class LocalizableAction extends AbstractAction {

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
	private String shortDescription;

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

	public LocalizableAction(String key, ILocalizationProvider lp, String shortDescription) {
		this.key = key;
		this.lp = lp;
		this.shortDescription = shortDescription;
		this.listener = this::update;
		update();
		lp.addLocalizationListener(listener);

	}

	/**
	 * Updates the listeners.
	 */

	@Override
	public void actionPerformed(ActionEvent e) {
		update();
	}

	/**
	 * Updates the names and short descriptions with localized translateions for all
	 * listeners.
	 */

	private void update() {
		putValue(NAME, lp.getString(key));
		if (shortDescription != null) {
			putValue(SHORT_DESCRIPTION, lp.getString(shortDescription));

		}

	}

}
