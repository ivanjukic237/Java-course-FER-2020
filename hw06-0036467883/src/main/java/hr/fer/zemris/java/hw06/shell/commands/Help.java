package hr.fer.zemris.java.hw06.shell.commands;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import hr.fer.zemris.java.hw06.shell.Environment;
import hr.fer.zemris.java.hw06.shell.ShellCommand;
import hr.fer.zemris.java.hw06.shell.ShellStatus;

/**
 * If started with no arguments, the command lists names of all supported
 * commands. If started with single argument, it prints name and the description
 * of selected command (or prints an appropriate error message if no such
 * command exists).
 * 
 * @author Ivan Jukić
 *
 */

public class Help implements ShellCommand {

	/**
	 * If there are no arguments, method lists all of the available commands in the
	 * shell. If there is an argument, method writes command description to console.
	 * 
	 * @param env       writes error and progess messages to the user using this
	 *                  environment object
	 * @param arguments no arguments or command name argument
	 * @return ShellStatus.CONTINUE if the user inputs invalid arguments or if the
	 *         copying was successful
	 */

	@Override
	public ShellStatus executeCommand(Environment env, String arguments) {
		ArrayList<String> argumentList = new ArgumentSplitting(arguments).getListOfArguments();
		int numberOfArguments = argumentList.size();
		if (numberOfArguments != 0 && numberOfArguments != 1) {
			env.writeln("Command help should have 0 or 1 argument.");
			return ShellStatus.CONTINUE;
		}
		String key = argumentList.get(0);
		if (!env.commands().containsKey(key) && key != "") {
			env.writeln(String.format("%s is an unknown command.", key));
			return ShellStatus.CONTINUE;
		}

		if (argumentList.get(0).equals("")) {
			env.writeln("List of available commands: ");
			env.commands().keySet().forEach(env::writeln);
		} else {
			env.commands().get(argumentList.get(0)).getCommandDescription().forEach(env::writeln);
		}

		return ShellStatus.CONTINUE;
	}

	/**
	 * {@inheritDoc}
	 */
	
	@Override
	public String getCommandName() {
		return "help";
	}

	/**
	 * {@inheritDoc}
	 */
	
	@Override
	public List<String> getCommandDescription() {
		List<String> list = new ArrayList<>();
		list.add("If started with no arguments, it lists names of all supported commands.");
		list.add("If started with single argument, it prints name and the description of selected command\n"
				+ "(or print appropriate error message if no such command exists).");
		return Collections.unmodifiableList(list);
	}

}
