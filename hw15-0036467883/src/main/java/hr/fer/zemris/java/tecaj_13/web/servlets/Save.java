package hr.fer.zemris.java.tecaj_13.web.servlets;

import java.io.IOException;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import hr.fer.zemris.java.tecaj_13.dao.jpa.JPAEMFProvider;
import hr.fer.zemris.java.tecaj_13.model.BlogUser;
import hr.fer.zemris.java.tecaj_13.web.servlets.forms.FormularForm;

/**
 * Servlet that registers a new user with the information: first name, last
 * name, email, nickname and password.
 * 
 * @author Ivan Jukić
 */
@WebServlet("/servleti/save")
public class Save extends HttpServlet {

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
	 * Gets the user info from the http post form and registers a new user to the
	 * database.
	 * 
	 * @param req  servlet request
	 * @param resp servlet response
	 * @throws ServletException Defines a general exception a servlet can throw when
	 *                          itencounters difficulty.
	 * 
	 * @throws IOException      Defines a general exception a servlet can throw when
	 *                          itencounters difficulty.
	 * 
	 */

	protected void obradi(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {

		req.setCharacterEncoding("UTF-8");

		String metoda = req.getParameter("metoda");
		if (!"Pohrani".equals(metoda)) {
			resp.sendRedirect(req.getServletContext().getContextPath() + "/listaj");
			return;
		}
		FormularForm f = new FormularForm();
		f.popuniIzHttpRequesta(req);
		f.validiraj();

		if (f.imaPogresaka()) {
			req.setAttribute("zapis", f);
			req.getRequestDispatcher("/WEB-INF/pages/Formular.jsp").forward(req, resp);
			return;
		}
		dodajUsera(JPAEMFProvider.getEmf(), f);

		req.getSession().setAttribute("user", f.getNick());
		req.getSession().setAttribute("first_name", f.getIme());
		req.getSession().setAttribute("last_name", f.getPrezime());

		resp.sendRedirect("/blog/servleti/index");
	}

	/**
	 * Adds a new user to the database.
	 * 
	 * @param emf entity manager factory
	 * @param f   form
	 */

	public static void dodajUsera(EntityManagerFactory emf, FormularForm f) {
		EntityManager em = emf.createEntityManager();
		em.getTransaction().begin();
		BlogUser user = new BlogUser();
		user.setFirstName(f.getIme());
		user.setLastName(f.getPrezime());
		user.setNick(f.getNick());
		user.setPasswordHash(f.calculatePassHash());
		user.setUserEmail(f.getEmail());
		em.persist(user);
		em.getTransaction().commit();
		em.close();

	}
}
