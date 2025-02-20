//Funzione per effettuare log out
function logout() {
    const token = localStorage.getItem("authToken");
    if (!token) {
      console.error("Nessun token trovato nel localStorage");
      return;
    }
    
    fetch('http://localhost:8080/api/logout', { 
        method: 'POST',
        headers: {
          'Content-Type': 'application/json',
          'Authorization': 'Bearer ' + token
        }
    })
    .then(response => {
        if (!response.ok) {
            throw new Error('Logout fallito');
        }
        return response.json();
    })
    .then(data => {
        console.log('Logout effettuato:', data);
        // Rimuove il token dal localStorage
        localStorage.removeItem("authToken");
        window.alert("Logout effettuato.");
        location.replace("http://127.0.0.1:3000/SerraLana/HTML/home.html")

    })
    .catch(error => {
        console.error('Errore durante il logout:', error);
        window.alert("Logout errato.");

    });
  }

      // Funzione per aggiungere l'header Authorization alle richieste protette
    /* Legge il token salvato nel localStorage.
        Se esiste, ritorna un oggetto con l'header Authorization (Bearer <token>), che verrà usato per autenticare le richieste protette.
        Se il token non esiste, ritorna un oggetto vuoto. */
        function getAuthHeaders() {
          const token = localStorage.getItem("authToken");
          return token ? { 'Authorization': 'Bearer ' + token } : {};
        }



//DOM LOG OUT
  document.getElementById('logoutButton').addEventListener('click', function() {
  logout();
 });




//Funzione per modificare il profilo
 function update () {
  const token = localStorage.getItem("authToken");

  if (!token) {
      console.error("Nessun token trovato nel localStorage");
      return;
  }

  let username = document.getElementById("username").value;
  let email = document.getElementById("email").value;
  let password = document.getElementById("password").value;
  let nome = document.getElementById("nome").value;
  let cognome = document.getElementById("cognome").value;
  let indirizzo = document.getElementById("indirizzo").value;
  let dataNascita = document.getElementById("dataNascita").value;
  let cartaCredito = document.getElementById('cartaCredito').value;

  const userData = {
      username: username,
      email: email,
      password: password,
      nome: nome,
      cognome: cognome,
      indirizzo: indirizzo,
      dataNascita: dataNascita,
      cartaCredito : cartaCredito
  };

  fetch('http://localhost:8080/utente', { 
      method: 'PUT',
      headers: {
        'Content-Type': 'application/json',
        'Authorization': 'Bearer ' + token
      },
      body: JSON.stringify(userData)

  })
  .then(response => {
      if (!response.ok) {
          throw new Error('Aggiornamento fallito');
      }
      return response.json();
  })
  .then(data => {
      console.log('Success:', data);
      alert('Utente aggiornato con successo');
  })
  .catch(error => {
      console.error('Errore durante aggiornamento:', error);
      window.alert("Aggiornamento errato.");

  });
}

    function getAuthHeaders() {
    const token = localStorage.getItem("authToken");
    return token ? { 'Authorization': 'Bearer ' + token } : {};
    }



//MODIFICA
  document.getElementById('updateButton').addEventListener('click', function() {
  update();
 });


// Funzione per eliminare l'account
function deleteAccount() {
    const token = localStorage.getItem("authToken");
    if (!token) {
        console.error("Nessun token trovato nel localStorage");
        return;
    }
  
    if (!confirm("Sei sicuro di voler eliminare il tuo account? Questa azione è irreversibile!")) {
        return;
    }
  
    fetch('http://localhost:8080/utente', { 
        method: 'DELETE',
        headers: {
            'Content-Type': 'application/json',
            'Authorization': 'Bearer ' + token
        }
    })
    .then(response => {
        if (response.ok) {
            console.log('Account eliminato con successo.');
            localStorage.removeItem("authToken");
            window.alert("Account eliminato con successo.");
            location.replace("http://127.0.0.1:3000/SerraLana/HTML/home.html");
        } else {
            return response.json(); 
        }
    })
    .then(data => {
        if (data && data.message) {
            window.alert("Errore: " + data.message);
        }
    })
    .catch(error => {
        console.error('Errore durante l\'eliminazione dell\'account:', error);
        window.alert("Errore durante l'eliminazione dell'account.");
    });
  }
  
  //ELIMINA
  document.getElementById('deleteAccountButton').addEventListener('click', function() {
    deleteAccount();
  });



  

 //Navbar
 window.addEventListener('scroll', function() {
  const header = document.getElementById('navbar');
  if (window.scrollY > 50) {
      header.classList.add('scrolled');
  } else {
      header.classList.remove('scrolled');
  }
});

