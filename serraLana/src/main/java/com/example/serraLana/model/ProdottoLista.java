package com.example.serraLana.model;

import java.time.LocalDate;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Past;

@Entity
public class ProdottoLista {
	
	@Id
	@GeneratedValue(strategy= GenerationType.IDENTITY)
	private Long id;
	
	@ManyToOne
	@JoinColumn (name= "prodotto_id")
	private Prodotto prodotto;
	
	@ManyToOne
	@JoinColumn (name= "listaDesideri_id")
	private ListaDesideri listaDesideri;
	
	@NotNull(message = "Quantità è obbligatoria")
	@Min(value = 0, message = "Quantità non valida")
	private int quantita;
	
    @Past(message = "Data non valida")
    @NotNull(message = "Data inserimento è obbligatoria")
    private LocalDate dataInserimento;
    
    //Getters e setters

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public Prodotto getProdotto() {
		return prodotto;
	}

	public void setProdotto(Prodotto prodotto) {
		this.prodotto = prodotto;
	}

	public ListaDesideri getListaDesideri() {
		return listaDesideri;
	}

	public void setListaDesideri(ListaDesideri listaDesideri) {
		this.listaDesideri = listaDesideri;
	}

	public int getQuantita() {
		return quantita;
	}

	public void setQuantita(int quantita) {
		this.quantita = quantita;
	}

	public LocalDate getDataInserimento() {
		return dataInserimento;
	}

	public void setDataInserimento(LocalDate dataInserimento) {
		this.dataInserimento = dataInserimento;
	}
    

    
    

}
