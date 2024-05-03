class IndexCtrl {
    constructor() {
        this.selectionType = document.getElementById('selectionType');
        this.selectionList = document.getElementById('selectionList');
        this.textField1 = document.getElementById('textField1');
        this.textField2 = document.getElementById('textField2');
        // Ajouter les autres champs texte
        this.textField3 = document.getElementById('textField3');
        this.textField4 = document.getElementById('textField4');
        this.textField5 = document.getElementById('textField5');
        this.textField6 = document.getElementById('textField6');
        this.textField7 = document.getElementById('textField7');
        this.textField8 = document.getElementById('textField8');

        // Données pour chaque type
        this.utilisateurs = ['Utilisateur 1', 'Utilisateur 2', 'Utilisateur 3'];
        this.pays = ['Pays 1', 'Pays 2', 'Pays 3'];
        this.voyage = ['Voyage 1', 'Voyage 2', 'Voyage 3']


        // Initialisation de la liste avec le type sélectionné par défaut
        this.selectionType.value = 'voyage';
        this.populateList(this.selectionType.value);
        


        this.selectionType.addEventListener('change', () => {
            const type = this.selectionType.value;
            this.populateList(type); // Met à jour la liste en fonction du type sélectionné
            this.handleVisibility(type); // Gère la visibilité des champs texte et des labels en fonction du type sélectionné
        });
        

        // Gestionnaire d'événements pour rendre les éléments de la liste interactifs
        this.selectionList.addEventListener('mouseover', (event) => {
        if (event.target.tagName === 'LI') {
            event.target.style.cursor = 'pointer'; // Change le curseur lorsque la souris passe sur un élément de la liste
            }
        });
        
        this.selectionList.addEventListener('click', (event) => {
            if (event.target.tagName === 'LI') {
                const selectedItem = event.target.textContent; // Récupère le texte de l'élément cliqué
                this.textField1.value = selectedItem; 
            }
        });
    }

    handleVisibility(type) {
        // Liste des champs texte et de leurs labels
        const textFields = [this.textField1, this.textField2, this.textField3, this.textField4, this.textField5, this.textField6, this.textField7, this.textField8];
        const labels = [this.textField1.previousElementSibling, this.textField2.previousElementSibling, this.textField3.previousElementSibling, this.textField4.previousElementSibling, this.textField5.previousElementSibling, this.textField6.previousElementSibling, this.textField7.previousElementSibling, this.textField8.previousElementSibling];
    
        // Si le type est 'utilisateurs' ou 'pays', cacher tous les champs texte et labels sauf le premier
        if (type === 'utilisateurs' || type === 'pays') {
            for (let i = 1; i < textFields.length; i++) {
                textFields[i].style.display = 'none';
                labels[i].style.display = 'none';
            }
        } else {
            // Si le type est 'voyage', afficher tous les champs texte et labels
            textFields.forEach((textField, index) => {
                textField.style.display = 'block';
                labels[index].style.display = 'block';
            });
        }
    }
    



    // Fonction pour vider la liste des éléments
    clearList() {
        this.selectionList.innerHTML = '';
    }

    // Fonction pour ajouter des éléments à la liste en fonction du type sélectionné
    populateList(type) {
        this.clearList();
    
        if (type === 'utilisateurs') {
            this.populateUsers();
        } else if (type === 'pays') {
            this.populateCountries();
        } else if (type === 'voyage') {
            this.populateTrips();
        }
    
        if (this.selectionList.firstChild) {
            this.selectionList.firstChild.click();
        }
    
    }
    

    // Fonction pour ajouter des utilisateurs à la liste
    populateUsers() {
        this.populateListItems(this.utilisateurs);
    }

    // Fonction pour ajouter des pays à la liste
    populateCountries() {
        this.populateListItems(this.pays);
    }

    // Fonction pour ajouter des voyages à la liste
    populateTrips() {
        this.populateListItems(this.voyage);
    }

    // Fonction pour ajouter des éléments à la liste
    populateListItems(data) {

        data.forEach(item => {
            const li = document.createElement('li');
            li.textContent = item;
            this.selectionList.appendChild(li);
        });
    }
}

// Initialiser le gestionnaire de listea
const indexCtrl = new IndexCtrl();
