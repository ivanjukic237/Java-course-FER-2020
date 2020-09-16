package hr.fer.zemris.java.custom.scripting.demo;

import java.io.BufferedInputStream;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import hr.fer.zemris.java.custom.scripting.exec.SmartScriptEngine;
import hr.fer.zemris.java.custom.scripting.parser.SmartScriptParser;
import hr.fer.zemris.java.webserver.RequestContext;
import hr.fer.zemris.java.webserver.RequestContext.RCCookie;

/**
 * Demo program for testing the SmartScriptEngine.
 * 
 * @author Ivan Jukić
 *
 */

public class SmartScriptEngineDemo1 {

	/**
	 * Reads the text from the given path. Used by all Demos.
	 * 
	 * @param argument path of the file
	 * @return text from the given path
	 */

	public static String read(String argument) {

		StringBuilder sb = new StringBuilder();
		Path p = Paths.get(argument);
		BufferedReader br;
		try {
			br = new BufferedReader(new InputStreamReader(new BufferedInputStream(Files.newInputStream(p)), "UTF-8"));
			String line;
			while ((line = br.readLine()) != null) {
				sb.append(line + "\n");

			}
		} catch (IOException e) {
			e.printStackTrace();
		}

		return sb.toString();
	}

	/**
	 * Starts the demo.
	 * 
	 * @param args command line arguments (not used).
	 */
	
	public static void main(String[] args) {
		// String documentBody = read("src\\main\\resources\\doc1.txt");
		String documentBody = read("src\\main\\resources\\osnovni.smscr");
		Map<String, String> parameters = new HashMap<String, String>();
		Map<String, String> persistentParameters = new HashMap<String, String>();
		List<RCCookie> cookies = new ArrayList<RequestContext.RCCookie>();
		// create engine and execute it
		new SmartScriptEngine(new SmartScriptParser(documentBody).getDocumentNode(),
				new RequestContext(System.out, parameters, persistentParameters, cookies)).execute();

	}
}
