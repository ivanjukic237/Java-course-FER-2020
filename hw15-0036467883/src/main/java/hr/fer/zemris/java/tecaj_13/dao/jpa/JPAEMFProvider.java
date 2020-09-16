package hr.fer.zemris.java.tecaj_13.dao.jpa;

import javax.persistence.EntityManagerFactory;

/**
 * Returns the EntityManagerFactory.
 * 
 * @author Ivan Jukić
 *
 */

public class JPAEMFProvider {

	public static EntityManagerFactory emf;

	/**
	 * Returns the EntityManagerFactory.
	 * 
	 * @return EntityManagerFactory
	 */
	public static EntityManagerFactory getEmf() {
		return emf;
	}

	/**
	 * Sets the EntityManagerFactory.
	 * 
	 * @param emf EntityManagerFactory
	 */
	public static void setEmf(EntityManagerFactory emf) {
		JPAEMFProvider.emf = emf;
	}
}