package hr.fer.zemris.java.hw06.shell.commands;

import java.io.File;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import hr.fer.zemris.java.hw06.shell.Environment;
import hr.fer.zemris.java.hw06.shell.ShellCommand;
import hr.fer.zemris.java.hw06.shell.ShellStatus;

/**
 * The tree command expects a single argument: directory name and prints a tree
 * (each directory level shifts output two charatcers to the right).
 * 
 * @author Ivan Jukić
 *
 */

public class Tree implements ShellCommand {

	/**
	 * Helper method which returns the number of spaces which each directory level
	 * is going to be shifted by.
	 * 
	 * @param level shift level
	 * @return String of spaces for given level
	 */

	private static String getSpaces(int level) {
		StringBuilder sb = new StringBuilder();

		for (int i = 0; i < level; i += 2) {
			sb.append(" ");
		}

		return sb.toString();
	}

	/**
	 * Method prints a tree like structure for a given directory.
	 * 
	 * @param file starting directory
	 * @param level starting level of the directory
	 * @param env environment object which prints the structure
	 */
	
	public static void printTree(File file, int level, Environment env) {
		if (!file.isDirectory()) {
			env.writeln(getSpaces(level) + file.getName());
		} else {
			env.writeln(getSpaces(level) + file.getName());
			File[] children = file.listFiles();
			for (File child : children) {
				printTree(child, level + 2, env);
			}
		}
	}

	/**
	 * Method prints the tree for a given argument - directory path.
	 * 
	 * @param env       writes error and progess messages to the user using this
	 *                  environment object
	 * @param arguments one argument - directory path
	 * @return ShellStatus.CONTINUE if the user inputs invalid arguments or if
	 *         printing was successful
	 */
	
	@Override
	public ShellStatus executeCommand(Environment env, String arguments) {
		ArrayList<String> argumentList = new ArgumentSplitting(arguments).getListOfArguments();
		if (argumentList.size() != 1) {
			System.out.println("Tree command should have only 1 argument (directory name).");
			return ShellStatus.CONTINUE;
		}

		File directory = new File(argumentList.get(0));

		if (!directory.exists()) {
			System.out.println("Given path is not a valid directory path. Please input a valid path.");
			return ShellStatus.CONTINUE;
		}

		printTree(directory, 0, env);

		return ShellStatus.CONTINUE;
	}

	/**
	 * {@inheritDoc}
	 */
	
	@Override
	public String getCommandName() {
		return "tree";
	}

	/**
	 * {@inheritDoc}
	 */
	
	@Override
	public List<String> getCommandDescription() {
		List<String> list = new ArrayList<>();
		list.add("The tree command expects a single argument: directory name and prints a tree\n"
				+ "(each directory level shifts output two charatcers to the right).");

		return Collections.unmodifiableList(list);
	}

}
