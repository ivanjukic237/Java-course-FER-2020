package hr.fer.zemris.java.hw06.shell.commands;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import hr.fer.zemris.java.hw06.shell.Environment;
import hr.fer.zemris.java.hw06.shell.ShellCommand;
import hr.fer.zemris.java.hw06.shell.ShellStatus;

/**
 * Exits command exits the shell if the user inputs "exit".
 * 
 * @author Ivan Jukić
 *
 */

public class Exit implements ShellCommand {

	/**
	 * Method lists names of supported charsets for your Java platform
	 * 
	 * @param env       writes the message that the shell is terminating
	 * @param arguments no arguments
	 * @return ShellStatus.TERMINATE which terminates the shell
	 */

	@Override
	public ShellStatus executeCommand(Environment env, String arguments) {
		System.out.println("Shell is now terminating. Goodbye!");
		return ShellStatus.TERMINATE;
	}

	/**
	 * {@inheritDoc}
	 */
	
	@Override
	public String getCommandName() {
		return "exit";
	}

	/**
	 * {@inheritDoc}
	 */
	
	@Override
	public List<String> getCommandDescription() {
		List<String> list = new ArrayList<>();
		list.add("Command exits the shell.");
		return Collections.unmodifiableList(list);
	}

}
