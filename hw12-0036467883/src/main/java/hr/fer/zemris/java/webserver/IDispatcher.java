package hr.fer.zemris.java.webserver;

/**
 * Dispatches the request of the client.
 * 
 * @author Ivan Jukić
 *
 */

public interface IDispatcher {
	
	/**
	 * Dispatches the request given by the client.
	 * 
	 * @param urlPath of the request
	 * @throws Exception if an exception occurs
	 */
	
	void dispatchRequest(String urlPath) throws Exception;

}
