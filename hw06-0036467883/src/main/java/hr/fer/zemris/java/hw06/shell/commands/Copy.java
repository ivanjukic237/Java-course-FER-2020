package hr.fer.zemris.java.hw06.shell.commands;

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import hr.fer.zemris.java.hw06.shell.Environment;
import hr.fer.zemris.java.hw06.shell.ShellCommand;
import hr.fer.zemris.java.hw06.shell.ShellStatus;

/**
 * The copy command expects two arguments: source file name and destination file
 * name (i.e. paths and names). If the destination file exists, user is asked if
 * the command is allowed to overwrite it. If the user says yes, file is
 * overwritten and if the user says no file is generated in destination folder
 * with a counter (n) after the name (i.e. example.txt is copied to
 * example(1).txt). This copy command works only with files (no directories). If
 * the second argument is directory, command assumes that user wants to copy the
 * original file into that directory using the original file name.
 * 
 * @author Ivan Jukić
 *
 */

public class Copy implements ShellCommand {

	/**
	 * Method reads the file and copies the contents to the destination file.
	 * 
	 * @param env       writes error and progess messages to the user using this
	 *                  environment object
	 * @param arguments source file name and destination file name
	 * @return ShellStatus.CONTINUE if the user inputs invalid arguments or if the
	 *         copying was successful
	 */

	@Override
	public ShellStatus executeCommand(Environment env, String arguments) {
		ArrayList<String> argumentList = new ArgumentSplitting(arguments).getListOfArguments();
		int numberOfArguments = argumentList.size();
		if (numberOfArguments != 2) {
			env.writeln("Command copy should only have 2 arguments (file path and destination path).");
			return ShellStatus.CONTINUE;
		}

		Path pathToCopy = Paths.get(argumentList.get(0));
		if (Files.isDirectory(pathToCopy)) {
			env.writeln("Can't copy a directory.");
			return ShellStatus.CONTINUE;
		}
		if (!Files.exists(pathToCopy)) {
			env.writeln(String.format("%s doesn't exist", pathToCopy));
			return ShellStatus.CONTINUE;
		}

		Path destinationPath = Paths.get(argumentList.get(1));

		if (Files.isDirectory(destinationPath)) {
			destinationPath = Paths.get(String.format("%s\\%s", argumentList.get(1), pathToCopy.getFileName()));
		}

		if (Files.exists(destinationPath)) {
			env.writeln(String.format("File %s already exists. Do you want to overwrite this file? (y/n)",
					destinationPath));
			String userInput = env.readLine().toLowerCase();
			if (userInput.equals("n")) {
				int counter = 1;
				String fileName = argumentList.get(1);
				String beforeExtenstion = fileName.substring(0, fileName.lastIndexOf("."));
				String extension = fileName.substring(fileName.lastIndexOf("."));
				while (Files.exists(destinationPath)) {
					destinationPath = Paths.get(String.format("%s(%d)%s", beforeExtenstion, counter, extension));
					counter++;
				}
			} else if (!userInput.equals("y")) {
				env.writeln(String.format("You have entered an invalid command %s.", userInput));
				return ShellStatus.CONTINUE;
			}

		}

		try (InputStream input = Files.newInputStream(pathToCopy)) {
			OutputStream output = Files.newOutputStream(destinationPath);

			byte[] buff = new byte[1024];
			int r;
			while ((r = input.read(buff)) > 0) {
				output.write(buff, 0, r);
			}
			input.close();
			output.close();
		} catch (IOException ex) {
			env.writeln(String.format(
					"Given path: \"" + destinationPath + "\" is not a valid file path. Please input a valid path."));
			return ShellStatus.CONTINUE;
		}
		env.writeln(String.format("%s was copied to %s", pathToCopy, destinationPath));
		return ShellStatus.CONTINUE;
	}

	/**
	 * {@inheritDoc}
	 */

	@Override
	public String getCommandName() {
		return "copy";
	}

	/**
	 * {@inheritDoc}
	 */

	@Override
	public List<String> getCommandDescription() {
		List<String> list = new ArrayList<>();
		list.add(
				"Command copy expects two arguments - path of a file to copy, and a path where the file should be copied.");
		list.add(
				"If the user doesn't enter the file name (the destination path is a directory), the name of the original file will be copied to the new path.");
		list.add("Example of usage: copy \"C:\\FER_zadace\\New folder\\test.txt\" C:\\users\\ivan\\desktop\\test.txt");
		list.add(
				"Example of usage where the destination path is a directory: copy \"C:\\FER_zadace\\New folder\\test.txt\" C:\\users\\ivan\\desktop");

		return Collections.unmodifiableList(list);
	}

}
