// Funzione per ottenere e visualizzare i prodotti filtrati per categoria
function nuovaCollezioneClicked() {
    const categoria = "women's clothing"; // Imposto la categoria desiderata
  //chiamata all api con il fetch  per recuperare tutti i prodotti
    fetch("https://fakestoreapi.com/products")
        .then(response => response.json()) //quando l api risponde converto i dati in json
        .then(data => { // mi scorro tutti i dati 
            //Una volta ottenuti i dati, uso il metodo .filter()
            //  per ottenere solo i prodotti che appartengono alla categoria desiderata
            //  (in questo caso "women's clothing").simulo che la categoria restituita dall api abbia lo stesso tipo di carattere della categoria che ho settato inizialmente in modo da risultare che il confronto sia uguale.

            const filteredProducts = data.filter(product => product.category.toLowerCase() === categoria.toLowerCase());

            const output = document.getElementById("prodotti");  // Contenitore dove visualizzare i prodotti
           
             
            filteredProducts.forEach(product => {  //  mi scorro i prodotti filtrati e li aggiungo all interno del contenitore ,
                //per ogni prodotto ci sara l immagine, il titolo,  e un button per mostrare la descrzione 
                // al clik del bottone partira la funzione togledescription (giu)
                output.innerHTML += `
                    <div class="card">
                        <img src="${product.image}" class="card-img-top" alt="${product.title}">
                        <div class="card-body">
                            <h5 class="card-title">${product.title}</h5>
                            <button class="btn btn-primary" onclick="toggleDescription(${product.id})">Vedi Dettagli</button>
                            <p class="card-text" id="desc-${product.id}">${product.description}</p> <!-- Descrizione nascosta -->
                        </div>
                    </div>
                `;
            });

            // Se non ci sono prodotti per la categoria viene mostrato un mexx per avvisare l utente 
            if (filteredProducts.length === 0) {
                output.innerHTML += "<p>Nessun prodotto trovato nella categoria Women's Clothing.</p>";
            }
        })
        .catch(error => console.error("Errore nel caricare i prodotti:", error));
}

// Funzione per mostrare/nascondere la descrizione al clic
function toggleDescription(productId) { //accetta l ingresso id del prodotto 

    const description = document.getElementById(`desc-${productId}`);//recupera la descrizione del prodotto in base all id 
    if (description.style.display === "none" || description.style.display === "") {//verifica che la descrizione non sia gia mostrata a video in
        description.style.display = "block"; // in caso negativo mostra la descrizione 
    } else {     //altrimenti
        description.style.display = "none"; // la nasconde 
    }
}
