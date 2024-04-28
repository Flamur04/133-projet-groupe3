package com.example.apigateway.apiController;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.example.apigateway.services.ServiceApiRest1;
import com.example.apigateway.services.ServiceApiRest2;

import jakarta.servlet.http.HttpSession;

@RestController
public class Controller {

    private final ServiceApiRest1 serviceApiRest1;
    private final ServiceApiRest2 serviceApiRest2;

    @Autowired
    public Controller(ServiceApiRest1 serviceApiRest1, ServiceApiRest2 serviceApiRest2) {
        this.serviceApiRest1 = serviceApiRest1;
        this.serviceApiRest2 = serviceApiRest2;
    }

    @GetMapping("/getUsers")
    public ResponseEntity<String> getUsers(HttpSession session) {
        if (session.getAttribute("username") != null) {
            // L'utilisateur est connecté, procéder à la récupération des utilisateurs
            return serviceApiRest1.getUsers(); // Retourne directement la réponse de serviceApiRest1.getUsers()
        } else {
            // L'utilisateur n'est pas connecté, renvoyer une erreur non autorisée
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body("User not logged in");
        }
    }

    @GetMapping("/getAllVoyages")
    public ResponseEntity<String> getAllVoyages(HttpSession session) {
        if (session.getAttribute("username") != null) {
            // L'utilisateur est connecté, procéder à la récupération des voyages
            ResponseEntity<String> voyages = serviceApiRest2.getAllVoyages();
            return voyages; // Retourne directement la réponse de serviceApiRest2.getAllVoyages()
        } else {
            // L'utilisateur n'est pas connecté, renvoyer une erreur non autorisée
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body("User not logged in");
        }
    }

    @PostMapping("/login")
    public ResponseEntity<String> login(@RequestParam String username,
            @RequestParam String password,
            HttpSession session) {
        ResponseEntity<String> authenticated = serviceApiRest1.login(username, password);

        // Vérifie le code de statut HTTP de la réponse retournée par
        // serviceApiRest1.login()
        if (authenticated.getStatusCode().is2xxSuccessful()) {
            // Si l'authentification réussit, stocke le nom d'utilisateur dans la session et
            // retourne HTTP 200
            session.setAttribute("username", username);
            return ResponseEntity.ok("Logged in with " + username);
        } else {
            // Si l'authentification échoue, retourne HTTP 400 avec le message d'erreur
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(authenticated.getBody());
        }
    }

    @PostMapping("/logout")
    public ResponseEntity<String> logout(HttpSession session) {
        try {
            // Effectue la déconnexion en invalidant la session
            session.invalidate();
            // Retourne HTTP 200 en cas de succès de la déconnexion
            return ResponseEntity.ok("Déconnexion réussie");
        } catch (Exception e) {
            // Retourne HTTP 400 en cas d'erreur lors de la déconnexion
            return ResponseEntity.status(HttpStatus.BAD_REQUEST)
                    .body("Erreur lors de la déconnexion : " + e.getMessage());
        }
    }

    @PostMapping("/addUser")
    public ResponseEntity<String> addUser(@RequestParam String username,
            @RequestParam String password) {
        try {
            // Ajoute l'utilisateur en utilisant le service approprié
            serviceApiRest1.addUser(username, password);
            // Retourne HTTP 200 en cas de succès de l'ajout de l'utilisateur
            return ResponseEntity.ok("Utilisateur ajouté avec succès");
        } catch (Exception e) {
            // Retourne HTTP 400 en cas d'erreur lors de l'ajout de l'utilisateur
            return ResponseEntity.status(HttpStatus.BAD_REQUEST)
                    .body("Erreur lors de l'ajout de l'utilisateur : " + e.getMessage());
        }
    }

    @PostMapping("/addReservation")
    public ResponseEntity<String> addReservation(@RequestParam Integer Fk_voyage, HttpSession session) {
        try {
            // Vérifie si l'utilisateur est connecté en vérifiant la présence de son nom
            // d'utilisateur dans la session
            String username = (String) session.getAttribute("username");
            if (username == null) {
                // Retourne HTTP 401 (Unauthorized) si aucun utilisateur n'est connecté
                return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body("User not logged in");
            }

            // Récupère l'ID de l'utilisateur à partir de la session
            Integer fkUser = (Integer) session.getAttribute("userId");

            // Ajoute la réservation en utilisant le service approprié avec les IDs du
            // voyage et de l'utilisateur
            serviceApiRest1.addReservation(Fk_voyage, fkUser);

            // Retourne HTTP 200 en cas de succès de l'ajout de la réservation
            return ResponseEntity.ok("Réservation ajoutée avec succès");
        } catch (Exception e) {
            // Retourne HTTP 400 en cas d'erreur lors de l'ajout de la réservation
            return ResponseEntity.status(HttpStatus.BAD_REQUEST)
                    .body("Erreur lors de l'ajout de la réservation : " + e.getMessage());
        }
    }

}
