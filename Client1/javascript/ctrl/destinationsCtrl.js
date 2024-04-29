/*
 * Contrôleur de la vue "destinations.html"
 *
 * @Flamur Hoti
 * @version 1.0 / 28.04.2024
 */

// Importe la fonction getVoyages depuis destinationsCtrl.js
import { getVoyages, handleReserverVoyage } from '../services/serviceHttp.js';

function afficherVoyages(voyages) {
    const destinationsSection = document.querySelector('.destinations');

    // Pour chaque voyage, crée une carte de destination et l'ajoute à la section des destinations
    voyages.forEach(voyage => {
        const voyageElement = document.createElement('div');
        voyageElement.classList.add('destination-card');
        voyageElement.innerHTML = `
            <div class="destination-info">
                <input type="checkbox" class="checkbox-voyage">
                <h3>${voyage.destination}</h3>
                <p>Date de Départ: ${voyage.dateDepart}</p>
                <p>Date de Retour: ${voyage.dateRetour}</p>
                <p>Prix: ${voyage.prix}</p>
                <p>Description: ${voyage.description}</p>
                <img src="${voyage.imageUrl}" alt="${voyage.destination}">
            </div>
        `;
        destinationsSection.appendChild(voyageElement);
    });
}



// Au chargement de la page, récupère les voyages et affiche-les
document.addEventListener('DOMContentLoaded', () => {
    getVoyages()
        .then(voyages => {
            afficherVoyages(voyages);
        })
        .catch(error => {
            console.error('Erreur lors de la récupération et de l\'affichage des voyages:', error);
        });
});

// Attacher le gestionnaire d'événements au bouton "Réserver Voyage"
document.getElementById('#btnReserverVoyage').addEventListener('click', handleReserverVoyage);

