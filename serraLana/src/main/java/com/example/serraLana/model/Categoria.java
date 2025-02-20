package com.example.serraLana.model;

import java.util.List;

import com.fasterxml.jackson.annotation.JsonManagedReference;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.validation.constraints.NotBlank;
import jakarta.persistence.OneToMany;

@Entity
public class Categoria {
	
	@Id
	@GeneratedValue (strategy = GenerationType.IDENTITY)
	private Long id;
	
	@OneToMany
	@JoinColumn (name="categoria_id")
	@JsonManagedReference
	List<Prodotto> prodotto;
	
	@NotBlank(message = "Categoria è obbligatoria")
	private String categoria;
	
    public Categoria() {
    }
	
	public Categoria(Long id,
			@NotBlank(message = "Categoria è obbligatoria") String categoria) {
		this.id = id;
		this.categoria = categoria;
	}
	
	//Getters e setters

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public List<Prodotto> getProdotto() {
		return prodotto;
	}

	public void setProdotto(List<Prodotto> prodotto) {
		this.prodotto = prodotto;
	}

	public String getCategoria() {
		return categoria;
	}

	public void setCategoria(String categoria) {
		this.categoria = categoria;
	}


	
	
	
	
	
	
	


}
