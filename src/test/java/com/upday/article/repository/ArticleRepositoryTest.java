package com.upday.article.repository;

import java.util.List;
import java.util.Optional;

import javax.transaction.Transactional;

import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.test.context.ActiveProfiles;

import com.upday.article.model.entity.Article;
import com.upday.article.testhelper.TestUtil;

@DataJpaTest
@Transactional
@ActiveProfiles("test")
class ArticleRepositoryTest {

	
	
	@Autowired
	ArticleRepository articleRepository;
	
	@BeforeEach
	void setUp() {
		articleRepository.save(TestUtil.getArticleStub());
	}
	
	@Test
	void shouldReturnArticle() {
		Optional<Article> article = articleRepository.findById(1);	
		Assertions.assertThat(article).contains(TestUtil.getArticleStub());
	}
	
	@Test
	void shouldReturnNoArticle() {
		Optional<Article> article = articleRepository.findById(4);	
		Assertions.assertThat(article).isNotPresent();
	}
	
	@Test
	void findAllshouldReturnArticle() {
		List<Article> article = articleRepository.findAllByAuthors_Id(1);	
		Assertions.assertThat(article).hasSize(1);
	}

}
