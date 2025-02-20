//Funzione per effettuare log in
function login(username, password) {
    fetch('http://localhost:8080/api/login', { 
        method: 'POST',
        headers: {
          'Content-Type': 'application/json' //il corpo della richiesta sarà in formato JSON
        },
        body: JSON.stringify({ username, password }) //I dati (username e password) vengono convertiti in una stringa JSON con JSON.stringify()
    })
    .then(response => {
        if (!response.ok) {
            throw new Error('Login fallito');
        }
        return response.json();
    })
    .then(data => {
        console.log('Login effettuato:', data);
        window.alert("Login effettuato.");
        location.replace("http://127.0.0.1:3000/SerraLana/HTML/profilo2.html")

        if(data.token) {
          localStorage.setItem("authToken", data.token);
          // Salva il token nel localStorage, dove i dati sono disponibili anche alla chiusura del browser, in modo persistente e senza scadenza sul client
        }
    })
    .catch(error => {
        console.error('Errore nel login:', error);
        window.alert("Login errato.");

    });
  }


    //DOM LOG IN
      document.getElementById('loginForm').addEventListener('submit', function(event) {
        event.preventDefault();
        const username = document.getElementById('username').value;
        const password = document.getElementById('password').value;
        login(username, password);
      });





    // Funzione per aggiungere l'header Authorization alle richieste protette
    /* Legge il token salvato nel localStorage.
        Se esiste, ritorna un oggetto con l'header Authorization (Bearer <token>), che verrà usato per autenticare le richieste protette.
        Se il token non esiste, ritorna un oggetto vuoto. */
      function getAuthHeaders() {
        const token = localStorage.getItem("authToken");
        return token ? { 'Authorization': 'Bearer ' + token } : {};
      }


    // Funzione per formattare la data in formato YYYY-MM-DD
    function formatDate(date) {
      const d = new Date(date);
      const year = d.getFullYear();
      const month = ("0" + (d.getMonth() + 1)).slice(-2); // Aggiunge uno zero davanti ai numeri minori di 10
      const day = ("0" + d.getDate()).slice(-2);
      return `${year}-${month}-${day}`; // Restituisce la data nel formato corretto
    }

    //Funzione per registrarsi
    function addUser(newUser) {
      newUser.birthday = formatDate(newUser.birthday); // Assicurati che la data di nascita sia nel formato YYYY-MM-DD

        fetch('http://localhost:8080/utente', { 
            method: 'POST',
            headers: {
              'Content-Type': 'application/json',
              ...getAuthHeaders()
              
            },
            
            body: JSON.stringify(newUser)
            
        })
        .then(response => {
        
          if (!response.ok) {
            return response.json().then(err => {
                console.error('Errore API:', err); // Aggiungi questo log per esaminare il contenuto della risposta
                throw new Error(`Errore: ${err.message || 'Errore durante l\'aggiunta dell\'utente'}`);
            });
        }
            return response.json();
        })
        .then(data => {
            console.log('Registrazione effettuata:', data);
            window.alert("Registrazione effettuata.");

        })
        .catch(error => {
            console.error('Errore nell\'aggiunta dell\'utente:', error);
            window.alert("Registrazione non andata a buon fine.");
        });
      }

    //DOM REGISTRAZIONE
      document.getElementById('addUserForm').addEventListener('submit', function(event) {
        event.preventDefault();
        const newUser = {
          nome: document.getElementById('newUserName').value,
          cognome : document.getElementById('newUserSurname').value,
          username : document.getElementById('newUserName2').value,
          dataNascita : document.getElementById('newUserBirthday').value,
          email: document.getElementById('newUserEmail').value,
          password: document.getElementById('newUserPassword').value
        };

        console.log("Dati raccolti:", newUser); // Aggiungi un log per verificare i dati prima dell'invio

        if (!newUser.cognome) {
          alert("Compila il cognome.");
          return;  // Interrompe l'invio della richiesta
      }

        if (!newUser.nome) {
          alert("Compila  il nome.");
          return;  // Interrompe l'invio della richiesta
      }

        if (!newUser.dataNascita) {
          alert("Compila la data.");
          return;  // Interrompe l'invio della richiesta
      }
      
        addUser(newUser);
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

