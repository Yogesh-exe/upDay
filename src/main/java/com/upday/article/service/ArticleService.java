package com.upday.article.service;

import java.time.LocalDate;
import java.util.List;
import java.util.stream.Collectors;

import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.upday.article.config.ServiceProperties;
import com.upday.article.exception.BusinessException;
import com.upday.article.model.dto.ArticleDto;
import com.upday.article.model.entity.Article;
import com.upday.article.model.entity.Author;
import com.upday.article.model.entity.KeyWord;
import com.upday.article.model.mapper.ArticleMapper;
import com.upday.article.repository.ArticleRepository;
import com.upday.article.utils.ArticleValidator;

import lombok.AllArgsConstructor;


@Service
@AllArgsConstructor
@Transactional
public class ArticleService implements IArticleService {

	private ArticleRepository articleRepository;
	
	private ServiceProperties serviceProperties;

	private ArticleValidator articleValidator;
	
	@Transactional(readOnly = true)
	@Override
	public ArticleDto getArticleById(Integer articleId) {
		 Article article = findArticle(articleId);
		 return ArticleMapper.articleToArticleDto(article);
	}

	private Article findArticle(Integer articleId) {
		return articleRepository.findById(articleId)
		 .orElseThrow(()-> new BusinessException(HttpStatus.NOT_FOUND, serviceProperties.getPostNotFound(),articleId));
	}

	@Override
	public Integer createArticle(ArticleDto articleDto) {
		articleValidator.validate(articleDto);
		Article article = articleRepository.save(ArticleMapper.articleDtoToArticle(articleDto));
		return article.getId();
	}

	@Override
	public void updateArticleById(Integer articleId, ArticleDto articleDto) {
		articleValidator.validate(articleDto);
		Article originalArticle = findArticle(articleId);
		Article articleWithUpdates = ArticleMapper.articleDtoToArticle(articleDto);
		Article updatedArticle = ArticleMapper.updateArticle(originalArticle,articleWithUpdates);

		articleRepository.save(updatedArticle);
	}

	@Override
	public List<ArticleDto> getArticles(Integer authorId, Integer keywordId, LocalDate fromDate, LocalDate toDate) {
		List<Article> allArticles = null;
		if(authorId !=null) {
			allArticles = articleRepository.findAllByAuthors_Id(authorId);}
		else if(keywordId !=null) {
			allArticles = articleRepository.findAllByKeyWords_Id(keywordId);}
		else if(fromDate !=null && toDate!=null) {
			allArticles = articleRepository.findAllByPublishDateBetween(fromDate,toDate);}
		else {
			allArticles = articleRepository.findAll();
		}
		
		if(allArticles.isEmpty()) throw new BusinessException(HttpStatus.NOT_FOUND, serviceProperties.getNoArticlesFound());
		
		return allArticles.stream().map(ArticleMapper::articleToArticleDto).collect(Collectors.toList());
	}

	@Override
	public Boolean deleteArticle(Integer articleId) {
		 Article article = this.findArticle(articleId);
		 for(Author author: article.getAuthors()) {
			 article.removeAuthor(author);
		 }
		 for(KeyWord keyword: article.getKeyWords()) {
			 article.removeKeyWord(keyword);
		 }
		 articleRepository.deleteById(articleId);
		return true;
	}

}
