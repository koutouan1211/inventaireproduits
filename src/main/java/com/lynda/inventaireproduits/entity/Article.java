package com.lynda.inventaireproduits.entity;



import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;

@Entity
@Table
public class Article {

	@Id
	@GeneratedValue(strategy=GenerationType.IDENTITY)
	private Integer id;
	private String nom;
	private Double prix;
	private Integer articlerestant;
	
	@ManyToOne
	@JoinColumn(name="panier_id", nullable=false)
	private Panier panier;
	
	
	
	public Panier getPanier() {
		return panier;
	}
	public void setPanier(Panier panier) {
		this.panier = panier;
	}
	public Integer getId() {
		return id;
	}
	public void setId(Integer id) {
		this.id = id;
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
	public Integer getArticlerestant() {
		return articlerestant;
	}
	public void setArticlerestant(Integer articlerestant) {
		this.articlerestant = articlerestant;
	}
	
	
}
