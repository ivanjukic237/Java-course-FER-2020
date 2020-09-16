package hr.fer.zemris.java.tecaj_13.model;

import java.util.ArrayList;
import java.util.List;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.OneToMany;
import javax.persistence.OrderBy;
import javax.persistence.Table;
import javax.persistence.UniqueConstraint;

/**
 * Model of the blog user. Password is hashed by SHA-1 encoding.
 * 
 * @author Ivan Jukić
 *
 */

@Entity
@Table(name = "blog_user", uniqueConstraints = { @UniqueConstraint(columnNames = { "nick" }) })
public class BlogUser {

	/**
	 * User id.
	 */
	private long id;
	/**
	 * User first name.
	 */
	private String firstName;
	/**
	 * User last name.
	 */
	private String lastName;
	/**
	 * User nickname.
	 */
	private String nick;
	/**
	 * User email.
	 */
	private String email;
	/**
	 * User password hash.
	 */
	private String passwordHash;
	/**
	 * List of blog entries.
	 */
	private List<BlogEntry> blogEntries = new ArrayList<>();

	@Id
	@GeneratedValue
	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	@OneToMany(mappedBy = "creator", fetch = FetchType.LAZY, cascade = CascadeType.PERSIST, orphanRemoval = true)
	@OrderBy("createdAt")
	public List<BlogEntry> getBlogEntries() {
		return blogEntries;
	}

	public void setBlogEntries(List<BlogEntry> blogEntries) {
		this.blogEntries = blogEntries;
	}

	@Column(length = 100, nullable = false)
	public String getUserEmail() {
		return email;
	}

	public void setUserEmail(String email) {
		this.email = email;
	}

	@Column(length = 200, nullable = false)
	public String getFirstName() {
		return firstName;
	}

	public void setFirstName(String firstName) {
		this.firstName = firstName;
	}

	@Column(length = 200, nullable = false)
	public String getLastName() {
		return lastName;
	}

	public void setLastName(String lastName) {
		this.lastName = lastName;
	}

	@Column(length = 200, nullable = false)
	public String getNick() {
		return nick;
	}

	public void setNick(String nick) {
		this.nick = nick;
	}

	@Column(length = 200, nullable = false)
	public String getPasswordHash() {
		return passwordHash;
	}

	public void setPasswordHash(String passwordHash) {
		this.passwordHash = passwordHash;
	}

}
