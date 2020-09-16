package hr.fer.zemris.java.hw06.shell;

import java.util.SortedMap;

/**
 * Shell environment which reads user input, writes to user and manages shell
 * symbols like prompt symbol, more line symbol and multi line symbol. Prompt
 * symbol is written to the user for every new command. If the user puts the
 * more lines symbol after the end of their command, shell writes the multi line
 * symbol in the new line and allows user tu write the command in more lines.
 * Multi line symbol is a symbol that the shell writes after more lines symbol
 * is used. It tells the user that the command can be written in the new line.
 * 
 * @author Ivan Jukić
 *
 */

public interface Environment {

	/**
	 * Reads user input.
	 * 
	 * @return user input
	 * @throws ShellIOException if user input was not able to be read
	 */

	String readLine() throws ShellIOException;

	/**
	 * Prints some text to user without a new line after text.
	 * 
	 * @param text text for printing to the user
	 * @throws ShellIOException if printing could not be achieved
	 */

	void write(String text) throws ShellIOException;

	/**
	 * Prints some text to user with a new line after text.
	 * 
	 * @param text text for printing to the user
	 * @throws ShellIOException if printing could not be achieved
	 */

	void writeln(String text) throws ShellIOException;

	/**
	 * Map of all commands for the shell. Key is how user input calls the command,
	 * and value is the ShellCommand.
	 * 
	 * @return map of all commands for the shell
	 */

	SortedMap<String, ShellCommand> commands();

	/**
	 * Returns the multi line symbol. Multi line symbol is a symbol that the shell
	 * writes after more lines symbol is used. It tells the user that the command
	 * can be written in the new line.
	 * 
	 * @return multi line symbol
	 */

	Character getMultilineSymbol();

	/**
	 * Sets the multi line symbol.
	 * 
	 * @param symbol symbol to set the multi line symbol
	 */

	void setMultilineSymbol(Character symbol);

	/**
	 * Returns the prompt symbol. Prompt symbol is written to the user for every new
	 * command.
	 * 
	 * @return the prompt symbol
	 */

	Character getPromptSymbol();

	/**
	 * Sets the prompt symbol.
	 * 
	 * @param symbol to set the prompt symbol to.
	 */

	void setPromptSymbol(Character symbol);

	/**
	 * Return the more lines symbol. If the user puts the more lines symbol after
	 * the end of their command, shell writes the multi line symbol in the new line
	 * and allows user tu write the command in more lines.
	 * 
	 * @return the more lines symbol
	 */

	Character getMorelinesSymbol();

	/**
	 * Sets the more lines symbol.
	 * 
	 * @param symbol to set the prompt symbol to
	 */

	void setMorelinesSymbol(Character symbol);

}
