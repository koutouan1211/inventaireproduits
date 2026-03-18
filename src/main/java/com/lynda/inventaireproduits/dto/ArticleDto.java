package com.lynda.inventaireproduits.dto;



public class ArticleDTO {

	private  String nom;
	private  Double prix;
	private  Integer quantite;
    private Integer stockId;
    
	
	public ArticleDTO(String nom,Double prix,Integer quantite) {
		this.nom=nom;
		this.prix=prix;
		this.quantite=quantite;
		
	}


	public ArticleDTO() {
		
	}

	
	public Integer getStockId() {
		return stockId;
	}


	public void setStockId(Integer stockId) {
		this.stockId = stockId;
	}


	public String getNom() {
		return nom;
	}


	public void setNom(String nom) {
		this.nom = nom;
	}


	public Double getPrix() {
		return prix;
	}


	public void setPrix(Double prix) {
		this.prix = prix;
	}


	public Integer getQuantite() {
		return quantite;
	}


	public void setQuantite(Integer quantite) {
		this.quantite = quantite;
	}
	
	
	
	
}