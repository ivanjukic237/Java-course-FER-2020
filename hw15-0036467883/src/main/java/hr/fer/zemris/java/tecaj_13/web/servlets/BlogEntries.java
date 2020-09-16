package hr.fer.zemris.java.tecaj_13.web.servlets;

import java.io.IOException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

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
 * Servlets for showing author blog titles, blog entries and for making new
 * entries.
 * 
 * @author Ivan Jukić
 *
 */

@WebServlet("/servleti/author/*")
public class BlogEntries extends HttpServlet {

	private static final long serialVersionUID = 1L;

	/**
	 * {@inheritDoc}
	 */

	@Override
	protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		req.setCharacterEncoding("UTF-8");
		String pathInfo = req.getPathInfo();
		String[] urlSplit = pathInfo.replaceFirst("/", "").split("/");
		System.out.println(pathInfo);
		if (urlSplit.length == 1) {
			String nick = urlSplit[0];
			EntityManager em = JPAEMFProvider.getEmf().createEntityManager();
			List<BlogUser> user = em.createQuery("select bu from BlogUser as bu where bu.nick=:nick", BlogUser.class)
					.setParameter("nick", nick).getResultList();

			Long id = user.get(0).getId();
			// System.out.println(blogEntries);
			List<BlogEntry> entries = em
					.createQuery("select be from BlogEntry as be where be.creator.id=:id", BlogEntry.class)
					.setParameter("id", id).getResultList();

			Map<Long, String> titles = new HashMap<>();
			for (BlogEntry entry : entries) {
				titles.put(entry.getId(), entry.getTitle());
			}
			System.out.println(titles);
			req.setAttribute("korisnik", nick);
			req.setAttribute("titles", titles);
			em.close();
			req.getRequestDispatcher("/WEB-INF/pages/titlePage.jsp").forward(req, resp);

		} else if (urlSplit[1].contains("new")) {
			req.setAttribute("nick", urlSplit[0]);
			req.getRequestDispatcher("/WEB-INF/pages/New.jsp").forward(req, resp);
		} else {
			EntityManager em = JPAEMFProvider.getEmf().createEntityManager();
			List<BlogEntry> blogEntryList = em
					.createQuery("select be from BlogEntry as be where be.id=:id", BlogEntry.class)
					.setParameter("id", Long.parseLong(urlSplit[1])).getResultList();

			BlogEntry blogEntry = blogEntryList.get(0);

			req.setAttribute("comments", blogEntry.getComments());
			req.setAttribute("nick", urlSplit[0]);
			req.setAttribute("id", urlSplit[1]);
			req.setAttribute("title", blogEntry.getTitle());
			req.setAttribute("text", blogEntry.getText());

			req.getRequestDispatcher("/WEB-INF/pages/blogContent.jsp").forward(req, resp);
			em.close();
		}

	}
}
