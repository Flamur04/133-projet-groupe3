package com.example.apigateway.services;

import java.time.LocalDate;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

@Service
public class ServiceApiRest2 {
    private final RestTemplate restTemplate;

    @Autowired
    public ServiceApiRest2(RestTemplate restTemplate) {

        this.restTemplate = restTemplate;
    }

    public ResponseEntity<String> getAllPays() {
        String url = "http://api-externe.com/getAllPays";

        try {
            // Effectuer une requête GET
            ResponseEntity<String> response = restTemplate.getForEntity(url, String.class);

            // Vérifier si la réponse est réussie (code d'état 200)
            if (response.getStatusCode().is2xxSuccessful()) {
                // Traiter la réponse si nécessaire
                // Pour l'instant, renvoyons simplement le corps de la réponse
                return ResponseEntity.ok(response.getBody());
            } else {
                // Gérer les erreurs si nécessaire
                return ResponseEntity.badRequest().body("Échec de la récupération des données");
            }
        } catch (Exception e) {
            // Gérer les exceptions (par exemple, erreurs réseau)
            return ResponseEntity.badRequest().body("Erreur : " + e.getMessage());
        }
    }

    public ResponseEntity<String> addNewPays(String name) {
        String url = "http://api-externe.com/addPays";

        // Créer le corps de la requête avec les informations du pays
        String requestBody = "{\"name\":\"" + name + "\"}";

        // Créer l'entité HttpEntity avec le corps de la requête
        HttpEntity<String> requestEntity = new HttpEntity<>(requestBody);

        // Effectuer la requête POST
        ResponseEntity<String> response = restTemplate.postForEntity(url, requestEntity, String.class);

        // Vérifier la réponse
        if (response.getStatusCode().is2xxSuccessful()) {
            // Traitement réussi
            return ResponseEntity.ok("Pays ajouté avec succès");
        } else {
            // Gérer les erreurs si nécessaire
            return ResponseEntity.badRequest().body("Échec de l'ajout du pays");
        }
    }


    public ResponseEntity<String> updatePays( int id, String name) {
        String url = "http://api-externe.com/updatePays/" + id;

        // Créer le corps de la requête avec les informations du pays
        String requestBody = "{\"name\":\"" + name + "\"}";

        // Créer l'entité HttpEntity avec le corps de la requête
        HttpEntity<String> requestEntity = new HttpEntity<>(requestBody);

        // Effectuer la requête PUT
        ResponseEntity<String> response = restTemplate.exchange(url, HttpMethod.PUT, requestEntity, String.class);

        // Vérifier la réponse
        if (response.getStatusCode().is2xxSuccessful()) {
            // Traitement réussi
            return ResponseEntity.ok("Pays mis à jour avec succès");
        } else {
            // Gérer les erreurs si nécessaire
            return ResponseEntity.badRequest().body("Échec de la mise à jour du pays");
        }
    }

    public ResponseEntity<String> deletePays(int id) {
        String url = "http://api-externe.com/deletePays";

        // Créer le corps de la requête avec l'ID du pays à supprimer
        String requestBody = "{\"id\":\"" + id + "\"}";

        // Créer l'entité HttpEntity avec le corps de la requête
        HttpEntity<String> requestEntity = new HttpEntity<>(requestBody);

        // Effectuer la requête DELETE
        ResponseEntity<String> response = restTemplate.exchange(url, HttpMethod.DELETE, requestEntity, String.class);

        // Vérifier la réponse
        if (response.getStatusCode().is2xxSuccessful()) {
            // Traitement réussi
            return ResponseEntity.ok("Pays supprimé avec succès");
        } else {
            // Gérer les erreurs si nécessaire
            return ResponseEntity.badRequest().body("Échec de la suppression du pays");
        }
    }

    public ResponseEntity<String> addNewVoyage(String name, String description, int prix, String fkPays, LocalDate dateDepart, LocalDate dateRetour) {
    String url = "http://api-externe.com/addVoyage";

    // Créer le corps de la requête avec les informations du voyage
    String requestBody = "{\"name\":\"" + name + "\", \"description\":\"" + description + "\", \"prix\":" + prix + ", \"fkPays\":\"" + fkPays + "\", \"dateDepart\":\"" + dateDepart + "\", \"dateRetour\":\"" + dateRetour + "\"}";

    // Créer l'entité HttpEntity avec le corps de la requête
    HttpEntity<String> requestEntity = new HttpEntity<>(requestBody);

    // Effectuer la requête POST
    ResponseEntity<String> response = restTemplate.postForEntity(url, requestEntity, String.class);

    // Vérifier la réponse
    if (response.getStatusCode().is2xxSuccessful()) {
        // Traitement réussi
        return ResponseEntity.ok("Voyage ajouté avec succès");
    } else {
        // Gérer les erreurs si nécessaire
        return ResponseEntity.badRequest().body("Échec de l'ajout du voyage");
    }
}

public ResponseEntity<String> updateVoyage(int id, String name, String description, int prix, String fkPays, int version, LocalDate dateDepart, LocalDate dateRetour) {
    String url = "http://api-externe.com/updateVoyage";

    // Créer le corps de la requête avec les informations du voyage
    String requestBody = "{\"id\":" + id + ", \"name\":\"" + name + "\", \"description\":\"" + description + "\", \"prix\":" + prix +", \"fkPays\":\"" + fkPays + "\", \"version\":" + version + ", \"dateDepart\":\"" + dateDepart + "\", \"dateRetour\":\"" + dateRetour + "\"}";

    // Créer l'entité HttpEntity avec le corps de la requête
    HttpEntity<String> requestEntity = new HttpEntity<>(requestBody);

    // Effectuer la requête PUT
    ResponseEntity<String> response = restTemplate.exchange(url, HttpMethod.PUT, requestEntity, String.class);

    // Vérifier la réponse
    if (response.getStatusCode().is2xxSuccessful()) {
        // Traitement réussi
        return ResponseEntity.ok("Voyage mis à jour avec succès");
    } else {
        // Gérer les erreurs si nécessaire
        return ResponseEntity.badRequest().body("Échec de la mise à jour du voyage");
    }
}


public ResponseEntity<String> deleteVoyage(int id) {
    String url = "http://api-externe.com/deleteVoyage";

    // Créer le corps de la requête avec l'ID du voyage à supprimer
    String requestBody = "{\"id\":\"" + id + "\"}";

    // Créer l'entité HttpEntity avec le corps de la requête
    HttpEntity<String> requestEntity = new HttpEntity<>(requestBody);

    // Effectuer la requête DELETE
    ResponseEntity<String> response = restTemplate.exchange(url, HttpMethod.DELETE, requestEntity, String.class);

    // Vérifier la réponse
    if (response.getStatusCode().is2xxSuccessful()) {
        // Traitement réussi
        return ResponseEntity.ok("Voyage supprimé avec succès");
    } else {
        // Gérer les erreurs si nécessaire
        return ResponseEntity.badRequest().body("Échec de la suppression du voyage");
    }
}

public ResponseEntity<String> getAllVoyages() {
    String url = "http://api-externe.com/getAllVoyages";

    try {
        // Effectuer une requête GET
        ResponseEntity<String> response = restTemplate.getForEntity(url, String.class);

        // Vérifier si la réponse est réussie (code d'état 200)
        if (response.getStatusCode().is2xxSuccessful()) {
            // Traiter la réponse si nécessaire
            // Pour l'instant, renvoyons simplement le corps de la réponse
            return ResponseEntity.ok(response.getBody());
        } else {
            // Gérer les erreurs si nécessaire
            return ResponseEntity.badRequest().body("Échec de la récupération des données");
        }
    } catch (Exception e) {
        // Gérer les exceptions (par exemple, erreurs réseau)
        return ResponseEntity.badRequest().body("Erreur : " + e.getMessage());
    }
}



}
