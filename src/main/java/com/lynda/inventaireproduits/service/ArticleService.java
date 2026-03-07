package com.lynda.inventaireproduits.service;





import org.springframework.stereotype.Service;


import com.lynda.inventaireproduits.entity.Article;
import com.lynda.inventaireproduits.entity.Panier;
import com.lynda.inventaireproduits.repository.ArticleRepository;
import com.lynda.inventaireproduits.repository.PanierRepository;

@Service
public class ArticleService {

	private final ArticleRepository articleRepository;
	private final PanierRepository panierRepository ;
	public ArticleService(ArticleRepository articleRepository,PanierRepository panierRepository ) {
		this.articleRepository=articleRepository;
		this.panierRepository=panierRepository;
	}
	
	
	
	
	//on defini une constante TVA
	public static final double tva=0.18;
	
	
	//on va ajouter des articles au panier
	
	public Panier ajoutArticle(Integer articleID ,Integer panierID){
		
		Panier panier=panierRepository.findById(panierID)
				.orElseThrow(()-> new RuntimeException("Panier vide"));
		Article article=articleRepository.findById(articleID)
				.orElseThrow(() -> new RuntimeException("cet article est en rupture de stock"));
		panier.getArticles().add(article);
		
		//on va sauvegarder
		return panierRepository.save(panier);
	}
	
	
	//on va calculer le total des prix et ajouter le TVA
	public Double calculMontantHt(Integer panierID) {
		Panier panier=panierRepository.findById(panierID)
				.orElseThrow();
		return panier.getArticles()
				.stream()
				.mapToDouble(Article->Article.getPrix())
				.sum();
	
	}
	
	
	public Double calculTVA(Integer panierID) {
		return calculMontantHt(panierID)*tva;
	}
	
	public Double calculMontantTTC(Integer panierID) {
		return calculMontantHt(panierID)+calculTVA(panierID);
	}
}
