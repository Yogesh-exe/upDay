package com.upday.article.model.dto;

import java.time.LocalDate;
import java.util.HashSet;
import java.util.Set;

import javax.validation.Valid;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;

import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
public class ArticleDto {

	private Integer id;

	@NotNull(message = "Article Header should not be empty.")
	private String header;

	@NotNull(message = "Article Description should not be empty.")
	private String description;

	@NotNull(message = "Article Body should not be empty.")
	private String text;

	private LocalDate publishDate;
	
	@NotEmpty(message = "Article should have atleast one Author.")
	@Valid
	private Set<AuthorDto> authors = new HashSet<>();
	
	@Valid
	private Set<KeyWordDto> keywords = new HashSet<>();

	public ArticleDto(String header,
			 String description,
			 String text,
			 LocalDate publishDate,
			 Set<AuthorDto> authors,
			Set<KeyWordDto> keywords) {
		super();
		this.header = header;
		this.description = description;
		this.text = text;
		this.publishDate = publishDate;
		this.authors = authors;
		this.keywords = keywords;
	}

}
