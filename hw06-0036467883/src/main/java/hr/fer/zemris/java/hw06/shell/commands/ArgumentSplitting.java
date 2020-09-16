package hr.fer.zemris.java.hw06.shell.commands;

import java.util.ArrayList;

/**
 * Helper class for splitting the argumens inputted by the user. Argument is a
 * word separated by spaces or a expression in quotation marks.
 * 
 * @author Ivan Jukić
 *
 */

public class ArgumentSplitting {

	/**
	 * Arguments split in char array.
	 */

	private char[] data;
	private int currentIndex = 0;

	/**
	 * List of arguments (Strings) which will be returned.
	 */

	private ArrayList<String> listOfArguments = new ArrayList<>();
	private String arguments;

	/**
	 * Constructor sets the arguments object after stripping it of excess blank
	 * spaces.
	 * 
	 * @param arguments arguments for splitting
	 */

	public ArgumentSplitting(String arguments) {
		this.arguments = arguments.strip();
	}

	/**
	 * Method returns the list of arguments.
	 * 
	 * @return list of arguments
	 */
	
	public ArrayList<String> getListOfArguments() {
		if (!arguments.contains("\"")) {
			String[] arrayOfArguments = arguments.split(" ");
			for (String argument : arrayOfArguments) {
				listOfArguments.add(argument);
			}
			return listOfArguments;
		}

		this.data = arguments.toCharArray();

		while (true) {
			if (currentIndex >= data.length) {
				break;
			} else if (data[currentIndex] == ' ') {
				currentIndex++;
			} else if (data[currentIndex] == '\"') {
				listOfArguments.add(parseQuotation());
				currentIndex++;
			} else {
				listOfArguments.add(parseWord());
			}
		}
		return this.listOfArguments;
	}

	/**
	 * Helper method which parses the expression in quotation marks.
	 * 
	 * @return expression in quotation marks
	 */
	
	private String parseQuotation() {
		StringBuilder sb = new StringBuilder();
		currentIndex++;
		while (data[currentIndex] != '\"') {
			sb.append(data[currentIndex]);
			currentIndex++;
			if (currentIndex >= data.length) {
				throw new IllegalArgumentException("There is no closing quotation mark.");
			}
		}

		return sb.toString();

	}

	/**
	 * Helper method which parses a word which is not in quotation marks.
	 * 
	 * @return word not in quotation marks
	 */
	
	private String parseWord() {

		StringBuilder sb = new StringBuilder();

		while (currentIndex < data.length && data[currentIndex] != ' ') {
			sb.append(data[currentIndex]);
			currentIndex++;
		}
		return sb.toString();
	}

}
