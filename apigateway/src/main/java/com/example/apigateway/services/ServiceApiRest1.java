package com.example.apigateway.services;

import org.springframework.http.MediaType;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.web.client.RestTemplateBuilder;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpMethod;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.client.HttpClientErrorException;
import org.springframework.web.client.RestTemplate;

@Service
public class ServiceApiRest1 {

    private final RestTemplate restTemplate;
    private final String apiGatewayUrl = "http://localhost:8081";

    @Autowired
    public ServiceApiRest1(RestTemplateBuilder restTemplateBuilder) {
        this.restTemplate = restTemplateBuilder.build();
    }

    public ResponseEntity<String> getNothing() {
        String url = apiGatewayUrl + "/";
        return restTemplate.getForEntity(url, String.class);
    }

    public ResponseEntity<String> getUsers() {
        String url = apiGatewayUrl + "/getUsers";
        ResponseEntity<String> reponse = restTemplate.getForEntity(url, String.class);
        return ResponseEntity.ok(reponse.getBody());
    }

    public ResponseEntity<String> addUser(@RequestParam String username, @RequestParam String password) {
        String url = apiGatewayUrl + "/addUser";

        // User userDTO = new User(username, password);

        LinkedMultiValueMap<String, String> params = new LinkedMultiValueMap<>();
        params.add("username", username);
        params.add("password", password);

        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_FORM_URLENCODED);
        HttpEntity<MultiValueMap<String, String>> requestEntity = new HttpEntity<>(params, headers);

        try {
            ResponseEntity<String> response = restTemplate.postForEntity(url, requestEntity, String.class);
            // Vérifier la réponse
            if (response.getStatusCode().is2xxSuccessful()) {
                // Traitement réussi
                return ResponseEntity.ok(response.getBody());
            } else {
                // Gérer les erreurs si nécessaire
                return ResponseEntity.badRequest().body(response.getBody());
            }
        } catch (HttpClientErrorException.BadRequest ex) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("L'utilisateur " + username + " existe déjà !");
        }
    }

    public ResponseEntity<?> login(@RequestParam String username, @RequestParam String password) {
        String url = apiGatewayUrl + "/login";

        LinkedMultiValueMap<String, String> params = new LinkedMultiValueMap<>();
        params.add("username", username);
        params.add("password", password);

        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_FORM_URLENCODED);
        HttpEntity<MultiValueMap<String, String>> requestEntity = new HttpEntity<>(params, headers);

        try {
            ResponseEntity<String> response = restTemplate.postForEntity(url, requestEntity, String.class);
            // Vérifier la réponse
            if (response.getStatusCode().is2xxSuccessful()) {
                // Traitement réussi
                return ResponseEntity.ok(response.getBody());
            } else {
                // Gérer les erreurs si nécessaire
                return ResponseEntity.badRequest().body(response.getBody());
            }
        } catch (HttpClientErrorException.BadRequest ex) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("Le username ou le password est faux !!");
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

    public ResponseEntity<String> addReservation(@RequestParam Integer fk_voyage, @RequestParam Integer fk_user) {
        // Appeler votre API Gateway pour gérer la déconnexion
        String url = apiGatewayUrl + "/addReservation";

        LinkedMultiValueMap<String, String> params = new LinkedMultiValueMap<>();
        params.add("fk_voyage", fk_voyage.toString());
        params.add("fk_user", fk_user.toString());

        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_FORM_URLENCODED);
        HttpEntity<MultiValueMap<String, String>> requestEntity = new HttpEntity<>(params, headers);

        try {
            ResponseEntity<String> response = restTemplate.postForEntity(url, requestEntity, String.class);
            // Vérifier la réponse
            if (response.getStatusCode().is2xxSuccessful()) {
                // Traitement réussi
                return ResponseEntity.ok(response.getBody());
            } else {
                // Gérer les erreurs si nécessaire
                return ResponseEntity.badRequest().body(response.getBody());
            }
        } catch (HttpClientErrorException.BadRequest ex) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("La reservation n'a pas pu être ajoutée !");
        }
    }

    public ResponseEntity<String> deleteReservation(@RequestParam Integer id) {
        // Appeler votre API Gateway pour gérer la déconnexion
        String url = apiGatewayUrl + "/deleteReservation";

        LinkedMultiValueMap<String, String> params = new LinkedMultiValueMap<>();
        params.add("id", id.toString());

        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_FORM_URLENCODED);
        HttpEntity<MultiValueMap<String, String>> requestEntity = new HttpEntity<>(params, headers);

        try {
            ResponseEntity<String> response = restTemplate.exchange(url, HttpMethod.DELETE, requestEntity,
                    String.class);
            // Vérifier la réponse
            if (response.getStatusCode().is2xxSuccessful()) {
                // Traitement réussi
                return ResponseEntity.ok(response.getBody());
            } else {
                // Gérer les erreurs si nécessaire
                return ResponseEntity.badRequest().body(response.getBody());
            }
        } catch (HttpClientErrorException.BadRequest ex) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("Error durant la suppression");
        }
    }

}
