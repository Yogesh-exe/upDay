package com.upday.article.service;

import java.time.LocalDate;
import java.util.List;

import com.upday.article.model.dto.ArticleDto;

public interface IArticleService {

	public ArticleDto getArticleById(Integer articleId) ;

	public Integer createArticle(ArticleDto articleDto);

	public void updateArticleById(Integer articleId, ArticleDto articleDto) ;
	
	public List<ArticleDto> getArticles(Integer authorId, Integer keywordId, LocalDate fromDate, LocalDate toDate);
	
	public Boolean deleteArticle(Integer articleId);
	
}
