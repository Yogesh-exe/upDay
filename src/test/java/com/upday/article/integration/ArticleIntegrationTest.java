package com.upday.article.integration;

import static com.upday.article.testhelper.TestUtil.getArticleStub;
import static com.upday.article.testhelper.TestUtil.objectToString;
import static org.hamcrest.CoreMatchers.containsString;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.header;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import java.util.HashSet;

import javax.transaction.Transactional;

import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.context.SpringBootTest.WebEnvironment;
import org.springframework.http.MediaType;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.RequestBuilder;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;

import com.upday.article.model.dto.ArticleDto;
import com.upday.article.model.entity.Article;
import com.upday.article.model.mapper.ArticleMapper;
import com.upday.article.repository.ArticleRepository;

@SpringBootTest(webEnvironment = WebEnvironment.RANDOM_PORT)
@AutoConfigureMockMvc
@Transactional
@ActiveProfiles("test")
class ArticleIntegrationTest {

	@Autowired
	MockMvc mockMvc;

	@Autowired
	ArticleRepository articleRepository;

	@BeforeAll
	static void setUpBeforeClass() throws Exception {
		//articleRepository.save(ArticleMapper.articleToArticleDto(TestUtil.getArticleStub()));
	}

	@Test
	void shouldReturn404ForBadURL() throws Exception {
		RequestBuilder request = MockMvcRequestBuilders.get("/bad-url");
		mockMvc.perform(request).andExpect(status().isNotFound());
	}


	@Test
	void getArticleByIdshouldReturnArticle() throws Exception {

		articleRepository.save(getArticleStub());

		RequestBuilder request = MockMvcRequestBuilders.get("/articles/1");
		mockMvc.perform(request).andExpect(status().is2xxSuccessful())
		.andExpect(jsonPath("id").value(1))
		.andExpect(jsonPath("header").value("Header"));
	}


	@Test
	void getArticleByIdshouldReturnNotFound() throws Exception {

		RequestBuilder request = MockMvcRequestBuilders.get("/articles/1");
		mockMvc.perform(request).andExpect(status().isNotFound())
		.andExpect(jsonPath("errorMessage", containsString("Article 1 is Not Available")));
	}


	@Test
	void createArticleshouldCreateArticle() throws Exception {

		String articleDto = objectToString(ArticleMapper.articleToArticleDto(getArticleStub()));

		RequestBuilder request = MockMvcRequestBuilders.post("/articles")
				.contentType(MediaType.APPLICATION_JSON_VALUE)
				.content(articleDto);
		mockMvc.perform(request).andExpect(status().isCreated())
		.andExpect(header().string("location",containsString("articles/")));
	}
	
	@Test
	void createArticleshouldThrowBadRequest() throws Exception {

		String articleDto = objectToString(new ArticleDto("header", null, null, null, null, null));

		RequestBuilder request = MockMvcRequestBuilders.post("/articles")
				.contentType(MediaType.APPLICATION_JSON_VALUE)
				.content(articleDto);
		mockMvc.perform(request).andExpect(status().isBadRequest())
		.andExpect(jsonPath("errorMessage", containsString("should")));
	}
	
	@Test
	void getArticlesByParamshouldReturnArticle() throws Exception {

		articleRepository.save(getArticleStub());

		RequestBuilder request = MockMvcRequestBuilders.get("/articles").queryParam("authorId", "1");
		mockMvc.perform(request).andExpect(status().is2xxSuccessful())
		.andExpect(jsonPath("$[0].id").value(2));
	}
	
	
	@Test
	void updateArticlesshouldUpdateArticle() throws Exception {

		Article article = articleRepository.save(getArticleStub());
		
		String articleDto = objectToString(new ArticleDto("updated header", null, null, null, new HashSet<>(), new HashSet<>()));

		RequestBuilder request = MockMvcRequestBuilders.patch("/articles/{id}", article.getId())
				.contentType(MediaType.APPLICATION_JSON_VALUE)
				.content(articleDto);
		mockMvc.perform(request).andExpect(status().is2xxSuccessful())
		.andDo(print());
		
		RequestBuilder getRequest = MockMvcRequestBuilders.get("/articles/{id}", article.getId());
		mockMvc.perform(getRequest)
		.andExpect(jsonPath("$.header").value("updated header"));
	}

}
