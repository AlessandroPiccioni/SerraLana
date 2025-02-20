package com.example.serraLana.model;

import java.util.Arrays;

import java.util.List;

import com.fasterxml.jackson.annotation.JsonBackReference;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.Lob;
import jakarta.persistence.OneToMany;
import jakarta.validation.constraints.*;
import jakarta.persistence.ManyToOne;

@Entity
public class Prodotto {
	
	public static final long Max_file_size= 100000;
	public static List<String> Content_Types = Arrays.asList(
    "image/svg+xml", 
    "image/png", 
    "image/jpeg", 
    "image/jpe", 
    "image/jpg", 
    "application/pdf"
	);
	
	/*
	 * Chiave primaria dell'entità Prodotto
	 * Chiave primaria autoincrementante
	 * 
	 */
	@Id
	@GeneratedValue(strategy= GenerationType.IDENTITY)
	private Long id;
	
	@OneToMany
	@JoinColumn (name="prodotto_id")
	private List<ProdottoLista> prodottoLista;
	
	@ManyToOne
	@JoinColumn (name= "categoria_id")
	@JsonBackReference
	private Categoria categoria;
	
	@OneToMany
	@JoinColumn (name="prodotto_id")
	private List<ProdottoCarrello> prodottoCarello;
	
	//Nome prodotto
	@NotBlank(message = "Nome prodotto è obbligatorio")
	private String titolo;
	
	//descrizione prodotto
	@Size(max = 500, message = "Descrizione massimo di 500 caratteri")
	private String descrizione;
	
	//prezzo prodotto
	@NotNull(message = "Prezzo è obbligatorio")
	@Positive(message = "Prezzo deve essere positivo")
	private float prezzo;
	
	//quantita massima prodotto
	@NotNull(message = "Quantità è obbligatoria")
	@Min(value = 0, message = "Quantità non valida")
	private int quantitaMax;
	
    //Campo che memorizza il nome dell'immagine
    private String nomeImg;

    //Campo che contiene i dati binari dell'immagine
    //@Lob indica che il campo deve essere trattato come un Large Object (ad esempio BLOB per dati binari)
    @Lob
    //Specifica la lunghezza massima della colonna nel database
    @Column(length = 100000)
    private byte[] data;
    
    public Prodotto() {
    }
	
	//getters e setters
	
	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public String getTitolo() {
		return titolo;
	}

	public void setTitolo(String titolo) {
		this.titolo = titolo;
	}

	public String getDescrizione() {
		return descrizione;
	}

	public void setDescrizione(String descrizione) {
		this.descrizione = descrizione;
	}

	public float getPrezzo() {
		return prezzo;
	}

	public void setPrezzo(float prezzo) {
		this.prezzo = prezzo;
	}

	public int getQuantitaMax() {
		return quantitaMax;
	}

	public void setQuantitaMax(int quantitaMax) {
		this.quantitaMax = quantitaMax;
	}

	public List<ProdottoLista> getProdottoLista() {
		return prodottoLista;
	}

	public void setProdottoLista(List<ProdottoLista> prodottoLista) {
		this.prodottoLista = prodottoLista;
	}

	public Categoria getCategoria() {
		return categoria;
	}

	public void setCategoria(Categoria categoria) {
		this.categoria = categoria;
	}

	public String getNomeImg() {
		return nomeImg;
	}

	public void setNomeImg(String nomeImg) {
		this.nomeImg = nomeImg;
	}

	public byte[] getData() {
		return data;
	}

	public void setData(byte[] data) {
		this.data = data;
	}

	public List<ProdottoCarrello> getProdottoCarello() {
		return prodottoCarello;
	}

	public void setProdottoCarello(List<ProdottoCarrello> prodottoCarello) {
		this.prodottoCarello = prodottoCarello;
	}

	public Prodotto(Long id, Categoria categoria, @NotBlank(message = "Nome prodotto è obbligatorio") String titolo,
			@Size(max = 500, message = "Descrizione massimo di 500 caratteri") String descrizione,
			@NotNull(message = "Prezzo è obbligatorio") @Positive(message = "Prezzo deve essere positivo") float prezzo,
			@NotNull(message = "Quantità è obbligatoria") @Min(value = 0, message = "Quantità non valida") int quantitaMax,
			String nomeImg, byte[] data) {
		super();
		this.id = id;
		this.categoria = categoria;
		this.titolo = titolo;
		this.descrizione = descrizione;
		this.prezzo = prezzo;
		this.quantitaMax = quantitaMax;
		this.nomeImg = nomeImg;
		this.data = data;
	}
	
	
	
	
	

}
