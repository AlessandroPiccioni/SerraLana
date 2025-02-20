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
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.example.serraLana.auth.TokenService;
import com.example.serraLana.model.Categoria;
import com.example.serraLana.model.Utente;
import com.example.serraLana.repository.CategoriaRepository;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.validation.Valid;

@RestController
@RequestMapping("/categoria")
@CrossOrigin ("*")
public class CategoriaController {
	
	//Elimina una categroia
	
	@Autowired
	CategoriaRepository categoriaRepository;
	
	@Autowired 
	TokenService tokenService;
	
	
	@GetMapping("/all")
	public List<Categoria> getAllCategoria () {
	    return categoriaRepository.findAll();
	}
	
	@GetMapping("{id}")
	public ResponseEntity<Categoria> getCategoria (@PathVariable Long id) {
	    Optional<Categoria> OpCategoria = categoriaRepository.findById(id);
	    if (!OpCategoria.isPresent()) {
	        return ResponseEntity.status(HttpStatus.NOT_FOUND).body(null);
	    }
	    return ResponseEntity.ok(OpCategoria.get());
	}
	
    @PutMapping("/{id}") 
    public Object updateCategoria(@PathVariable Long id, @Valid @RequestBody Categoria categoria, HttpServletRequest request, HttpServletResponse response) {
    	
    	//Oggetto Optional che rappresenta l'utente che ha fatto la richiesta
    	Optional<Utente> authUser = getAuthenticatedUser(request);
    	//Controlla se ha i permessi
    	if (!authUser.isPresent() && !authUser.get().getRuolo().equals("admin")) {
            response.setStatus(HttpServletResponse.SC_UNAUTHORIZED);
            return Collections.singletonMap("message", "Autenticazione richiesta");
        }
        
        //Salva le modifiche
        categoria.setId(id);
        Categoria updatedCategoria = categoriaRepository.save(categoria);
        
        //Ritorna la risposta dell'endpoint
        return new ResponseEntity<>(updatedCategoria, HttpStatus.OK);
    }
    
    @DeleteMapping("{id}")
    public Object deleteImage(@PathVariable Long id, HttpServletRequest request, HttpServletResponse response) {
    	Optional<Utente> authUser = getAuthenticatedUser(request);
        if (!authUser.isPresent() && !authUser.get().getRuolo().equals("admin")) {
            response.setStatus(HttpServletResponse.SC_UNAUTHORIZED);
            return Collections.singletonMap("message", "Non autorizzato");
        }else {
        	categoriaRepository.deleteById(id);
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
