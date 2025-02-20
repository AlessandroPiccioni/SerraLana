package com.example.serraLana.controller;

import java.util.Collections;

import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.example.serraLana.model.Utente;
import com.example.serraLana.repository.UtenteRepository;
import com.example.serraLana.auth.*;


import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.validation.Valid;

@RestController
@RequestMapping("/utente")
@Validated
@CrossOrigin("*")
public class UtenteController {
	
	@Autowired
	private UtenteRepository utenteRepository;
	
    @Autowired
    private TokenService tokenService;
	
    /**Endpoint (solo per admin) per visualizzare tutti gli utenti
     * 
     * @param token
     * @param request
     * @param response
     * @return tutti gli utenti
     */
    @GetMapping("/all") 
    public Object getAllUtenti(String token, HttpServletRequest request, HttpServletResponse response) {
    	Optional<Utente> authUser = getAuthenticatedUser(request);
        if (authUser == null && !authUser.get().getRuolo().equals("admin")) {
            response.setStatus(HttpServletResponse.SC_UNAUTHORIZED);
            return Collections.singletonMap("message", "Non autorizzato");
        }
        return utenteRepository.findAll();
    }
	
    /**
     * Endpoint per trovare un singolo utente
     * 
     * @param id
     * @param request
     * @param response
     * 
     * @return ritrona lo stato http e l'utente ricercato
     */
    @GetMapping("/admin/{id}") //da admin
    public Object getUtenteByIdAdmin(@PathVariable Long id, HttpServletRequest request, HttpServletResponse response) {
    	Optional<Utente> authUser = getAuthenticatedUser(request);
        if (authUser == null && !authUser.get().getRuolo().equals("admin")) {
            response.setStatus(HttpServletResponse.SC_UNAUTHORIZED);
            return Collections.singletonMap("message", "Non autorizzato");
        }
        Optional<Utente> utente = utenteRepository.findById(id);
        if (utente.isPresent()) {
            return new ResponseEntity<>(utente.get(), HttpStatus.OK);
        } else {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
    }
    
    /**
     * Endpoint per far visualizzare le informazione dell'utentew
     * 
     * @param request
     * @param response
     * @return ritorna lo strato e le info dell utente
     */
    @GetMapping 
    public Object getUtenteById(HttpServletRequest request, HttpServletResponse response) {
    	//Ottiene l'informazioi dell'utente che ha fatto la richiesya
    	Optional<Utente> authUser = getAuthenticatedUser(request);
    	//Controlla se l'utente si stato passato correttamente ed abbia l'autorizzazione
        if (!authUser.isPresent()) {
            response.setStatus(HttpServletResponse.SC_UNAUTHORIZED);
            return Collections.singletonMap("message", "Non autorizzato");
        }else {
        	return new ResponseEntity<>(authUser.get(), HttpStatus.OK);
        }
    }
    
    /**
     * Endpoint per la registrazione dell'utente al sito
     * 
     * @param utente
     * @param request
     * @param response
     * @return ritorna la risposta dell'endpint composta da httpstatus e dal nuovo oggetto Utente
     */
    @PostMapping 
    public Object createUtenteUser(@Valid @RequestBody Utente utente,HttpServletRequest request, HttpServletResponse response) {
    	//imposta il ruolo user
    	utente.setRuolo("user");
    	//salva l'utente
    	Utente savedUtente = utenteRepository.save(utente);
        return new ResponseEntity<>(savedUtente, HttpStatus.CREATED);
    }
    
    /**
     * Endpoint per creare un altro admin
     * 
     * @param utente
     * @param request
     * @param response
     * @return ritorna lo stato e il  nuovo admin 
     */
    @PostMapping("/admin") //sia da admin che da user
    public Object createUtenteAdmin(@Valid @RequestBody Utente utente, HttpServletRequest request, HttpServletResponse response) {
    	Optional<Utente> authUser = getAuthenticatedUser(request);
        if (authUser == null && !authUser.get().getRuolo().equals("admin")) {
            response.setStatus(HttpServletResponse.SC_UNAUTHORIZED);
            return Collections.singletonMap("message", "Non autorizzato");
        }
        utente.setRuolo("admin");
    	Utente savedUtente = utenteRepository.save(utente);
        return new ResponseEntity<>(savedUtente, HttpStatus.CREATED);
    }
    
    @PutMapping("/admin/{id}") //sia da admin che da user
    public Object updateUtenteAdmin(@PathVariable Long id, @Valid @RequestBody Utente utente, HttpServletRequest request, HttpServletResponse response) {
    	Optional<Utente> authUser = getAuthenticatedUser(request);
        if (authUser == null && !authUser.get().getRuolo().equals("admin")) {
            response.setStatus(HttpServletResponse.SC_UNAUTHORIZED);
            return Collections.singletonMap("message", "Non autorizzato");
        }
        Optional<Utente> existingUtente = utenteRepository.findById(id);
        if (existingUtente.isPresent()) {
            utente.setId(id);
            Utente updatedProduct = utenteRepository.save(utente);
            return new ResponseEntity<>(updatedProduct, HttpStatus.OK);
        } else {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
    }
    
    /**
     * Endpoint per aggiornare le informazioni dell'utente
     * 
     * @param id
     * @param utente
     * @param request
     * @param response
     * @return uno stato http e le nuove informazioni dell'utente aggiornate in caso di modica con successo
     */
    @PutMapping 
    public Object updateUtenteUser(@Valid @RequestBody Utente utente, HttpServletRequest request, HttpServletResponse response) {
    	
    	//Oggetto Optional che rappresenta l'utente che ha fatto la richiesta
    	Optional<Utente> authUser = getAuthenticatedUser(request);
    	//Controlla se ha i permessi
    	if (!authUser.isPresent()) {
            response.setStatus(HttpServletResponse.SC_UNAUTHORIZED);
            return Collections.singletonMap("message", "Autenticazione richiesta");
        }
    	
    	Utente existingUtente = authUser.get();
        
        //Salva le modifiche
    	existingUtente.setUsername(utente.getUsername());
    	existingUtente.setNome(utente.getNome());
    	existingUtente.setCognome(utente.getCognome());
    	existingUtente.setEmail(utente.getEmail());
    	existingUtente.setPassword(utente.getPassword());
    	existingUtente.setDataNascita(utente.getDataNascita());
    	existingUtente.setCartaCredito(utente.getCartaCredito());
    	existingUtente.setIndirizzo(utente.getIndirizzo());
    	
    	
    	utenteRepository.save(existingUtente);  // Salva nel database
    	return new ResponseEntity<>(existingUtente, HttpStatus.OK);

    }
    
    /**
     * Endpoint per eliminare l'admin
     * 
     * @param id
     * @param request
     * @param response
     * @return lo stato http
     */
    @DeleteMapping("/admin/{id}") //sia da admin che da user
    public Object deleteUtenteAdmin(@PathVariable Long id,  HttpServletRequest request, HttpServletResponse response) {
    	Optional<Utente> authUser = getAuthenticatedUser(request);
        if (authUser == null && !authUser.get().getRuolo().equals("admin")) {
            response.setStatus(HttpServletResponse.SC_UNAUTHORIZED);
            return Collections.singletonMap("message", "Non autorizzato");
        }
        if (utenteRepository.existsById(id)) {
        	utenteRepository.deleteById(id);
            return new ResponseEntity<>(HttpStatus.NO_CONTENT);
        } else {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
    }
    
    /**
     * Endpoint per l'eliminazione dell'accoutn dell'utente
     * @param request
     * @param response
     * @return la risposta della richiesta
     */
    @DeleteMapping
    public Object deleteUtenteUser(HttpServletRequest request, HttpServletResponse response) {
    	Optional<Utente> authUser = getAuthenticatedUser(request);
        if (!authUser.isPresent()) {
            response.setStatus(HttpServletResponse.SC_UNAUTHORIZED);
            return Collections.singletonMap("message", "Non autorizzato");
        }else {
        	utenteRepository.deleteById(authUser.get().getId());
            return new ResponseEntity<>(HttpStatus.NO_CONTENT);
        }
    }
    
    /**
     * Metodo di utilità per estrarre il token di autenticazione dall'header "Authorization".
     * Il token viene inviato nel formato "Bearer <token>".
     *
     * @param request Oggetto HttpServletRequest contenente gli header della richiesta
     * @return L'oggetto AuthUser associato al token, oppure null se il token non è presente o non valido
     */
    private Optional<Utente> getAuthenticatedUser(HttpServletRequest request) {
        // Legge l'header "Authorization"
        String authHeader = request.getHeader("Authorization");
        if (authHeader != null && !authHeader.isEmpty()) {
            String token;
            // Se il token è inviato come "Bearer <token>", lo estrae
            if (authHeader.startsWith("Bearer ")) {
                token = authHeader.substring(7);
            } else {
                token = authHeader;
            }
            // Usa il TokenService per ottenere l'utente associato al token
            return tokenService.getAuthUser(token);
        }
        System.out.println("Se non c'è header \"Authorization\", restituisce null");
        // Se non c'è header "Authorization", restituisce null
        return null;
    }

}
