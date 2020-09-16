package hr.fer.zemris.java.hw05.db;

import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

/**
 * Razred pokreće aplikaciju za rad s bazom podataka. Baza se čita iz tekstualne
 * datoteke database.txt stavljene u projekt.
 * 
 * @author Ivan Jukić
 *
 */

public class Runner {

	/**
	 * Metoda prima bazu podataka iz .txt datoteke te nakon toga pokreće petlju za
	 * filtriranje podataka iz baze. Od korisnika se traži da se upiše validan query
	 * i vrti se sve dok korisnik ne upiše {@exit}. Nakon što korisnik upiše validan
	 * query, program ispisuje sve podatke iz baze koji zadovoljavaju upisane
	 * filtere i ispisuje koliko je podataka filtrirano. Ako korisnik upiše query
	 * izlistat će se svi studenti.
	 * 
	 * @param args argumenti komandne linije koji se ne koriste u ovoj metodi
	 * @throws IOException ako baza nije nađena
	 */

	public static void main(String[] args) throws IOException {

		List<String> lines = Files.readAllLines(Paths.get("./database.txt"), StandardCharsets.UTF_8);
		String[] studentRecords = new String[lines.size()];
		for (int i = 0; i < lines.size(); i++) {
			studentRecords[i] = lines.get(i);
		}

		StudentDatabase db = new StudentDatabase(studentRecords);
		Scanner input = new Scanner(System.in);

		while (true) {
			try {
				System.out.print("> ");
				String query = input.nextLine();
				query = query.strip();

				if (query.toLowerCase().equals("exit")) {
					System.out.println("Goodbye!");
					input.close();
					break;
				}

				if (!query.toLowerCase().contains("query")) {
					System.out.println("Nedostaje query operacija.");

				} else {
					query = query.substring(5, query.length());
					QueryParser parser = new QueryParser(query);
					ArrayList<StudentRecord> listOfFilteredStudents = new ArrayList<>();

					if (parser.isDirectQuery()) {
						StudentRecord r = db.forJMBAG(parser.getQueriedJMBAG());
						listOfFilteredStudents.add(r);
					} else {
						for (StudentRecord r : db.filter(new QueryFilter(parser.getQuery()))) {
							listOfFilteredStudents.add(r);
						}
					}

					System.out.println(new QueryToString(listOfFilteredStudents).getQuery());
					System.out.println("Records selected: " + listOfFilteredStudents.size());
				}

				// ispisuje gdje je greška u upisanom queryju
			} catch (Exception ex) {
				System.out.println(ex.getMessage());
				continue;
			}
		}
	}
}
