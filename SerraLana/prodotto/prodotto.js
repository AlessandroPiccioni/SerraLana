const link = document.createElement('link');
link.rel = 'stylesheet';
link.href = "/SerraLana/prodotto/style.css"; // Sostituisci con il percorso del tuo file CSS
document.head.appendChild(link);

let idProdotto = 0;
fetch('https://fakestoreapi.com/products/15')
            .then(res=>res.json())
            .then((data) => displayProduct(data))
            .catch(error => {
                throw new exception(error);
            });


const divProdotto = document.getElementById("prodotto");
function displayProduct(product) {
    idProdotto = product.id;
    const productCard2 =
            `
   <div class="col-md-6">
    <img src="${product.image}" class="img-fluid">
        </div>
        <div class="col-md-6" id="prodotto">
          <h2 class="product-title">${product.title} </h2>
            <p class="price">${product.price}</p>
            <p class="description">${product.description}</p>

        </div> 
                    
        `; 
    
        divProdotto.innerHTML = `<div class="row"> ${productCard2} </div>`

}
const carrello = document.getElementById("bottoneCarrello");

carrello.addEventListener("click", function() {
    console.log(idProdotto);
    sessionStorage.setItem("datoSalvato", idProdotto);
});