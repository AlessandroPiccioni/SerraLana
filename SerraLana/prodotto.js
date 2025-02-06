<script>
    // Funzione per recuperare i dati del prodotto
    fetch('https://fakestoreapi.com/products/1')
        .then(response => response.json())
        .then(data => {
            // Aggiorna l'HTML con i dati ricevuti
            document.querySelector('.product-title').textContent = data.title;
            document.querySelector('.price').textContent = '€' + data.price.toFixed(2);
            document.querySelector('.description').textContent = data.description;
            document.querySelector('.product-img').src = data.image;
        })
        .catch(error => console.error('Errore nel recupero dei dati:', error));
</script>
