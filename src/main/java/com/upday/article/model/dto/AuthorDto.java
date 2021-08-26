package com.upday.article.model.dto;

import javax.validation.constraints.NotNull;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class AuthorDto {

	@NotNull(message = "Author should have id")
	private Integer id;

	private String firstName;

	private String lastName;
	
}
