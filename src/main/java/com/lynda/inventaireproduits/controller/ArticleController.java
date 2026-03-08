package com.lynda.inventaireproduits.controller;



import java.util.List;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.lynda.inventaireproduits.entity.Article;

import com.lynda.inventaireproduits.repository.ArticleRepository;
import com.lynda.inventaireproduits.service.ArticleService;

@RestController
@RequestMapping("/article")
public class ArticleController {

	private final ArticleRepository articleRepository;
	private final ArticleService articleService;
	public ArticleController(ArticleRepository articleRepository,ArticleService articleService){
		this.articleRepository=articleRepository;
		this.articleService=articleService;
	}
	
	@GetMapping
	public List<Article> getListArticle(){
		List<Article> toutArticle=articleRepository.findAll();
		return toutArticle;
	}
	
	
	 @PostMapping("/{panierId}/article/{articleId}")
	    public String ajouterArticle(@PathVariable Integer panierId,
	                                 @PathVariable Integer articleId ,Integer quantite) {
	        return articleService.ajoutArticle(articleId,panierId,quantite);
	    }
	
	 
	 
	 @GetMapping("/{panierId}/total")
	    public double total(@PathVariable Integer panierId) {
	        return articleService.calculMontantTTC(panierId);
	    }
	
}
