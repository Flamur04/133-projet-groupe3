/*
 * Couche de services HTTP (worker).
 *
 * @author FLamur Hoti
 * @version 1.0 / 28.04.2024
 * 
 */

var BASE_URL = "http://localhost:8080/";


/**
 * Fonction permettant de se connecter.
 * @param {type} Fonction de callback lors du retour avec succès de l'appel.
 * @param {type} Fonction de callback en cas d'erreur.
 */
function login(username, password, successCallback, errorCallback) {
    

    $.ajax({
        type: "POST",
        dataType: "json",
        data: {
            username: username,
            password: password,
        },
        url: BASE_URL + "login",
        xhrFields: {
            withCredentials: true
        },
        success: successCallback,
        error: errorCallback,
    });
}

/**
 * Fonction permettant de se déconnecter.
 * @param {type} Fonction de callback lors du retour avec succès de l'appel.
 * @param {type} Fonction de callback en cas d'erreur.
 */
function logout(successCallback, errorCallback) {
    $.ajax({
        type: "POST",
        dataType: "json",
        url: BASE_URL + "logout",
        xhrFields: {
            withCredentials: true
        },
        success: successCallback,
        error: errorCallback,
    });
}

/**
 * Fonction permettant de récupérer les voyages.
 * @param {type} Fonction de callback lors du retour avec succès de l'appel.
 * @param {type} Fonction de callback en cas d'erreur.
 */
function chargerVoyages(successCallback, errorCallback) {
    $.ajax({
        type: "GET",
        dataType: "json",
        url: BASE_URL + "getAllVoyages",
        xhrFields: {
            withCredentials: true
        },
        success: successCallback,
        error: errorCallback,
    });
}
/**
 * Fonction permettant d'ajouter un utilisateur.
 * @param {string} username Le nom d'utilisateur à ajouter.
 * @param {string} password Le mot de passe associé à l'utilisateur.
 * @param {function} successCallback Fonction de callback lors du retour avec succès de l'appel.
 * @param {function} errorCallback Fonction de callback en cas d'erreur.
 */
function addUser(username, password, successCallback, errorCallback) {
    $.ajax({
        type: "POST",
        dataType: "json",
        data: {
            username: username,
            password: password
        },
        url: BASE_URL + "addUser",
        xhrFields: {
            withCredentials: true
        },
        success: successCallback,
        error: errorCallback,
    });
}


/**
 * Fonction permettant d'ajouter une réservation.
 * @param {string} idUser L'identifiant de l'utilisateur effectuant la réservation.
 * @param {string} idVoyage L'identifiant du voyage à réserver.
 * @param {function} successCallback Fonction de callback lors du retour avec succès de l'appel.
 * @param {function} errorCallback Fonction de callback en cas d'erreur.
 */
function addReservation(idUser, idVoyage, successCallback, errorCallback) {
    $.ajax({
        type: "POST",
        dataType: "json",
        data: {
            idUser: idUser,
            idVoyage: idVoyage
        },
        url: BASE_URL + "addReservation",
        xhrFields: {
            withCredentials: true
        },
        success: successCallback,
        error: errorCallback,
    });
}

/**
 * Fonction permettant de supprimer une réservation.
 * @param {string} reservationId L'identifiant de la réservation à supprimer.
 * @param {function} successCallback Fonction de callback lors du retour avec succès de l'appel.
 * @param {function} errorCallback Fonction de callback en cas d'erreur.
 */
function deleteReservation(reservationId, successCallback, errorCallback) {
    $.ajax({
        type: "DELETE",
        dataType: "json",
        url: BASE_URL + "reservations/" + reservationId,
        xhrFields: {
            withCredentials: true
        },
        success: successCallback,
        error: errorCallback,
    });
}
