function nuovaCollezioneClicked() {
    const categoria = "women's clothing"; 
    fetch("https://fakestoreapi.com/products")
        .then(response => response.json()) 
        .then(data => { 
            const filteredProducts = data.filter(product => product.category.toLowerCase() === categoria.toLowerCase());
            const output = document.getElementById("prodotti"); 
           
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

            if (filteredProducts.length === 0) {
                output.innerHTML += "<p>Nessun prodotto trovato nella categoria Women's Clothing.</p>";
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


