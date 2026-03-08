package com.lynda.inventaireproduits.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.lynda.inventaireproduits.entity.Stock;

public interface StockRepository extends JpaRepository<Stock, Integer>{

}
