package hr.fer.zemris.java.gui.charts;

import java.awt.Container;
import java.io.File;
import java.io.FileNotFoundException;
import javax.swing.BoxLayout;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.SwingUtilities;
import javax.swing.WindowConstants;

/**
 * Reads the file with the given informatin creating the BarChart object and
 * creates the graph.
 * 
 * @author Ivan Jukić
 *
 */

public class BarChartDemo extends JFrame {
	static String pathToFile;

	private static final long serialVersionUID = 1L;
	static BarChart model;

	/**
	 * Sets the location, size title and default close operation of the window.
	 * Initializes the GUI elements.
	 */

	public BarChartDemo() {
		setLocation(300, 300);
		setSize(700, 500);
		setTitle("BarChartDemo");
		setDefaultCloseOperation(WindowConstants.DISPOSE_ON_CLOSE);

		initGUI();
	}

	/**
	 * Initializes the GUI elements.
	 */
	
	private void initGUI() {
		/*
		 * BarChart model = new BarChart( Arrays.asList(new XYValue(1, 8), new
		 * XYValue(2, 20), new XYValue(3, 22), new XYValue(4, 10), new XYValue(5, 4)),
		 * "Number of people in the car", "Frequency", 2, // y-os kreće od 0 22, //
		 * y-os ide do 22 2);
		 */
		Container p = getContentPane();
		p.setLayout(new BoxLayout(p, BoxLayout.PAGE_AXIS));
		JLabel label = new JLabel(pathToFile);
		p.add(label);
		p.add(new BarChartComponent(model));

	}

	/**
	 * Takes one command line argument - path to some file and creates the bar chart.
	 * 
	 * @param args path to some file
	 * @throws FileNotFoundException if the file was not found
	 */
	
	public static void main(String[] args) throws FileNotFoundException {
		if (args.length == 0) {
			throw new IllegalArgumentException("There are no arguments.");
		}
		File file = new File(args[0]);
		pathToFile = file.getAbsolutePath();
		model = new BarChartParser(file).parse();

		SwingUtilities.invokeLater(() -> {
			JFrame frame = new BarChartDemo();
			frame.setVisible(true);
		});
	}
}
