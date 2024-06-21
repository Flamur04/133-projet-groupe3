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
        // Sélectionner la case à cocher cochée
        var checkboxChecked = document.querySelector('.voyage-checkbox:checked');

        if (checkboxChecked) {
            // Récupérer la valeur de card-pkVoyage pour la carte parente de la case cochée
            var idVoyage = checkboxChecked.closest('.card').querySelector('.card-pkVoyage').textContent;

            // Vous avez maintenant l'ID du voyage (pkVoyage) que vous pouvez utiliser
            console.log("ID du voyage sélectionné :", idVoyage);

            if (idUser) {
                addReservation(idUser, idVoyage, addReservationSuccess, CallbackError)
            } else {
                alert("Utilisateur non connecté. Veuillez vous connecter.");
                window.location.href = 'http://localhost:5500/Client1/login.html'; // Rediriger vers la page de connexion
            }

        } else {
            // Aucune case à cocher cochée
            alert("Veuillez sélectionner un voyage à réserver.");
        }
    });


});

function addReservationSuccess(data, textStatus, jqXHR) {
    if (jqXHR.status === 200) {
        alert("Voyage réservé !");
        window.location.href = "http://localhost:5500/Client1/destinations.html";
    } else {
        alert("Erreur lors de la réservation : " + textStatus); // Afficher le statut d'erreur
    }
}



function chargerVoyagesSuccess(data, text, jqXHR) {
    var destinationsSection = document.getElementById("destinationsSection");

    // Vider le contenu de la section avant de charger les nouvelles cartes
    destinationsSection.innerHTML = '';

    // Fonction pour gérer la sélection des cases à cocher
    function handleCheckboxChange(event) {
        var checkboxes = document.querySelectorAll('.voyage-checkbox');
        checkboxes.forEach(function (checkbox) {
            if (checkbox !== event.target) {
                checkbox.checked = false;
            }
        });
    }

    data.forEach(function (voyage) {
        var card = document.createElement("div");
        card.classList.add("card");

        var cardContent = `
            <img src="/Client1/images/image1.jpg" alt="${voyage.nom}" class="card-image">
            <div class="card-content">
                <h2 class="card-title">${voyage.nom}</h2>
                <p class="card-pkVoyage">${voyage.pkVoyage}</p>
                <p class="card-description">${voyage.description}</p>
                <p class="card-description">Prix: ${voyage.prix}</p>
                <p class="card-description">Date de départ: ${voyage.dateDepart}</p>
                <p class="card-description">Date de retour: ${voyage.dateRetour}</p>
                <label>
                    <input type="checkbox" class="voyage-checkbox">
                    Réserver ce voyage
                </label>
            </div>
        `;

        card.innerHTML = cardContent;
        destinationsSection.appendChild(card);
    });

    var checkboxes = document.querySelectorAll('.voyage-checkbox');
    checkboxes.forEach(function (checkbox) {
        checkbox.addEventListener('change', handleCheckboxChange);
    });
}


function disconnectSuccess(data, text, jqXHR) {

    if (data === true) {
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
    if (request.status === 401) {
        alert("Utilisateur non connecté. Veuillez vous connecter.");
        window.location.href = 'http://localhost:5500/Client1/login.html'; // Rediriger vers la page de connexion
        chargerVoyages(chargerVoyagesSuccess, CallbackError);
    }
}
