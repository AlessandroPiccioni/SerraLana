package com.example.serraLana.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.example.serraLana.model.Prodotto;

@Repository
public interface ProdottoRepository extends JpaRepository<Prodotto, Long>{

}
