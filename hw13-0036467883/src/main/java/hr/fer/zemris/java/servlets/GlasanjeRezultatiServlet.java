package hr.fer.zemris.java.servlets;

import java.io.BufferedInputStream;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.TreeMap;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * Makes a map of all the voting results and the winners at that time. Forwards
 * the request to glasanjeRez.jsp.
 * 
 * @author Ivan Jukić
 *
 */

@WebServlet(name = "glasanje-rezultati", urlPatterns = { "/glasanje-rezultati" })

public class GlasanjeRezultatiServlet extends HttpServlet {

	private static final long serialVersionUID = 1L;

	/*
	 * {@inheritDoc}
	 */
	
	@Override
	protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {

		TreeMap<String, String> map = new TreeMap<>();

		String fileName = req.getServletContext().getRealPath("/WEB-INF/glasanjerezultati.txt");

		BufferedReader br = new BufferedReader(
				new InputStreamReader(new BufferedInputStream(Files.newInputStream(Paths.get(fileName))), "UTF-8"));

		String line;
		while ((line = br.readLine()) != null) {
			String[] bandInfo = line.split("\t");
			map.put(bandInfo[0], bandInfo[1]);
		}

		ArrayList<String> winners = new ArrayList<>();
		for (String key : map.keySet()) {
			int inMap = Integer.parseInt(map.get(key));
			if (winners.isEmpty()) {
				winners.add(key);
			} else {
				int inList = Integer.parseInt(map.get(winners.get(0)));

				if (inMap > inList) {
					winners.clear();
					winners.add(key);
				} else if (inMap == inList) {
					winners.add(key);
				}
			}
		}

		br.close();
		req.getSession().setAttribute("winners", winners);
		req.getSession().setAttribute("scores", map);
		req.getRequestDispatcher("pages/glasanjeRez.jsp").forward(req, resp);
	}
}
