package com.lynda.inventaireproduits.service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import org.springframework.stereotype.Service;


import com.lynda.inventaireproduits.dto.ArticleDTO;
import com.lynda.inventaireproduits.dto.StockDto;
import com.lynda.inventaireproduits.entity.Article;
import com.lynda.inventaireproduits.entity.Panier;
import com.lynda.inventaireproduits.entity.Stock;
import com.lynda.inventaireproduits.exception.RessourceNotFoundException;
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
	
	
	    public List<Article> getListArticle(){
	        return articleRepository.findAll();
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
	
	
	
	//on va ajouter des articles au panier si celui ci n'existe pas deja dans la base de donnée
	
	public String ajoutArticle(StockDto request,Integer panierId) {

		//si le panier exixte on ajoute simplement sur la quantitée sinon on cré un nouveau panier
	  Panier panier = panierRepository.findById(panierId)
	      .orElseGet(() -> {
	            Panier nouveauPanier = new Panier();
	         return panierRepository.save(nouveauPanier);
	           });

	    Article article = articleRepository.findById(request.getArticleId())
	            .orElseThrow(() -> new RessourceNotFoundException("Cet Article avec cet  id  "+request.getArticleId()+"est introuvable"));

	    Optional<Stock> stockExiste = stockRepository.findByPanierIdAndArticleId(panier.getId(),request.getArticleId());

	    if(stockExiste.isPresent()){
	        Stock stock = stockExiste.get();
	        stock.setQuantite(stock.getQuantite() + request.getQuantite());
	        stockRepository.save(stock);
	    }
	    else{
	        Stock stock = new Stock();
	        stock.setArticle(article);
	        stock.setPanier(panier);
	        stock.setQuantite(request.getQuantite());

	        stockRepository.save(stock);
	    }

	    return "Article ajouté au panier";
	}

	
	//supprimer le stock (des article dans le panier)
	
	public void deleteStock(Integer id) {
		Stock stock=stockRepository.findById(id)
				.orElseThrow(()-> new RuntimeException("cet stock n'existe pas"));
		stockRepository.delete(stock);
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
