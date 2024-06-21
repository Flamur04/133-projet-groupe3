$(document).ready(function () {
    var butConnect = $("#butConnect");
    

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



function _affichePageClient() {
    window.location.href = "http://localhost:5500/Client2/index.html";
}


function connectSuccess(data, text, jqXHR) {
    console.log(data);
    if (data.id != null) {
        alert(data.username + ", vous êtes connecté.");
        // Stockez le nom d'utilisateur dans le stockage local
        localStorage.setItem('username', data.username);
        _affichePageClient();
    } else {
        alert("Erreur lors du login");
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
}

})

