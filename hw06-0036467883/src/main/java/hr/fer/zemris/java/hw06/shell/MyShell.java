package hr.fer.zemris.java.hw06.shell;

import java.util.Scanner;
import java.util.SortedMap;
import java.util.TreeMap;

import hr.fer.zemris.java.hw06.shell.commands.*;

/**
 * MyShell is a custom implementation of a shell. It has commands cat, charsets,
 * copy, exit, help, hexdump, ls, mkdir, symbol, tree.
 * 
 * @author Ivan Jukić
 *
 */

public class MyShell {

	private static ShellStatus status = ShellStatus.CONTINUE;

	/**
	 * Custom Environment class for the shell. In addition to the methods described
	 * in the Environment interface, the class holds starting prompt, more lines and
	 * multi line symbols. When the outer class starts the commands map is made
	 * which holds all of the commands.
	 * 
	 * @author Ivan Jukić
	 *
	 */

	private static class ShellEnvironment implements Environment {

		public static char PROMPTSYMBOL = '>';
		public static char MORELINESYMBOL = '\\';
		public static char MULTILINESYMBOL = '|';

		@Override
		public String readLine() throws ShellIOException {
			try {
				@SuppressWarnings("resource")
				Scanner input = new Scanner(System.in);
				return input.nextLine();
			} catch (Exception ex) {
				throw new ShellIOException("");
			}

		}

		@Override
		public void write(String text) throws ShellIOException {
			System.out.print(text);

		}

		@Override
		public void writeln(String text) throws ShellIOException {
			System.out.println(text);

		}

		@Override
		public SortedMap<String, ShellCommand> commands() {
			SortedMap<String, ShellCommand> map = new TreeMap<>();
			map.put("cat", new Cat());
			map.put("charsets", new Charsets());
			map.put("copy", new Copy());
			map.put("help", new Help());
			map.put("hexdump", new Hexdump());
			map.put("ls", new Ls());
			map.put("mkdir", new Mkdir());
			map.put("symbol", new Symbol());
			map.put("tree", new Tree());
			map.put("exit", new Exit());
			map.put("pascal", new Pascal());
			return map;

		}

		@Override
		public Character getMultilineSymbol() {
			return MULTILINESYMBOL;
		}

		@Override
		public void setMultilineSymbol(Character symbol) {
			MULTILINESYMBOL = symbol;

		}

		@Override
		public Character getPromptSymbol() {
			return PROMPTSYMBOL;
		}

		@Override
		public void setPromptSymbol(Character symbol) {
			PROMPTSYMBOL = symbol;

		}

		@Override
		public Character getMorelinesSymbol() {
			return MORELINESYMBOL;
		}

		@Override
		public void setMorelinesSymbol(Character symbol) {
			MORELINESYMBOL = symbol;

		}

	}

	/**
	 * Command line methodod which starts the shell. Shell is exited if user inputs
	 * "exit" or if ShellIOException occurs.
	 * 
	 * @param args method expects no arguments
	 */

	public static void main(String[] args) {
		ShellEnvironment env = new ShellEnvironment();
		env.writeln("Welcome to MyShell v 1.0");
		String arguments = "";
		while (status != ShellStatus.TERMINATE) {
			try {
				System.out.print(env.getPromptSymbol() + " ");
				arguments = env.readLine();
				arguments = arguments.strip();
				if (!arguments.equals("")) {
					if (arguments.endsWith(env.getMorelinesSymbol() + "")) {

						do {
							System.out.print(env.getMultilineSymbol());
							arguments = arguments.substring(0, arguments.length() - 1);
							arguments += env.readLine();

						} while (arguments.endsWith(env.getMorelinesSymbol() + ""));
					}
					String[] argumentSplit = arguments.split("\\s+");

					if (env.commands().containsKey(argumentSplit[0])) {
						arguments = arguments.replaceFirst(argumentSplit[0], "");
						status = env.commands().get(argumentSplit[0]).executeCommand(env, arguments);
					} else {
						env.writeln(String.format("%s is an unknown command.", arguments));
					}
				}

			} catch (IllegalArgumentException ex) {
				System.out.println(ex.getMessage());

			} catch (ShellIOException ex) {
				System.out.println("There is a problem when reading from or writing to user.");
				System.exit(-1);
			}
		}

	}
}
