package hr.fer.zemris.java.servlets;

import java.io.IOException;
import java.util.HashMap;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * Draws a table of values of trigonometric functions sin(x) and cos(x) for all
 * integer angles (in degrees) in a range determined by URL parameters a and b
 * (if a is missing, it is assumed that a=0; if b is missing, it is assumed that
 * b=360; if a > b, they are swapped ; if b > a+720, b is set to a+720).
 * 
 * @author Ivan Jukić
 *
 */

@WebServlet(name = "trigonometricTable", urlPatterns = { "/trigonometric" })
public class TrigonometricTable extends HttpServlet {

	private static final long serialVersionUID = 1L;

	/**
	 * {@inheritDoc}
	 */
	
	@Override
	protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		int a = 0;
		int b = 360;
		try {
			a = Integer.parseInt(req.getParameter("a"));
		} catch (NumberFormatException ex) {
		}
		try {
			b = Integer.parseInt(req.getParameter("b"));
		} catch (NumberFormatException ex) {
		}
		if (a > b) {
			int temp;
			temp = a;
			a = b;
			b = temp;
		}
		if (b > a + 720) {
			b = a + 720;
		}
		HashMap<Integer, Double[]> map = new HashMap<>();

		for (int i = a; i <= b; i++) {
			double sin = Math.sin(Math.toRadians(i));
			double cos = Math.cos(Math.toRadians(i));
			map.put(i, new Double[] { sin, cos });
		}
		req.setAttribute("trigonometryMap", map);
		req.getRequestDispatcher("pages/trigonometric.jsp").forward(req, resp);
	}

}
