package com.lynda.inventaireproduits.repository;


import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

import com.lynda.inventaireproduits.entity.Stock;



public interface StockRepository extends JpaRepository<Stock, Integer>{

	Optional<Stock> findByPanierIdAndArticleId(Integer panierId,Integer articleId );
	
}
