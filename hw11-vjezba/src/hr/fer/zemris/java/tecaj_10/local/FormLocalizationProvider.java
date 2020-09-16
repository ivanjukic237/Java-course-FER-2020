package hr.fer.zemris.java.tecaj_10.local;

import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;

import javax.swing.JFrame;

public class FormLocalizationProvider extends LocalizationProviderBridge {
	ILocalizationProvider localizationProvider;

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
