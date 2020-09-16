package hr.fer.zemris.java.hw06.shell.commands;

import java.io.File;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import hr.fer.zemris.java.hw06.shell.Environment;
import hr.fer.zemris.java.hw06.shell.ShellCommand;
import hr.fer.zemris.java.hw06.shell.ShellStatus;

/**
 * The mkdir command takes a single argument: directory name, and creates the
 * appropriate directory structure. 
 * 
 * @author Ivan Jukić
 *
 */

public class Mkdir implements ShellCommand {

	/**
	 * The mkdir command takes a single argument: directory name, and creates the
	 * appropriate directory structure.
	 * 
	 * @param env       writes error and progess messages to the user using this
	 *                  environment object
	 * @param arguments directory name
	 * @return ShellStatus.CONTINUE if the user inputs invalid arguments or if the
	 *         printing was successful
	 */

	@Override
	public ShellStatus executeCommand(Environment env, String arguments) {

		ArrayList<String> argumentList = new ArgumentSplitting(arguments).getListOfArguments();
		int numberOfArguments = argumentList.size();
		if (numberOfArguments != 1) {
			env.writeln("Command mkdir should only have 1 arguments.");
			return ShellStatus.CONTINUE;
		}

		File file = new File(argumentList.get(0));
		if (file.mkdirs()) {
			env.writeln("Directory structure was made");
		} else {
			env.writeln("Directory structure was not made. Make sure you entered a valid path.");
		}

		return ShellStatus.CONTINUE;
	}

	/**
	 * {@inheritDoc}
	 */
	
	@Override
	public String getCommandName() {
		return "mkdir";
	}

	/**
	 * {@inheritDoc}
	 */
	
	@Override
	public List<String> getCommandDescription() {
		List<String> list = new ArrayList<>();
		list.add("Command mkdir expects one argument - path of a directory structure.");
		list.add("Command will write a message informing the user if the creatin was successful.");
		return Collections.unmodifiableList(list);
	}

}
