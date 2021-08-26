package com.upday.article.controller;

import java.net.URI;
import java.time.LocalDate;
import java.util.List;

import javax.validation.Valid;

import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import com.upday.article.model.dto.ArticleDto;
import com.upday.article.service.IArticleService;

import lombok.AllArgsConstructor;

@RestController
@RequestMapping("/articles")
@AllArgsConstructor
public class ArticleController {

	private IArticleService articleService;

	@GetMapping("/{articleId}")
	public ResponseEntity<ArticleDto> getArticleById(@PathVariable(value = "articleId",required = true) Integer articleId){
		return ResponseEntity.ok(articleService.getArticleById(articleId));
	}
	
	@PostMapping
	public ResponseEntity<URI> createArticle(@RequestBody @Valid ArticleDto article){
		Integer articleId = articleService.createArticle(article);
		
		URI location = ServletUriComponentsBuilder
                .fromCurrentRequest()
                .path("/{id}")
                .buildAndExpand(articleId)
                .toUri();
		return ResponseEntity.created(location).build();
	}

	
	@PatchMapping("/{articleId}")
	public ResponseEntity<URI> updateArticleById(@PathVariable("articleId") Integer articleId,
			@RequestBody ArticleDto article){
		articleService.updateArticleById(articleId, article);
		URI location = ServletUriComponentsBuilder
                .fromCurrentRequest()
                .build()
                .toUri();
		return new ResponseEntity<>(location, HttpStatus.ACCEPTED);
	}
	
	@DeleteMapping("/{articleId}")
	public ResponseEntity<Boolean> deleteArticleById(@PathVariable("articleId") Integer articleId){
		Boolean deleteSuccess = articleService.deleteArticle(articleId);
		return new ResponseEntity<>(deleteSuccess,HttpStatus.NO_CONTENT);
	}
	
	
	@GetMapping
	public ResponseEntity<List<ArticleDto>> getArticles(@RequestParam(value="authorId",required = false) Integer authorId,
			@RequestParam(value="keywordId",required = false) Integer keywordId,
			@RequestParam(value="fromDate",required = false) @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate fromDate,
			@RequestParam(value="toDate",required = false) @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate toDate
			){
		return ResponseEntity.ok(articleService.getArticles(authorId,keywordId,fromDate,toDate));
	}
	
}
