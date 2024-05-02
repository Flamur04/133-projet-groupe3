package com.example.apigateway.services;

import java.util.HashMap;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.web.client.RestTemplateBuilder;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.HttpClientErrorException;
import org.springframework.web.client.RestTemplate;

import com.example.apigateway.dto.UserDTO;

@Service
public class ServiceApiRest1 {

    private final RestTemplate restTemplate;
    //private final String apiGatewayUrl = "https://hotif.emf-informatique.ch/133-projet-groupe3/rest1/src/main/java/com/example/rest1/controller/Controller.java";
    private final String apiGatewayUrl = "http//localhost:8080";
    

    @Autowired
    public ServiceApiRest1(RestTemplateBuilder restTemplateBuilder) {
        this.restTemplate = restTemplateBuilder.build();
    }

    public ResponseEntity<String> getUsers() {
        String url = apiGatewayUrl + "/getUsers";
        return restTemplate.getForEntity(url, String.class);
    }

    public ResponseEntity<Void> addUser(String username, String password) {
        String url = apiGatewayUrl + "/addUser";
        UserDTO userDTO = new UserDTO(username, password);
        try {
            restTemplate.postForEntity(url, userDTO, Void.class);
            return ResponseEntity.ok().build(); // Utilisateur ajouté avec succès (HTTP 200)
        } catch (HttpClientErrorException.BadRequest ex) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).build(); // Échec de l'ajout de l'utilisateur (HTTP //
                                                                          // 400)
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
        Map<Integer, Integer> credentials = new HashMap<>();
        // credentials.put("Fk_voyage", fk_voyage);
        // credentials.put("Fk_user", fk_user);
        // Effectuer la requête GET ou POST selon l'API Gateway
        ResponseEntity<String> response = restTemplate.postForEntity(url, credentials, String.class);

        return response.getStatusCode().is2xxSuccessful();
    }

    public ResponseEntity<String> deleteReservation(Integer id) {
        // Appeler votre API Gateway pour gérer la déconnexion
        String url = apiGatewayUrl + "/deleteReservation";

        // Effectuer la requête POST pour la déconnexion
        ResponseEntity<String> response = restTemplate.postForEntity(url, id, String.class);

        // Retourner la réponse avec le code de statut approprié et un message
        if (response.getStatusCode().is2xxSuccessful()) {
            // Succès (code de statut dans la plage des 2xx)
            return ResponseEntity.ok("Rervation supprimée");
        } else {
            // Erreur (code de statut dans la plage des 4xx)
            return ResponseEntity.badRequest().body("Error durant la suppression");
        }
    }

}
