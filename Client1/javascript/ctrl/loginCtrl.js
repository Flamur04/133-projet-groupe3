/*
 * Contrôleur de la vue "login.html"
 *
 * @Flamur Hoti
 * @version 1.0 / 28.04.2024
 */

// login.js

// Importe les fonctions depuis loginCtrl.js
import { login, addUser } from '../services/serviceHttp.js';

// Fonction pour gérer la soumission du formulaire de connexion
function handleLoginFormSubmit(event) {
    event.preventDefault(); // Empêche la soumission normale du formulaire

    const username = document.getElementById('username').value;
    const password = document.getElementById('password').value;

    // Appelle la fonction de connexion avec les identifiants
    login(username, password)
        .then(() => {
            // Connexion réussie, redirige vers une autre page
            window.location.href = '../../pages/destinations.html';
        })
        .catch(error => {
            // Affiche l'erreur de connexion dans l'élément span
            document.getElementById('loginError').textContent = error.message;
        });
}

// Fonction pour gérer l'ajout d'utilisateur
function handleAddUser() {
    const username = document.getElementById('newUsername').value;
    const password = document.getElementById('newPassword').value;

    // Appelle la fonction d'ajout d'utilisateur
    addUser(username, password)
        .then(response => {
            // Affiche un message de succès ou de confirmation à l'utilisateur
            console.log(response);
            // Connexion réussie, redirige vers une autre page
            window.location.href = '../../pages/login.html';
        })
        .catch(error => {
            console.error('Erreur lors de l\'ajout de l\'utilisateur:', error);
        });
}

// Ajoute des écouteurs d'événement pour les interactions utilisateur
document.addEventListener('DOMContentLoaded', () => {
    const loginForm = document.getElementById('login');
    loginForm.addEventListener('submit', handleLoginFormSubmit);

    const addUserButton = document.getElementById('signUp');
    addUserButton.addEventListener('click', handleAddUser);
});
