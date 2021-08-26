package com.upday.article.model.entity;

import java.time.LocalDate;
import java.util.HashSet;
import java.util.Set;

import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.EntityListeners;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.JoinTable;
import javax.persistence.ManyToMany;

import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity
@EntityListeners(AuditingEntityListener.class)
@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
public class Article {

	@Override
	public String toString() {
		return "Article [id=" + id + ", header=" + header + ", description=" + description + ", text=" + text
				+ ", publishDate=" + publishDate + "]";
	}

	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	private Integer id;

	private String header;
	
	private String description;
	
	private String text;

	@CreatedDate
	private LocalDate publishDate;

	@ManyToMany//(cascade = {CascadeType.MERGE}  )
    @JoinTable(name = "article_author",
            joinColumns = @JoinColumn(name = "article_id", referencedColumnName = "id"),
            inverseJoinColumns = @JoinColumn(name = "author_id", referencedColumnName = "id"))
	private Set<Author> authors = new HashSet<>();
	
	@ManyToMany//(cascade = {CascadeType.MERGE}  )
	private Set<KeyWord> keyWords = new HashSet<>();
	
	public void addAuthor(Author author) {
		this.authors.add(author);
		author.getArticles().add(this);
	}

	public void removeAuthor(Author author) {
		this.authors.remove(author);
		author.getArticles().remove(this);
	}
	
	public void addKeyWord(KeyWord keyword) {
		this.keyWords.add(keyword);
		keyword.getArticles().add(this);
	}
	

	public void removeKeyWord(KeyWord keyword) {
		this.keyWords.remove(keyword);
		keyword.getArticles().remove(this);
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((header == null) ? 0 : header.hashCode());
		result = prime * result + ((id == null) ? 0 : id.hashCode());
		return result;
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		Article other = (Article) obj;
		if (header == null) {
			if (other.header != null)
				return false;
		} else if (!header.equals(other.header))
			return false;
		if (id == null) {
			if (other.id != null)
				return false;
		} else if (!id.equals(other.id))
			return false;
		return true;
	}

}
