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
import com.example.serraLana.model.ListaDesideri;
import com.example.serraLana.model.Prodotto;
import com.example.serraLana.model.ProdottoLista;
import com.example.serraLana.model.Utente;
import com.example.serraLana.repository.ListaDesideriRepository;
import com.example.serraLana.repository.ProdottoListaRepository;
import com.example.serraLana.repository.ProdottoRepository;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.validation.Valid;

@RestController
@RequestMapping("/prodottolista")
@CrossOrigin ("*")
public class ProdottoListaController {
	
	@Autowired
	TokenService tokenService;
	
	@Autowired
	ProdottoListaRepository prodottoListaRepository;
	
	@Autowired
	ProdottoRepository prodottoRepository;
	
	@Autowired
	ListaDesideriRepository listaDesideriRepository;
	
	   @PostMapping ("/prdotti/{id}/listadesideri/{iddesiri}")
	    public Object createProdottoLista(@Valid @RequestBody ProdottoLista prodottoLista, @PathVariable Long id, @PathVariable Long iddesiri, HttpServletRequest request, HttpServletResponse response) {
	    	//Oggetto Optional che rappresenta l'utente che ha fatto la richiesta
	    	Optional<Utente> authUser = getAuthenticatedUser(request);
	    	//Controlla se ha i permessi
	    	if (!authUser.isPresent()) {
	            response.setStatus(HttpServletResponse.SC_UNAUTHORIZED);
	            return Collections.singletonMap("message", "Autenticazione richiesta");
	        }
	    	Prodotto prodotto = prodottoRepository.findById(id).orElse(null);
	    	ListaDesideri listaDesideri = listaDesideriRepository.findById(id).orElse(null);
	    	prodottoLista.setProdotto(prodotto);
	    	prodottoLista.setListaDesideri(listaDesideri);
	    	//prodottoLista.setCarrello(prodottoListaRepository.findByUtente_Username(authUser.get().getUsername()));
	    	ProdottoLista savedProdottoCarrello = prodottoListaRepository.save(prodottoLista);
	        return new ResponseEntity<>(savedProdottoCarrello, HttpStatus.CREATED);
	    }
	    
		@GetMapping("/all")
		public List<ProdottoLista> getAllProdottoCarrello () {
		    return prodottoListaRepository.findAll();
		}
		
		@GetMapping("{id}")
		public ResponseEntity<ProdottoLista> getProdottoCarrello (@PathVariable Long id) {
		    Optional<ProdottoLista> OpProdottoLista = prodottoListaRepository.findById(id);
		    if (!OpProdottoLista.isPresent()) {
		        return ResponseEntity.status(HttpStatus.NOT_FOUND).body(null);
		    }
		    return ResponseEntity.ok(OpProdottoLista.get());
		}
	    
	    @DeleteMapping("/{id}")
	    public Object deleteImage(@PathVariable Long id, HttpServletRequest request, HttpServletResponse response) {
	    	Optional<Utente> authUser = getAuthenticatedUser(request);
	        if (!authUser.isPresent() && !authUser.get().getRuolo().equals("admin")) {
	            response.setStatus(HttpServletResponse.SC_UNAUTHORIZED);
	            return Collections.singletonMap("message", "Non autorizzato");
	        }else {
	        	prodottoListaRepository.deleteById(id);
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
