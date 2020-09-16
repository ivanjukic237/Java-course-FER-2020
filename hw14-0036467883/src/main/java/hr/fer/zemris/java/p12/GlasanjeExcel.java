package hr.fer.zemris.java.p12;

import java.io.IOException;
import java.io.OutputStream;
import java.util.Map;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.poi.hssf.usermodel.HSSFRow;
import org.apache.poi.hssf.usermodel.HSSFSheet;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;

/**
 * Makes an excel file with one sheet with the names of the options in the first
 * column and number of votes for every option in the second column.
 * 
 * @author Ivan Jukić
 *
 */

@WebServlet(name = "glasanje-excel", urlPatterns = { "/servleti/glasanje-excel" })

public class GlasanjeExcel extends HttpServlet {

	private static final long serialVersionUID = 1L;

	/**
	 * {@inheritDoc}
	 */
	@Override
	protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		resp.setContentType("application/vnd.ms-excel");
		resp.setHeader("Content-Disposition", "attachment; filename=\"rezultati.xls\"");
		OutputStream os = resp.getOutputStream();

		HSSFWorkbook hwb = new HSSFWorkbook();
		HSSFSheet sheet = hwb.createSheet("Rezultati");
		@SuppressWarnings("unchecked")
		Map<Integer, String> scores = (Map<Integer, String>) req.getSession().getAttribute("scores");
		@SuppressWarnings("unchecked")
		Map<Integer, String[]> bandInfo = (Map<Integer, String[]>) req.getSession().getAttribute("bandInfo");
		int count = 0;
		for (int key : scores.keySet()) {
			HSSFRow row = sheet.createRow(count);
			row.createCell(0).setCellValue(bandInfo.get(key)[0]);
			row.createCell(1).setCellValue(scores.get(key));
			count++;
		}
		hwb.write(os);
		hwb.close();
	}
}
