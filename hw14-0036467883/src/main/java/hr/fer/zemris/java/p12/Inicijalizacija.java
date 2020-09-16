package hr.fer.zemris.java.p12;

import java.beans.PropertyVetoException;
import java.io.BufferedInputStream;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.UnsupportedEncodingException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.SQLTransactionRollbackException;
import java.sql.Statement;
import java.util.Properties;

import javax.servlet.ServletContextEvent;
import javax.servlet.ServletContextListener;
import javax.servlet.annotation.WebListener;

import com.mchange.v2.c3p0.ComboPooledDataSource;
import com.mchange.v2.c3p0.DataSources;

/**
 * Connects to the database given in the dbsettings.properties file. If there
 * are no tables in the database, this class creates two tables: polls and
 * pollOptions and populates it with the info from
 * glasanje-definicija-bendovi.txt and glasanje-definicija-filmovi.txt.
 * 
 * @author Ivan Jukić
 *
 */

@WebListener
public class Inicijalizacija implements ServletContextListener {

	/**
	 * {@inheritDoc}
	 */

	@Override
	public void contextInitialized(ServletContextEvent sce) {
		Properties dbsettings = new Properties();
		try {
			dbsettings.load(sce.getServletContext().getResourceAsStream("/WEB-INF/dbsettings.properties"));
		} catch (IOException e2) {
			e2.printStackTrace();
		}

		String connectionURL = "jdbc:derby://" + dbsettings.getProperty("host") + ":" + dbsettings.getProperty("port")
				+ "/" + dbsettings.getProperty("name");

		ComboPooledDataSource cpds = new ComboPooledDataSource();
		try {
			cpds.setDriverClass("org.apache.derby.jdbc.ClientDriver");
		} catch (PropertyVetoException e1) {
			throw new RuntimeException("Pogreška prilikom inicijalizacije poola.", e1);
		}
		cpds.setJdbcUrl(connectionURL);
		cpds.setUser(dbsettings.getProperty("user"));
		cpds.setPassword(dbsettings.getProperty("password"));

		sce.getServletContext().setAttribute("hr.fer.zemris.dbpool", cpds);
		Connection con = null;
		try {
			con = cpds.getConnection();

			try {
				PreparedStatement pollsTableStatement = con.prepareStatement(
						"CREATE TABLE Polls\r\n" + " (id BIGINT PRIMARY KEY GENERATED ALWAYS AS IDENTITY,\r\n"
								+ " title VARCHAR(150) NOT NULL,\r\n" + " message CLOB(2048) NOT NULL\r\n" + ")");
				pollsTableStatement.executeUpdate();
				System.out.println("Poll table was missing and was created.");

			} catch (SQLTransactionRollbackException ex) {
				System.out.println("Poll table is already created.");
			}
			try {
				PreparedStatement pollOptionsTableStatement = con.prepareStatement(
						"CREATE TABLE PollOptions\r\n" + " (id BIGINT PRIMARY KEY GENERATED ALWAYS AS IDENTITY,\r\n"
								+ " optionTitle VARCHAR(100) NOT NULL,\r\n" + " optionLink VARCHAR(150) NOT NULL,\r\n"
								+ " pollID BIGINT,\r\n" + " votesCount BIGINT,\r\n"
								+ " FOREIGN KEY (pollID) REFERENCES Polls(id)\r\n" + ")");
				pollOptionsTableStatement.executeUpdate();
				System.out.println("Poll options table was missing and was created.");
			} catch (SQLTransactionRollbackException ex) {
				System.out.println("Poll options table is already created.");
			}

			String sql = "SELECT COUNT(*) as count FROM Polls";
			PreparedStatement query = con.prepareStatement(sql, ResultSet.TYPE_SCROLL_INSENSITIVE,
					ResultSet.CONCUR_READ_ONLY);
			ResultSet rs = query.executeQuery();
			rs.next();
			int count = rs.getInt(1);
			System.out.println("Number of records in the polls table: " + count);
			if (count == 0) {
				System.out.println("Adding polls...");
				addPoll("glasanje-definicija-bendovi.txt", sce, con, "Glasanje za omiljeni bend",
						"Od sljedećih bendova, koji Vam je bend najdraži? Kliknite na link kako biste glasali!");
				addPoll("glasanje-definicija-filmovi.txt", sce, con, "Glasanje za omiljeni film",
						"Od sljedećih filmova, koji Vam je film najdraži? Kliknite na link kako biste glasali!");
			}
			rs.close();
		} catch (SQLException e) {
			e.printStackTrace();
		}
		try {
			con.close();
		} catch (SQLException ex) {
			ex.printStackTrace();
		}
	}

	/**
	 * Adds a poll to the database.
	 * 
	 * @param fileName name of the file that hold the information about the poll
	 * @param sce      servlet context event
	 * @param con      connection used to communicate with the database
	 * @param title    title of the poll
	 * @param message  message (description) of the poll
	 */

	private void addPoll(String fileName, ServletContextEvent sce, Connection con, String title, String message) {
		String query = " insert into Polls (title, message) values(?,?)";
		try {
			PreparedStatement preparedStmt = con.prepareStatement(query, Statement.RETURN_GENERATED_KEYS);
			preparedStmt.setString(1, title);
			preparedStmt.setString(2, message);
			preparedStmt.executeUpdate();
			ResultSet rset = preparedStmt.getGeneratedKeys();
			long pollId = 0;
			try {
				if (rset != null && rset.next()) {
					pollId = rset.getLong(1);
				}
			} finally {
				try {
					rset.close();
				} catch (SQLException ex) {
					ex.printStackTrace();
				}
			}
			rset.close();
			addPollOptions(fileName, sce, con, pollId);

		} catch (SQLException | IOException e) {
			e.printStackTrace();
		}
	}

	/**
	 * Adds the options for the pollOptions database.
	 * 
	 * @param fileName file name of the file that holds the options information
	 * @param sce      servlet context event
	 * @param con      connection used to communicate with the database
	 * @param pollId   id of the poll whose information is added
	 * @throws UnsupportedEncodingException if the character encoding is not
	 *                                      supported
	 * @throws IOException                  if I/O exception occures
	 */

	private void addPollOptions(String fileName, ServletContextEvent sce, Connection con, long pollId)
			throws UnsupportedEncodingException, IOException {
		String realPath = sce.getServletContext().getRealPath("/WEB-INF/" + fileName);

		BufferedReader br = new BufferedReader(
				new InputStreamReader(new BufferedInputStream(Files.newInputStream(Paths.get(realPath))), "UTF-8"));
		String line;
		while ((line = br.readLine()) != null) {
			String[] bandInfo = line.split("\t");

			try {
				String query = " insert into PollOptions (optionTitle, optionLink, pollID, votesCount) values(?,?,?,?)";
				PreparedStatement preparedStmt = con.prepareStatement(query);
				preparedStmt.setString(1, bandInfo[0]);
				preparedStmt.setString(2, bandInfo[1]);
				preparedStmt.setLong(3, pollId);
				preparedStmt.setInt(4, 0);
				preparedStmt.executeUpdate();
			} catch (SQLException e) {
				e.printStackTrace();
			}
		}
		br.close();
	}

	/**
	 * Destroys the combo pooled data source.
	 * 
	 * @param servlet context event
	 */

	@Override
	public void contextDestroyed(ServletContextEvent sce) {
		ComboPooledDataSource cpds = (ComboPooledDataSource) sce.getServletContext()
				.getAttribute("hr.fer.zemris.dbpool");
		if (cpds != null) {
			try {
				DataSources.destroy(cpds);
			} catch (SQLException e) {
				e.printStackTrace();
			}
		}
	}

}