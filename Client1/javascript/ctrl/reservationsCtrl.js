/*
 * Contrôleur de la vue "destinations.html"
 *
 * @Flamur Hoti
 * @version 1.0 / 28.04.2024
 */

$(document).ready(function () {


    $.getScript("javascript/services/serviceHttp.js", function () {
        console.log("reservations servicesHttp.js chargé !");

    });


    // Récupérer les valeurs de sessionStorage
    var username = sessionStorage.getItem('username');
    var idUser = sessionStorage.getItem('idUser');

    var butLogout = $("#logout");

    butLogout.click(function (event) {
        logout(disconnectSuccess, CallbackError);
    });

});



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
    }
}
