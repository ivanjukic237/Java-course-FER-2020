package hr.fer.zemris.java.hw11.localization;

import java.util.ArrayList;
import java.util.List;

/**
 * Abstract class that provides the localization for listeners.
 * 
 * @author Ivan Jukić
 *
 */

public abstract class AbstractLocalizationProvider implements ILocalizationProvider {

	/**
	 * Holds all listeners that needs to be localized.
	 */
	
	private List<ILocalizationListener> listeners = new ArrayList<>();

	/**
	 * {@inheritDoc}
	 */
	
	@Override
	public void addLocalizationListener(ILocalizationListener localizationListener) {
		listeners.add(localizationListener);
	}

	/**
	 * {@inheritDoc}
	 */
	
	@Override
	public void removeLocalizationListener(ILocalizationListener localizationListener) {
		listeners.remove(localizationListener);

	}

	/**
	 * Returns the string to be translated.
	 */
	
	public abstract String getString(String toTranslate);

	/**
	 * Notifies all listeners that the localization changed.
	 */
	
	public void fire() {
		for (ILocalizationListener l : listeners) {
			l.localizationChanged();
		}
	}

}
