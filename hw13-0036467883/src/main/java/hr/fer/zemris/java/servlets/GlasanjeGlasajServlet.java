package hr.fer.zemris.java.servlets;

import java.io.BufferedInputStream;
import java.io.BufferedOutputStream;
import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.io.Writer;
import java.nio.file.Files;
import java.nio.file.Paths;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * Updates or creates a fresh txt file with band id's and votes for all id's.
 * Updates the given band counter from the given parameter id.
 * 
 * @author Ivan Jukić
 *
 */

@WebServlet(name = "glasanje-glasaj", urlPatterns = { "/glasanje-glasaj" })

public class GlasanjeGlasajServlet extends HttpServlet {

	private static final long serialVersionUID = 1L;

	/**
	 * {@inheritDoc}
	 */
	
	@Override
	protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		String fileName = req.getServletContext().getRealPath("/WEB-INF/glasanjerezultati.txt");
		String fileNameDef = req.getServletContext().getRealPath("/WEB-INF/glasanje-definicija.txt");
		File file = new File(fileName);

		if (!file.exists()) {
			Writer bw = new BufferedWriter(new OutputStreamWriter(
					new BufferedOutputStream(Files.newOutputStream(Paths.get(fileName))), "UTF-8"));
			BufferedReader br = new BufferedReader(new InputStreamReader(
					new BufferedInputStream(Files.newInputStream(Paths.get(fileNameDef))), "UTF-8"));
			String line;
			while ((line = br.readLine()) != null) {
				String[] bandInfo = line.split("\t");
				bw.write(bandInfo[0]);
				bw.write("\t0\n");
			}
			br.close();
			bw.close();
		}
		String id = req.getParameter("id");

		BufferedReader br1 = new BufferedReader(
				new InputStreamReader(new BufferedInputStream(Files.newInputStream(Paths.get(fileName))), "UTF-8"));
		String line;
		StringBuilder sb = new StringBuilder();
		while ((line = br1.readLine()) != null) {
			String[] scoreInfo = line.split("\t");
			if (scoreInfo[0].equals(id)) {
				int score = Integer.parseInt(scoreInfo[1]);
				sb.append(id + "\t" + (score + 1) + "\n");
			} else {
				sb.append(line + "\n");
			}
		}
		Writer bw1 = new BufferedWriter(
				new OutputStreamWriter(new BufferedOutputStream(Files.newOutputStream(Paths.get(fileName))), "UTF-8"));
		bw1.write(sb.toString());
		bw1.close();
		br1.close();
		resp.sendRedirect("/webapp2/glasanje-rezultati");

	}
}
