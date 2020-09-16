package hr.fer.zemris.java.webserver.workers;

import hr.fer.zemris.java.webserver.IWebWorker;
import hr.fer.zemris.java.webserver.RequestContext;

/**
 * Makes the home page via the home.smscr script. Main page contains the
 * osnovni.smscr, brojPoziva.smscr, fibonaccih.smscr scripts, hello and circle
 * workers, form to submit two numbers which then runs the zbrajanje.smcr
 * script. It also has four radio buttons which change the background color of
 * the page.
 * 
 * @author Ivan Jukić
 *
 */

public class Home implements IWebWorker {

	/**
	 * {@inheritDoc}
	 */

	@Override
	public void processRequest(RequestContext context) throws Exception {

		if (context.getPersistentParameterNames().contains("bgcolor")) {
			context.setTemporaryParameter("background", context.getPersistentParameter("bgcolor"));
		} else {
			context.setTemporaryParameter("background", "7F7F7F");
		}
		context.getDispatcher().dispatchRequest("/private/pages/home.smscr");

	}

}
