package com.example.apigateway.services;

import java.util.HashMap;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpEntity;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

@Service
public class ServiceApiRest1 {

    private final RestTemplate restTemplate;

    @Autowired
    public ServiceApiRest1(RestTemplate restTemplate) {
        this.restTemplate = restTemplate;
    }

    public ResponseEntity<String> getUsers() {
        String url = "http://api-externe.com/getUsers";

        // Effectuer la requête GET
        ResponseEntity<String> response = restTemplate.getForEntity(url, String.class);

        // Vérifier la réponse
        if (response.getStatusCode().is2xxSuccessful()) {
            // Traiter la réponse si nécessaire

            // Convertir la réponse JSON en objet ou en Map pour vérification, ici simplifié
            String responseBody = response.getBody();
            return ResponseEntity.ok(response.getBody());
        } else {
            // Gérer les erreurs si nécessaire
            return ResponseEntity.badRequest().body("Échec de la récupération des données");
        }
    }

    public ResponseEntity<String> addUser(String username, String password) {
        String url = "http://api-externe.com/users";

        // Créer le corps de la requête avec les informations de l'utilisateur
        String requestBody = "{\"username\":\"" + username + "\", \"password\":\"" + password + "\"}";

        // Créer l'entité HttpEntity avec le corps de la requête
        HttpEntity<String> requestEntity = new HttpEntity<>(requestBody);

        // Effectuer la requête POST
        ResponseEntity<String> response = restTemplate.postForEntity(url, requestEntity, String.class);

        // Vérifier la réponse
        if (response.getStatusCode().is2xxSuccessful()) {
            // Traitement réussi
            return ResponseEntity.ok("Utilisateur ajouté avec succès");
        } else {
            // Gérer les erreurs si nécessaire
            return ResponseEntity.badRequest().body("Échec de l'ajout de l'utilisateur");
        }
    }

    public boolean authenticateUser(String username, String password) {
        // Appel à votre API externe pour l'authentification
        String url = "http://api-externe.com/login";
        // Préparer la requête (ajustez selon l'API que vous appelez)
        Map<String, String> credentials = new HashMap<>();
        credentials.put("username", username);
        credentials.put("password", password);

        // Effectuer l'appel API et recevoir la réponse => postFor peut être un getFor !
        ResponseEntity<String> response = restTemplate.postForEntity(url, credentials, String.class);
        // Supposons que l'API renvoie un statut 200 avec un corps contenant
        // {"status":"success"} en cas de succès
        if (response.getStatusCode().is2xxSuccessful()) {
            // Convertir la réponse JSON en objet ou en Map pour vérification, ici simplifié
            String responseBody = response.getBody();
            if (responseBody != null && responseBody.contains("\"status\":\"success\"")) {
                // Configurer la session en cas de succès
                session.setAttribute("username", username);
                session.setAttribute("visites", 0);
                return ResponseEntity.ok("Connecté");
            }
        }

    }

}
