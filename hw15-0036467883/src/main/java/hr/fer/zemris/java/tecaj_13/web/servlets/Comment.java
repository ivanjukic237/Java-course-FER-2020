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
import hr.fer.zemris.java.tecaj_13.model.BlogComment;
import hr.fer.zemris.java.tecaj_13.model.BlogEntry;

/**
 * Servlet that adds a new comment to an entry.
 * 
 * @author Ivan Jukić
 *
 */

@WebServlet("/servleti/comment")

public class Comment extends HttpServlet {

	private static final long serialVersionUID = 1L;

	/**
	 * {@inheritDoc}
	 */

	@Override
	protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		req.setCharacterEncoding("UTF-8");
		EntityManager em = JPAEMFProvider.getEmf().createEntityManager();
		String message = (String) req.getParameter("noviZapis");
		String email = (String) req.getParameter("email");
		dodajKomentar(em, Long.parseLong(req.getParameter("id")), message, email);
		resp.sendRedirect("/blog/servleti/author/" + req.getParameter("nick") + "/" + req.getParameter("id"));

	}

	/**
	 * Writes to the database a new comment to the given entry id.
	 * 
	 * @param em          EntityManager
	 * @param blogEntryID id of the blog entry
	 * @param message     message of the comment
	 * @param email       email of the person who writes a comment
	 */

	public static void dodajKomentar(EntityManager em, Long blogEntryID, String message, String email) {
		em.getTransaction().begin();
		List<BlogEntry> blogEntryList = em
				.createQuery("select be from BlogEntry as be where be.id=:id", BlogEntry.class)
				.setParameter("id", blogEntryID).getResultList();
		BlogEntry blogEntry = blogEntryList.get(0);
		BlogComment blogComment = new BlogComment();
		blogComment.setUsersEMail(email);
		blogComment.setPostedOn(new Date());
		blogComment.setMessage(message);
		blogComment.setBlogEntry(blogEntry);
		em.persist(blogComment);

		blogEntry.getComments().add(blogComment);

		em.getTransaction().commit();
		em.close();
	}
}
