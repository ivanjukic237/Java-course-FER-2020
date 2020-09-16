package hr.fer.zemris.java.webserver;

/**
 * Web worker that processes the request and sends the generated content to the
 * context which sends it to the client.
 * 
 * @author Ivan Jukić
 *
 */

public interface IWebWorker {

	/**
	 * Processes the request.
	 * 
	 * @param context context of the cliend request
	 * @throws Exception if an exception occurs
	 */
	
	public void processRequest(RequestContext context) throws Exception;

}
