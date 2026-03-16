package com.lynda.inventaireproduits.repository;



import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import com.lynda.inventaireproduits.entity.Article;

public interface ArticleRepository extends JpaRepository<Article, Integer> {

	List<Article> findAll();
	
	}
