package hr.fer.zemris.java.hw06.shell.commands;

import java.nio.charset.Charset;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.SortedMap;

import hr.fer.zemris.java.hw06.shell.Environment;
import hr.fer.zemris.java.hw06.shell.ShellCommand;
import hr.fer.zemris.java.hw06.shell.ShellStatus;

/**
 * Command charsets takes no arguments and lists names of supported charsets for
 * your Java platform. A single charset name is written per line.
 * 
 * @author Ivan Jukić
 *
 */

public class Charsets implements ShellCommand {

	/**
	 * Method lists names of supported charsets for your Java platform
	 * 
	 * @param env       writes error, progess messages and list of charsets to the
	 *                  user using this environment object
	 * @param arguments no arguments
	 * @return ShellStatus.CONTINUE if the user inputs invalid arguments or if the
	 *         writing was successful
	 */

	@Override
	public ShellStatus executeCommand(Environment env, String arguments) {

		if (arguments.equals("")) {
			SortedMap<String, Charset> listOfCharsets = Charset.availableCharsets();

			listOfCharsets.forEach((k, v) -> env.writeln(k));

		} else {
			env.writeln("Command charsets takes no arguments.");
			return ShellStatus.CONTINUE;
		}
		return ShellStatus.CONTINUE;
	}

	/**
	 * {@inheritDoc}
	 */
	
	@Override
	public String getCommandName() {
		return "charsets";
	}

	/**
	 * {@inheritDoc}
	 */
	
	@Override
	public List<String> getCommandDescription() {
		List<String> list = new ArrayList<>();
		list.add("Command charsets expects no arguments.");
		list.add("Lists names of supported charsets for your Java platform");
		list.add("A single charset name is written per line.");
		return Collections.unmodifiableList(list);
	}

}
