package hr.fer.zemris.java.webserver.workers;

import hr.fer.zemris.java.webserver.IWebWorker;
import hr.fer.zemris.java.webserver.RequestContext;

/**
 * Worker for changing the color of the home page. After the color is changed it
 * redirects the user to the color message page which shows the user a message
 * if the color was changed.
 * 
 * @author Ivan Jukić
 *
 */

public class BgColorWorker implements IWebWorker {

	/**
	 * Checks if the color parameter given by the client is a correct color. If it
	 * is then sets it as a persistant parameter. After that it calls the color
	 * message page.
	 * 
	 * @param context context of the client
	 */

	@Override
	public void processRequest(RequestContext context) throws Exception {
		if (context.getParameterNames().contains("bgcolor")) {
			String hexColor = context.getParameter("bgcolor");
			if (hexColor.matches("[0-9A-F]+") && hexColor.length() == 6) {
				context.setPersistentParameter("bgcolor", hexColor);
				context.setTemporaryParameter("updated", "");
			}
		} else {
			context.setTemporaryParameter("updated", "not ");
		}
		context.getDispatcher().dispatchRequest("/private/pages/colorMessage.smscr");

	}

}
