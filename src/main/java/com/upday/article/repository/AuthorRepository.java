package com.upday.article.repository;

import org.springframework.data.repository.CrudRepository;

import com.upday.article.model.entity.Author;

public interface AuthorRepository extends CrudRepository<Author, Integer> {

}
