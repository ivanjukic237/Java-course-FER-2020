package hr.fer.zemris.java.hw06.shell.commands;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import hr.fer.zemris.java.hw06.shell.Environment;
import hr.fer.zemris.java.hw06.shell.ShellCommand;
import hr.fer.zemris.java.hw06.shell.ShellStatus;

/**
 * The symbol command changes the shell symbols for prompt (PROMPT), more line
 * symbol (MORELINESYMBOL) and multiple line symbol (MULTILINESYMBOL). The
 * command takes one or two arguments. If there is only one argument - symbol
 * name, shell returns the symbol for the name. If there are two arguments -
 * symbol name and a symbol, shell changes the symbol for the name to the given
 * symbol.Example of use: symbol PROMPT #, which changes the prompt symbol to
 * '#'.
 * 
 * @author Ivan Jukić
 *
 */

public class Symbol implements ShellCommand {

	/**
	 * The symbol command changes the shell symbols for prompt (PROMPT), more line
	 * symbol (MORELINESYMBOL) and multiple line symbol (MULTILINESYMBOL).
	 * 
	 * @param env       writes error and progess messages to the user using this
	 *                  environment object
	 * @param arguments no argument or 1 argument (symbol name)
	 * @return ShellStatus.CONTINUE if the user inputs invalid arguments or if
	 *         printing was successful
	 */

	@Override
	public ShellStatus executeCommand(Environment env, String arguments) {
		ArrayList<String> argumentList = new ArgumentSplitting(arguments).getListOfArguments();
		int numberOfArguments = argumentList.size();

		if (numberOfArguments != 1 && numberOfArguments != 2) {
			env.writeln("Comman symbol should have 2 or 3 arguments.");
			return ShellStatus.CONTINUE;
		}
		if (numberOfArguments == 1) {
			String command = argumentList.get(0);
			char symbolName;
			if (command.equals("PROMPT")) {
				symbolName = env.getPromptSymbol();
			} else if (command.equals("MORELINES")) {
				symbolName = env.getMorelinesSymbol();
			} else if (command.equals("MULTILINE")) {
				symbolName = env.getMultilineSymbol();
			} else {
				env.writeln(String.format("There is no symbol with the name: '" + command + "'"));
				return ShellStatus.CONTINUE;
			}
			env.writeln(String.format("Symbol for %s is '%c'\n", command, symbolName));
		} else {
			String command = argumentList.get(0);
			char oldSymbolName;
			char symbolName;
			String symbolString = argumentList.get(1);
			if (symbolString.length() != 1) {
				env.writeln("'" + symbolString + "' is not a valid symbol.");
				return ShellStatus.CONTINUE;
			}

			symbolName = symbolString.charAt(0);

			if (command.equals("PROMPT")) {
				oldSymbolName = env.getPromptSymbol();
				env.setPromptSymbol(symbolName);
			} else if (command.equals("MORELINES")) {
				oldSymbolName = env.getMorelinesSymbol();
				env.setMorelinesSymbol(symbolName);
			} else if (command.equals("MULTILINE")) {
				oldSymbolName = env.getMultilineSymbol();
				env.setMultilineSymbol(symbolName);
			} else {
				env.writeln(String.format("There is no symbol with the name: '" + command + "'"));
				return ShellStatus.CONTINUE;
			}
			env.writeln(String.format("Symbol for %s changed from '%c' to '%c'\n", command, oldSymbolName, symbolName));
		}

		return ShellStatus.CONTINUE;
	}

	/**
	 * {@inheritDoc}
	 */
	
	@Override
	public String getCommandName() {
		return "symbol";
	}

	/**
	 * {@inheritDoc}
	 */
	
	@Override
	public List<String> getCommandDescription() {
		List<String> list = new ArrayList<>();
		list.add(
				"The symbol command changes the shell symbols for prompt (PROMPT), more line symbol (MORELINESYMBOL) and multiple line symbol (MULTILINESYMBOL).");
		list.add(
				"The command takes one or two arguments. If there is only one argument - symbol name, shell returns the symbol for the name.");
		list.add(
				"If there are two arguments - symbol name and a symbol, shell changes the symbol for the name to the given symbol.");
		list.add("Example of use: symbol PROMPT #, which changes the prompt symbol to '#'");

		return Collections.unmodifiableList(list);
	}

}
