package bd2.mappings;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;

@Entity
public class PostComment {

	@Id
	@GeneratedValue
	private Long id;
	private String comment;
	@ManyToOne
	@JoinColumn(name = "id_post")
	private Post post;

	protected PostComment() {
	}

	public PostComment(String comment) {
		this.comment = comment;
	}

	private Long getId() {
		return id;
	}

	private void setId(Long id) {
		this.id = id;
	}

	private String getComment() {
		return comment;
	}

	private void setComment(String comment) {
		this.comment = comment;
	}

	private Post getPost() {
		return post;
	}

	private void setPost(Post post) {
		this.post = post;
	}
}
