package hr.fer.zemris.java.tecaj_10.local;

import java.util.ArrayList;
import java.util.List;

public abstract class AbstractLocalizationProvider implements ILocalizationProvider {

	private List<ILocalizationListener> listeners = new ArrayList<>();

	public AbstractLocalizationProvider() {

	}

	@Override
	public void addLocalizationListener(ILocalizationListener localizationListener) {
		listeners.add(localizationListener);
	}

	@Override
	public void removeLocalizationListener(ILocalizationListener localizationListener) {
		listeners.remove(localizationListener);

	}

	public abstract String getString(String toTranslate);

	public void fire() {
		for (ILocalizationListener l : listeners) {
			l.localizationChanged();
		}
	}

}
