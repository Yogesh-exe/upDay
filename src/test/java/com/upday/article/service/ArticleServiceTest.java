package com.upday.article.service;


import static com.upday.article.testhelper.TestUtil.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyInt;
import static org.mockito.Mockito.doNothing;
import static org.mockito.Mockito.doReturn;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import java.time.LocalDate;
import java.util.Collections;
import java.util.HashSet;
import java.util.List;
import java.util.Optional;

import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Spy;
import org.mockito.junit.jupiter.MockitoExtension;

import com.upday.article.config.ServiceProperties;
import com.upday.article.exception.BusinessException;
import com.upday.article.model.dto.ArticleDto;
import com.upday.article.model.entity.Article;
import com.upday.article.model.entity.Author;
import com.upday.article.model.entity.KeyWord;
import com.upday.article.model.mapper.ArticleMapper;
import com.upday.article.repository.ArticleRepository;
import com.upday.article.utils.ArticleValidator;

@ExtendWith(MockitoExtension.class)
class ArticleServiceTest {

	@Mock
	ArticleRepository articlerepository;

	@Spy
	ArticleMapper articleMapper;

	@Mock
	ArticleValidator articleValidator;

	@InjectMocks
	ArticleService articleService;
	
    @Mock
    ServiceProperties serviceProperties;

    Article article = getArticleStub();

	@BeforeAll
	static void setUpBeforeClass() throws Exception {
	}
	
	@BeforeEach
	void setUpBeforeEach() {

	}

	@Test
	void getArticleByIdShouldReturnArticle() {
		when(articlerepository.findById(anyInt())).thenReturn(Optional.of(article));

		ArticleDto articleDto = articleService.getArticleById(1);

		Assertions.assertThat(articleDto.getId()).isEqualTo(article.getId());
		Assertions.assertThat(articleDto.getAuthors()).isNotEmpty();
	}
	
	@Test
	void getArticleByIdShouldThrowNotFound() {
		when(articlerepository.findById(anyInt())).thenReturn(Optional.ofNullable(null));
		when(serviceProperties.getPostNotFound()).thenReturn("Article %d is Not Available");

		Assertions.assertThatThrownBy(()-> articleService.getArticleById(1))
					.isInstanceOf(BusinessException.class)
					.extracting("finalMessage")
					.isEqualTo("Article 1 is Not Available");
	}

	@Test
	void createArticleShouldReturnCreatedArticleId() {
		when(articlerepository.save(any())).thenReturn(article);
		doNothing().when(articleValidator).validate(any());
		
		Integer articleId = articleService.createArticle(new ArticleDto("Header", "Description","text", LocalDate.now(), new HashSet<>(),new HashSet<>()));
		Assertions.assertThat(articleId).isEqualTo(1);
	}

	@Test
	void testGetArticles() {
		Article article= new Article(1, "Header", "Description","text", LocalDate.now(), new HashSet<>(),new HashSet<>());
		article.addAuthor(getAUthorStub());
		article.addKeyWord(new KeyWord(2, "tag"));
		doReturn(List.of(getArticleStub(),article)).when(articlerepository).findAllByKeyWords_Id(1);
		
		List<ArticleDto> articles = articleService.getArticles(null, 1, null, null);
		
		verify(articlerepository,times(1)).findAllByKeyWords_Id(1);
	}
	
	@Test
	void testGetArticlesShouldThrowNotFound() {
		doReturn(Collections.EMPTY_LIST).when(articlerepository).findAll();
		when(serviceProperties.getNoArticlesFound()).thenReturn("No Articles are found as per filer criteria.");

		Assertions.assertThatThrownBy(()-> articleService.getArticles(null, null, null, null))
					.isInstanceOf(BusinessException.class)
					.extracting("finalMessage")
					.isEqualTo("No Articles are found as per filer criteria.");

	}

	@Test
	void testDeleteArticle() {
		Article article = getArticleStub();
		when(articlerepository.findById(anyInt())).thenReturn(Optional.of(article));

		Boolean isDeleted = articleService.deleteArticle(1);

		Assertions.assertThat(isDeleted).isTrue();
	}


}
