
    fetch('https://fakestoreapi.com/products?limit=8')
        .then(res => res.json())
        .then(data => { 
            const output = document.getElementById("prodottiAggiunti"); 
           
            data.forEach(product => {  
                output.innerHTML += 
                `
                    <div class="card" style="width: 18rem;">
                        <img class="card-img-top" src="${product.image}" alt="${product.title}">
                        <div class="card-body">
                            <h5 class="card-title">"${product.title}"</h5> 
                            <p> Prezzo: ${product.price} €</p>
                            <p id="quantità"> Quantità: 1 </p>
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