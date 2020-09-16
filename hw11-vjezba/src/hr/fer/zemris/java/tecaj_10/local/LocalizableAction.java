package hr.fer.zemris.java.tecaj_10.local;

import java.awt.event.ActionEvent;
import javax.swing.AbstractAction;

public abstract class LocalizableAction extends AbstractAction {

	private static final long serialVersionUID = 1L;

	private String key;
	private ILocalizationListener listener;
	private ILocalizationProvider lp;
	private String shortDescription = null;

	public LocalizableAction(String key, ILocalizationProvider lp, String shortDescription) {
		this.key = key;
		this.lp = lp;
		this.listener = this::update;
		update();
		lp.addLocalizationListener(listener);

	}

	@Override
	public void actionPerformed(ActionEvent e) {
		update();
	}

	private void update() {
		putValue(NAME, lp.getString(key));
		if (shortDescription != null) {
			putValue(SHORT_DESCRIPTION, lp.getString(shortDescription));
		}

	}

}
