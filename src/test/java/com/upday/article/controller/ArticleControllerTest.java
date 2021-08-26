package com.upday.article.controller;

import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.test.context.ActiveProfiles;

import com.upday.article.exception.BusinessException;
import com.upday.article.model.dto.ArticleDto;
import com.upday.article.model.mapper.ArticleMapper;
import com.upday.article.service.IArticleService;
import com.upday.article.testhelper.TestUtil;

@WebMvcTest(controllers = ArticleController.class)
@ActiveProfiles("test")
class ArticleControllerTest {

	@Autowired 
	ArticleController articleController;
	
	@MockBean
	IArticleService articleService;
	
	@Test
	void getArticleShouldReturnArticle() {
		Mockito.when(articleService.getArticleById(1))
		.thenReturn(ArticleMapper.articleToArticleDto(TestUtil.getArticleStub()));
		
		ResponseEntity<ArticleDto> articleById = articleController.getArticleById(1);
		
		Assertions.assertThat(articleById.getBody().getId()).isEqualTo(1);
		Assertions.assertThat(articleById.getStatusCode()).isEqualTo(HttpStatus.OK);
	}
	
	
	@Test
	void getArticleShouldReturnErrorResponse() {
		Mockito.when(articleService.getArticleById(1))
		.thenThrow(new BusinessException(HttpStatus.NOT_FOUND, "Error"));
		
		Assertions.assertThatThrownBy(()->articleController.getArticleById(1)).isInstanceOf(BusinessException.class);
	}
	
	@Test
	void deleteArticleShouldReturnSuccess() {
		Mockito.when(articleService.deleteArticle(1))
		.thenReturn(true);
		
		ResponseEntity<Boolean> articleById = articleController.deleteArticleById(1);
		
		Assertions.assertThat(articleById.getBody()).isTrue();
		Assertions.assertThat(articleById.getStatusCode()).isEqualTo(HttpStatus.NO_CONTENT);
	}

}
