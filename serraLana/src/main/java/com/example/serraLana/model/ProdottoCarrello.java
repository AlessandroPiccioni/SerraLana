package com.example.serraLana.model;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotNull;

@Entity
public class ProdottoCarrello {
	
	@Id
	@GeneratedValue(strategy= GenerationType.IDENTITY)
	private Long id;
	
	@ManyToOne
	@JoinColumn (name= "prodotto_id")
	private Prodotto prodotto;
	
	@ManyToOne
	@JoinColumn (name= "carrello_id")
	private Carrello carrello;
	
	@NotNull(message = "Quantità è obbligatoria")
	@Min(value = 0, message = "Quantità non valida")
	private int quantita;
	
	//getters and setters
	
	public Prodotto getProdotto() {
		return prodotto;
	}

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public void setProdotto(Prodotto prodotto) {
		this.prodotto = prodotto;
	}

	public Carrello getCarrello() {
		return carrello;
	}

	public void setCarrello(Carrello carrello) {
		this.carrello = carrello;
	}
	
	

}
