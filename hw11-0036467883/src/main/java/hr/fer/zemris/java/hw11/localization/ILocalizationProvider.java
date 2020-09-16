package hr.fer.zemris.java.hw11.localization;

/**
 * Model of the localization provider that adds and removes the listener, and
 * returns the text to be localized.
 * 
 * @author Ivan Jukić
 *
 */

public interface ILocalizationProvider {

	/**
	 * Adds the localization listener
	 * 
	 * @param localizationListener localization listener
	 */

	void addLocalizationListener(ILocalizationListener localizationListener);

	/**
	 * Removes the localization listener.
	 * 
	 * @param localizationListener localization listener
	 */

	void removeLocalizationListener(ILocalizationListener localizationListener);

	/**
	 * Returns the translated text.
	 * 
	 * @param translation text to be translated
	 * @return translated text
	 */

	String getString(String translation);
}
