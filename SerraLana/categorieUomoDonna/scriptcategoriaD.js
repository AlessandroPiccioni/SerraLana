function nuovaCollezioneClicked() {
    const categoria = "women's clothing"; 
    fetch("https://fakestoreapi.com/products")
        .then(response => response.json()) 
        .then(data => { 
            const filteredProducts = data.filter(product => product.category.toLowerCase() === categoria.toLowerCase());
            const output = document.getElementById("prodotti"); 
           
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

            if (filteredProducts.length === 0) {
                output.innerHTML += "<p>Nessun prodotto trovato nella categoria Women's Clothing.</p>";
            }
        })
        .catch(error => console.error("Errore nel caricare i prodotti:", error));
}


function toggleDescription(productId) { 
    const description = document.getElementById(`desc-${productId}`);
    if (description.style.display === "none" || description.style.display === "") {
        description.style.display = "block";
    } else {     
        description.style.display = "none"; 
    }
}