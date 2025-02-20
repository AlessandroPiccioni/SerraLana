package com.example.serraLana.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.example.serraLana.model.ListaDesideri;

@Repository
public interface ListaDesideriRepository extends JpaRepository<ListaDesideri, Long> {

}
