package hr.fer.zemris.java.hw06.shell.commands;

import hr.fer.zemris.java.hw06.*;
import java.io.IOException;
import java.io.InputStream;
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
 * The hexdump command expects a single argument: file name, and produces a
 * hex-output. Command reads the bytes of the file, groups 16 bytes by 16.
 * Grouping is visualized like this: first column is a counter of groups of 16
 * bytes in the file, second column are the first 8 bytes of the group (in
 * hexadecimal), third column is the last 8 bytes of the group (also in
 * hexadecimal) and the last column is the character representation of the group
 * of 16 bytes. Characters whose value is less than 32 or greater than 127 is
 * replaced by '.'.
 * 
 * @author Ivan Jukić
 *
 */

public class Hexdump implements ShellCommand {

	/**
	 * The hexdump command expects a single argument: file name, and produces a
	 * hex-output
	 * 
	 * @param env       writes error and progess messages to the user using this
	 *                  environment object
	 * @param arguments file name
	 * @return ShellStatus.CONTINUE if the user inputs invalid arguments or if the
	 *         printing was successful
	 */

	@Override
	public ShellStatus executeCommand(Environment env, String arguments) {
		ArrayList<String> argumentList = new ArgumentSplitting(arguments).getListOfArguments();
		int numberOfArguments = argumentList.size();

		if (numberOfArguments != 1) {
			env.writeln("Command hexdump should have 1 argument (path to some file).");
			return ShellStatus.CONTINUE;
		}

		Path path = Paths.get(argumentList.get(0));

		if (!Files.isDirectory(path)) {
			try (InputStream input = Files.newInputStream(path)) {
				byte[] buff = new byte[16];
				int counter = 0;
				while (input.read(buff) > 0) {
					env.write((String.format("%08d: ", counter)));

					for (int i = 0; i < buff.length; i++) {
						env.write(Util.decimalToHex(buff[i]) + " ");
						if (i == 8 || i == 15) {
							env.write("| ");
						}
					}
					for (byte oneByte : buff) {
						if (oneByte < 32 || oneByte > 127) {
							env.write(".");
						} else {
							env.write("" + (char) oneByte);
						}

					}
					env.writeln("");
					counter++;

				}
				input.close();
			} catch (IOException ex) {
				env.writeln("Please insert a valid file path.");
				return ShellStatus.CONTINUE;
			}
		} else {
			env.writeln("Input path is a directory. Please insert a valid file path.");
			return ShellStatus.CONTINUE;
		}
		return ShellStatus.CONTINUE;
	}

	/**
	 * {@inheritDoc}
	 */

	@Override
	public String getCommandName() {
		return "hexdump";
	}

	/**
	 * {@inheritDoc}
	 */

	@Override
	public List<String> getCommandDescription() {
		List<String> list = new ArrayList<>();
		list.add("The hexdump command expects a single argument: file name, and produces hex-output.");
		list.add("All bytes whose value is less than 32 or greater than 127 are replaced with '.");
		list.add(
				"Example of hex-output: 00000001: 6f 6c 6f 72 20 73 69 74 20 | 61 6d 65 74 2c 20 63 | olor sit amet, c");

		return Collections.unmodifiableList(list);
	}

}
