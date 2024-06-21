
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

