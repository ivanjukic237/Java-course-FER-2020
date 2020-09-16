package hr.fer.zemris.java.webserver;

import java.io.BufferedInputStream;
import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.InetSocketAddress;
import java.net.ServerSocket;
import java.net.Socket;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Properties;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

import hr.fer.zemris.java.custom.scripting.exec.SmartScriptEngine;
import hr.fer.zemris.java.custom.scripting.parser.SmartScriptParser;
import hr.fer.zemris.java.webserver.RequestContext.RCCookie;

/**
 * Simple server that sends the client html, txt, gif, png and jpg files. Server
 * configurations (like server address, domain name, port...) are given in the
 * config/server.properties file. To use this server the server.workers,
 * server.documentRoot and server.mimeConfig should be edited in the
 * config/server.properties. Workers supported by this server is given in the
 * config/workers.properties file.
 * 
 * @author Ivan Jukić
 *
 */

public class SmartHttpServer {

	/**
	 * IP address of this server. Default is 127.0.0.1.
	 */
	private String address;
	/**
	 * Domain name of this server. Default is www.localhost.com.
	 */
	private String domainName;
	/**
	 * Port used by this server. Default is 5721.
	 */
	private int port;
	/**
	 * Number of worker threads available to the server. Default is 10.
	 */
	private int workerThreads;
	/**
	 * Time after the session data for the client is destroyed. Default is 10
	 * minutes.
	 */
	private int sessionTimeout;
	/**
	 * Map that holds all available mime types. Mime types supported are html, htm,
	 * txt, jpg, png and gif.
	 */
	private Map<String, String> mimeTypes = new HashMap<String, String>();
	/**
	 * Server thread for the client.
	 */
	private ServerThread serverThread;
	/**
	 * Thread pool for client threads.
	 */
	private ExecutorService threadPool;
	/**
	 * Root of webroot folder.
	 */
	private Path documentRoot;
	/**
	 * Map containing all available workers.
	 */
	private Map<String, IWebWorker> workersMap = new HashMap<String, IWebWorker>();
	/**
	 * Map containing all sessions currently served by the server. Key is a randomly
	 * generated 20 letter string.
	 */
	private Map<String, SessionMapEntry> sessions = new HashMap<String, SmartHttpServer.SessionMapEntry>();

	/**
	 * Starts the server using the properties from server.properties file.
	 * 
	 * @param args command line arguments (not used).
	 */

	public static void main(String[] args) {
		new SmartHttpServer("config\\server.properties").start();
	}

	/**
	 * Initializes the server, mime and worker properties from the properties files
	 * in the config folder. Starts all worker classes contained in the workers map.
	 * 
	 * @throws IOException                 if any of the properties files don't
	 *                                     exit.
	 * @throws IllegalAccessException      - if the class or its nullaryconstructor
	 *                                     is not accessible.
	 * @throws InstantiationException      - if this Class represents an abstract
	 *                                     class,an interface, an array class, a
	 *                                     primitive type, or void;or if the class
	 *                                     has no nullary constructor;or if the
	 *                                     instantiation fails for some other
	 *                                     reason.
	 * @throws ExceptionInInitializerError - if the initializationprovoked by this
	 *                                     method fails.SecurityException - If a
	 *                                     security manager, s, is present andthe
	 *                                     caller's class loader is not the same as
	 *                                     or anancestor of the class loader for the
	 *                                     current class andinvocation of
	 *                                     s.checkPackageAccess() denies access to
	 *                                     the packageof this class.
	 * @param configFileName server.properties file
	 */

	public SmartHttpServer(String configFileName) {
		String mimeConfigPath = null;
		String workerConfigPath = null;
		Properties serverProperties = new Properties();
		try {
			FileInputStream is = new FileInputStream(configFileName);
			serverProperties.load(is);
			address = serverProperties.getProperty("server.address");
			domainName = serverProperties.getProperty("server.domainName");
			port = Integer.parseInt(serverProperties.getProperty("server.port"));
			documentRoot = Paths.get(serverProperties.getProperty("server.documentRoot"));
			mimeConfigPath = serverProperties.getProperty("server.mimeConfig");
			sessionTimeout = Integer.parseInt(serverProperties.getProperty("session.timeout"));
			workerThreads = Integer.parseInt(serverProperties.getProperty("server.workerThreads"));
			workerConfigPath = serverProperties.getProperty("server.workers");
		} catch (IOException e) {
			e.printStackTrace();
		}

		Properties mimeProperties = new Properties();
		try {
			FileInputStream is = new FileInputStream(mimeConfigPath);
			mimeProperties.load(is);
			for (String name : mimeProperties.stringPropertyNames()) {
				mimeTypes.put(name, mimeProperties.getProperty(name));
			}
			is.close();
		} catch (IOException e) {
			System.out.println("Mime properties missing.");
		}
		Properties workerProperties = new Properties();
		try {
			FileInputStream is = new FileInputStream(workerConfigPath);
			workerProperties.load(is);
			for (String name : workerProperties.stringPropertyNames()) {
				Class<?> referenceToClass = this.getClass().getClassLoader()
						.loadClass(workerProperties.getProperty(name));
				@SuppressWarnings("deprecation")
				Object newObject = referenceToClass.newInstance();
				IWebWorker iww = (IWebWorker) newObject;
				workersMap.put(name, iww);

			}
		} catch (IOException | ClassNotFoundException | InstantiationException | IllegalAccessException e) {
			e.printStackTrace();
		}
	}

	/**
	 * Creates a fixed thread pool for worker threads and starts the server thread.
	 * Also after the session timeout time passes, it starts the cleaner thread that
	 * removes from the session map expired sessions.
	 */

	protected synchronized void start() {
		serverThread = new ServerThread();
		serverThread.start();
		threadPool = Executors.newFixedThreadPool(workerThreads);

		// every 5 minutes this thread activates and cleans expired sessions
		Thread cleaner = new Thread(() -> {
			while (true) {
				for (String sid : sessions.keySet()) {
					if (sessions.get(sid).validUntil < System.currentTimeMillis()) {
						sessions.remove(sid);
					}
				}
				try {
					Thread.sleep(sessionTimeout * 1000);
				} catch (InterruptedException e) {
					e.printStackTrace();
				}
			}

		});
		cleaner.setDaemon(true);
		cleaner.start();

	}

	/**
	 * Stops the thread.
	 */

	@SuppressWarnings("deprecation")
	protected synchronized void stop() {
		serverThread.stop();
		threadPool.shutdown();

	}

	/**
	 * Server thread that binds the address to the given IP address.
	 * 
	 * @author Ivan Jukić
	 *
	 */

	protected class ServerThread extends Thread {

		/**
		 * Starts the server thread.
		 * 
		 * @throws IOException IO error when opening the socket.
		 */

		@Override
		public void run() {
			try {
				// closed by the worker
				@SuppressWarnings("resource")
				ServerSocket serverSocket = new ServerSocket();
				serverSocket.bind(new InetSocketAddress(address, port));
				while (true) {
					Socket client = serverSocket.accept();
					ClientWorker cw = new ClientWorker(client);
					threadPool.execute(cw);
				}
			} catch (IOException e) {
				e.printStackTrace();
				threadPool.shutdown();
			}

		}
	}

	/**
	 * Contains all information needed by the server from the client.
	 * 
	 * @author Ivan Jukić
	 *
	 */

	private static class SessionMapEntry {
		/**
		 * SID number for the client.
		 */
		@SuppressWarnings("unused")
		String sid;
		/**
		 * Host that hosted the client.
		 */
		String host;
		/**
		 * Time when the session will expire for inactive client.
		 */
		long validUntil;
		/**
		 * Map that is given to the permanent parameters.
		 */
		Map<String, String> map;

		/**
		 * Initializes the fields of the class.
		 * 
		 * @param sid        SID number for the client.
		 * @param host       Host that hosted the client.
		 * @param validUntil Time when the session will expire for inactive client.
		 * @param map        Map that is given to the permanent parameters.
		 */

		public SessionMapEntry(String sid, String host, long validUntil, Map<String, String> map) {
			super();
			this.sid = sid;
			this.host = host;
			this.validUntil = validUntil;
			this.map = map;
		}

	}

	/**
	 * Client worker that generates the files asked by the client.
	 * 
	 * @author Ivan Jukić
	 *
	 */

	private class ClientWorker implements IDispatcher, Runnable {
		/**
		 * Socket for the server.
		 */
		private Socket csocket;
		/**
		 * Stream from client to server.
		 */
		private InputStream istream;
		/**
		 * Stream from server to client.
		 */
		private OutputStream ostream;
		/**
		 * HTML version.
		 */
		private String version;
		/**
		 * HTML method.
		 */
		private String method;
		/**
		 * Host name.
		 */
		private String host;
		/**
		 * Map of parameters given by the client.
		 */
		private Map<String, String> params = new HashMap<String, String>();
		/**
		 * Temporary parameters used by the scripts.
		 */
		private Map<String, String> tempParams = new HashMap<String, String>();
		/**
		 * Permanent parameters.
		 */
		private Map<String, String> permPrams = new HashMap<String, String>();
		/**
		 * Output cookies sent to the client.
		 */
		private List<RCCookie> outputCookies = new ArrayList<RequestContext.RCCookie>();
		/**
		 * Session id.
		 */
		private String SID;
		/**
		 * Context of the client.
		 */
		private RequestContext context = null;

		/**
		 * Creates a client worker with the given socket.
		 * 
		 * @param csocket socket
		 */

		public ClientWorker(Socket csocket) {
			super();
			this.csocket = csocket;

		}

		/**
		 * Method generates an session id for the client. Id is a random 20 letter
		 * string containing only upper-case English letters.
		 * 
		 * @return
		 */

		private String generateSID() {
			String sidLetters = "ABCDEFGHIJKLMNOPQRSTUVWXYZ";
			StringBuilder sb = new StringBuilder();
			int len = sidLetters.length();
			for (int i = 0; i < 20; i++) {
				sb.append(sidLetters.charAt((int) (len * Math.random())));
			}
			return sb.toString();
		}

		/**
		 * Extracts the headers sent by the client to a list .
		 * 
		 * @param requestHeader header sent by the client
		 * @return list of headers
		 */

		private List<String> extractHeaders(String requestHeader) {
			List<String> headers = new ArrayList<String>();
			String currentLine = null;
			for (String s : requestHeader.split("\n")) {
				if (s.isEmpty())
					break;
				char c = s.charAt(0);
				if (c == 9 || c == 32) {
					currentLine += s;
				} else {
					if (currentLine != null) {
						headers.add(currentLine);
					}
					currentLine = s;
				}
			}
			if (!currentLine.isEmpty()) {
				headers.add(currentLine);
			}
			return headers;
		}

		/**
		 * Sends the error to the client.
		 * 
		 * @param cos        output stream
		 * @param statusCode status code shown to the client
		 * @param statusText text shown to the client
		 * @throws IOException if an I/O error occurs
		 */

		private void sendError(OutputStream cos, int statusCode, String statusText) throws IOException {

			cos.write(("HTTP/1.1 " + statusCode + " " + statusText + "\r\n" + "Server: simple java server\r\n"
					+ "Content-Type: text/plain;charset=UTF-8\r\n" + "Content-Length: 0\r\n" + "Connection: close\r\n"
					+ "\r\n").getBytes(StandardCharsets.US_ASCII));
			cos.flush();

		}

		/**
		 * Parses the input parameters given by the client.
		 * 
		 * @param parameterString String of parameters
		 */

		private void parseParameters(String parameterString) {
			if (parameterString != null) {
				String[] paramCouples = parameterString.split("&");
				for (int i = 0; i < paramCouples.length; i++) {
					String[] pair = paramCouples[i].split("=");
					if (pair.length == 1) {
						params.put(pair[0], "");
					} else {
						params.put(pair[0], pair[1]);
					}
				}
			}

		}

		/**
		 * Checks the current session. If the headers given by the client don't contain
		 * a cookie, it creates a cookie containing the session id and sends it to the
		 * client. If a cookie was given, it checks if the session was timeout, and if
		 * it was it generates a new session id for that client. If the session is not
		 * expired, it updates the time for that session. If server was shut down and
		 * SID was lost for the current session, then it generates a new SID for that
		 * session.
		 * 
		 * @param headers headers sent by the client
		 */

		private synchronized void checkSession(List<String> headers) {

			String sidCandidate = null;
			for (String line : headers) {
				if (line.startsWith("Cookie")) {
					if (line.contains("sid")) {
						sidCandidate = line.split("=")[1].replaceAll("\"", "");
						break;
					}
				}
			}
			if (sidCandidate == null) {
				generateSessionMapEntry();
				return;
			}
			// if server was shut down and SID was lost for the current session, then
			// generate new SID for that session
			if (!sessions.containsKey(sidCandidate)) {
				generateSessionMapEntry();
				return;
			}
			SessionMapEntry sessionMapEntry = sessions.get(sidCandidate);
			if (!sessionMapEntry.host.equals(host)) {
				generateSessionMapEntry();
				return;
			}

			if (sessionMapEntry.validUntil < System.currentTimeMillis()) {
				sessions.remove(sidCandidate);
				generateSessionMapEntry();
				return;
			}
			SID = sidCandidate;

			sessionMapEntry.validUntil = System.currentTimeMillis() + sessionTimeout * 1000;
		}

		/**
		 * Generates the session map entry with the session id, valid time which is
		 * current time + session timeout. Creates outputCookies that are sent to the
		 * client.
		 */

		private void generateSessionMapEntry() {
			SID = generateSID();
			SessionMapEntry sessionMapEntry = new SessionMapEntry(SID, host,
					System.currentTimeMillis() + sessionTimeout * 1000, new ConcurrentHashMap<String, String>());
			sessions.put(SID, sessionMapEntry);
			outputCookies.add(new RCCookie("sid", SID, null, host, "/"));
		}

		/**
		 * Runs the worker thread. Creates output and input streams for communicating
		 * with the client. Reads the client request, extracts the headers from the
		 * request and checks if the request is valid. Checks the method and version
		 * given by the client. Only the get method and http version 1.1 and 1.0 are
		 * allowed. After that it parses given parameters, checks session and starts
		 * dispatching of the content.
		 */

		@Override
		public void run() {
			try {
				istream = csocket.getInputStream();
				ostream = csocket.getOutputStream();

				byte[] requestArray = readRequest(istream);
				if (requestArray == null) {
					sendError(ostream, 400, "Bad request");
					return;
				}

				String requestStr = new String(requestArray, StandardCharsets.US_ASCII);
				List<String> headers = extractHeaders(requestStr);
				String[] firstLine = headers.isEmpty() ? null : headers.get(0).split(" ");

				if (firstLine == null || firstLine.length != 3) {
					sendError(ostream, 400, "Bad request");
					return;
				}

				method = firstLine[0].toUpperCase();
				if (!method.equals("GET")) {
					sendError(ostream, 405, "Method Not Allowed");
					return;
				}

				version = firstLine[2].toUpperCase();
				if (!version.equals("HTTP/1.1") && !version.equals("HTTP/1.0")) {
					sendError(ostream, 505, "HTTP Version Not Supported");
					return;
				}

				for (String line : headers) {
					if (line.contains("Host")) {
						String property = line.replace("Host:", "").trim();
						if (property.contains(":")) {
							host = property.substring(0, property.indexOf(':'));
						} else {
							host = property;
						}
					}
				}
				if (host == null) {
					host = domainName;
				}

				checkSession(headers);
				permPrams = sessions.get(SID).map;

				// parsing parameters
				String requestedPath = firstLine[1];
				int separator = requestedPath.indexOf("?");
				String path = null;
				String parameterString = null;
				if (separator == -1) {
					path = requestedPath;
				} else {
					path = requestedPath.substring(0, separator);
					parameterString = requestedPath.substring(separator + 1);
				}
				parseParameters(parameterString);

				internalDispatchRequest(path, true);
			} catch (IOException e) {
				e.printStackTrace();
			} finally {
				try {
					csocket.close();
				} catch (IOException e) {
					e.printStackTrace();
				}
			}
		}

		/**
		 * Starts the dispatching of the content to the client. It creates the context of the sent files.
		 * 
		 * @param urlPath url path
		 * @param directCall true if it is a direct call to the function
		 */
		
		private void internalDispatchRequest(String urlPath, boolean directCall) {
			Path fullPath = documentRoot.resolve(urlPath.substring(1));

			String extension = urlPath.substring(urlPath.indexOf('.') + 1);
			String mimeType = mimeTypes.get(extension);
			if (mimeType == null) {
				mimeType = "application/octet-stream";
			}
			if (context == null) {
				context = new RequestContext(ostream, params, permPrams, outputCookies, this, tempParams, SID);
				context.setStatusCode(200);

			}
			context.setMimeType(mimeType);
			if (urlPath.contains("ext")) {
				Class<?> referenceToClass;
				try {
					referenceToClass = this.getClass().getClassLoader().loadClass(
							"hr.fer.zemris.java.webserver.workers." + urlPath.substring(urlPath.lastIndexOf("/") + 1));
					@SuppressWarnings("deprecation")
					Object newObject = referenceToClass.newInstance();
					IWebWorker iww = (IWebWorker) newObject;
					iww.processRequest(context);
				} catch (Exception e) {
					e.printStackTrace();
				}

			} else if (workersMap.containsKey(urlPath)) {
				try {
					workersMap.get(urlPath).processRequest(context);
				} catch (Exception e) {
					e.printStackTrace();
				}
			} else {
				if (!Files.isReadable(fullPath)) {
					try {
						sendError(ostream, 404, "File not found");
					} catch (IOException e) {
						e.printStackTrace();
					}
					return;
				}

				if (extension.contains("smscr")) {
					new SmartScriptEngine(new SmartScriptParser(new String(read(fullPath))).getDocumentNode(), context)
							.execute();
				} else {
					try {
						context.write(read(fullPath));
					} catch (IOException e) {
						e.printStackTrace();
					}
				}
			}

		}

		/**
		 * Dispatch request where the direct call is false.
		 */
		
		public void dispatchRequest(String urlPath) throws Exception {
			internalDispatchRequest(urlPath, false);
		}

		/**
		 * Reads bytes from the file of the given path.
		 * 
		 * @param p path
		 * @return byte array of the file of the given path
		 */
		
		private byte[] read(Path p) {

			File file = (p.toFile());
			InputStream in;
			byte[] buf = null;
			try {
				in = new BufferedInputStream(new FileInputStream(file));
				buf = new byte[(int) file.length()];
				in.read(buf);
			} catch (IOException e) {
				e.printStackTrace();
			}
			return buf;
		}

		/**
		 * Reads the client request.
		 * @param is input stream
		 * @return byte array of the client request
		 * @throws IOException if an I/O error occurs
		 */
		
		private byte[] readRequest(InputStream is) throws IOException {

			ByteArrayOutputStream bos = new ByteArrayOutputStream();
			int state = 0;
			l: while (true) {
				int b = is.read();
				if (b == -1)
					return null;
				if (b != 13) {
					bos.write(b);
				}
				switch (state) {
				case 0:
					if (b == 13) {
						state = 1;
					} else if (b == 10)
						state = 4;
					break;
				case 1:
					if (b == 10) {
						state = 2;
					} else
						state = 0;
					break;
				case 2:
					if (b == 13) {
						state = 3;
					} else
						state = 0;
					break;
				case 3:
					if (b == 10) {
						break l;
					} else
						state = 0;
					break;
				case 4:
					if (b == 10) {
						break l;
					} else
						state = 0;
					break;
				}
			}
			return bos.toByteArray();
		}
	}
}
