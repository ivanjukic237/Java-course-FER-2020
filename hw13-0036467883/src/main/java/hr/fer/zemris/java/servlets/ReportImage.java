package hr.fer.zemris.java.servlets;

import java.io.IOException;
import java.io.OutputStream;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.jfree.chart.ChartFactory;
import org.jfree.chart.ChartUtils;
import org.jfree.chart.JFreeChart;
import org.jfree.data.general.DefaultPieDataset;

/**
 * Creates a pie chart PNG image of the OS usage with random values. Sends it to
 * the response output stream.
 * 
 * @author Ivan Jukić
 *
 */

@WebServlet(name = "reportImage", urlPatterns = { "/reportImage" })

public class ReportImage extends HttpServlet {

	private static final long serialVersionUID = 1L;

	/**
	 * {@inheritDoc}
	 */
	
	@Override
	protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {

		resp.setContentType("image/png");
		OutputStream os = resp.getOutputStream();
		JFreeChart chart = getChart();
		int width = 500;
		int height = 350;

		ChartUtils.writeChartAsPNG(os, chart, width, height);
	}

	public JFreeChart getChart() {
		var dataset = new DefaultPieDataset();
		dataset.setValue("Windows", 71.8);
		dataset.setValue("Macintosh", 12.1);
		dataset.setValue("iOS", 6.3);
		dataset.setValue("Android", 5.8);
		dataset.setValue("Linux", 2.7);
		dataset.setValue("Other", 1.3);
		JFreeChart chart = ChartFactory.createPieChart("OS usage", dataset, true, false, false);

		chart.setBorderVisible(false);

		return chart;
	}
}
