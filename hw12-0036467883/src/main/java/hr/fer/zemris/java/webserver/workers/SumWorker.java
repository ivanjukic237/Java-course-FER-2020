package hr.fer.zemris.java.webserver.workers;

import hr.fer.zemris.java.webserver.IWebWorker;
import hr.fer.zemris.java.webserver.RequestContext;

/**
 * Worker that runs the zbrajanje.smscr script. The script shows the sum of two
 * numbers input in the home page form, or given as parameters in the url. If
 * the two parameters aren't number the default values are given. If the sum is
 * even one picture is embedded to the page, and another if the sum is odd.
 * 
 * @author Ivan Jukić
 *
 */

public class SumWorker implements IWebWorker {

	private int a;
	private int b;

	@Override
	public void processRequest(RequestContext context) throws Exception {
		try {
			a = Integer.parseInt(context.getParameter("a"));
		} catch (NumberFormatException ex) {
			a = 1;
		}
		try {
			b = Integer.parseInt(context.getParameter("b"));
		} catch (NumberFormatException ex) {
			b = 2;
		}

		int sumInt = a + b;
		String sum = String.format("%d", sumInt);
		context.setTemporaryParameter("zbroj", sum);
		context.setTemporaryParameter("varA", String.format("%d", a));
		context.setTemporaryParameter("varB", String.format("%d", b));
		context.setTemporaryParameter("varA", String.format("%d", a));
		if (sumInt % 2 == 0) {
			context.setTemporaryParameter("imgName", "images/panda.jpg");
		} else {
			context.setTemporaryParameter("imgName", "images/cat.jpg");
		}
		context.getDispatcher().dispatchRequest("/private/pages/calc.smscr");
	}

}
