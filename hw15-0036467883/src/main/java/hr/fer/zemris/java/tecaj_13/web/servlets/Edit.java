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
import hr.fer.zemris.java.tecaj_13.model.BlogEntry;

/**
 * Servlet that edits the already made entry in the database.
 * 
 * @author Ivan Jukić
 *
 */

@WebServlet("/servleti/edit")

public class Edit extends HttpServlet {

	private static final long serialVersionUID = 1L;

	/**
	 * {@inheritDoc}
	 */
	
	@Override
	protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		req.setCharacterEncoding("UTF-8");

		String nick = req.getParameter("nick");
		String metoda = req.getParameter("metoda");
		if ("Pohrani".equals(metoda)) {
			String id = req.getParameter("id");
			String noviZapis = req.getParameter("noviZapis");
			String naslov = req.getParameter("naslov");
			EntityManager em = JPAEMFProvider.getEmf().createEntityManager();
			em.getTransaction().begin();
			List<BlogEntry> entry = em
					.createQuery("select be from BlogEntry as be where be.id=:id", BlogEntry.class)
					.setParameter("id", Long.parseLong(id)).getResultList();
			BlogEntry blogEntry = entry.get(0);
			blogEntry.setText(noviZapis);
			blogEntry.setTitle(naslov);
			em.persist(blogEntry);
			em.getTransaction().commit();
			em.close();
		}
		resp.sendRedirect("/blog/servleti/author/" + nick);
	}
}
