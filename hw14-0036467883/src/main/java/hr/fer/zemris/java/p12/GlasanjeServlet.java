package hr.fer.zemris.java.p12;

import java.io.IOException;
import java.util.Map;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import hr.fer.zemris.java.p12.dao.DAOProvider;

/**
 * Sets the band info and poll id's session attributes and forwards the request
 * to /WEB-INF/pages/glasanje.jsp.
 * 
 * @author Ivan Jukić
 *
 */

@WebServlet("/servleti/glasanje")

public class GlasanjeServlet extends HttpServlet {

	private static final long serialVersionUID = 1L;

	/**
	 * {@inheritDoc}
	 */

	@Override
	protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		int pollID = Integer.parseInt(req.getParameter("pollID"));
		Map<Integer, String[]> listOfBands = DAOProvider.getDao().getBandInfo(pollID);
		req.getSession().setAttribute("bandInfo", listOfBands);
		req.getSession().setAttribute("pollID", pollID);
		req.setAttribute("pollInfo", DAOProvider.getDao().getPollInfo().get(pollID));
		req.getRequestDispatcher("/WEB-INF/pages/glasanje.jsp").forward(req, resp);

	}
}
