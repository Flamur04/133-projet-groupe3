class IndexCtrl {
    constructor() {
        this.serviceHttp = new ServiceHttp();

        this.selectionType = document.getElementById('selectionType');
        this.selectionList = document.getElementById('selectionList');
        this.textField1 = document.getElementById('textField1');
        this.textField2 = document.getElementById('textField2');
        this.textField3 = document.getElementById('textField3');
        this.textField4 = document.getElementById('textField4');
        this.textField5 = document.getElementById('textField5');
        this.textField6 = document.getElementById('textField6');
        this.textField7 = document.getElementById('textField7');
        this.textField8 = document.getElementById('textField8');

        this.selectionType.value = 'pays';
        this.populateList(this.selectionType.value);

        this.selectionType.addEventListener('change', () => {
            const type = this.selectionType.value;
            this.populateList(type);
            this.handleVisibility(type);
        });

        this.selectionList.addEventListener('mouseover', (event) => {
            if (event.target.tagName === 'LI') {
                event.target.style.cursor = 'pointer';
            }
        });

        this.selectionList.addEventListener('click', (event) => {
            if (event.target.tagName === 'LI') {
                const selectedItem = event.target.textContent;
                this.textField1.value = selectedItem;
            }
        });
    }

    populateList(type) {
        this.selectionList.innerHTML = '';
        let items = [];
        if (type === 'utilisateurs') {
            items = this.utilisateurs;
        } else if (type === 'pays') {
            this.populateCountries();
        } else if (type === 'voyage') {
            items = this.voyage;
        }
        items.forEach(item => {
            const li = document.createElement('li');
            li.textContent = item;
            this.selectionList.appendChild(li);
        });
    }



    clearList() {
        this.selectionList.innerHTML = '';
    }

    populateCountries() {
        this.serviceHttp.getAllPays((pays) => {
            this.populateListItems(pays);
        }, (error) => {
            console.error("Erreur lors du chargement des pays: ", error);
        });
    }

    populateListItems(data) {
        data.forEach(item => {
            const li = document.createElement('li');
            li.textContent = item;
            this.selectionList.appendChild(li);
        });
    }   

    handleVisibility(type) {
        const textFields = [this.textField1, this.textField2, this.textField3, this.textField4, this.textField5, this.textField6, this.textField7, this.textField8];
        const labels = [this.textField1.previousElementSibling, this.textField2.previousElementSibling, this.textField3.previousElementSibling, this.textField4.previousElementSibling, this.textField5.previousElementSibling, this.textField6.previousElementSibling, this.textField7.previousElementSibling, this.textField8.previousElementSibling];
        if (type === 'utilisateurs' || type === 'pays') {
            for (let i = 1; i < textFields.length; i++) {
                textFields[i].style.display = 'none';
                labels[i].style.display = 'none';
            }
        } else {
            textFields.forEach((textField, index) => {
                textField.style.display = 'block';
                labels[index].style.display = 'block';
            });
        }
    }



}

const indexCtrl = new IndexCtrl();
