package com.example.serraLana.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.example.serraLana.model.Carrello;

@Repository
public interface CarrelloRepository extends JpaRepository<Carrello, Long>{
	Carrello findByUtente_Username(String username);
}
