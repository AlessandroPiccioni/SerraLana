package com.example.serraLana.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.example.serraLana.model.Ordine;

@Repository
public interface OrdineRepository extends JpaRepository<Ordine, Long>{

}
