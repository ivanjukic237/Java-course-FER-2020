package hr.fer.zemris.java.gui.charts;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.List;
import java.util.NoSuchElementException;
import java.util.Scanner;

/**
 * Parser for the specific text file of given information to draw the bar chart
 * and creates a BarChart object. Example of a valid file: 
 * Number of people in the car 
 * Frequency 
 * 1,8 2,20 3,22 4,10 5,4 
 * 0 
 * 22 
 * 2 
 * First line is the x axis
 * label, second line is the y axis label, third line are the points on the
 * graph divided by space. Fourth line is the minimal y value, fifth line is the
 * maximal y value and the last line is the y step.
 * 
 * @author ivanjukic
 *
 */

public class BarChartParser {
	File file;

	/**
	 * Sets the file that contains the needed information.
	 * 
	 * @param file file with the information
	 */

	public BarChartParser(File file) {
		this.file = file;
	}

	/**
	 * Parses all lines.
	 * 
	 * @return BarChart object with given information
	 * @throws FileNotFoundException  if the file was not found
	 * @throws NumberFormatException  if the numbers could not be parsed
	 * @throws NoSuchElementException if one of the needed lines is missing
	 */

	public BarChart parse() throws FileNotFoundException {
		List<XYValue> list;
		String xDescription;
		String yDescription;
		int yMin;
		int yMax;
		int yDiff;

		Scanner input;
		try {
			input = new Scanner(file);

			xDescription = input.nextLine();
			yDescription = input.nextLine();
			list = parseList(input.nextLine());
			yMin = Integer.parseInt(input.nextLine().strip());
			yMax = Integer.parseInt(input.nextLine().strip());
			yDiff = Integer.parseInt(input.nextLine().strip());
			input.close();
			return new BarChart(list, xDescription, yDescription, yMin, yMax, yDiff);

		} catch (FileNotFoundException e) {
			throw new FileNotFoundException("File was not found.");
		} catch (NumberFormatException ex) {
			throw new NumberFormatException("File is not written correctly.");
		} catch (NoSuchElementException ex) {
			throw new NoSuchElementException("Line missing.");
		}
	}

	/**
	 * Parses the points line of the file. Points are divided by space, and x and y
	 * values are divided by a comma.
	 * 
	 * @param values
	 * @return
	 */

	private List<XYValue> parseList(String values) {
		String[] listOfValues = values.split(" ");
		List<XYValue> list = new ArrayList<>();
		for (String value : listOfValues) {
			try {
				int idexOfComma = value.indexOf(',');
				int x = Integer.parseInt(value.substring(0, idexOfComma));
				int y = Integer.parseInt(value.substring(idexOfComma + 1, value.length()));
				list.add(new XYValue(x, y));
			} catch (NumberFormatException ex) {
				throw new NumberFormatException("One of the XY Values is not written correctly.");
			}
		}
		return list;
	}
}
