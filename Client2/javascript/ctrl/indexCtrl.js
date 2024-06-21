$(document).ready(function () {
    $.getScript("javascript/services/serviceHttp.js", function () {
        console.log("index servicesHttp.js chargé !");
        getAllPays(paysSuccess, CallbackError);
        getUsers(userSucess, CallbackError);
        getAllVoyages(voyageSuccess, CallbackError);

        // Attachez le listener ici
        $('#logoutBtn').on('click', function (event) {
            logout(disconnectSuccess, CallbackError);
        });
    });

    var selectionType = $('#selectionType');

    // Écoutez les changements de sélection
    $('#selectionType').on('change', function () {
        var selectedValue = $(this).val();
        if (selectedValue === 'pays') {
            getAllPays(paysSuccess, CallbackError);
        } else if (selectedValue === 'voyage') {
            getAllVoyages(voyageSuccess, CallbackError);
        } else if (selectedValue === 'utilisateurs') {
            getUsers(userSucess, CallbackError);
        }
    });

    // Écoutez les clics sur les éléments de la liste pour sélectionner un élément
    $('#selectionList').on('click', 'li', function () {
        $('#selectionList li').removeClass('selected');
        $(this).addClass('selected');
    });

    // Écoutez les clics sur le bouton de suppression
    $('#deleteButton').on('click', function () {
        var selectedElement = $('#selectionList li.selected');
        if (selectedElement.length) {
            var id = selectedElement.data('id');
            deleteVoyage(id, deleteSuccess, CallbackError);
        } else {
            alert("Veuillez sélectionner un élément à supprimer.");
        }
    });
});

function paysSuccess(data, text, jqXHR) {
    var paysArray = JSON.parse(data);
    var selectionList = $('#selectionList');
    selectionList.empty();

    paysArray.forEach(function (pays) {
        var li = $('<li>').text(pays.nom).data('id', pays.id);
        selectionList.append(li);
    });
}

function voyageSuccess(data, text, jqXHR) {
    var voyageArray = JSON.parse(data);
    var selectionList = $('#selectionList');
    selectionList.empty();

    voyageArray.forEach(function (voyage) {
        var li = $('<li>').text(voyage.nom).data('id', voyage.pkVoyage);
        selectionList.append(li);
    });
}

function userSucess(data, text, jqXHR) {
    var userArray = JSON.parse(data);
    var selectionList = $('#selectionList');
    selectionList.empty();

    userArray.forEach(function (user) {
        var li = $('<li>').text(user.username).data('id', userArray.id);
        selectionList.append(li);
    });
}

function deleteSuccess(response) {


    alert("Voyage supprimé avec succès");
    // Recharger la liste des voyages après suppression
    getAllVoyages(voyageSuccess, CallbackError);
}

function disconnectSuccess(data, text, jqXHR) {
    if (data === true) {
        console.log("déconnecté");
        window.location.href = "http://localhost:5500/Client2/login.html";
    } else {
        alert("Aucun Utilisateur connecté");
    }
}

function CallbackError(request, status, error) {
    alert("Erreur : " + error + ", request: " + request + ", status: " + status);
}
