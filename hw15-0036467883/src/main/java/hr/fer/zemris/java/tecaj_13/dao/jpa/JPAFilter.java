package hr.fer.zemris.java.tecaj_13.dao.jpa;

import java.io.IOException;

import javax.servlet.Filter;
import javax.servlet.FilterChain;
import javax.servlet.FilterConfig;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.annotation.WebFilter;

/**
 * Intercepts the servlet /servleti/* request and sets the connection.
 * 
 * @author Ivan Jukić
 *
 */

@WebFilter("/servleti/*")
public class JPAFilter implements Filter {

	/**
	 * {@inheritDoc}
	 */
	
	@Override
	public void init(FilterConfig filterConfig) throws ServletException {
	}

	/**
	 * {@inheritDoc}
	 */
	
	@Override
	public void doFilter(ServletRequest request, ServletResponse response,
			FilterChain chain) throws IOException, ServletException {
		try {
			chain.doFilter(request, response);
		} finally {
			JPAEMProvider.close();
		}
	}

	/**
	 * {@inheritDoc}
	 */
	
	@Override
	public void destroy() {
	}
	
}