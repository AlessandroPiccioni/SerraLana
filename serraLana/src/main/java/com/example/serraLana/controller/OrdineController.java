package com.example.serraLana.controller;

import java.util.Collections;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.example.serraLana.auth.TokenService;
import com.example.serraLana.model.Ordine;
import com.example.serraLana.model.Utente;
import com.example.serraLana.repository.CarrelloRepository;
import com.example.serraLana.repository.OrdineRepository;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.validation.Valid;

@RestController
@RequestMapping("/ordine")
@CrossOrigin ("*")
public class OrdineController {
	
	@Autowired
	TokenService tokenService;
	
	@Autowired
	OrdineRepository ordineRepository;
	
	@Autowired
	CarrelloRepository carrelloRepository;
	
	   @PostMapping 
	    public Object createOrdine(@Valid @RequestBody Ordine ordine, HttpServletRequest request, HttpServletResponse response) {
	    	//Oggetto Optional che rappresenta l'utente che ha fatto la richiesta
	    	Optional<Utente> authUser = getAuthenticatedUser(request);
	    	//Controlla se ha i permessi
	    	if (!authUser.isPresent()) {
	            response.setStatus(HttpServletResponse.SC_UNAUTHORIZED);
	            return Collections.singletonMap("message", "Autenticazione richiesta");
	        }
	    	ordine.setCarrello(carrelloRepository.findByUtente_Username(authUser.get().getUsername()));
	    	Ordine savedOrdine = ordineRepository.save(ordine);
	        return new ResponseEntity<>(savedOrdine, HttpStatus.CREATED);
	    }
	    
		@GetMapping("/all")
		public List<Ordine> getAllProdotto () {
		    return ordineRepository.findAll();
		}
		
		@GetMapping("{id}")
		public ResponseEntity<Ordine> getProdotto (@PathVariable Long id) {
		    Optional<Ordine> OpOrdine = ordineRepository.findById(id);
		    if (!OpOrdine.isPresent()) {
		        return ResponseEntity.status(HttpStatus.NOT_FOUND).body(null);
		    }
		    return ResponseEntity.ok(OpOrdine.get());
		}
	    
	    @DeleteMapping("/image/{id}")
	    public Object deleteImage(@PathVariable Long id, HttpServletRequest request, HttpServletResponse response) {
	    	Optional<Utente> authUser = getAuthenticatedUser(request);
	        if (!authUser.isPresent() && !authUser.get().getRuolo().equals("admin")) {
	            response.setStatus(HttpServletResponse.SC_UNAUTHORIZED);
	            return Collections.singletonMap("message", "Non autorizzato");
	        }else {
	        	ordineRepository.deleteById(id);
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
