package main.java.com.example.apigateway.services;

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
    private final String apiGatewayUrl = "http://localhost:8080";

    @Autowired
    public ServiceApiRest1(RestTemplate restTemplate) {
        this.restTemplate = restTemplate;
    }

    public ResponseEntity<String> getUsers() {
        String url = apiGatewayUrl + "/getUsers";

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
        String url = apiGatewayUrl + "/addUser";

        // Créer le corps de la requête avec les informations de l'utilisateur
        String requestBody = "{\"username\":\"" + username + "\", \"password\":\"" + password + "\"}";

        // Créer l'entité HttpEntity avec le corps de la requête
        HttpEntity<String> requestEntity = new HttpEntity<>(requestBody);

        // Effectuer la requête POST
        ResponseEntity<Void> response = restTemplate.postForEntity(url, requestEntity, Void.class);

        // Retourner la réponse avec le code de statut approprié et un message
        if (response.getStatusCode().is2xxSuccessful()) {
            // Succès (code de statut dans la plage des 2xx)
            return ResponseEntity.ok("Utilisateur ajouté avec succès");
        } else {
            // Erreur (code de statut dans la plage des 4xx)
            return ResponseEntity.badRequest().body("Échec de l'ajout de l'utilisateur");
        }
    }

    public ResponseEntity<String> login(String username, String password) {
        String url = apiGatewayUrl + "/login";

        // Préparer la requête (ajustez selon l'API que vous appelez)
        Map<String, String> credentials = new HashMap<>();
        credentials.put("username", username);
        credentials.put("password", password);

        // Effectuer l'appel API et recevoir la réponse => postFor peut être un getFor !
        ResponseEntity<String> response = restTemplate.postForEntity(url, credentials, String.class);

        // Retourner la réponse avec le code de statut approprié et un message
        if (response.getStatusCode().is2xxSuccessful()) {
            // Succès (code de statut dans la plage des 2xx)
            return ResponseEntity.ok("Connecté");
        } else {
            // Erreur (code de statut dans la plage des 4xx)
            return ResponseEntity.badRequest().body("Échec de l'authentification");
        }
    }

    public ResponseEntity<String> logout() {
        // Appeler votre API Gateway pour gérer la déconnexion
        String url = apiGatewayUrl + "/logout";

        // Effectuer la requête POST pour la déconnexion
        ResponseEntity<String> response = restTemplate.postForEntity(url, null, String.class);

        // Retourner la réponse avec le code de statut approprié et un message
        if (response.getStatusCode().is2xxSuccessful()) {
            // Succès (code de statut dans la plage des 2xx)
            return ResponseEntity.ok("Déconnexion réussie");
        } else {
            // Erreur (code de statut dans la plage des 4xx)
            return ResponseEntity.badRequest().body("Échec de la déconnexion");
        }
    }

    public boolean addReservation(Integer fk_voyage, Integer fk_user) {
        // Appeler votre API Gateway pour gérer la déconnexion
        String url = apiGatewayUrl + "/addReservation";

        // Préparer la requête (ajustez selon l'API que vous appelez)
        Map<String, String> credentials = new HashMap<>();
        credentials.put("Fk_voyage", fk_voyage);
        credentials.put("Fk_user", fk_user);
        // Effectuer la requête GET ou POST selon l'API Gateway
        ResponseEntity<String> response = restTemplate.postForEntity(url, credentials, String.class);

        return response.getStatusCode().is2xxSuccessful();
    }

}