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

    $.getScript("../services/serviceHttp.js", function () {
        console.log("login servicesHttp.js chargé !");
    });

    // Lorsque le bouton est cliqué, cette fonction anonyme est appelée.
    butConnect.click(function (event) {
        var username = document.getElementById("username").value;
        var password = document.getElementById("password").value;
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
            alert("Erreur lors de l'enregistrement");
        }
    });
});


function _affichePageClient() {
    window.location.href = "https://hotif.emf-informatique.ch/133-projet-groupe3/Client1/login.html";
}

/**
 * Methode qui permet de s'enregistrer pour la premiere fois
 * @param {*} data 
 * @param {*} text 
 * @param {*} jqXHR 
 */
function signUpSuccess(data, text, jqXHR) {
    if (data.result == true) {
        alert("Sign In ok");
        document.getElementById("txtLogin").value = "";
        document.getElementById("password").value = "";
    } else {
        alert("Erreur lors de l'enregistrement");
    }
}

function connectSuccess(data, text, jqXHR) {
    if (data.result == true) {
        alert(data.username + ", vous êtes connecté.");
        // Stockez le nom d'utilisateur dans le stockage local
        localStorage.setItem('username', data.username);
        _affichePageClient();
    }
    else {
        alert("Erreur lors du login");
    }
}

function disconnectSuccess(data, text, jqXHR) {
    alert("Utilisateur déconnecté");
}

/**
 * Méthode appelée en cas d'erreur lors de la lecture du webservice
 * @param {type} data
 * @param {type} text
 * @param {type} jqXHR
 */
function CallbackError(request, status, error) {
    alert("erreur : " + error + ", request: " + request + ", status: " + status);

}


