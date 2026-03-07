package com.lynda.inventaireproduits.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.lynda.inventaireproduits.entity.Panier;

public interface PanierRepository extends JpaRepository<Panier, Integer> {

}
