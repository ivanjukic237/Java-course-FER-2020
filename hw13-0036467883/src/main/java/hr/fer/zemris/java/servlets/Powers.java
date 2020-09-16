package hr.fer.zemris.java.servlets;

import java.io.IOException;
import java.io.OutputStream;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.poi.hssf.usermodel.HSSFRow;
import org.apache.poi.hssf.usermodel.HSSFSheet;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;

/**
 * Creates an excel file of the given parameters a, b and n.Parametera a is an
 * integer in [-100,100], b is an integer in [-100,100] and n is an integer in
 * [1, 5]. Excel file is created with n sheets. On sheet i there must be a table
 * with two columns. The first column contains integer numbers from a to b. The
 * second column contains i-th powers of these numbers.
 * 
 * @author Ivan Jukić
 *
 */

@WebServlet(name = "powers", urlPatterns = { "/powers" })

public class Powers extends HttpServlet {

	private static final long serialVersionUID = 1L;

	/**
	 * {@inheritDoc}
	 */
	
	@Override
	protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {

		boolean validInput = true;
		int a = 0, b = 0, n = 0;
		try {
			a = Integer.parseInt(req.getParameter("a"));
			b = Integer.parseInt(req.getParameter("b"));
			n = Integer.parseInt(req.getParameter("n"));
		} catch (NumberFormatException ex) {
			validInput = false;
		}
		if (a < -100 || a > 100 || b < -100 || b > 100 || n < 1 || n > 5) {
			validInput = false;
		}
		if (validInput) {
			resp.setContentType("application/vnd.ms-excel");
			resp.setHeader("Content-Disposition", "attachment; filename=\"tablica.xls\"");
			OutputStream os = resp.getOutputStream();
			try {
				HSSFWorkbook hwb = new HSSFWorkbook();
				for (int i = 0; i < n; i++) {
					HSSFSheet sheet = hwb.createSheet("Sheet " + (i + 1));
					int count = 0;
					for (int j = a; j <= b; j++) {
						HSSFRow row = sheet.createRow(count);
						row.createCell(0).setCellValue(j);
						row.createCell(1).setCellValue(Math.pow(j, i));
						count++;
					}
					count = 0;
				}
				hwb.write(os);
				hwb.close();

			} catch (Exception ex) {

				System.out.println(ex);
			}
		} else {
			resp.sendRedirect("pages/error.jsp");

		}

	}

}
