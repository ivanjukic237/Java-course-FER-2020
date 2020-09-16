package hr.fer.zemris.java.hw06.shell.commands;

import java.io.IOException;
import java.nio.file.DirectoryStream;
import java.nio.file.Files;
import java.nio.file.LinkOption;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.attribute.BasicFileAttributeView;
import java.nio.file.attribute.BasicFileAttributes;
import java.nio.file.attribute.FileTime;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Date;
import java.util.List;

import hr.fer.zemris.java.hw06.shell.Environment;
import hr.fer.zemris.java.hw06.shell.ShellCommand;
import hr.fer.zemris.java.hw06.shell.ShellStatus;

/**
 * Command ls takes a single argument – directory – and writes a directory
 * listing. Output consists of information like: is a file a directory, is a
 * file readable, writable, executable. Command also prints the file size, date
 * created and the absolute path.
 * 
 * @author Ivan Jukić
 *
 */

public class Ls implements ShellCommand {

	/**
	 * Helper method which returns time created for a file. Format is yyyy-MM-dd HH:mm:ss.
	 * 
	 * @param file path of the file
	 * @return time the file was created
	 */

	private String getTimeCreated(String file) {
		SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
		Path path = Paths.get(file);
		BasicFileAttributeView faView = Files.getFileAttributeView(path, BasicFileAttributeView.class,
				LinkOption.NOFOLLOW_LINKS);
		BasicFileAttributes attributes;
		try {

			attributes = faView.readAttributes();
			FileTime fileTime = attributes.creationTime();
			String formattedDateTime = sdf.format(new Date(fileTime.toMillis()));
			return formattedDateTime.toString();

		} catch (IOException e) {
			e.printStackTrace();
		}
		return null;
	}

	/**
	 * Helper method which recursively returns the size of the file or directory.
	 * 
	 * @param path of the file or directory
	 * @return size of directory
	 * @throws IOException if the file doesn't exist
	 */
	
	private long getSizeOfDirectory(Path path) throws IOException {
		if (!Files.isDirectory(path)) {
			return Files.size(path);
		} else {
			long len = 0;
			DirectoryStream<Path> children = Files.newDirectoryStream(path);

			for (Path childPath : children) {
				len += getSizeOfDirectory(childPath);
			}
			return len;
		}
	}

	/**
	 * Command ls takes a single argument – directory – and writes a directory
	 * listing.
	 * 
	 * @param env       writes error and progess messages to the user using this
	 *                  environment object
	 * @param arguments source file name and destination file name
	 * @return ShellStatus.CONTINUE if the user inputs invalid arguments or if the
	 *         printing was successful
	 */
	
	@Override
	public ShellStatus executeCommand(Environment env, String arguments) {
		ArrayList<String> argumentList = new ArgumentSplitting(arguments).getListOfArguments();
		int numberOfArguments = argumentList.size();
		if (numberOfArguments != 1) {
			env.writeln("Command ls should have only 1 argument.");
		}
		Path path = Paths.get(argumentList.get(0));

		if (Files.isDirectory(path)) {
			try {
				DirectoryStream<Path> children = Files.newDirectoryStream(path);

				for (Path childPath : children) {

					String isDirectory = Files.isDirectory(childPath) ? "d" : "-";
					String isReadable = Files.isReadable(childPath) ? "r" : "-";
					String isWritable = Files.isWritable(childPath) ? "w" : "-";
					String isExecutable = Files.isExecutable(childPath) ? "x" : "-";

					env.writeln((String.format("%s%s%s%s %10s %s %s", isDirectory, isReadable, isWritable, isExecutable,
							getSizeOfDirectory(childPath), getTimeCreated(argumentList.get(0)), childPath)));
				}
			} catch (IOException ex) {
				env.writeln(path + " is not a valid path.");
			}
		} else {
			env.writeln(path + " is not a valid directory path. Please input a valid directory path.");
			return ShellStatus.CONTINUE;
		}
		return ShellStatus.CONTINUE;
	}

	/**
	 * {@inheritDoc}
	 */
	
	@Override
	public String getCommandName() {
		return "ls";
	}

	/**
	 * {@inheritDoc}
	 */
	
	@Override
	public List<String> getCommandDescription() {
		List<String> list = new ArrayList<>();
		list.add("The ls command expects one argument - path to some directory.");
		list.add("The command prints the details of every file in the given directory.");
		list.add(
				"Output consists of information like: is a file a directory, is a file readable, writable, executable.\n"
						+ "Command also prints the file size, date created and the absolute path.");
		list.add("Example of output: drwx   51020236 2020-04-04 15:33:28 C:\\Users\\ivan\\FER-workspace\\.metadata");

		return Collections.unmodifiableList(list);
	}
}
