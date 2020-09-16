package hr.fer.zemris.java.p12.dao;

import java.util.Map;

/**
 * 
 * Interface for the data persistence subsystem
 * 
 * @author Ivan Jukić
 *
 */
public interface DAO {

	/**
	 * Gets the map of polls where the key is the poll id, and value is the poll
	 * name.
	 * 
	 * @return map of polls
	 * @throws DAOException if an exception occurs
	 */

	public Map<Integer, String> getListOfPolls() throws DAOException;

	/**
	 * Gets the object information from the pollOptions database. Key is the object
	 * id, and the array holds the name of the object, link and vote count.
	 * 
	 * @param pollID id of the poll in question
	 * @return map of object information
	 */

	public Map<Integer, String[]> getBandInfo(long pollID);

	/**
	 * Returns all poll information from a row in polls database.
	 * 
	 * @return map of all poll information from a row in polls database.
	 */

	public Map<Integer, String[]> getPollInfo();

	/**
	 * Adds a vote for an option.
	 * 
	 * @param objectID id of the option
	 */

	public void addVote(int objectID);

}