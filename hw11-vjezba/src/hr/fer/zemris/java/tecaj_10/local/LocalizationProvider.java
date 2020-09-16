package hr.fer.zemris.java.tecaj_10.local;

import java.util.Locale;
import java.util.ResourceBundle;

public class LocalizationProvider extends AbstractLocalizationProvider {

	public String language;
	public ResourceBundle bundle;
	private final static LocalizationProvider instance = new LocalizationProvider();

	public static LocalizationProvider getInstance() {
		return instance;
	}

	public void setLanguage(String language) {
		this.language = language;
		Locale locale = Locale.forLanguageTag(language);
		this.bundle = ResourceBundle.getBundle("hr.fer.zemris.java.hw08.vjezba.prijevodi", locale);
	}

	@Override
	public String getString(String toTranslate) {
		try {
			return bundle.getString(toTranslate);
		} catch(java.util.MissingResourceException ex) {
			setLanguage("en");
			return bundle.getString(toTranslate);
		}
		
	}
}
