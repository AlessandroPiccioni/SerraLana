package com.example.serraLana.model;

import java.util.List;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.OneToMany;
import jakarta.persistence.OneToOne;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotNull;

@Entity
public class Carrello {
	
	@Id
	@GeneratedValue(strategy= GenerationType.IDENTITY)
	private Long id;
	
	@NotNull(message = "Quantità è obbligatoria")
	@Min(value = 0, message = "Quantità non valida")
	private int quantita;
	
	@ManyToOne
	@JoinColumn (name= "utente_id")
	private Utente utente;
	
	@OneToMany
	@JoinColumn (name="carrello_id")
	private List<ProdottoCarrello> prodottoCarrello;
    
	@OneToOne(mappedBy = "carrello")   
    private Ordine ordine;
	
	//getters and setters
	
	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public int getQuantita() {
		return quantita;
	}

	public void setQuantita(int quantita) {
		this.quantita = quantita;
	}

	public Utente getUtente() {
		return utente;
	}

	public void setUtente(Utente utente) {
		this.utente = utente;
	}

	public List<ProdottoCarrello> getProdottoCarrello() {
		return prodottoCarrello;
	}

	public void setProdottoCarrello(List<ProdottoCarrello> prodottoCarrello) {
		this.prodottoCarrello = prodottoCarrello;
	}

	public Ordine getOrdine() {
		return ordine;
	}

	public void setOrdine(Ordine ordine) {
		this.ordine = ordine;
	}
	
	
	

	
	

}
