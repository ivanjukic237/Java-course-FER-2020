package hr.fer.zemris.java.tecaj_10.local;

public interface ILocalizationProvider {

	void addLocalizationListener(ILocalizationListener localizationListener);
	
	void removeLocalizationListener(ILocalizationListener localizationListener);
	
	String getString(String translation);
}
