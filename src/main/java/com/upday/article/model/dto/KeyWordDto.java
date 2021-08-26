package com.upday.article.model.dto;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

@AllArgsConstructor
@NoArgsConstructor
@Getter
public class KeyWordDto {

	@NotNull
	private Integer id;
	
	@NotBlank
	private String tag;
	
}
