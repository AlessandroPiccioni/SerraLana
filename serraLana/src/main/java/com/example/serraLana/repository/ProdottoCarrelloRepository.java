package com.example.serraLana.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.example.serraLana.model.ProdottoCarrello;

@Repository
public interface ProdottoCarrelloRepository extends JpaRepository<ProdottoCarrello, Long>{

}
