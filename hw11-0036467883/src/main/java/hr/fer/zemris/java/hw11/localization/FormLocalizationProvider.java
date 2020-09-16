package hr.fer.zemris.java.hw11.localization;

import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;

import javax.swing.JFrame;

/**
 * Class used to connect all listeners when the window is opened, and
 * disconnects it when it's closed.
 * 
 * @author Ivan Jukić
 *
 */

public class FormLocalizationProvider extends LocalizationProviderBridge {
	/**
	 * Localization provider.
	 */
	ILocalizationProvider localizationProvider;

	/**
	 * Sets the frame and localization provider used.
	 * 
	 * @param localizationProvider localization provider
	 * @param frame frame that will use localization
	 */
	
	public FormLocalizationProvider(ILocalizationProvider localizationProvider, JFrame frame) {
		super(localizationProvider);

		frame.addWindowListener(new WindowAdapter() {

			
			public void windowOpened(WindowEvent e) {
				connect();
			}

			public void windowClosed(WindowEvent e) {
				disconnect();
			}
		});
	}

}
