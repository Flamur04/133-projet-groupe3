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


    // Récupérer les valeurs de sessionStorage
    var username = sessionStorage.getItem('username');
    var idUser = sessionStorage.getItem('idUser');

    var butLogout = $("#logout");
    var btnReserverVoyage = $("#btnReserverVoyage");

    butLogout.click(function (event) {
        logout(disconnectSuccess, CallbackError);
    });

    btnReserverVoyage.click(function (event) {
        //Integer userId = session.getAttribute("fk_user");

        addReservation(idUser, idVoyage, successCallback, errorCallback)
    });

});

function chargerVoyagesSuccess(data, text, jqXHR) {
    // Sélection de la section où les cartes de destination seront ajoutées
    var destinationsSection = document.getElementById("destinationsSection");

    // Fonction pour gérer la sélection des cases à cocher
    function handleCheckboxChange(event) {
        // Désélectionner toutes les autres cases à cocher
        var checkboxes = document.querySelectorAll('.voyage-checkbox');
        checkboxes.forEach(function (checkbox) {
            if (checkbox !== event.target) {
                checkbox.checked = false;
            }
        });
    }

    // Parcourir les données (supposons que data soit un tableau d'objets JSON)
    data.forEach(function (voyage) {
        // Création de la carte de voyage
        var card = document.createElement("div");
        card.classList.add("card");

        // Construction du contenu de la carte
        var cardContent = `
            <img src="/Client1/images/image1.jpg" alt="${voyage.nom}" class="card-image">
            <div class="card-content">
                <h2 class="card-title">${voyage.nom}</h2>
                <p class="card-description">${voyage.pays}</p>
                <p class="card-description">Date de départ: ${voyage.dateDepart}</p>
                <p class="card-description">Date de retour: ${voyage.dateRetour}</p>
                <p class="card-description">Prix: ${voyage.prix}</p>
                <p class="card-description">${voyage.description}</p>
                <label>
                    <input type="checkbox" class="voyage-checkbox" data-voyage-id="${voyage.id}">
                    Réserver ce voyage
                </label>
            </div>
        `;

        // Ajout du contenu à la carte
        card.innerHTML = cardContent;

        // Ajout de la carte à la section
        destinationsSection.appendChild(card);
    });

    // Ajout des gestionnaires d'événements pour les cases à cocher
    var checkboxes = document.querySelectorAll('.voyage-checkbox');
    checkboxes.forEach(function (checkbox) {
        checkbox.addEventListener('change', handleCheckboxChange);
    });
}

function disconnectSuccess(data, text, jqXHR) {

    if (data === true) {
        //alert("Utilisateur déconnecté");
        //window.location.reload();
        window.location.href = "http://localhost:5500/Client1/login.html";
    } else {
        alert("Aucun Utilisateur connecté");
    }
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
