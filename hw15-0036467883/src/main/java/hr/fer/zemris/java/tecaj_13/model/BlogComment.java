package hr.fer.zemris.java.tecaj_13.model;

import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;

/**
 * Model of the blog comments.
 * 
 * @author Ivan Jukić
 *
 */

@Entity
@Table(name = "blog_comments")
public class BlogComment {

	/**
	 * Id of the blog comment, generated.
	 */
	private Long id;
	/**
	 * Blog entry for the comment.
	 */
	private BlogEntry blogEntry;
	/**
	 * Email of the user that writes the comment.
	 */
	private String usersEMail;
	/**
	 * Message of the comment.
	 */
	private String message;
	/**
	 * Date the comment was posted.
	 */
	private Date postedOn;

	/**
	 * Returns the comment id.
	 * 
	 * @return comment id
	 */

	@Id
	@GeneratedValue
	public Long getId() {
		return id;
	}

	/**
	 * Sets the comment id.
	 * 
	 * @param id comment id
	 */

	public void setId(Long id) {
		this.id = id;
	}

	/**
	 * Returns the blog entry for the comment. Connected to the comment field in
	 * BlogEntry class by ManyToOne connection.
	 * 
	 * @return blog entry
	 */

	@ManyToOne
	@JoinColumn(nullable = false)
	public BlogEntry getBlogEntry() {
		return blogEntry;
	}

	/**
	 * Sets the blog entry.
	 * 
	 * @param blogEntry blog entry
	 */

	public void setBlogEntry(BlogEntry blogEntry) {
		this.blogEntry = blogEntry;
	}

	/**
	 * Returns the users email.
	 * 
	 * @return users email
	 */

	@Column(length = 100, nullable = false)
	public String getUsersEMail() {
		return usersEMail;
	}

	/**
	 * Sets the users email.
	 * 
	 * @param usersEMail users email
	 */
	public void setUsersEMail(String usersEMail) {
		this.usersEMail = usersEMail;
	}

	/**
	 * Gets the message.
	 * 
	 * @return message
	 */
	@Column(length = 4096, nullable = false)
	public String getMessage() {
		return message;
	}

	/**
	 * Sets the message.
	 * 
	 * @param message comment message
	 */
	public void setMessage(String message) {
		this.message = message;
	}

	/**
	 * Gets the time posted on.
	 * 
	 * @return time posted on
	 */
	@Temporal(TemporalType.TIMESTAMP)
	@Column(nullable = false)
	public Date getPostedOn() {
		return postedOn;
	}

	public void setPostedOn(Date postedOn) {
		this.postedOn = postedOn;
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((id == null) ? 0 : id.hashCode());
		return result;
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		BlogComment other = (BlogComment) obj;
		if (id == null) {
			if (other.id != null)
				return false;
		} else if (!id.equals(other.id))
			return false;
		return true;
	}
}