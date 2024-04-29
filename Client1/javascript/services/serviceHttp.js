/*
 * Couche de services HTTP (worker).
 *
 * @author FLamur Hoti
 * @version 1.0 / 28.04.2024
 * 
 */

// Fonction pour récupérer les voyages depuis l'ApiGateway
function getVoyages() {
    return fetch('/getAllVoyages')
        .then(response => {
            if (!response.ok) {
                throw new Error('Erreur lors de la récupération des voyages');
            }
            return response.json();
        })
        .catch(error => {
            console.error('Erreur lors de la récupération des voyages:', error);
            throw error; // Propage l'erreur pour que le code appelant puisse la gérer
        });
}

// Exporte la fonction getVoyages pour qu'elle soit accessible depuis d'autres fichiers JavaScript
export { getVoyages };


// loginCtrl.js

// Fonction pour effectuer une requête de connexion à l'API Gateway
function login(username, password) {
    return fetch('/login', {
        method: 'POST',
        headers: {
            'Content-Type': 'application/x-www-form-urlencoded'
        },
        body: `username=${username}&password=${password}`
    })
        .then(response => {
            if (!response.ok) {
                throw new Error('Identifiants incorrects');
            }
            return response.text();
        });
}

// Fonction pour effectuer une requête de déconnexion à l'API Gateway
function logout() {
    return fetch('/logout', {
        method: 'POST'
    })
        .then(response => {
            if (!response.ok) {
                throw new Error('Erreur lors de la déconnexion');
            }
            return response.text();
        });
}

// Fonction pour effectuer une requête d'ajout d'utilisateur à l'API Gateway
function addUser(username, password) {
    return fetch('/addUser', {
        method: 'POST',
        headers: {
            'Content-Type': 'application/x-www-form-urlencoded'
        },
        body: `username=${username}&password=${password}`
    })
        .then(response => {
            if (!response.ok) {
                throw new Error('Erreur lors de l\'ajout de l\'utilisateur');
            }
            return response.text();
        });
}

export { login, logout, addUser };

//reservations.html

// Fonction pour gérer le clic sur le bouton "Réserver Voyage"
function handleReserverVoyage() {
    // Vérifier si au moins un voyage est sélectionné
    const voyagesSelectionnes = document.querySelectorAll('.destination-card input[type="checkbox"]:checked');
    if (voyagesSelectionnes.length === 0) {
        alert("Veuillez sélectionner au moins un voyage.");
        return;
    }

    // Vérifier si l'utilisateur est connecté
    if (sessionStorage.getItem('username') == null) {
        alert("Vous devez être connecté pour effectuer une réservation.");
        return;
    }

    // Récupérer l'identifiant de l'utilisateur à partir de la session
    const utilisateurId = sessionStorage.getItem('utilisateurId');
    if (!utilisateurId) {
        alert("Impossible de récupérer l'identifiant de l'utilisateur.");
        return;
    }

    // Récupérer les identifiants des voyages sélectionnés
    const voyagesIds = Array.from(voyagesSelectionnes).map(voyage => voyage.dataset.id);

    // Envoyer une requête à l'API Gateway pour ajouter les réservations
    ajouterReservations(voyagesCoches, utilisateurId);
}

// Fonction pour gérer l'ajout d'une réservation
function ajouterReservations(voyagesIds, utilisateurId) {
    // Envoyer la requête pour ajouter la réservation pour chaque voyage sélectionné
    voyagesIds.forEach(voyageId => {
        fetch('/addReservation', {
            method: 'POST',
            headers: {
                'Content-Type': 'application/json'
            },
            body: JSON.stringify({
                fk_voyage: voyageId,
                fk_user: utilisateurId
            })
        })
            .then(response => {
                if (!response.ok) {
                    throw new Error('Erreur lors de l\'ajout de la réservation.');
                }
                alert('Réservation ajoutée avec succès!');
                // Actualiser la page ou effectuer d'autres actions si nécessaire
            })
            .catch(error => {
                console.error('Erreur:', error);
                alert('Une erreur est survenue lors de l\'ajout de la réservation.');
            });
    });
}

// Exporter les fonctions nécessaires
export { ajouterReservations };

