const link = document.createElement('link');
link.rel = 'stylesheet';
link.href = "/SerraLana/categorieUomoDonna/style.css"; // Sostituisci con il percorso del tuo file CSS
document.head.appendChild(link);


    function getQueryParam(param) {
        const params = new URLSearchParams(window.location.search); 
        return params.get(param);
    }

    const productId = getQueryParam('id');

    if (!productId) {
        document.getElementById('productDetail').innerHTML = '<p>Errore: Nessun prodotto specificato.</p>';
    } else {
        fetch('https://fakestoreapi.com/products/' + productId)
            .then(response => {
                if (!response.ok) {
                    throw new Error('Errore nella risposta del server');
                }
                return response.json();
            })
            .then(product => {
                document.getElementById('productDetail').innerHTML = `
                    <img src="${product.image}" id="imgProdotto" >
                    <div class="card-body">
                    <div id="dettagliAggiuntivi" >
                    <h1 class= "product-title" >${product.title}</h1> 
                    <p class="description" ><strong>Descrizione:</strong> ${product.description}</p>
                    <p class="price">Prezzo: $${product.price}</p>
                    </div id="bottone">  
    <button class="btn btn-dark" id="bottoneCarrello" >Aggiungi al Carrello</button>

</div>
                    </div>
                    </div>
                `;
            })
            .catch(error => {
                document.getElementById('productDetail').innerHTML = '<p>Errore nel recupero del prodotto.</p>';
                console.error('Errore:', error);
            });
    }

window.addEventListener('scroll', function() {
    const header = document.getElementById('navbar');
    if (window.scrollY > 50) {
        header.classList.add('scrolled');
    } else {
        header.classList.remove('scrolled');
    }
});