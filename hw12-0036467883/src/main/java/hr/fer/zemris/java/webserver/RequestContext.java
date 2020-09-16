package hr.fer.zemris.java.webserver;

import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.nio.charset.Charset;
import java.nio.charset.StandardCharsets;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;

/**
 * Context for the client request. It is used for communication between the
 * server and the client.
 * 
 * @author Ivan Jukić
 *
 */

public class RequestContext {
	/**
	 * Stream from server to client.
	 */
	private OutputStream outputStream;
	/**
	 * Charset used.
	 */
	private Charset charset;
	/**
	 * Encoding used.
	 */
	private String encoding = "UTF-8";
	/**
	 * Status code used.
	 */
	private int statusCode = 200;
	/**
	 * Status text used.
	 */
	private String statusText = "OK";
	/**
	 * Mime type used.
	 */
	private String mimeType = "text/html";
	/**
	 * Content length given by the clien.
	 */
	private Long contentLength = null;
	// collections
	/**
	 * Parameters map given by the client.
	 */
	private Map<String, String> parameters;
	/**
	 * Temporary parameters map.
	 */
	private Map<String, String> temporaryParameters;
	/**
	 * Persistent parameters map.
	 */
	private Map<String, String> persistentParameters;
	/**
	 * List of output cookies sent to the client.
	 */
	private List<RCCookie> outputCookies;
	/**
	 * Flag set if the header was generated.
	 */
	private boolean headerGenerated = false;
	/**
	 * Dispatcher that dispatches the content to the client.
	 */
	private IDispatcher dispatcher;
	/**
	 * Session id of the client.
	 */
	private String SID;

	/**
	 * Initializes the fields for the class. Sets the session id and the dispatcher
	 * as null and creates a new map for temporary parameters. Used for direct
	 * creation of the content.
	 * 
	 * @param outputStream         output stream
	 * @param parameters           parameters map
	 * @param persistentParameters persistent parameters map
	 * @param outputCookies        output cookies
	 */

	public RequestContext(OutputStream outputStream, // must not be null!
			Map<String, String> parameters, // if null, treat as empty
			Map<String, String> persistentParameters, // if null, treat as empty
			List<RCCookie> outputCookies // if null, treat as empty
	) {
		this(outputStream, parameters, persistentParameters, outputCookies, null, new HashMap<>(), null);
	}

	/**
	 * Initializes the fields for the class. Used for indirect creation of the
	 * content (over a dispatcher).
	 * 
	 * @param outputStream         output stream
	 * @param parameters           parameters map
	 * @param persistentParameters persistent parameters map
	 * @param outputCookies        output cookies list
	 * @param dispatcher           dispatcher
	 * @param temporaryParameters  temporary paremeters map
	 * @param sid                  session id
	 * @throws IllegalArgumentException if output stream is null
	 */

	public RequestContext(OutputStream outputStream, Map<String, String> parameters,
			Map<String, String> persistentParameters, List<RCCookie> outputCookies, IDispatcher dispatcher,
			Map<String, String> temporaryParameters, String sid) {

		if (outputStream == null) {
			throw new IllegalArgumentException("Output stream can't be null.");
		}

		this.outputStream = outputStream;
		this.parameters = parameters;
		this.persistentParameters = persistentParameters;
		this.outputCookies = outputCookies;
		this.temporaryParameters = temporaryParameters;
		this.dispatcher = dispatcher;
		this.SID = sid;
	}

	/**
	 * Returns the dispatcher for this client.
	 * 
	 * @return dispatcher dispatcher for this client
	 */

	public IDispatcher getDispatcher() {
		return this.dispatcher;
	}

	/**
	 * Method that retrieves value from parameters map (or null if no association
	 * exists):
	 * 
	 * @param name key for the map
	 * @return value from parameters map
	 */

	public String getParameter(String name) {
		return parameters.get(name);
	}

	/**
	 * Read-only method that retrieves names of all parameters in parameters map.
	 * 
	 * @return set of all parameters in parameters map
	 */

	public Set<String> getParameterNames() {
		return parameters.keySet();
	}

	/**
	 * Method that retrieves value from persistentParameters map (or null if no
	 * association exists).
	 * 
	 * @param name key name
	 * @return value from persistentParemeters map
	 */

	public String getPersistentParameter(String name) {
		if (persistentParameters == null) {
			return null;
		}
		return persistentParameters.get(name);
	}

	/**
	 * Method that retrieves names of all parameters in persistent parameters map
	 * (note, this set must be read-only).
	 * 
	 * @return set of all parameters in persistent parameters map
	 */

	public Set<String> getPersistentParameterNames() {
		return this.persistentParameters.keySet();
	}

	/**
	 * Method that stores a value to persistentParameters map.
	 * 
	 * @param name  key name
	 * @param value value to be stored in the persistent parameters map
	 */

	public void setPersistentParameter(String name, String value) {
		if (persistentParameters != null) {
			persistentParameters.put(name, value);
		}
	}

	/**
	 * Method that removes a value from persistentParameters map.
	 * 
	 * @param name key name to be removed from the map
	 */

	public void removePersistentParameter(String name) {
		if (persistentParameters != null) {
			persistentParameters.remove(name);
		}
	}

	/**
	 * Method that retrieves value from temporaryParameters map (or null if no
	 * association exists).
	 * 
	 * @param name key name
	 * @return value from temporary parameters map
	 */

	public String getTemporaryParameter(String name) {
		return temporaryParameters.get(name);
	}

	/**
	 * Method that retrieves names of all parameters in read-only temporary
	 * parameters map.
	 * 
	 * @return set of all parameters in the temporary paremeters map
	 */

	public Set<String> getTemporaryParameterNames() {
		return this.temporaryParameters.keySet();
	}

	/**
	 * Method that retrieves an identifier which is unique for current user session.
	 * 
	 * @return
	 */

	public String getSessionID() {
		return this.SID;
	}

	/**
	 * Method that stores a value to temporaryParameters map.
	 * 
	 * @param name  key name
	 * @param value map value
	 */

	public void setTemporaryParameter(String name, String value) {
		temporaryParameters.put(name, value);

	}

	/**
	 * Method that removes a value from temporaryParameters map.
	 * 
	 * @param name key name
	 */

	public void removeTemporaryParameter(String name) {
		temporaryParameters.remove(name);

	}

	/**
	 * Writes a byte array to the output stream with offset 0 and length 1024.
	 * 
	 * @param data byte array of data to be sent
	 * @return an instance of RequestContext (this).
	 * @throws IOException if an I/O error occurs
	 */

	public RequestContext write(byte[] data) throws IOException {
		return write(data, 0, 1024);

	}

	/**
	 * Writes a string to the stream. Sets the charset for the encoding.
	 * 
	 * @param text text to be sent
	 * @return an instance of RequestContext (this).
	 * @throws IOException if an I/O error occurs
	 */

	public RequestContext write(String text) throws IOException {
		if (!headerGenerated) {
			headerGenerated = true;
			outputStream.write(getHeader());
			charset = Charset.forName(encoding);
		}
		outputStream.write(text.getBytes(charset));
		return this;
	}

	/**
	 * Generates a header to be sent to the client.
	 * 
	 * @return byte array of the header
	 */

	private byte[] getHeader() {
		StringBuilder sb = new StringBuilder(String.format("HTTP/1.1 %s %s\r\n", statusCode, statusText));
		sb.append(String.format("Content-Type: %s", mimeType));
		if (mimeType.toLowerCase().startsWith("text/")) {
			sb.append(String.format("; charset=%s\r\n", encoding));
		} else {
			sb.append("\r\n");
		}
		if (contentLength != null) {
			sb.append(String.format("Content-Length: %d\r\n", contentLength));
		}
		if (outputCookies != null && outputCookies.size() != 0) {
			for (RCCookie cookie : outputCookies) {
				sb.append("Set-Cookie: ");
				if (cookie.getName() != null && cookie.getValue() != null) {
					sb.append(String.format("%s=\"%s\"", cookie.getName(), cookie.getValue()));
				}
				if (cookie.getDomain() != null) {
					sb.append(String.format("; Domain=%s", cookie.getDomain()));
				}
				if (cookie.getPath() != null) {
					sb.append(String.format("; Path=%s", cookie.getPath()));
				}
				if (cookie.getMaxAge() != null) {
					sb.append(String.format("; Max-Age=%s", cookie.getMaxAge()));
				}
				sb.append("; HttpOnly");
				sb.append("\r\n");
			}
		}
		sb.append("\r\n");
		return sb.toString().getBytes(StandardCharsets.ISO_8859_1);

	}

	/**
	 * Writes a byte array to the output stream with offset and length.
	 * 
	 * @param data byte array of data to be sent
	 * @return an instance of RequestContext (this).
	 * @throws IOException if an I/O error occurs
	 */

	public RequestContext write(byte[] data, int offset, int len) throws IOException {
		if (!headerGenerated) {
			headerGenerated = true;
			outputStream.write(getHeader());
			charset = Charset.forName(encoding);
		}
		try (InputStream is = new ByteArrayInputStream(data)) {
			byte[] buf = new byte[len];
			while (true) {
				int r = is.read(buf);
				if (r < 1)
					break;
				outputStream.write(buf, offset, r);
			}
			outputStream.flush();
		}
		return this;
	}

	/**
	 * Class that represents a cookie given to the client.
	 * 
	 * @author Ivan Jukić
	 *
	 */

	public static class RCCookie {
		private String name, value, domain, path;
		private Integer maxAge;

		/**
		 * Initializes the values of the cookie.
		 * 
		 * @param name   name of the cookie
		 * @param value  value of the cookie
		 * @param maxAge max age of the cookie
		 * @param domain domain name of the cookie
		 * @param path   path of the cookie
		 */

		public RCCookie(String name, String value, Integer maxAge, String domain, String path) {
			super();
			this.name = name;
			this.value = value;
			this.domain = domain;
			this.path = path;
			this.maxAge = maxAge;
		}

		/**
		 * Returns the cookie name.
		 * 
		 * @return cookie name
		 */
		public String getName() {
			return name;
		}

		/**
		 * Returns the cookie value.
		 * 
		 * @return cookie value
		 */
		public String getValue() {
			return value;
		}

		/**
		 * Returns the cookie domain name.
		 * 
		 * @return domain name
		 */
		public String getDomain() {
			return domain;
		}

		/**
		 * Returns the cookie path.
		 * 
		 * @return cookie path
		 */
		public String getPath() {
			return path;
		}

		/**
		 * Returns the max age of the cookie.
		 * 
		 * @return max age of the cookie
		 */
		public Integer getMaxAge() {
			return maxAge;
		}
	}

	/**
	 * Returns the encoding.
	 * 
	 * @return encoding
	 */
	public String getEncoding() {
		return encoding;
	}

	/**
	 * Sets the encoding.
	 * 
	 * @param encoding encoding to be set.
	 * @throws RuntimeException if the header was already generated.
	 */
	public void setEncoding(String encoding) {
		if (headerGenerated) {
			throw new RuntimeException("Header was already generated.");
		}
		this.encoding = encoding;
	}

	/**
	 * Returns the status code.
	 * 
	 * @return status code
	 */
	public int getStatusCode() {
		return statusCode;
	}

	/**
	 * Sets the status code.
	 * 
	 * @param statusCode status code
	 * @throws RuntimeException if the header was already generated.
	 */
	public void setStatusCode(int statusCode) {
		if (headerGenerated) {
			throw new RuntimeException("Header was already generated.");
		}
		this.statusCode = statusCode;
	}

	/**
	 * Returns the status text.
	 * 
	 * @return status text
	 */
	public String getStatusText() {
		return statusText;
	}

	/**
	 * Sets the status text.
	 * 
	 * @param statusText status text
	 * @throws RuntimeException if the header was already generated.
	 */
	public void setStatusText(String statusText) {
		if (headerGenerated) {
			throw new RuntimeException("Header was already generated.");
		}
		this.statusText = statusText;
	}

	/**
	 * Returns the mime type.
	 * 
	 * @return mime type
	 */
	public String getMimeType() {
		return mimeType;
	}

	/**
	 * Sets mime type.
	 * 
	 * @param mimeType mime type
	 * @throws RuntimeException if the header was already generated.
	 */
	public void setMimeType(String mimeType) {
		if (headerGenerated) {
			throw new RuntimeException("Header was already generated.");
		}
		this.mimeType = mimeType;
	}

	/**
	 * Returns the content length.
	 * 
	 * @return content length
	 */
	public Long getContentLength() {
		return contentLength;
	}

	/**
	 * Sets the content length
	 * 
	 * @param contentLength content length
	 * @throws RuntimeException if the header was already generated.
	 */
	public void setContentLength(Long contentLength) {
		if (headerGenerated) {
			throw new RuntimeException("Header was already generated.");
		}
		this.contentLength = contentLength;
	}

	/**
	 * Sets charset.
	 * 
	 * @param charset charset
	 */
	public void setCharset(Charset charset) {
		this.charset = charset;
	}

	/**
	 * Adds a cookie
	 * 
	 * @param cookie RCCookie
	 * @throws RuntimeException if the header was already generated.
	 */
	public void addRCCookie(RCCookie cookie) {
		if (headerGenerated) {
			throw new RuntimeException("Header was already generated.");
		}
		outputCookies.add(cookie);
	}

}
