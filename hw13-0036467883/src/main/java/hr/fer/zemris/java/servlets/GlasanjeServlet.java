package hr.fer.zemris.java.servlets;

import java.io.BufferedInputStream;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.TreeMap;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * Creates a session attribute map of all the band info: band id, band name and
 * link. Forwards the request to glasanjeIndex.jsp.
 * 
 * @author Ivan Jukić
 *
 */

@WebServlet(name = "glasanje", urlPatterns = { "/glasanje" })

public class GlasanjeServlet extends HttpServlet {

	private static final long serialVersionUID = 1L;

	protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		String fileName = req.getServletContext().getRealPath("/WEB-INF/glasanje-definicija.txt");
		TreeMap<String, String[]> map = new TreeMap<>();

		BufferedReader br = new BufferedReader(
				new InputStreamReader(new BufferedInputStream(Files.newInputStream(Paths.get(fileName))), "UTF-8"));
		String line;
		while ((line = br.readLine()) != null) {
			String[] bandInfo = line.split("\t");
			map.put(bandInfo[0], new String[] { bandInfo[1], bandInfo[2] });
		}

		req.getSession().setAttribute("bandInfo", map);
		req.getRequestDispatcher("pages/glasanjeIndex.jsp").forward(req, resp);

	}
}
