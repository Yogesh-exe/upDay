package com.upday.article.model.entity;

import java.util.HashSet;
import java.util.Set;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.ManyToMany;
import javax.persistence.Table;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity
@Table(name = "KEYWORD")
@NoArgsConstructor
@Getter
@Setter
public class KeyWord {
	
	@Id
	@GeneratedValue
	private Integer id;
	
	private String tag;
	
	@ManyToMany(mappedBy = "keyWords")
	private Set<Article> articles  = new HashSet<>();

	public KeyWord(Integer id, String tag) {
		super();
		this.id=id;
		this.tag = tag;
	}

	@Override
	public String toString() {
		return "KeyWord [id=" + id + ", tag=" + tag + "]";
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((id == null) ? 0 : id.hashCode());
		result = prime * result + ((tag == null) ? 0 : tag.hashCode());
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
		KeyWord other = (KeyWord) obj;
		if (id == null) {
			if (other.id != null)
				return false;
		} else if (!id.equals(other.id))
			return false;
		if (tag == null) {
			if (other.tag != null)
				return false;
		} else if (!tag.equals(other.tag))
			return false;
		return true;
	}


	
	
	

}
