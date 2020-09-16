package hr.fer.zemris.java.hw11.localization;

import java.util.Locale;
import java.util.ResourceBundle;

/**
 * Localization provider that sets the locale and bundle path for localization.
 * Class is only created once.
 * 
 * @author Ivan Jukić
 *
 */

public class LocalizationProvider extends AbstractLocalizationProvider {
	/**
	 * Language for the localization.
	 */
	public String language;
	/**
	 * Localization bundle.
	 */
	public ResourceBundle bundle;
	/**
	 * Creates the instance of the class.
	 */
	private final static LocalizationProvider instance = new LocalizationProvider();

	/**
	 * Returns the instance of the class.
	 * 
	 * @return instance of the class
	 */

	public static LocalizationProvider getInstance() {
		return instance;
	}

	/**
	 * Sets the localization language.
	 * 
	 * @param language language to be set
	 */

	public void setLanguage(String language) {
		this.language = language;
		Locale locale = Locale.forLanguageTag(language);
		this.bundle = ResourceBundle.getBundle("hr.fer.zemris.java.hw11.jnotepadpp.prijevodi", locale);
	}

	/**
	 * {@inheritDoc}
	 */
	
	@Override
	public String getString(String toTranslate) {

		return bundle.getString(toTranslate);
	}

}
