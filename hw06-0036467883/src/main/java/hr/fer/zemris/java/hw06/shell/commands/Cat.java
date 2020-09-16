package hr.fer.zemris.java.hw06.shell.commands;

import java.io.BufferedInputStream;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.nio.charset.Charset;
import java.nio.charset.IllegalCharsetNameException;
import java.nio.charset.UnsupportedCharsetException;
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
 * Command cat takes one or two arguments. The first argument is path to some
 * file and is mandatory. The second argument is charset name that is used to
 * interpret chars from bytes. If not provided, a default platform charset is
 * used. This command opens the given file and writes its content to console.
 * 
 * @author Ivan Jukić
 *
 */

public class Cat implements ShellCommand {

	/**
	 * Method opens the given file and writes its content to console over the
	 * environment parameter.
	 * 
	 * @param env       environment of the shell
	 * @param arguments 1 or 2 arguments - path to some file and charset name
	 * 
	 * @return ShellStatus.CONTINUE if user input is not valid or if the file was
	 *         read
	 */

	@Override
	public ShellStatus executeCommand(Environment env, String arguments) {
		ArrayList<String> argumentList = new ArgumentSplitting(arguments).getListOfArguments();
		int numberOfArguments = argumentList.size();
		if (numberOfArguments != 2 && numberOfArguments != 1 || argumentList.get(0).equals("")) {
			env.writeln("Cat command should have 1 or 2 arguments (path to some file and charset name)");
			return ShellStatus.CONTINUE;
		}
		Path path = Paths.get(argumentList.get(0));
		Charset charset;

		if (Files.isDirectory(path)) {
			env.writeln(String.format("%s is a directory. Please input a valid file path.", path));
			return ShellStatus.CONTINUE;
		}

		if (numberOfArguments == 2) {
			try {
				charset = Charset.forName(argumentList.get(1));
			} catch (IllegalCharsetNameException | UnsupportedCharsetException ex) {
				env.writeln(String.format(
						"Your input for charset: \"%s\" is not supported by this platform. Use command charsets to get the list of valid charsets.",
						argumentList.get(1)));
				return ShellStatus.CONTINUE;
			}
		} else {
			charset = Charset.defaultCharset();
		}

		BufferedReader br;
		try {
			br = new BufferedReader(
					new InputStreamReader(new BufferedInputStream(Files.newInputStream(path)), charset));
			String line;
			while ((line = br.readLine()) != null) {
				env.writeln(line);
			}
			br.close();
		} catch (IOException e) {
			env.writeln(String.format("\"%s\" is not a valid file path. Please input a valid path.", path));
			return ShellStatus.CONTINUE;
		}

		return ShellStatus.CONTINUE;
	}

	/**
	 * {@inheritDoc}
	 */

	@Override
	public String getCommandName() {
		return "cat";
	}

	/**
	 * {@inheritDoc}
	 */

	@Override
	public List<String> getCommandDescription() {
		List<String> list = new ArrayList<>();
		list.add("Command cat takes one or two arguments. The first argument is path to some file and is mandatory.\n"
				+ "The second argument is charset name that should be used to interpret chars from bytes.\n"
				+ "If not provided, a default platform charset is used.");
		list.add("Command opens given file and writes its content to console.");

		return Collections.unmodifiableList(list);
	}

}
