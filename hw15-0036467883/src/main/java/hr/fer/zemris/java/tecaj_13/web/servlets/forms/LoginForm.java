package hr.fer.zemris.java.tecaj_13.web.servlets.forms;

import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.persistence.EntityManager;
import javax.servlet.http.HttpServletRequest;

import hr.fer.zemris.java.tecaj_13.dao.jpa.JPAEMFProvider;
import hr.fer.zemris.java.tecaj_13.model.BlogUser;
import hr.fer.zemris.java.tecaj_13.model.Util;
/**
 * <p>Model formulara koji odgovara web-reprezentaciji domenskog objekta {@link Record}.
 * U domenskom modelu, različita svojstva su različitih tipova; primjerice, {@link Record#getId()}
 * je tipa {@link Long}. U formularu, sva su svojstva stringovi jer se tako prenose preko HTTP-a
 * i čuvaju u web-stranici u formularima.</p>
 * 
 * <p>Za svako svojstvo, mapa {@link #greske} omogućava da se pri validaciji (metoda {@link #validiraj()}) upiše 
 * je li došlo do pogreške u podatcima. Formular nudi sljedeće funkcionalnosti.</p>
 * 
 * <ol>
 * <li>Punjenje iz trenutnog zahtjeva metodom {@link #popuniIzHttpRequesta(HttpServletRequest)}. Čita parametre
 *     i upisuje odgovarajuća svojstva u formular.</li>
 * <li>Punjenje iz domenskog objekta metodom {@link #popuniIzRecorda(Record)}. Prima {@link Record} kao argument
 *     i temeljem toga što je u njemu upisano popunjava ovaj formular.</li>
 * <li>Punjenje domenskog objekta temeljem upisanog sadržaja u formularu metodom {@link #popuniURecord(Record)}.
 *     Ideja je da se ovo radi tek ako su podatci u formularu prošli validaciju. Pogledajte pojedine servlete koji
 *     su pripremljeni uz ovaj primjer za demonstraciju kako se to radi.</li>
 * </ol>
 * 
 * @author marcupic
 */
public class LoginForm {

	private String nick;
	private String password;

	Map<String, String> greske = new HashMap<>();

	public LoginForm() {
	}

	/**
	 * Dohvaća poruku pogreške za traženo svojstvo.
	 * 
	 * @param ime naziv svojstva za koje se traži poruka pogreške
	 * @return poruku pogreške ili <code>null</code> ako svojstvo nema pridruženu
	 *         pogrešku
	 */

	public String dohvatiPogresku(String ime) {
		return greske.get(ime);
	}

	/**
	 * Provjera ima li barem jedno od svojstava pridruženu pogrešku.
	 * 
	 * @return <code>true</code> ako ima, <code>false</code> inače.
	 */

	public boolean imaPogresaka() {
		return !greske.isEmpty();
	}

	/**
	 * Provjerava ima li traženo svojstvo pridruženu pogrešku.
	 * 
	 * @param ime naziv svojstva za koje se ispituje postojanje pogreške
	 * @return <code>true</code> ako ima, <code>false</code> inače.
	 */

	public boolean imaPogresku(String ime) {
		return greske.containsKey(ime);
	}

	/**
	 * Na temelju parametara primljenih kroz {@link HttpServletRequest} popunjava
	 * svojstva ovog formulara.
	 * 
	 * @param req objekt s parametrima
	 */

	public void popuniIzHttpRequesta(HttpServletRequest req) {
		this.nick = pripremi(req.getParameter("nick"));
		this.password = pripremi(req.getParameter("password"));
	}

	/**
	 * Pomoćna metoda koja <code>null</code> stringove konvertira u prazne
	 * stringove, što je puno pogodnije za uporabu na webu.
	 * 
	 * @param s string
	 * @return primljeni string ako je različit od <code>null</code>, prazan string
	 *         inače.
	 */
	private String pripremi(String s) {
		if (s == null)
			return "";
		return s.trim();
	}

	/**
	 * Metoda obavlja validaciju formulara. Formular je prethodno na neki način
	 * potrebno napuniti. Metoda provjerava semantičku korektnost svih podataka te
	 * po potrebi registrira pogreške u mapu pogrešaka.
	 */
	public void validiraj() {
		greske.clear();
		EntityManager em = JPAEMFProvider.getEmf().createEntityManager();
		List<BlogUser> listNick = null;
		if (this.nick.isEmpty()) {
			greske.put("nick", "Nadimak je obavezan!");
		} else {
			listNick = em.createQuery("select bu from BlogUser as bu where bu.nick=:nick", BlogUser.class)
					.setParameter("nick", this.nick).getResultList();
			if (listNick.isEmpty()) {
				greske.put("no_nick", "Nadimak ne postoji!");
			}
		}

		if (this.password.isEmpty()) {
			greske.put("password", "Lozinka je obavezna!");
		} else if (listNick != null && !listNick.isEmpty()) {
			List<BlogUser> listPass = em
					.createQuery("select bu from BlogUser as bu where bu.nick=:nick and bu.passwordHash=:pass",
							BlogUser.class)
					.setParameter("nick", this.nick).setParameter("pass", calculatePassHash()).getResultList();
			if(listPass.isEmpty()) {
				greske.put("wrong_pass", "Lozinka nije ispravna!");
			}
		}

		em.close();
	}

	public String calculatePassHash() {
		byte[] hash = null;
		try {
			MessageDigest digest = MessageDigest.getInstance("SHA-1");
			byte[] buff = this.password.getBytes();
			digest.update(buff);
			hash = digest.digest();
		} catch (NoSuchAlgorithmException e) {
			e.printStackTrace();
		}
		return Util.bytetohex(hash);
	}

	public String getNick() {
		return nick;
	}

	public void setNick(String nick) {
		this.nick = nick;
	}

	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
	}

}
