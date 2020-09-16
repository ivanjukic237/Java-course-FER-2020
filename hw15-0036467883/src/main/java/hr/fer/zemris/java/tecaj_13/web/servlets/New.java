package hr.fer.zemris.java.tecaj_13.web.servlets;

import java.io.IOException;
import java.util.Date;
import java.util.List;

import javax.persistence.EntityManager;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import hr.fer.zemris.java.tecaj_13.dao.jpa.JPAEMFProvider;
import hr.fer.zemris.java.tecaj_13.model.BlogEntry;
import hr.fer.zemris.java.tecaj_13.model.BlogUser;

/**
 * Creates a new entry from the http post requests. 
 * @author Ivan Jukić
 *
 */

@WebServlet("/servleti/new")
public class New extends HttpServlet {

	private static final long serialVersionUID = 1L;

	/**
	 * {@inheritDoc}
	 */
	
	@Override
	protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		req.setCharacterEncoding("UTF-8");
		String nick = req.getParameter("nick");
		String metoda = req.getParameter("metoda");
		if (!"Pohrani".equals(metoda)) {
			resp.sendRedirect("/blog/servleti/author/" + nick);
		} else {
			if (nick.equals(req.getSession().getAttribute("user"))) {
				String noviZapis = req.getParameter("noviZapis");
				String naslov = req.getParameter("naslov");

				EntityManager em = JPAEMFProvider.getEmf().createEntityManager();
				em.getTransaction().begin();

				BlogEntry blogEntry = new BlogEntry();
				blogEntry.setCreatedAt(new Date());
				blogEntry.setLastModifiedAt(blogEntry.getCreatedAt());
				blogEntry.setTitle(naslov);
				blogEntry.setText(noviZapis);
				List<BlogUser> user = em
						.createQuery("select bu from BlogUser as bu where bu.nick=:nick", BlogUser.class)
						.setParameter("nick", nick).getResultList();
				blogEntry.setCreator(user.get(0));

				em.persist(blogEntry);

				em.getTransaction().commit();
				em.close();
				resp.sendRedirect("/blog/servleti/author/" + nick);
			} else {
				resp.sendRedirect("WEB-INF/pages/error.jsp");

			}
		}

	}
}
