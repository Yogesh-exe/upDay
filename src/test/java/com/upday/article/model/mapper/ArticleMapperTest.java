package com.upday.article.model.mapper;

import java.time.LocalDate;
import java.util.Collections;
import java.util.HashSet;
import java.util.Set;

import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.Test;

import com.upday.article.model.dto.ArticleDto;
import com.upday.article.model.entity.Article;
import com.upday.article.model.entity.Author;
import com.upday.article.testhelper.TestUtil;

class ArticleMapperTest {
	
	
	@Test
	void shouldReturnDto() {
		ArticleDto articleDto = ArticleMapper.articleToArticleDto(TestUtil.getArticleStub());
		
		Assertions.assertThat(articleDto.getId()).isEqualTo(1);
		Assertions.assertThat(articleDto.getHeader()).isEqualTo("Header");
		Assertions.assertThat(articleDto.getDescription()).isEqualTo("Description");
		Assertions.assertThat(articleDto.getText()).isEqualTo("text");
		Assertions.assertThat(articleDto.getPublishDate()).isEqualTo(LocalDate.now());
		Assertions.assertThat(articleDto.getAuthors()).hasSize(1);
		Assertions.assertThat(articleDto.getKeywords()).hasSize(1);
		
	}
	
	
	@Test
	void shouldReturnArticle() {
		Article article = ArticleMapper.articleDtoToArticle(new ArticleDto("header", "Description", "text", LocalDate.now(), new HashSet<>(), new HashSet<>()));
		
		Assertions.assertThat(article.getHeader()).isEqualTo("header");
		Assertions.assertThat(article.getDescription()).isEqualTo("Description");
		Assertions.assertThat(article.getText()).isEqualTo("text");
		Assertions.assertThat(article.getPublishDate()).isEqualTo(LocalDate.now());
		Assertions.assertThat(article.getAuthors()).isEmpty();
		Assertions.assertThat(article.getKeyWords()).isEmpty();
		
	}
	
	
	@Test
	void shouldReturnUpdatedArticle() {
		
		Article articleWithUpdate = TestUtil.getArticleStub();
		articleWithUpdate.setHeader("updated header");
		articleWithUpdate.setDescription("Updated description");
		articleWithUpdate.setPublishDate(null);
		articleWithUpdate.addAuthor(new Author(2, "", ""));
		articleWithUpdate.setKeyWords(Collections.EMPTY_SET);
		
		Article updatedArticle = ArticleMapper.updateArticle(TestUtil.getArticleStub(), articleWithUpdate);

		Assertions.assertThat(updatedArticle.getId()).isEqualTo(1);
		Assertions.assertThat(updatedArticle.getHeader()).isEqualTo("updated header");
		Assertions.assertThat(updatedArticle.getDescription()).isEqualTo("Updated description");
		Assertions.assertThat(updatedArticle.getText()).isEqualTo("text");
		Assertions.assertThat(updatedArticle.getPublishDate()).isEqualTo(LocalDate.now());
		Assertions.assertThat(updatedArticle.getAuthors()).hasSize(2);
		Assertions.assertThat(updatedArticle.getKeyWords()).isEmpty();
		
	}

}
