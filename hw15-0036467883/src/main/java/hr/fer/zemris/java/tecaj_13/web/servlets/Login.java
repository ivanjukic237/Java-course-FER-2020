package hr.fer.zemris.java.tecaj_13.web.servlets;

import java.io.IOException;
import java.util.List;

import javax.persistence.EntityManager;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import hr.fer.zemris.java.tecaj_13.dao.jpa.JPAEMFProvider;
import hr.fer.zemris.java.tecaj_13.model.BlogUser;
import hr.fer.zemris.java.tecaj_13.web.servlets.forms.LoginForm;

/**
 * Servlet that gets the information from the http request (nickname and
 * password) and logs in the user. if there are errors during login it rederects
 * the request to the login page.
 * 
 * @author Ivan Jukić
 *
 */

@WebServlet("/servleti/main")

public class Login extends HttpServlet {

	private static final long serialVersionUID = 1L;

	/**
	 * {@inheritDoc}
	 */

	@Override
	protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		obradi(req, resp);
	}

	/**
	 * {@inheritDoc}
	 */

	@Override
	protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		obradi(req, resp);
	}

	/**
	 * Gets the information from the http request (nickname and password), if there
	 * are errors during login it rederects the request to the login page.
	 * 
	 * @param req  servlet request
	 * @param resp servlet response
	 * @throws ServletException   Defines a general exception a servlet can throw
	 *                            when itencounters difficulty
	 * @throws IOExceptionDefines a general exception a servlet can throw when
	 *                            itencounters difficulty
	 */

	protected void obradi(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		req.setCharacterEncoding("UTF-8");

		String metoda = req.getParameter("metoda");
		if (!"Ulogiraj se".equals(metoda)) {
			resp.sendRedirect(req.getServletContext().getContextPath() + "/listaj");
			return;
		}
		LoginForm lf = new LoginForm();
		lf.popuniIzHttpRequesta(req);
		lf.validiraj();

		if (lf.imaPogresaka()) {
			req.setAttribute("zapis", lf);
			req.getRequestDispatcher("/WEB-INF/pages/login.jsp").forward(req, resp);
			return;
		}
		req.getSession().setAttribute("user", lf.getNick());
		EntityManager em = JPAEMFProvider.getEmf().createEntityManager();
		List<BlogUser> blogUserList = em
				.createQuery("select bu from BlogUser as bu where bu.nick=:nick", BlogUser.class)
				.setParameter("nick", lf.getNick()).getResultList();
		req.getSession().setAttribute("first_name", blogUserList.get(0).getFirstName());
		req.getSession().setAttribute("last_name", blogUserList.get(0).getLastName());
		em.close();
		resp.sendRedirect("index");

	}
}
