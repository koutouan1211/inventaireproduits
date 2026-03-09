package com.lynda.inventaireproduits.repository;

import java.util.List;


import org.springframework.data.jpa.repository.JpaRepository;

import com.lynda.inventaireproduits.entity.Stock;



public interface StockRepository extends JpaRepository<Stock, Integer>{

	List<Stock> findByPanierIdAndArticleId(Integer panierId,Integer articleId );
	
}
