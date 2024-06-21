
var BASE_URL = "http://localhost:8080/";


/**
 * Fonction permettant de mettre à jour un voyage.
 * @param {int} id - id du voyage.
 * @param {String} name - nom du voyage.
 * @param {String} description - description du voyage.
 * @param {int} prix - prix du voyage.
 * @param {String} fkPays - pays du voyage.
 * @param {int} version - version du voyage.
 * @param {String} dateDepart - date de départ du voyage.
 * @param {String} dateRetour - date de retour du voyage.
 * @param {Function} successCallback - callback en cas de succès.
 * @param {Function} errorCallback - callback en cas d'erreur.
 */
function updateVoyage(id, name, description, prix, fkPays, version, dateDepart, dateRetour, successCallback, errorCallback) {
    $.ajax({
        type: "PUT",
        dataType: "json",
        data: {
            id: id,
            name: name,
            description: description,
            prix: prix,
            fkPays: fkPays,
            version: version,
            dateDepart: dateDepart,
            dateRetour: dateRetour
        },
        url: this.BASE_URL + "updateVoyage/" + id,
        xhrFields: {
            withCredentials: true
        },
        success: successCallback,
        error: errorCallback,
    });
}

/**
 * Fonction permettant d'obtenir tous les voyages.
 * @param {Function} successCallback - callback en cas de succès.
 * @param {Function} errorCallback - callback en cas d'erreur.
 */
function getAllVoyages(successCallback, errorCallback) {
    $.ajax({
        type: "GET",
        url: this.BASE_URL + "getAllVoyages",
        xhrFields: {
            withCredentials: true
        },
        success: successCallback,
        error: errorCallback,
    });
}

/**
 * Fonction permettant d'obtenir tous les pays.
 * @param {Function} successCallback - callback en cas de succès.
 * @param {Function} errorCallback - callback en cas d'erreur.
 */
function getAllPays(successCallback, errorCallback) {
    $.ajax({
        type: "GET",
        url: this.BASE_URL + "getAllPays",
        xhrFields: {
            withCredentials: true
        },
        success: successCallback,
        error: errorCallback,
    });
}


/**
 * Fonction permettant de supprimer un voyage.
 * @param {int} id - ID du voyage à supprimer.
 * @param {Function} successCallback - Callback en cas de succès.
 * @param {Function} errorCallback - Callback en cas d'erreur.
 */
function deleteVoyage(id, successCallback, errorCallback) {
    $.ajax({
        type: "DELETE",
        url: BASE_URL + "deleteVoyage/" + id,
        xhrFields: {
            withCredentials: true
        },
        success: successCallback,
        error: errorCallback,
    });
}


/**
 * Fonction permettant d'obtenir tous les pays.
 * @param {Function} successCallback - callback en cas de succès.
 * @param {Function} errorCallback - callback en cas d'erreur.
 */
function getUsers(successCallback, errorCallback) {
    $.ajax({
        type: "GET",
        url: this.BASE_URL + "getUsers",
        xhrFields: {
            withCredentials: true
        },
        success: successCallback,
        error: errorCallback,
    });
}


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
