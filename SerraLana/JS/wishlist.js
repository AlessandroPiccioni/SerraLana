fetch('https://fakestoreapi.com/products?limit=8')
.then(res => res.json())
.then(data => { 
    window.onload = function() {  };
    const output = document.getElementById("prodottiAggiunti"); 

        data.forEach(product => {  
            output.innerHTML += 
            `


                            <div class="card" style="width: 18rem;">                        
                                <img class="card-img-top" src="${product.image}" alt="${product.title}" id="idProdotto">
                                    <div class="card-body">
                                        <h5 class="card-title">${product.title}</h5> 
                                            <p> Prezzo: ${product.price} €</p>
                                            <p id="quantità"> Quantità: 1 </p>
                                    </div>
                                        <div id="rimuovi">
                                        <button class="btn btn-dark" id="bottoneAggiungi">Aggiungi al Carrello </button>
                                        <a href="https://www.example.com">
                                        <img class="card-img-top" src="/SerraLana/img/elimina.png" id="rimuoviCarrello">
                                        </a>
                                        </div>
                            </div>

            `;
          
        }); 
    })


    window.addEventListener('scroll', function() {
        const header = document.getElementById('navbar');
        if (window.scrollY > 50) {
            header.classList.add('scrolled');
        } else {
            header.classList.remove('scrolled');
        }
    });


/*     <div class="card" style="width: 18rem;">
    <img class="card-img-top" src="${product.image}" alt="${product.title}">
    <div class="card-body">
        <h5 class="card-title">"${product.title}"</h5> 
        <p> Prezzo: ${product.price} €</p>
        <p id="quantità"> Quantità: 1 </p>
    <p id="bottone1"> <button class="btn btn-dark"  >Aggiungi al Carrello </button> </p>
    <p id="bottone2"> <button class="btn btn-dark"  >Rimuovi dai preferiti</button> </p> 
</div>
</div>  */