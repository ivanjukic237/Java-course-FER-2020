package hr.fer.zemris.java.tecaj_13.web.servlets;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import hr.fer.zemris.java.tecaj_13.dao.jpa.JPAEMFProvider;
import hr.fer.zemris.java.tecaj_13.model.BlogUser;

/**
 * Sets the list of current blog users and forwards the request to
 * pages/login.jsp.
 * 
 * @author Ivan Jukić
 *
 */

@WebServlet("/servleti/index")

public class Index extends HttpServlet {
	private static final long serialVersionUID = 1L;

	/**
	 * {@inheritDoc}
	 */
	@Override
	protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		req.setCharacterEncoding("UTF-8");
		EntityManagerFactory emf = JPAEMFProvider.getEmf();
		EntityManager em = emf.createEntityManager();

		List<BlogUser> listOfBlogUsers = em.createQuery("select bu from BlogUser as bu", BlogUser.class)
				.getResultList();
		List<String> listOfAuthors = new ArrayList<>();
		for (BlogUser blogUser : listOfBlogUsers) {
			listOfAuthors.add(blogUser.getNick());
		}
		Collections.sort(listOfAuthors);
		req.getSession().setAttribute("authors", listOfAuthors);
		req.getRequestDispatcher("/WEB-INF/pages/login.jsp").forward(req, resp);
	}
}
