/*
 * Contrôleur de la vue "login.html"
 *
 * @Flamur Hoti
 * @version 1.0 / 28.04.2024
 */

/*
 * Contrôleur de la vue "index.html"
 *
 * @Flamur Hoti
 * @version 1.0 / 23.02.2024
 */

/**
 * Méthode "start" appelée après le chargement complet de la page
 */

$(document).ready(function () {
    var butConnect = $("#login");
    var butSignUp = $("#signUp");


    $.getScript("javascript/services/serviceHttp.js", function () {
        console.log("login servicesHttp.js chargé !");
    });

    // Lorsque le bouton est cliqué, cette fonction anonyme est appelée.
    butConnect.click(function (event) {
        var username = document.getElementById("username").value;
        var password = document.getElementById("password").value;
        console.log(username);

        if (username != "" && password != "") {
            login(username, password, connectSuccess, CallbackError);
        } else {
            alert("Erreur lors du login");
        }
    });

    butSignUp.click(function (event) {
        var username = document.getElementById("username").value;
        var password = document.getElementById("password").value;

        if (username != "" && password != "") {
            addUser(username, password, signUpSuccess, CallbackError);
        } else {
            alert("Veuillez remplire les champs.");
        }
    });

});


function _affichePageClient() {
    window.location.href = "http://localhost:5500/Client1/destinations.html";
}

/**
 * Methode qui permet de s'enregistrer pour la premiere fois
 * @param {*} data 
 * @param {*} text 
 * @param {*} jqXHR 
 */
function signUpSuccess(data, text, jqXHR) {
    console.log(data);
    if (data === true) {
        alert("Sign In ok");
        window.location.reload();
    } else {
        alert("Erreur lors de l'enregistrement");
    }
}

function connectSuccess(data, text, jqXHR) {
    console.log(data);
    if (data.id != null) {
        alert(data.username + ", vous êtes connecté.");
        // Stockez le nom d'utilisateur et l'ID dans le stockage de session
        sessionStorage.setItem('username', data.username);
        sessionStorage.setItem('idUser', data.id);
        _affichePageClient();
    } else if (jqXHR.status === 401) {
        alert("Identifiants invalides. Veuillez réessayer.");
        return;
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
        alert("Erreur durant le login : 401 : Identifiants invalides.");
    } else {
        alert("Erreur durant le login : 401 : Identifiants invalides.");
    }
}


