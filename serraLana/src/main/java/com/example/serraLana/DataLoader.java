package com.example.serraLana;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;

import org.springframework.boot.CommandLineRunner;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Component;
import org.springframework.web.multipart.MultipartFile;

import com.example.serraLana.repository.CategoriaRepository;
import com.example.serraLana.repository.ProdottoRepository;
import com.example.serraLana.model.Categoria;
import com.example.serraLana.model.Prodotto;

@Component
public class DataLoader implements CommandLineRunner {
    
    private final ProdottoRepository prodottoRepository;
    private final CategoriaRepository categoriaRepository;

    /**
     * Iniezione del repository tramite costruttore.
     *
     * @param prodottoRepository Repository per la gestione dei prodotti.
     */
    public DataLoader(ProdottoRepository prodottoRepository, CategoriaRepository categoriaRepository) {
        this.prodottoRepository = prodottoRepository;
        this.categoriaRepository = categoriaRepository;
    }
    
    private final String imageFolder = "C:\\Users\\picci\\Desktop\\img";
    
    public Prodotto Upload (Prodotto prodotto, String nomeImmagine) throws IOException {
        
        //Percorso Path
        String imagePath = Paths.get(imageFolder, nomeImmagine).toString();
        
        //Controlla l'esistenza immagine
        File file = new File(imagePath);
        if (file.exists()) {
            //Leggi i dati binari dal file
            byte[] imageData = Files.readAllBytes(file.toPath());

            prodotto.setNomeImg(nomeImmagine);
            prodotto.setData(imageData);
        } else {
            System.out.println("Immagine non trovata: " + nomeImmagine);
        }

        return prodotto;
    }
    
    /**
     * Metodo eseguito al termine dell'avvio dell'applicazione.
     * Se il database è vuoto, inserisce alcuni prodotti di esempio.
     */
    @Override
    public void run(String... args) throws Exception {
        if (categoriaRepository.count() == 0) {
            //Creazione categoria femminile
            Categoria categoriaF = new Categoria(
            		null,
            		"Femminile"     
            );
            categoriaRepository.save(categoriaF);
    	
            //Creazione categoria maschile
            Categoria categoriaM = new Categoria(
            		null,
            		"Maschile"
            );
            categoriaRepository.save(categoriaM);
        }
        if (prodottoRepository.count() == 0) {
        	Categoria categoriaF = categoriaRepository.findById(1L).get();
        	Categoria categoriaM = categoriaRepository.findById(2L).get();
            //Creazione prodotto1 
            Prodotto prodotto1 = new Prodotto(
            	null,                         
            	categoriaM,                  
                "Pantalone maschile",         
                "Pantalone comodo in cotone", 
                33.50f,                     
                10,                         
                "pantalone_maschile.jpg",     
                null //Data dell immagine                      
            );
            prodottoRepository.save(Upload(prodotto1, "omino.png"));

            Prodotto prodotto2 = new Prodotto(
                null,                       
                categoriaF,              
                "Jeans donna",         
                "Jeans slim fit",      
                45.00f,              
                15,                     
                "jeans_donna.jpg",      
                null                 
            );
            prodottoRepository.save(Upload(prodotto2, "dONNA.png"));
        }
    }
}
   
