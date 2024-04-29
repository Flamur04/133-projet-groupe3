/*
 * Contrôleur de la vue "destinations.html"
 *
 * @Flamur Hoti
 * @version 1.0 / 28.04.2024
 */

// Importez la fonction getVoyages depuis votre fichier JavaScript où elle est définie
import { getVoyages, ajouterReservations } from '../services/serviceHttp.js';

$(document).ready(function () {
    var btnReserverVoyage = $("#btnReserverVoyage");

    afficherVoyages();
});

// Fonction pour afficher les voyages dans la page HTML
function afficherVoyages() {
    // Appel de la fonction pour récupérer les voyages depuis l'APIGateway
    getVoyages()
        .then(voyages => {
            // Sélectionnez la section où vous souhaitez afficher les voyages
            var sectionVoyages = document.querySelector('.container');

            // Parcourez chaque voyage et créez une carte pour chaque voyage
            voyages.forEach(voyage => {
                // Créez un élément de carte pour ce voyage
                var carteVoyage = document.createElement('div');
                carteVoyage.classList.add('card');

                // Ajoutez du contenu à la carte de voyage (par exemple, titre, image, description, etc.)
                // Ici, vous pouvez ajouter les détails du voyage au contenu de la carte
                carteVoyage.innerHTML = `
                        <img src="${voyage.image}" alt="Image du voyage">
                        <h2>${voyage.titre}</h2>
                        <p>Date de départ: ${voyage.date_depart}</p>
                        <p>Date de retour: ${voyage.date_retour}</p>
                        <p>${voyage.description}</p>
                        <p>Prix: ${voyage.prix}</p>
                        <!-- Ajoutez d'autres éléments de contenu en fonction de vos besoins -->
                `;

                carteVoyage.addEventListener('dblclick', function () {
                    poserQuestionReservation(voyage);
                });

                // Ajoutez la carte de voyage à la section des voyages
                sectionVoyages.appendChild(carteVoyage);
            });
        })
        .catch(error => {
            console.error('Erreur lors de la récupération des voyages:', error);
            // Gérez l'erreur, par exemple affichez un message d'erreur à l'utilisateur
        });
}


function poserQuestionReservation(voyage) {
    // Vérifier si l'utilisateur est connecté
    if (utilisateurEstConnecte()) {
        // L'utilisateur est connecté, poser la question de réservation
        var confirmation = confirm("Voulez-vous réserver ce voyage ?");
        if (confirmation) {
            // Si l'utilisateur clique sur "OK", appelez la méthode pour ajouter la réservation
            var userId = recupererIdUtilisateurConnecte(); // Récupérer l'identifiant de l'utilisateur connecté depuis la session
            ajouterReservations(voyage.fk_voyage, userId);
        } else {
            // Si l'utilisateur clique sur "Annuler", vous pouvez rediriger ou prendre d'autres mesures
            // Dans ce cas, je vais simplement actualiser la page
            window.location.href = "../../pages/destinations";
        }
    } else {
        // L'utilisateur n'est pas connecté, vous pouvez le rediriger vers la page de connexion ou afficher un message
        alert("Vous devez être connecté pour effectuer une réservation.");
        // Rediriger vers la page de connexion
        window.location.reload;
    }
}

// Cette fonction vérifie si l'utilisateur est connecté en vérifiant l'état de la session
function utilisateurEstConnecte() {
    // Vérifiez si une session est active
    if (sessionStorage.getItem('utilisateur')) {
        // Une session est active, l'utilisateur est connecté
        return true;
    } else {
        // Aucune session active, l'utilisateur n'est pas connecté
        return false;
    }
}

// Fonction pour récupérer l'identifiant de l'utilisateur connecté à partir de la session
function recupererIdUtilisateurConnecte() {
    // Récupérez l'identifiant de l'utilisateur depuis la session
    return sessionStorage.getItem('fk_user');
}

// Attacher le gestionnaire d'événements au bouton "Réserver Voyage"
/*document.getElementById('#btnReserverVoyage').addEventListener('click', handleReserverVoyage);*/
