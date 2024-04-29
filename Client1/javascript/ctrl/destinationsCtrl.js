/*
 * Contrôleur de la vue "destinations.html"
 *
 * @Flamur Hoti
 * @version 1.0 / 28.04.2024
 */

// Importez la fonction getVoyages depuis votre fichier JavaScript où elle est définie
import { getVoyages } from '../services/serviceHttp.js';

$(document).ready(function () {
    var btnReserverVoyage = $("#btnReserverVoyage");

    // Lorsque le bouton est cliqué, cette fonction anonyme est appelée.
    btnReserverVoyage.click(function (event) {
        // Affichez une alerte lorsque le bouton est cliqué
        alert('Vous avez cliqué sur Réserver Voyage');
    });

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
                    <h2>${voyage.titre}</h2>
                    <p>${voyage.description}</p>
                    <!-- Ajoutez d'autres éléments de contenu en fonction de vos besoins -->

                `;

                // Ajoutez la carte de voyage à la section des voyages
                sectionVoyages.appendChild(carteVoyage);
            });
        })
        .catch(error => {
            console.error('Erreur lors de la récupération des voyages:', error);
            // Gérez l'erreur, par exemple affichez un message d'erreur à l'utilisateur
        });
}



// Attacher le gestionnaire d'événements au bouton "Réserver Voyage"
/*document.getElementById('#btnReserverVoyage').addEventListener('click', handleReserverVoyage);*/
