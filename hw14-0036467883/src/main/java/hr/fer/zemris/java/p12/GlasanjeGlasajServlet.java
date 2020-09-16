package hr.fer.zemris.java.p12;

import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;
import java.util.TreeMap;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import hr.fer.zemris.java.p12.dao.DAOProvider;

/**
 * Voting servlet that calculates the winners of the poll and forwards the
 * request to /WEB-INF/pages/glasanjeRez.jsp which show the result page.
 * 
 * @author Ivan Jukić
 *
 */
@WebServlet("/servleti/glasanje-glasaj")

public class GlasanjeGlasajServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;

	/**
	 * {@inheritDoc}
	 */

	@Override
	protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {

		Map<Integer, String> listOfPolls = DAOProvider.getDao().getListOfPolls();
		req.setAttribute("listOfPolls", listOfPolls);
		int id = Integer.parseInt(req.getParameter("id"));
		int pollID = (Integer) req.getSession().getAttribute("pollID");
		DAOProvider.getDao().addVote(id);

		HashMap<Integer, String[]> bandInfo = (HashMap<Integer, String[]>) DAOProvider.getDao().getBandInfo(pollID);
		TreeMap<Integer, String> map = new TreeMap<>();

		for (int key : bandInfo.keySet()) {
			map.put(key, bandInfo.get(key)[2] + "");
		}

		ArrayList<Integer> winners = new ArrayList<>();
		for (int key : map.keySet()) {
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

		req.setAttribute("winners", winners);
		req.getSession().setAttribute("scores", map);
		req.getRequestDispatcher("/WEB-INF/pages/glasanjeRez.jsp").forward(req, resp);

	}

}
