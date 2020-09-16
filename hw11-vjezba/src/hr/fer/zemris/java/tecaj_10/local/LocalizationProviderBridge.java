package hr.fer.zemris.java.tecaj_10.local;

public class LocalizationProviderBridge extends AbstractLocalizationProvider {
	
	private ILocalizationProvider localizationProvider;
    private ILocalizationListener listener;
	private boolean connected = false;
	
	public LocalizationProviderBridge(ILocalizationProvider localizationProvider) {
		this.localizationProvider = localizationProvider;
		listener = this::fire;
	}
	
	public void connect() {
		if(!connected) {
			localizationProvider.addLocalizationListener(listener);
			connected = true;
		} else {
			throw new IllegalStateException("Not connected.");
		}
	}
	
	public void disconnect() {
		localizationProvider.removeLocalizationListener(listener);
		connected = false;
		System.exit(0);
	}
	
	@Override
	public String getString(String toTranslate) {
		return localizationProvider.getString(toTranslate);
	}

}
