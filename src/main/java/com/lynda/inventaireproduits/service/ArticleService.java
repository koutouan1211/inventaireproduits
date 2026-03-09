package com.lynda.inventaireproduits.service;

import java.util.ArrayList;
import java.util.List;


import org.springframework.stereotype.Service;

import com.lynda.inventaireproduits.dto.ArticleDTO;
import com.lynda.inventaireproduits.dto.StockDto;
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
	
	//pour recuperer la liste des articles d'un panier
	
	public List<ArticleDTO> getArticlePanier(Integer panierID){
		Panier panier=panierRepository.findById(panierID)
				.orElseThrow(()-> new RuntimeException("Panier Introuvable"));
		
		
		List<ArticleDTO> listArticle=new ArrayList<>();
		
		for(Stock stock:panier.getStocks()) {
			
			ArticleDTO request = new ArticleDTO();
			request.setNom(stock.getArticle().getNom());
			request.setPrix(stock.getArticle().getPrix());
			request.setQuantite(stock.getQuantite());
			
			listArticle.add(request);
			
		}
		
		return listArticle;
		
		//return panier.getStocks()
			//	.stream()
			//	.map(stock->new ArticleDTO(
					//	stock.getArticle().getNom(),
	                  //  stock.getArticle().getPrix(),
	                  //  stock.getQuantite()
						//))
				//.toList();
		
		
	}
	
	
	
	
	//on defini une constante TVA
	public static final double tva=0.18;
	
	
	//on va ajouter des articles au panier
	
	public String ajoutArticle(StockDto request, Integer articleID ,Integer panierID,Integer quantite){
		
		Panier panier=panierRepository.findById(panierID)
				.orElseThrow(()-> new RuntimeException("Panier Introuvable"));
		Article article=articleRepository.findById(articleID)
			.orElseThrow(() -> new RuntimeException("cet article est en rupture de stock"));
		
		List<Stock> stockExistant=stockRepository.findByPanierIdAndArticleId(panierID, articleID);
		if(!stockExistant.isEmpty()) {
			
			Stock stock=stockExistant.get(0);
			stock.setQuantite(stock.getQuantite() +quantite);
			stockRepository.save(stock);
			
			for(int i=1;i<stockExistant.size();i++) {
				stockRepository.delete(stockExistant.get(i));
			}
		}
		else {
			
			//crée un nouveau stock
			Stock stock=new Stock();
			stock.setArticle(article);
			stock.setPanier(panier);
			stock.setQuantite(quantite);
			
			//mettre a jour les lists des stocks
			panier.getStocks().add(stock);
			article.getStocks().add(stock);
					
			//on va sauvegarder
			stockRepository.save(stock);
		}
		
		
		
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
