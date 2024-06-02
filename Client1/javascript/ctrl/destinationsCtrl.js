/*
 * Contrôleur de la vue "destinations.html"
 *
 * @Flamur Hoti
 * @version 1.0 / 28.04.2024
 */

$(document).ready(function () {


    $.getScript("javascript/services/serviceHttp.js", function () {
        console.log("destinations servicesHttp.js chargé !");
        chargerVoyages(chargerVoyagesSuccess, CallbackError);
    });


});

function chargerVoyagesSuccess(data, text, jqXHR) {
    // Sélection de la section où les cartes de destination seront ajoutées
    var destinationsSection = document.getElementById("destinationsSection");

    // Parcourir les données (supposons que data soit un tableau d'objets JSON)
    data.forEach(function (voyage) {
        // Création de la carte de voyage
        var card = document.createElement("div");
        card.classList.add("card");

        // Construction du contenu de la carte
        var cardContent = `
            <img src="/Client1/images/image1.jpg" alt="${voyage.nom}">
            <h2>${voyage.nom}</h2>
            <p>${voyage.pays}</p>
            <p>Date de départ: ${voyage.dateDepart}</p>
            <p>Date de retour: ${voyage.dateRetour}</p>
            <p>Prix: ${voyage.prix}</p>
            <p>${voyage.description}</p>
        `;

        // Ajout du contenu à la carte
        card.innerHTML = cardContent;

        // Ajout de la carte à la section
        destinationsSection.appendChild(card);
    });
}




/**
 * Méthode appelée en cas d'erreur lors de la lecture du webservice
 * @param {type} data
 * @param {type} text
 * @param {type} jqXHR
 */
function CallbackError(request, status, error) {
    alert("erreur : " + error + ", request: " + request + ", status: " + status);
    chargerVoyages(chargerVoyagesSuccess, CallbackError);
}
