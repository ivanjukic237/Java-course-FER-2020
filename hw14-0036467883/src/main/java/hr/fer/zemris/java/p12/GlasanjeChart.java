package hr.fer.zemris.java.p12;

import java.io.IOException;
import java.io.OutputStream;
import java.util.Map;

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
 * Creates a chart image of PNG format of votes for favourite band using the scores attribute.
 * 
 * @author Ivan Jukić
 *
 */

@WebServlet("/servleti/glasanje-chart")

public class GlasanjeChart extends HttpServlet {

	private static final long serialVersionUID = 1L;

	/**
	 * {@inheritDoc}
	 */
	
	@Override
	protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		var dataset = new DefaultPieDataset();
		
		@SuppressWarnings("unchecked")
		Map<Integer, String> scores = (Map<Integer, String>) req.getSession().getAttribute("scores");
		@SuppressWarnings("unchecked")
		Map<Integer, String[]> bandInfo = (Map<Integer, String[]>) req.getSession().getAttribute("bandInfo");
		for(int key : scores.keySet()) {
			dataset.setValue(bandInfo.get(key)[0], Integer.parseInt(scores.get(key)));
		}
		
		JFreeChart chart = ChartFactory.createPieChart("Rezultati glasanja", dataset, true, false, false);
		chart.setBorderVisible(false);
		
		resp.setContentType("image/png");
		OutputStream os = resp.getOutputStream();
		int width = 500;
		int height = 350;
		ChartUtils.writeChartAsPNG(os, chart, width, height);
	}

}
