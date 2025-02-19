// Funzione per ottenere e visualizzare i prodotti filtrati per categoria desiderata
function nuovaCollezioneClicked() {
    const categoria = "men's clothing"; 
    fetch("https://fakestoreapi.com/products")
        .then(response => response.json()) 
        .then(data => { // mi scorro tutti i dati e uso il metodo .filter() per ottenere solo i prodotti che appartengono alla categoria desiderata
            const filteredProducts = data.filter(product => product.category.toLowerCase() === categoria.toLowerCase());
            const output = document.getElementById("prodotti"); // Contenitore dove visualizzare i prodotti
           
            filteredProducts.forEach(product => {  
                output.innerHTML += 
                    `
                        <a href="prodotto.html?id=${product.id}" class="card-link">
                        <div class="card" style="width: 13rem;">                        
                                <img class="card-img-top" src="${product.image}" alt="${product.title}">
                                    <div class="card-body">
                                        <h5 class="card-title">${product.title}</h5> 
                                    </div>
                                        <div id="compra">
                                        <button class="btn btn-dark" id="vediDettagli" >Vedi Dettagli</button>
                                        <a href="#">
                                        <img class="card-img-top" src="/SerraLana/img/carrelloCategoria.png" id="compraCarrello">
                                        </a>
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


//Navbar
window.addEventListener('scroll', function() {
    const header = document.getElementById('navbar');
    if (window.scrollY > 50) {
        header.classList.add('scrolled');
    } else {
        header.classList.remove('scrolled');
    }
});