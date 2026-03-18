package com.lynda.inventaireproduits.controller;



import java.util.List;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.lynda.inventaireproduits.dto.ArticleDTO;
import com.lynda.inventaireproduits.dto.StockDto;
import com.lynda.inventaireproduits.entity.Article;
import com.lynda.inventaireproduits.entity.Stock;
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
	
	
	// récupérer tous les articles (pour la boutique)
    @GetMapping
    public List<Article> getListArticle(){
        return articleService.getListArticle();
    }
	
    
    @PostMapping("/panier/ajout/{panierId}")
    public ResponseEntity<String> ajouterArticle(
            @RequestBody StockDto request,
            @PathVariable Integer panierId) {

        String message = articleService.ajoutArticle(request, panierId);
        return ResponseEntity.ok(message);
    }
    
	
	//supprimer des articles dans le panier
    
    @DeleteMapping("/suppression/{id}")
    public ResponseEntity<String> supprimerArticle(@PathVariable Integer id){
    	articleService.deleteStock(id);
    	return ResponseEntity.ok("Cet Article a été supprimer");
    }
	
	 //c'est pour recuper la liste des articles du panier
	 
	@GetMapping("/panier/{panierID}")
	public List<ArticleDTO> getArticlePanier(@PathVariable Integer panierID){
		
		return articleService.getArticlePanier(panierID);
	}
	 
	
	 @GetMapping("/{panierId}/total")
	    public double total(@PathVariable Integer panierId) {
	        return articleService.calculMontantTTC(panierId);
	    }
	
}
