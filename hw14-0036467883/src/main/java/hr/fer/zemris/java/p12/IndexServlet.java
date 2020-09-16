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
 * Finds the list of polls available and sets them as an attribute and forwards
 * the request to /WEB-INF/pages/glasanjeIndex.jsp.
 * 
 * @author Ivan Jukić
 *
 */

@WebServlet("/servleti/index.html")
public class IndexServlet extends HttpServlet {

	private static final long serialVersionUID = 1L;

	/**
	 * {@inheritDoc}
	 */
	
	@Override
	protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {

		Map<Integer, String> listOfPolls = DAOProvider.getDao().getListOfPolls();
		req.setAttribute("listOfPolls", listOfPolls);
		req.getRequestDispatcher("/WEB-INF/pages/glasanjeIndex.jsp").forward(req, resp);

	}

}
