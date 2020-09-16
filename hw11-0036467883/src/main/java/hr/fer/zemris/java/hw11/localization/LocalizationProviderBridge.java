package hr.fer.zemris.java.hw11.localization;

/**
 * Proxy for localization provider that disconnects when the program stops.
 * 
 * @author Ivan Jukić
 *
 */

public class LocalizationProviderBridge extends AbstractLocalizationProvider {
	/**
	 * Localization provider.
	 */
	private ILocalizationProvider localizationProvider;
	/**
	 * Localization listener.
	 */
	private ILocalizationListener listener;
	/**
	 * Flag if the provider is connected.
	 */
	private boolean connected = false;

	/**
	 * Sets the provider and listener.
	 * 
	 * @param localizationProvider localization provider
	 */

	public LocalizationProviderBridge(ILocalizationProvider localizationProvider) {
		this.localizationProvider = localizationProvider;
		listener = this::fire;
	}

	/**
	 * {@inheritDoc}
	 */
	
	public void connect() {
		if (!connected) {
			localizationProvider.addLocalizationListener(listener);
			connected = true;
		} else {
			throw new IllegalStateException("Not connected.");
		}
	}

	/**
	 * {@inheritDoc}
	 */
	
	public void disconnect() {
		localizationProvider.removeLocalizationListener(listener);
		connected = false;
		System.exit(0);
	}

	/**
	 * {@inheritDoc}
	 */
	
	@Override
	public String getString(String toTranslate) {
		return localizationProvider.getString(toTranslate);
	}

}
