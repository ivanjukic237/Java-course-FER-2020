package hr.fer.zemris.java.servlets;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * Sets the session background color atribute, and redirects the page to
 * colors.jsp. Redirecting is used to refresh the page with the background
 * color.
 * 
 * @author Ivan Jukić
 *
 */

@WebServlet(name = "setColor", urlPatterns = { "/setcolor" })

public class SetColor extends HttpServlet {

	private static final long serialVersionUID = 1L;

	@Override
	protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		req.getSession().setAttribute("pickedBgCol", req.getParameter("color"));
		resp.sendRedirect("pages/colors.jsp");
	}
}
