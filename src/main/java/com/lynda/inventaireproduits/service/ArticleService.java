package com.lynda.inventaireproduits.service;

import org.springframework.stereotype.Service;


import com.lynda.inventaireproduits.entity.Article;
import com.lynda.inventaireproduits.entity.Panier;
import com.lynda.inventaireproduits.entity.Stock;
import com.lynda.inventaireproduits.repository.ArticleRepository;
import com.lynda.inventaireproduits.repository.PanierRepository;
import com.lynda.inventaireproduits.repository.StockRepository;

@Service
public class ArticleService {

	private final ArticleRepository articleRepository;
	private final PanierRepository panierRepository ;
	private final StockRepository stockRepository;
	public ArticleService(ArticleRepository articleRepository,PanierRepository panierRepository ,StockRepository stockRepository ) {
		this.articleRepository=articleRepository;
		this.panierRepository=panierRepository;
		this.stockRepository=stockRepository;
	}
	
	
	
	
	//on defini une constante TVA
	public static final double tva=0.18;
	
	
	//on va ajouter des articles au panier
	
	public String ajoutArticle(Integer articleID ,Integer panierID,Integer quantite){
		
		Panier panier=panierRepository.findById(panierID)
				.orElseThrow(()-> new RuntimeException("Panier Introuvable"));
		Article article=articleRepository.findById(articleID)
			.orElseThrow(() -> new RuntimeException("cet article est en rupture de stock"));
		
		//crée un nouveau stock
		Stock stocks=new Stock();
		stocks.setArticle(article);
		stocks.setPanier(panier);
		stocks.setQuantite(quantite);
		
		//mettre a jour les lists des stocks
		panier.getStocks().add(stocks);
		article.getStocks().add(stocks);
				
		//on va sauvegarder
		stockRepository.save(stocks);
		
		return "Article ajouter au panier";
	}
	
	//on va calculer le total des prix et ajouter le TVA
	public Double calculMontantHt(Integer panierID) {
		Panier panier=panierRepository.findById(panierID)
				.orElseThrow();
		return panier.getStocks()
				.stream()
				.mapToDouble(stock->stock.getArticle().getPrix()*stock.getQuantite())
				.sum();
	
	}
	
	//prix avec TVA
	public Double calculTVA(Integer panierID) {
		return calculMontantHt(panierID)*tva;
	}
	
	//montantTTC
	public Double calculMontantTTC(Integer panierID) {
		return calculMontantHt(panierID)+calculTVA(panierID);
	}
}
