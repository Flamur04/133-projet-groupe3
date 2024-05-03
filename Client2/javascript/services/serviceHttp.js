
var BASE_URL = "http://localhost:8080/";



/**
 * Fonction permettant de mettre à jour un voyage.
 * @param {type} int id du voyage.
 * @param {type} String name du voyage.
 * @param {type} String description du voyage.
 * @param {type} int prix du voyage.
 * @param {type} String fkPays du voyage.
 * @param {type} int version du voyage.
 * @param {type} String dateDepart du voyage.
 * @param {type} String dateRetour du voyage.
 * @param {type} Fonction de callback lors du retour avec succès de l'appel.
 * @param {type} Fonction de callback en cas d'erreur.
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
      url: BASE_URL + "updateVoyage/" + id,
      xhrFields: {
        withCredentials: true
      },
      success: successCallback,
      error: errorCallback,
    });


    
    /**
 * Fonction permettant d'obtenir tous les voyages.
 * @param {type} Fonction de callback lors du retour avec succès de l'appel.
 * @param {type} Fonction de callback en cas d'erreur.
 */
function getAllVoyages(successCallback, errorCallback) {
    $.ajax({
      type: "GET",
      url: BASE_URL + "getAllVoyages",
      xhrFields: {
        withCredentials: true
      },
      success: successCallback,
      error: errorCallback,
    });
}






  }
  