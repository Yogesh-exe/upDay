package com.upday.article.utils;

import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Component;

import com.upday.article.config.ServiceProperties;
import com.upday.article.exception.BusinessException;
import com.upday.article.model.dto.ArticleDto;
import com.upday.article.model.dto.AuthorDto;
import com.upday.article.model.dto.KeyWordDto;
import com.upday.article.repository.AuthorRepository;
import com.upday.article.repository.KeyWordRepository;

import lombok.AllArgsConstructor;

@Component
@AllArgsConstructor
public class ArticleValidator {

	private final AuthorRepository authorRepository;
	
	private final KeyWordRepository keyWordRepository;
	
	private final ServiceProperties serviceProperties;
	
	public void validate(ArticleDto articleDto) {
		articleDto.getAuthors().stream().forEach(this::isAuthorExist);
		articleDto.getKeywords().stream().forEach(this::isKeyWordExist);
	}
	
	private boolean isAuthorExist(AuthorDto author) {
		
		if(author.getId()==null || !authorRepository.existsById(author.getId())) {
			throw new BusinessException(HttpStatus.NOT_FOUND, serviceProperties.getAuthorNotFound(), author.getId());
		}
		return true;
		
	}
	
	private boolean isKeyWordExist(KeyWordDto keyWord) {
		if(keyWord.getId()== null || !keyWordRepository.existsById(keyWord.getId())) {
			throw new BusinessException(HttpStatus.NOT_FOUND, serviceProperties.getKeyWordNotFound(), keyWord.getId());
		}
		return true;
	}


}
