package com.upday.article.testhelper;

import java.time.LocalDate;
import java.util.HashSet;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;
import com.upday.article.model.entity.Article;
import com.upday.article.model.entity.Author;
import com.upday.article.model.entity.KeyWord;

public class TestUtil {
	
	public static Article getArticleStub() {
		Article article= new Article(1, "Header", "Description","text", LocalDate.now(), new HashSet<>(),new HashSet<>());
		article.addAuthor(getAUthorStub());
		article.addKeyWord(getKeyWordStub());
		return article;
	}
	public static Author getAUthorStub() {

		return new Author(1, "Yogesh", "Bagul");
	}
	public static KeyWord getKeyWordStub() {

		return new KeyWord(1, "news");
	}
	
	
	public static String objectToString(Object obj) {
		try {
			return new ObjectMapper().registerModule(new JavaTimeModule()).writeValueAsString(obj);
		} catch (JsonProcessingException e) {
			e.printStackTrace();
		}
		return "";
	}

}
