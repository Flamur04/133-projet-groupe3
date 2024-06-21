$(document).ready(function () {

    $.getScript("javascript/services/serviceHttp.js", function () {
        console.log("index servicesHttp.js chargé !");
        // L'utilisateur a sélectionné "Pays", appelez getAllPays
        getAllPays(paysSuccess, CallbackError);
        getAllVoyages(voyageSuccess, CallbackError);
    });

    var selectionType = $('#selectionType');

    var textField1 = $('#textField1');
    var textField2 = $('#textField2');
    var textField3 = $('#textField3');
    var textField4 = $('#textField4');
    var textField5 = $('#textField5');
    var textField6 = $('#textField6');
    var textField7 = $('#textField7');
    var textField8 = $('#textField8');


    // Ecoutez les changements de sélection
    $('#selectionType').on('change', function () {
        var selectedValue = $(this).val();
        if (selectedValue === 'pays') {
            
            getAllPays(paysSuccess, CallbackError);
        } else if (selectedValue === 'voyage') {
            
            getAllVoyages(voyageSuccess, CallbackError);
        } else if (selectedValue === 'utilisateurs') {

            // getAllPays(paysSuccess, errorCallback);
        }
    });

    // Ecoutez les clics sur les éléments de la liste
    $('#selectionList').on('click', 'li', function () {
    var selectedPays = $(this).text();
    console.log('Pays sélectionné :', selectedPays);
});


});


function paysSuccess(data, text, jqXHR) {
    var paysArray = JSON.parse(data);

    var selectionList = $('#selectionList');
    selectionList.empty();

    paysArray.forEach(function (pays) {
        var li = $('<li>').text(pays.nom);
        selectionList.append(li);
    });
}

function voyageSuccess(data, text, jqXHR) {
    var voyageArray = JSON.parse(data);

    var selectionList = $('#selectionList');
    selectionList.empty();

    voyageArray.forEach(function (voyage) {
        var li = $('<li>').text(voyage.nom);
        selectionList.append(li);
    });
}


function CallbackError(request, status, error) {
    alert("erreur : " + error + ", request: " + request + ", status: " + status);
    getAllPays(paysSuccess, CallbackError);
}
