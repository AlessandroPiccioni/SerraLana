// Funzione per ottenere e visualizzare i prodotti filtrati per categoria desiderata
function nuovaCollezioneClicked() {
    const categoria = "men's clothing"; 
    fetch("https://fakestoreapi.com/products")
        .then(response => response.json()) 
        .then(data => { // mi scorro tutti i dati e uso il metodo .filter() per ottenere solo i prodotti che appartengono alla categoria desiderata
            const filteredProducts = data.filter(product => product.category.toLowerCase() === categoria.toLowerCase());
            const output = document.getElementById("prodotti"); // Contenitore dove visualizzare i prodotti
           
            filteredProducts.forEach(product => {  
                output.innerHTML += `
                    <div class="card">
                        <img src="${product.image}" class="card-img-top" alt="${product.title}">
                        <div class="card-body">
                            <h5 class="card-title">${product.title}</h5>
                            <button class="btn btn-dark" onclick="toggleDescription(${product.id})">Vedi Dettagli</button>
                            <p class="card-text" id="desc-${product.id}">${product.description}</p> <br>
                            <button class="btn btn-dark" id="bottoneCarrello" >Aggiungi al Carrello</button>

                        </div>
                    </div>
                `;
            });

           // Se non ci sono prodotti per la categoria viene mostrato un mexx per avvisare l utente 
            if (filteredProducts.length === 0) {
                output.innerHTML += "<p>Nessun prodotto trovato nella categoria Men's Clothing.</p>";
            }
        })
        .catch(error => console.error("Errore nel caricare i prodotti:", error));
}

// Funzione per mostrare/nascondere la descrizione al clic
function toggleDescription(productId) { 
    const description = document.getElementById(`desc-${productId}`);//recupera la descrizione del prodotto in base all id 
    if (description.style.display === "none" || description.style.display === "") {//verifica che la descrizione non sia gia mostrata a video in
        description.style.display = "block"; //mostra la descrizione 
    } else {     
        description.style.display = "none"; //la nasconde 
    }
}