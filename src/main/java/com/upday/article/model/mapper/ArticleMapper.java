package com.upday.article.model.mapper;

import java.beans.PropertyDescriptor;
import java.util.Arrays;
import java.util.Objects;
import java.util.stream.Collectors;

import org.springframework.beans.BeanUtils;
import org.springframework.beans.BeanWrapper;
import org.springframework.beans.BeanWrapperImpl;
import org.springframework.stereotype.Component;

import com.upday.article.model.dto.ArticleDto;
import com.upday.article.model.dto.AuthorDto;
import com.upday.article.model.dto.KeyWordDto;
import com.upday.article.model.entity.Article;
import com.upday.article.model.entity.Author;
import com.upday.article.model.entity.KeyWord;

@Component
public class ArticleMapper {

	public static ArticleDto articleToArticleDto(Article article) {
		ArticleDto articleDto= new ArticleDto();
		BeanUtils.copyProperties(article, articleDto);
		
		 for(Author author: article.getAuthors()) {
			 articleDto.getAuthors().add(new AuthorDto(author.getId(), author.getFirstName(),author.getLastName()));
		 }
		 
		 for(KeyWord keyword: article.getKeyWords()) {
			 articleDto.getKeywords().add(new KeyWordDto(keyword.getId(), keyword.getTag()));
		 }
	
		return articleDto;
	}

	public static Article articleDtoToArticle(ArticleDto articleDto) {
		 Article article = new Article();
		 BeanUtils.copyProperties(articleDto, article);
		 for(AuthorDto author: articleDto.getAuthors()) {
			 article.addAuthor(new Author(author.getId(), author.getFirstName(),author.getLastName()));
		 }
		 
		 for(KeyWordDto keyword: articleDto.getKeywords()) {
			 article.addKeyWord(new KeyWord( keyword.getId(), keyword.getTag()));
		 }
		 
		 return article;
	}
	
	public static Article updateArticle( Article originalArticle,Article articleWithUpdate) {
		 Article article = new Article();
		 BeanUtils.copyProperties(originalArticle, article);
		 copyNonNullProperties(articleWithUpdate,article);
		 
		 article.getAuthors().retainAll(articleWithUpdate.getAuthors());
		 
		 for(Author author: articleWithUpdate.getAuthors()) {
			 article.addAuthor(new Author(author.getId(), author.getFirstName(),author.getLastName()));
		 }
		 
		 article.getKeyWords().retainAll(articleWithUpdate.getKeyWords());
		 for(KeyWord keyword: articleWithUpdate.getKeyWords()) {
			 article.addKeyWord(new KeyWord( keyword.getId(), keyword.getTag()));
		 }
				 
		 return article;
	}

	private static void copyNonNullProperties(Object src, Object target) {
	    BeanUtils.copyProperties(src, target, getNullPropertyNames(src));
	}

	private static String[] getNullPropertyNames (Object source) {
	    final BeanWrapper src = new BeanWrapperImpl(source);
	      
	    return  Arrays.stream(src.getPropertyDescriptors())
	    .filter(pd -> Objects.isNull(src.getPropertyValue(pd.getName())))
	    .map(PropertyDescriptor::getName)
	    .collect(Collectors.toSet()).toArray(new String[0]);
	}
	
	private ArticleMapper() {}
	
}
