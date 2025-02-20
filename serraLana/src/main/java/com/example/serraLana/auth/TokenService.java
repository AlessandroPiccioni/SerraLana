package com.example.serraLana.auth;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.example.serraLana.repository.UtenteRepository;
import com.example.serraLana.model.*;

import java.util.Map;
import java.util.Optional;
import java.util.UUID;
import java.util.concurrent.ConcurrentHashMap;

/** 
 * Un servizio in un'applicazione software è una classe che incapsula e implementa la logica 
 * di business dell'applicazione. 
 * Questo significa che il servizio è responsabile di eseguire operazioni, elaborare dati e 
 * coordinare le interazioni tra i vari componenti (come i repository, API esterne, ecc.) 
 * senza preoccuparsi della gestione della presentazione o dell'interfaccia utente.
 * 
 * A cosa serve un servizio?
   - Isolamento della logica di business: Separare la logica di business 
   dal livello di presentazione (controller) rende il codice più organizzato, 
   modulare e manutenibile.
   - Riusabilità: La logica incapsulata in un servizio può essere riutilizzata in più punti 
   dell'applicazione.
   
   Che differenze c'è tra un service e un controller?
   La differenza principale è data dalla separazione delle responsabilità:
	Controller:
	- È responsabile di gestire le richieste HTTP e di formattare le risposte.
	- Converte i dati provenienti dalla richiesta (ad es. JSON) in oggetti, 
	e viceversa per la risposta.
	
	Il controller si occupa della comunicazione con il client, della gestione degli status HTTP 
	e dell'autenticazione/autorization a livello di interfaccia.

	Service:
	- Incapsula la logica di business e le operazioni CRUD.
	- Interagisce con il repository o altre fonti dati per eseguire le operazioni.
	- Permette di centralizzare il codice di business, facilitando il riutilizzo, 
	la manutenzione e il testing (ad esempio, testando il service in isolamento dai controller).
	
	In pratica, spostare i metodi CRUD in un service significa che il controller delega la logica 
	di business a componenti specializzati, mantenendo il codice più modulare. 
	Questo porta a un'applicazione più facilmente manutenibile, testabile e riutilizzabile.

 * */

/**
 * Servizio che si occupa della generazione e della gestione dei token di autenticazione.
 * I token vengono memorizzati in una mappa in memoria, associando ad ogni token un oggetto AuthUser.
 */
@Service
public class TokenService {
	
	@Autowired
    private UtenteRepository utenteRepository;

    /**
     * Metodo che genera un token casuale (UUID) e lo associa ad un utente autenticato.
     *
     * @param username lo username dell'utente
     * @param role     il ruolo dell'utente (es. admin o user)
     * @return il token generato
     */
    public String generateToken(String username, String password) {
    	
    	//Controlla se i campi del login siano giusti
       Optional<Utente> optionalUser = utenteRepository.findByUsername(username);
        
        if (!optionalUser.isPresent()) {
            throw new IllegalArgumentException("L'utente non esiste");
        }
        
        Utente user = optionalUser.get();
        
        if(!user.getUsername().equals(username)&& !user.getPassword().equals(password)) {
        	 throw new IllegalArgumentException("Credenziali non valide");
        }
    	
    	 // Genera un token univoco usando UUID
        String token = UUID.randomUUID().toString();
      
        System.out.println("Token generato: " + token); //stringa di debug in console
        
        user.setToken(token);

        // Salva l'utente con il nuovo token
        utenteRepository.save(user);

        return token;
    }

    /**
     * Restituisce l'oggetto Utente associato al token.
     *
     * @param token il token di autenticazione
     * @return l'oggetto AuthUser, oppure null se il token non è valido
     */
    public Optional<Utente> getAuthUser(String token) {
      	Optional<Utente> optionalUser = utenteRepository.findByToken(token);
      	if(!optionalUser.isPresent()) {
      		System.out.println("non è presente");
      	}
        return optionalUser;
    }

    /**
     * Rimuove un token dalla mappa, ad esempio durante il logout.
     *
     * @param token il token da rimuovere
     */
    public void removeToken(String token) {
    	Optional<Utente> optionalUser = utenteRepository.findByToken(token);
    	if (!optionalUser.isPresent()) {
            throw new IllegalArgumentException("Token non trovato: " + token);
        }
        Utente user = optionalUser.get();  
        user.setToken(null);
        utenteRepository.save(user);
    }
}
