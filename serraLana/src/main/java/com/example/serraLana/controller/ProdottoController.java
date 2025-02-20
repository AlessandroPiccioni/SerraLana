package com.example.serraLana.controller;

import java.io.IOException;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;
import com.example.serraLana.auth.*;
import com.example.serraLana.model.Prodotto;
import com.example.serraLana.model.Utente;
import com.example.serraLana.repository.*;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.validation.Valid;

@RestController
@RequestMapping("/prodotto")
@CrossOrigin ("*")
public class ProdottoController {
	
	@Autowired
	private ProdottoRepository prodottoRepository;
	
    @Autowired
    private TokenService tokenService;
	
	/**
	 * Endpoint per prendere tutti i prodotti
	 * @return tutti i prodotti
	 */
	@GetMapping("/all")
	public List<Prodotto> getAllProdotto () {
	    return prodottoRepository.findAll();
	}
	
	/**
	 * Endpoint per prendere tutti per categoria (maschile/femminile)
	 * @param cat rappresenta la cetegoria dei prodotti
	 * @return tutti i prodotti per categoria
	 */
	@GetMapping("/genere/categoria/{cat}")
	public List<Prodotto> getAllGenereProdotto (@PathVariable String cat) {
	    List<Prodotto> risultati = new ArrayList<>();
	    for (Prodotto prodotto : prodottoRepository.findAll()) {
	        if (prodotto.getCategoria().getCategoria().equals(cat)) {
	        	risultati.add(prodotto);
	        }
	    }
	    return risultati;
	}
	
	/**
	 * Endpoint per prendere un prodotto per id
	 * @param id rappresenta is univoco del prodotto
	 * @return il prodotto corrispodente all'id
	 */
	@GetMapping("{id}")
	public ResponseEntity<Prodotto> getProdotto (@PathVariable Long id) {
	    Optional<Prodotto> OpProdotto = prodottoRepository.findById(id);
	    if (!OpProdotto.isPresent()) {
	        return ResponseEntity.status(HttpStatus.NOT_FOUND).body(null);
	    }
	    return ResponseEntity.ok(OpProdotto.get());
	}
	
	@GetMapping("/image/{id}")
	public ResponseEntity<byte[]> getImageForProdotto (@PathVariable Long id) {
	    Optional<Prodotto> OpProdotto = prodottoRepository.findById(id);
	    if (!OpProdotto.isPresent() || OpProdotto.get().getNomeImg() == null) {
	        return ResponseEntity.status(HttpStatus.NOT_FOUND).body(null);
	    }
	    Prodotto prodotto = OpProdotto.get();
	    return ResponseEntity.ok()
	            .header(HttpHeaders.CONTENT_DISPOSITION, "attachment; filename=\"" + prodotto.getNomeImg() + "\"")
	            .body(prodotto.getData());
	}

	@GetMapping("/image/genere/categoria/{cat}")
	public List<ResponseEntity<byte[]>> getImagesByCategory(@PathVariable String cat) {
	    List<ResponseEntity<byte[]>> responses = new ArrayList<>();
	    for (Prodotto prodotto : prodottoRepository.findAll()) {
	        if (prodotto.getCategoria().getCategoria().equals(cat)) {
	            if (prodotto.getNomeImg() != null) {
	                ResponseEntity<byte[]> response = ResponseEntity.ok()
	                        .header(HttpHeaders.CONTENT_DISPOSITION, "attachment; filename=\"" + prodotto.getNomeImg() + "\"")
	                        .body(prodotto.getData());
	                responses.add(response);  
	            } else {
	                ResponseEntity<byte[]> response = ResponseEntity.status(HttpStatus.NOT_FOUND).body(null);
	                responses.add(response);  
	            }
	        }
	    }
	    return responses;
	}
	
	@GetMapping("/image/all")
	public List<ResponseEntity<byte[]>> getAllImages() {
	    List<ResponseEntity<byte[]>> responses = new ArrayList<>();
	    for (Prodotto prodotto : prodottoRepository.findAll()) {
	        if (prodotto.getNomeImg() != null) {
	            ResponseEntity<byte[]> response = ResponseEntity.ok()
	            	.header(HttpHeaders.CONTENT_DISPOSITION, "attachment; filename=\"" + prodotto.getNomeImg() + "\"")
	                .body(prodotto.getData());
	            responses.add(response);  
	        } else {
	            ResponseEntity<byte[]> response = ResponseEntity.status(HttpStatus.NOT_FOUND).body(null);
	            responses.add(response);  
	        }
	    }
	    return responses;
	}
	
    @DeleteMapping("/{id}")
    public Object deleteProdotto(@PathVariable Long id, HttpServletRequest request, HttpServletResponse response) {
    	Optional<Utente> authUser = getAuthenticatedUser(request);
        if (!authUser.isPresent() && !authUser.get().getRuolo().equals("admin")) {
            response.setStatus(HttpServletResponse.SC_UNAUTHORIZED);
            return Collections.singletonMap("message", "Non autorizzato");
        }else {
        	prodottoRepository.deleteById(id);
            return new ResponseEntity<>(HttpStatus.NO_CONTENT);
        }
    }
    
    @PutMapping("/{id}") 
    public Object updateProdotto(@PathVariable Long id, @Valid @RequestBody Prodotto prodotto, HttpServletRequest request, HttpServletResponse response) {
    	
    	//Oggetto Optional che rappresenta l'utente che ha fatto la richiesta
    	Optional<Utente> authUser = getAuthenticatedUser(request);
    	//Controlla se ha i permessi
    	if (!authUser.isPresent() && !authUser.get().getRuolo().equals("admin")) {
            response.setStatus(HttpServletResponse.SC_UNAUTHORIZED);
            return Collections.singletonMap("message", "Autenticazione richiesta");
        }
        
        //Salva le modifiche
        prodotto.setId(id);
        Prodotto updatedProduct = prodottoRepository.save(prodotto);
        
        //Ritorna la risposta dell'endpoint
        return new ResponseEntity<>(updatedProduct, HttpStatus.OK);
    }
    
    @PostMapping 
    public Object createProdotto(@Valid @RequestBody Prodotto prodotto,HttpServletRequest request, HttpServletResponse response) {
    	//Oggetto Optional che rappresenta l'utente che ha fatto la richiesta
    	Optional<Utente> authUser = getAuthenticatedUser(request);
    	//Controlla se ha i permessi
    	if (!authUser.isPresent() && !authUser.get().getRuolo().equals("admin")) {
            response.setStatus(HttpServletResponse.SC_UNAUTHORIZED);
            return Collections.singletonMap("message", "Autenticazione richiesta");
        }
    	//salva l'utente
    	Prodotto savedProdotto = prodottoRepository.save(prodotto);
        return new ResponseEntity<>(savedProdotto, HttpStatus.CREATED);
    }
    
    @PostMapping("/image")
    public Object uploadImage(@PathVariable Long id, @RequestParam("file") MultipartFile file, HttpServletRequest request, HttpServletResponse response) {
        try {
        	//Oggetto Optional che rappresenta l'utente che ha fatto la richiesta
        	Optional<Utente> authUser = getAuthenticatedUser(request);
        	//Controlla se ha i permessi
        	if (!authUser.isPresent() && !authUser.get().getRuolo().equals("admin")) {
                response.setStatus(HttpServletResponse.SC_UNAUTHORIZED);
                return Collections.singletonMap("message", "Autenticazione richiesta");
            }
        	Optional<Prodotto> Opprodotto = prodottoRepository.findById(id);
        	if(!Opprodotto.isPresent()) {
                response.setStatus(HttpServletResponse.SC_BAD_REQUEST);
                return Collections.singletonMap("message", "Richiesta non valida");
        	}
        	Prodotto prodotto = Opprodotto.get();
        	prodotto.setNomeImg(file.getOriginalFilename());
        	prodotto.setData(file.getBytes());
            Prodotto savedProodtto = prodottoRepository.save(prodotto);
            // Restituisce una ResponseEntity con lo stato HTTP 201 (Created) e l'immagine salvata
            return new ResponseEntity<>(savedProodtto, HttpStatus.CREATED);
        } catch (IOException e) {
            // In caso di errore durante la lettura dei dati del file, restituisce lo stato HTTP 500 (Internal Server Error)
            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }
    
    @DeleteMapping("/image/{id}")
    public Object deleteImage(@PathVariable Long id, HttpServletRequest request, HttpServletResponse response) {
    	Optional<Utente> authUser = getAuthenticatedUser(request);
    	//Controlla se ha i permessi
    	if (!authUser.isPresent() && !authUser.get().getRuolo().equals("admin")) {
            response.setStatus(HttpServletResponse.SC_UNAUTHORIZED);
            return Collections.singletonMap("message", "Autenticazione richiesta");
        }
        Optional<Prodotto> Opprodotto = prodottoRepository.findById(id);
        if(!Opprodotto.isPresent()) {
            response.setStatus(HttpServletResponse.SC_BAD_REQUEST);
            return Collections.singletonMap("message", "Richiesta non valida");
        }
        Prodotto prodotto = Opprodotto.get();
        prodotto.setNomeImg(null);
        prodotto.setData(null);
        Prodotto updatedProduct = prodottoRepository.save(prodotto);
        
        //Ritorna la risposta dell'endpoint
        return new ResponseEntity<>(updatedProduct, HttpStatus.OK);
        
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
