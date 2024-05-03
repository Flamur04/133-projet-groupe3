package com.example.apigateway.apiController;

import java.time.LocalDate;

import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
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

    @GetMapping("/")
    public ResponseEntity<String> getNothing() {
        ResponseEntity<String> responseEntity = serviceApiRest1.getNothing();
        // Renvoyer la réponse du service REST1 directement au client
        return ResponseEntity.ok(responseEntity.getBody());
    }

    @GetMapping("/getUsers")
    public ResponseEntity<String> getUsers(HttpSession session) {
        // Vérifier si l'utilisateur est connecté
        if (session.getAttribute("username") != null) {
            // L'utilisateur est connecté, procéder à la récupération des utilisateurs
            ResponseEntity<String> getUser = serviceApiRest1.getUsers();
            if (getUser.getStatusCode().is2xxSuccessful()) {
                return ResponseEntity.ok(getUser.getBody());
            } else {
                // Si l'authentification échoue, retourne HTTP 400 avec le message d'erreur
                return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(getUser.getBody());
            }
        } else {
            // L'utilisateur n'est pas connecté, renvoyer une erreur non autorisée
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body("Utilisateur non connecté");
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

    @PostMapping("/login")
    public ResponseEntity<String> login(@RequestParam String username,
            @RequestParam String password,
            HttpSession session) {
        try {
            // Appel du service pour authentification
            ResponseEntity<?> authenticated = serviceApiRest1.login(username, password);

            // Vérification de l'authentification réussie
            if (authenticated.getStatusCode().is2xxSuccessful()) {
                // Récupération du nom d'utilisateur et de l'ID
                String responseString = authenticated.getBody().toString();
                String responseUsername = responseString.substring(responseString.indexOf("\"username\":\"") + 12,
                        responseString.indexOf("\",\"isAdmin\""));
                String responseId = responseString.substring(responseString.indexOf("\"id\":") + 5,
                        responseString.indexOf(",\"username\""));

                // Ajout du nom d'utilisateur et de l'ID à la session
                session.setAttribute("username", responseUsername);
                session.setAttribute("fk_user", responseId);

                return ResponseEntity.ok("Connexion réussie");
            } else {
                return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body("Identifiants invalides");
            }
        } catch (Exception e) {
            // Retourne HTTP 400 en cas d'erreur lors de la déconnexion
            return ResponseEntity.status(HttpStatus.BAD_REQUEST)
                    .body("Erreur lors durant le login : " + e.getMessage());
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

    @PostMapping("/addReservation")
    public ResponseEntity<String> addReservation(@RequestParam Integer Fk_voyage, HttpSession session) {
        try {
            if (session.getAttribute("username") == null) {
                // Retourne HTTP 401 (Unauthorized) si aucun utilisateur n'est connecté
                return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body("User not logged in");
            }

            // Récupère l'ID de l'utilisateur à partir de la session
            Integer fk_user = (Integer) session.getAttribute("fk_user");

            // Ajoute la réservation en utilisant le service approprié avec les IDs du
            // voyage et de l'utilisateur
            serviceApiRest1.addReservation(Fk_voyage, fk_user);

            // Retourne HTTP 200 en cas de succès de l'ajout de la réservation
            return ResponseEntity.ok("Réservation ajoutée avec succès");
        } catch (Exception e) {
            // Retourne HTTP 400 en cas d'erreur lors de l'ajout de la réservation
            return ResponseEntity.status(HttpStatus.BAD_REQUEST)
                    .body("Erreur lors de l'ajout de la réservation : " + e.getMessage());
        }
    }

    @GetMapping("/getAllVoyagesForUserConnected")
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

    @GetMapping("/getAllPays")
    public ResponseEntity<String> getAllPays() {
        try {
            // Appelle la méthode du service
            ResponseEntity<String> response = serviceApiRest2.getAllPays();

            // Vérifie si la réponse est réussie (code d'état 200)
            if (response.getStatusCode().is2xxSuccessful()) {
                // Retourne HTTP 200 avec le corps de la réponse en cas de succès
                return ResponseEntity.ok(response.getBody());
            } else {
                // Retourne HTTP 400 avec un message d'erreur en cas d'échec
                return ResponseEntity.badRequest().body("Échec de la récupération des données");
            }
        } catch (Exception e) {
            // Retourne HTTP 400 avec un message d'erreur en cas d'exception
            return ResponseEntity.badRequest().body("Erreur : " + e.getMessage());
        }
    }

    @PostMapping("/addNewPays")
    public ResponseEntity<String> addNewPays(@RequestParam String name) {
        try {
            // Appelle la méthode du service avec le nom du pays
            ResponseEntity<String> response = serviceApiRest2.addNewPays(name);

            // Vérifie si la réponse est réussie (code d'état 200)
            if (response.getStatusCode().is2xxSuccessful()) {
                // Retourne HTTP 200 avec le message de succès en cas de succès
                return ResponseEntity.ok("Pays ajouté avec succès");
            } else {
                // Retourne HTTP 400 avec un message d'erreur en cas d'échec
                return ResponseEntity.badRequest().body("Échec de l'ajout du pays");
            }
        } catch (Exception e) {
            // Retourne HTTP 400 avec un message d'erreur en cas d'exception
            return ResponseEntity.badRequest().body("Erreur : " + e.getMessage());
        }
    }

    @PutMapping("/updatePays/{id}")
    public ResponseEntity<String> updatePays(@PathVariable int id, @RequestParam String name) {
        try {
            // Appelle la méthode du service avec l'ID et le nom du pays
            ResponseEntity<String> response = serviceApiRest2.updatePays(id, name);

            // Vérifie si la réponse est réussie (code d'état 200)
            if (response.getStatusCode().is2xxSuccessful()) {
                // Retourne HTTP 200 avec le message de succès en cas de succès
                return ResponseEntity.ok("Pays mis à jour avec succès");
            } else {
                // Retourne HTTP 400 avec un message d'erreur en cas d'échec
                return ResponseEntity.badRequest().body("Échec de la mise à jour du pays");
            }
        } catch (Exception e) {
            // Retourne HTTP 400 avec un message d'erreur en cas d'exception
            return ResponseEntity.badRequest().body("Erreur : " + e.getMessage());
        }
    }

    @DeleteMapping("/deletePays/{id}")
    public ResponseEntity<String> deletePays(@PathVariable int id) {
        try {
            // Appelle la méthode du service avec l'ID du pays
            ResponseEntity<String> response = serviceApiRest2.deletePays(id);

            // Vérifie si la réponse est réussie (code d'état 200)
            if (response.getStatusCode().is2xxSuccessful()) {
                // Retourne HTTP 200 avec le message de succès en cas de succès
                return ResponseEntity.ok("Pays supprimé avec succès");
            } else {
                // Retourne HTTP 400 avec un message d'erreur en cas d'échec
                return ResponseEntity.badRequest().body("Échec de la suppression du pays");
            }
        } catch (Exception e) {
            // Retourne HTTP 400 avec un message d'erreur en cas d'exception
            return ResponseEntity.badRequest().body("Erreur : " + e.getMessage());
        }
    }

    @PostMapping("/addNewVoyage")
    public ResponseEntity<String> addNewVoyage(@RequestParam String name, @RequestParam String description,
            @RequestParam int prix, @RequestParam String fkPays,
            @DateTimeFormat(pattern = "dd-MM-yyyy") LocalDate dateDepart,
            @DateTimeFormat(pattern = "dd-MM-yyyy") LocalDate dateRetour) {
        try {
            // Appelle la méthode du service avec les informations du voyage
            ResponseEntity<String> response = serviceApiRest2.addNewVoyage(name, description, prix, fkPays, dateDepart,
                    dateRetour);

            // Vérifie si la réponse est réussie (code d'état 200)
            if (response.getStatusCode().is2xxSuccessful()) {
                // Retourne HTTP 200 avec le message de succès en cas de succès
                return ResponseEntity.ok("Voyage ajouté avec succès");
            } else {
                // Retourne HTTP 400 avec un message d'erreur en cas d'échec
                return ResponseEntity.badRequest().body("Échec de l'ajout du voyage");
            }
        } catch (Exception e) {
            // Retourne HTTP 400 avec un message d'erreur en cas d'exception
            return ResponseEntity.badRequest().body("Erreur : " + e.getMessage());
        }
    }

    @PutMapping("/updateVoyage/{id}")
    public ResponseEntity<String> updateVoyage(@PathVariable int id, @RequestParam String name,
            @RequestParam String description, @RequestParam int prix, @RequestParam String fkPays,
            @RequestParam int version, @RequestParam LocalDate dateDepart, @RequestParam LocalDate dateRetour) {
        try {
            // Appelle la méthode du service avec les informations du voyage
            ResponseEntity<String> response = serviceApiRest2.updateVoyage(id, name, description, prix, fkPays, version,
                    dateDepart, dateRetour);

            // Vérifie si la réponse est réussie (code d'état 200)
            if (response.getStatusCode().is2xxSuccessful()) {
                // Retourne HTTP 200 avec le message de succès en cas de succès
                return ResponseEntity.ok("Voyage mis à jour avec succès");
            } else {
                // Retourne HTTP 400 avec un message d'erreur en cas d'échec
                return ResponseEntity.badRequest().body("Échec de la mise à jour du voyage");
            }
        } catch (Exception e) {
            // Retourne HTTP 400 avec un message d'erreur en cas d'exception
            return ResponseEntity.badRequest().body("Erreur : " + e.getMessage());
        }
    }

    @DeleteMapping("/deleteVoyage/{id}")
    public ResponseEntity<String> deleteVoyage(@PathVariable int id) {
        try {
            // Appelle la méthode du service avec l'ID du voyage
            ResponseEntity<String> response = serviceApiRest2.deleteVoyage(id);

            // Vérifie si la réponse est réussie (code d'état 200)
            if (response.getStatusCode().is2xxSuccessful()) {
                // Retourne HTTP 200 avec le message de succès en cas de succès
                return ResponseEntity.ok("Voyage supprimé avec succès");
            } else {
                // Retourne HTTP 400 avec un message d'erreur en cas d'échec
                return ResponseEntity.badRequest().body("Échec de la suppression du voyage");
            }
        } catch (Exception e) {
            // Retourne HTTP 400 avec un message d'erreur en cas d'exception
            return ResponseEntity.badRequest().body("Erreur : " + e.getMessage());
        }
    }

    @GetMapping("/getAllVoyages")
    public ResponseEntity<String> getAllVoyages() {
        try {
            // Appelle la méthode du service
            ResponseEntity<String> response = serviceApiRest2.getAllVoyages();

            // Vérifie si la réponse est réussie (code d'état 200)
            if (response.getStatusCode().is2xxSuccessful()) {
                // Retourne HTTP 200 avec le corps de la réponse en cas de succès
                return ResponseEntity.ok(response.getBody());
            } else {
                // Retourne HTTP 400 avec un message d'erreur en cas d'échec
                return ResponseEntity.badRequest().body("Échec de la récupération des données");
            }
        } catch (Exception e) {
            // Retourne HTTP 400 avec un message d'erreur en cas d'exception
            return ResponseEntity.badRequest().body("Erreur : " + e.getMessage());
        }
    }
}
