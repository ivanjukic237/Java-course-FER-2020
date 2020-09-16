package hr.fer.zemris.java.p12.dao.sql;

import hr.fer.zemris.java.p12.dao.DAO;
import hr.fer.zemris.java.p12.dao.DAOException;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.HashMap;
import java.util.Map;

/**
 * Ovo je implementacija podsustava DAO uporabom tehnologije SQL. Ova konkretna
 * implementacija očekuje da joj veza stoji na raspolaganju preko
 * {@link SQLConnectionProvider} razreda, što znači da bi netko prije no što
 * izvođenje dođe do ove točke to trebao tamo postaviti. U web-aplikacijama
 * tipično rješenje je konfigurirati jedan filter koji će presresti pozive
 * servleta i prije toga ovdje ubaciti jednu vezu iz connection-poola, a po
 * zavrsetku obrade je maknuti.
 * 
 * @author marcupic
 */
public class SQLDAO implements DAO {

	/**
	 * Makes a map from the database where the key is the poll id, and value is the
	 * poll title.
	 * 
	 * @return map where the key is the poll id, and value is the poll title
	 */

	@Override
	public Map<Integer, String> getListOfPolls() throws DAOException {

		HashMap<Integer, String> listOfPolls = new HashMap<>();
		Connection con = SQLConnectionProvider.getConnection();
		PreparedStatement pst = null;
		try {
			pst = con.prepareStatement("select id, title from Polls order by id");
			try {
				ResultSet rs = pst.executeQuery();
				try {
					while (rs != null && rs.next()) {
						listOfPolls.put(rs.getInt(1), rs.getString(2));
					}
				} finally {
					try {
						rs.close();
					} catch (Exception ignorable) {
					}
				}
			} finally {
				try {
					pst.close();
				} catch (Exception ignorable) {
				}
			}
		} catch (Exception ex) {
			throw new DAOException("Pogreška prilikom dohvata liste korisnika.", ex);
		}
		return listOfPolls;
	}

	/**
	 * Makes a map from optionsDatabase where the key is the option id, and value is
	 * an array which hold the option title, option link, votes count.
	 * 
	 * @return map where the key is the option id, and value is an array which hold
	 *         the option title, option link, votes count
	 */

	@Override
	public Map<Integer, String[]> getBandInfo(long pollID) {
		Connection con = SQLConnectionProvider.getConnection();
		HashMap<Integer, String[]> listOfBands = new HashMap<>();
		PreparedStatement query = null;
		try {
			query = con.prepareStatement(
					"select id, optiontitle, optionlink, votescount from polloptions where pollid = " + pollID);
			ResultSet rset = query.executeQuery();
			try {
				while (rset.next()) {
					listOfBands.put(rset.getInt(1),
							new String[] { rset.getString(2), rset.getString(3), rset.getInt(4) + "" });
				}
			} finally {
				try {
					rset.close();
				} catch (SQLException ex) {
					ex.printStackTrace();
				}
			}
		} catch (SQLException ex) {
			ex.printStackTrace();
		} finally {
			try {
				query.close();
			} catch (SQLException ex) {
				ex.printStackTrace();
			}
		}
		return listOfBands;
	}

	/**
	 * Returns from the polls database a map where the key is the poll id and value
	 * is an array which holds all info about the poll.
	 * 
	 * @return map where the key is the poll id and value is an array which holds
	 *         all info about the poll
	 */

	@Override
	public Map<Integer, String[]> getPollInfo() {
		Connection con = SQLConnectionProvider.getConnection();
		HashMap<Integer, String[]> pollInfo = new HashMap<>();
		PreparedStatement query = null;
		try {
			query = con.prepareStatement("select * from polls");
			ResultSet rset = query.executeQuery();
			try {
				while (rset.next()) {
					pollInfo.put(rset.getInt(1), new String[] { rset.getString(2), rset.getString(3) });
				}
			} finally {
				try {
					rset.close();
				} catch (SQLException ex) {
					ex.printStackTrace();
				}
			}
		} catch (SQLException ex) {
			ex.printStackTrace();
		} finally {
			try {
				query.close();
			} catch (SQLException ex) {
				ex.printStackTrace();
			}
		}
		return pollInfo;
	}

	/**
	 * Adds one vote to the option in options database.
	 * 
	 * @param objectID id of the option whose vote is added
	 */

	@Override
	public void addVote(int objectID) {
		String sql = "update polloptions set votescount=votescount+1 where id=?";
		Connection con = SQLConnectionProvider.getConnection();
		PreparedStatement query = null;

		try {
			query = con.prepareStatement(sql);
			query.setInt(1, objectID);
			query.execute();
		} catch (SQLException e) {
			e.printStackTrace();
		}

	}

}