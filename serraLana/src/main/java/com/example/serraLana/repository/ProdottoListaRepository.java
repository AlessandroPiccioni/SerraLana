package com.example.serraLana.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.example.serraLana.model.ProdottoLista;

@Repository
public interface ProdottoListaRepository extends JpaRepository<ProdottoLista, Long>{

}
