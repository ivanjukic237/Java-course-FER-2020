package hr.fer.zemris.java.webserver.workers;

import hr.fer.zemris.java.webserver.IWebWorker;
import hr.fer.zemris.java.webserver.RequestContext;

/**
 * Worker that writes a html page directly to the context. It shows all
 * parameters input by the client in a html table.
 * 
 * @author Ivan Jukić
 *
 */

public class EchoParams implements IWebWorker {

	/**
	 * Writes a html page directly to the context. Shows all parameters input by the
	 * client.
	 * 
	 * @param context context of the client
	 */

	@Override
	public void processRequest(RequestContext context) throws Exception {
		context.setMimeType("text/html");
		context.write("<html><body>");
		context.write("<table><tbody>");

		for (String param : context.getParameterNames()) {
			context.write("<tr><td>");
			context.write(param + "\r\n");
			context.write("</td>");

			context.write("<td>");
			context.write(context.getParameter(param));
			context.write("</td></tr>");

		}
		context.write("</tbody>");
		context.write("</table>");
		context.write("</body></html>");

	}

}
