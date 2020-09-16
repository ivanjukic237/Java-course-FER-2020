package hr.fer.zemris.java.custom.scripting.demo;

import java.io.IOException;
import java.io.InputStream;
import java.nio.charset.Charset;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;

import hr.fer.zemris.java.custom.scripting.nodes.*;
import hr.fer.zemris.java.custom.scripting.parser.SmartScriptParser;

/**
 * Test tree writer for the SmartScriptWriter using the visitor model.
 * 
 * @author Ivan Jukić
 *
 */

public class TreeWriter {

	/**
	 * Wisitor for the tree writer.
	 * 
	 * @author Ivan Jukić
	 *
	 */

	public static class WriteVisitor implements INodeVisitor {
		/**
		 * {@inheritDoc}
		 */
		@Override
		public void visitTextNode(TextNode node) {
			System.out.print(node.toString());

		}

		/**
		 * {@inheritDoc}
		 */
		@Override
		public void visitForLoopNode(ForLoopNode node) {
			System.out.print(node.toString());

		}

		/**
		 * {@inheritDoc}
		 */
		@Override
		public void visitEchoNode(EchoNode node) {
			System.out.print(node.toString());
			System.out.println("????");

		}

		/**
		 * {@inheritDoc}
		 */
		@Override
		public void visitDocumentNode(DocumentNode node) {
			for (int i = 0; i < node.numberOfChildren(); i++) {
				node.getChild(i).accept(this);
			}

		}

	}

	/**
	 * Reads the text from the given path. Used by all Demos.
	 * 
	 * @param argument path of the file
	 * @return text from the given path
	 */

	public static String read(String argument) {

		Path path = Paths.get(argument);
		StringBuilder sb = new StringBuilder();
		try (InputStream input = Files.newInputStream(path)) {
			byte[] buff = new byte[1024];
			while ((input.read(buff)) > 0) {
				sb.append(new String(buff, Charset.defaultCharset()));
			}
			input.close();
		} catch (IOException ex) {
			ex.printStackTrace();
		}
		return sb.toString();
	}

	/**
	 * Reads from the file and runs the smart script parser.
	 * 
	 * @param args command line argument - path to the file
	 */

	public static void main(String[] args) {

		if (args.length != 1) {
			throw new IllegalArgumentException("Program takes only one argument - file path.");
		}
		SmartScriptParser parser = new SmartScriptParser(read(args[0]));

		WriteVisitor visitor = new WriteVisitor();
		parser.getDocumentNode().accept(visitor);
	}
}
