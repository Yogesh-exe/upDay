package com.upday.article.repository;

import java.time.LocalDate;
import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;

import com.upday.article.model.entity.Article;

public interface ArticleRepository extends JpaRepository<Article, Integer>, JpaSpecificationExecutor<Article> {

	public List<Article> findAllByAuthors_Id(Integer id);
	
	public List<Article> findAllByKeyWords_Id(Integer id);
	
	public List<Article> findAllByPublishDateBetween(LocalDate fromDate, LocalDate toDate);

}
