package hr.fer.zemris.java.hw06.shell;

import java.util.List;

/**
 * Model for all shell commands. It contains a method to execute the command in
 * the shell.
 * 
 * @author Ivan Jukić
 *
 */

public interface ShellCommand {

	/**
	 * Executes the command in the shell.
	 * 
	 * @param env       Environment object which is passed to the command
	 * @param arguments arguments passed to the command
	 * @return ShellStatus.CONTINUE if the shell should continue running and
	 *         ShellStatus.TERMINATE if the shell should terminate
	 */

	ShellStatus executeCommand(Environment env, String arguments);

	/**
	 * Returns the command name which the user calls from the console.
	 * 
	 * @return command name
	 */
	
	String getCommandName();

	/**
	 * List of command description. Used by the Help command.
	 * 
	 * @return list of command description
	 */
	
	List<String> getCommandDescription();

}
