package hr.fer.zemris.java.hw11.jnotepadpp;

/**
 * This class calculates the number of characters, number of characters without
 * blanks and number of lines for the given text.
 * 
 * @author Ivan Jukić
 *
 */

public class StatisticalInfo {

	/**
	 * Text to calculate the information for.
	 */
	String text;
	/**
	 * Number of characters.
	 */
	private int numberOfCharacters;
	/**
	 * Number of characters without blanks.
	 */
	private int numberOfCharactersWithoutBlanks;
	/**
	 * Number of lines in the text.
	 */
	private int numberOfLines;

	/**
	 * Sets the text to be calculated and starts the methods to calculate the
	 * information.
	 * 
	 * @param text
	 */

	public StatisticalInfo(String text) {
		this.text = text;
		numberOfCharacters();
		getInformation();
	}

	/**
	 * Returns the number of characters.
	 */

	public void numberOfCharacters() {
		numberOfCharacters = text.length();
	}

	/**
	 * Sets the number of characters and number of characters without blanks.
	 */

	public void getInformation() {
		int len = text.length();
		int counterOfCharsWithouBlanks = 0;
		int counterLines = 0;
		for (int i = 0; i < len; i++) {
			Character ch = text.charAt(i);
			if (!Character.isWhitespace(ch)) {
				counterOfCharsWithouBlanks++;
			}
			if (ch == '\n') {
				counterLines++;
			}
		}
		numberOfCharactersWithoutBlanks = counterOfCharsWithouBlanks;
		numberOfLines = counterLines;
	}

	/**
	 * Calculates the column number for the fictional caret in the text.
	 * 
	 * @param caretPosition column position of the caret in the text.
	 * @return column position
	 */

	public int calculateColumnNumber(int caretPosition) {
		int countColumn = 1;

		for (int i = 0; i < caretPosition; i++) {
			Character ch = text.charAt(i);
			if (ch == '\n') {
				countColumn = 0;
			}
			countColumn++;
		}
		return countColumn;
	}

	/**
	 * Calculates the row number for the fictional caret in the text.
	 * 
	 * @param caretPosition row position of the caret in the text.
	 * @return row position
	 */

	public int calculateRowNumber(int caretPosition) {
		int countRow = 1;
		for (int i = 0; i < caretPosition; i++) {
			Character ch = text.charAt(i);
			if (ch == '\n') {
				countRow++;
			}
		}
		return countRow;
	}

	/**
	 * Returns the number of characters.
	 * 
	 * @return number of characters
	 */

	public int getNumberOfCharacters() {
		return numberOfCharacters;
	}

	/**
	 * Returns the number of characters without blanks.
	 * 
	 * @return number of characters without blanks
	 */

	public int getNumberOfCharactersWithoutBlanks() {
		return numberOfCharactersWithoutBlanks;
	}

	/**
	 * Returns the number of lines.
	 * 
	 * @return number of lines
	 */

	public int getNumberOfLines() {
		return numberOfLines;
	}
}
