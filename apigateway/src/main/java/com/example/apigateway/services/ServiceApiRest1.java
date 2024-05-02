package com.example.apigateway.services;

import java.net.http.HttpHeaders;
import java.util.Collections;
import java.util.HashMap;
import java.util.Map;

import org.apache.catalina.User;
import org.apache.tomcat.util.http.parser.MediaType;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.web.client.RestTemplateBuilder;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpMethod;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.client.HttpClientErrorException;
import org.springframework.web.client.RestTemplate;

import com.example.apigateway.dto.UserDTO;

import jakarta.servlet.http.HttpSession;

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

    public ResponseEntity<Void> addUser(@RequestParam String username, @RequestParam String password) {
        String url = apiGatewayUrl + "/addUser";
        UserDTO userDTO = new UserDTO(username, password);

        LinkedMultiValueMap<String, String> params = new LinkedMultiValueMap<>();
        params.add("username", username);
        params.add("password", password);
        
        HttpHeaders headers;
        headers.setContentType(MediaType.APPLICATION_FORM_URLENCODED);
        HttpEntity<MultiValueMap<String, String>> requestEntity = new HttpEntity<>(params, headers);
        try {
            ResponseEntity<Void> reponse = restTemplate.postForEntity(url, userDTO, Void.class);
            return ResponseEntity.ok(reponse.getBody()); // Utilisateur ajouté avec succès (HTTP 200)
        } catch (HttpClientErrorException.BadRequest ex) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).build(); // Échec de l'ajout de l'utilisateur (HTTP //
                                                                          // 400)
        }
    }
    @PostMapping("/login")
    public ResponseEntity<?> login(HttpSession session, @RequestParam String username, @RequestParam String password) {
        String url = apiGatewayUrl + "/addUser";
        
        LinkedMultiValueMap<String, String> params = new LinkedMultiValueMap<>();
        params.add("username", username);
        params.add("password", password);

        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_FORM_URLENCODED);
        HttpEntity<MultiValueMap<String, String>> requestEntity = new HttpEntity<>(params, headers);
        
        try {
            ResponseEntity<User> response = restTemplate.exchange(url, HttpMethod.POST, requestEntity, User.class);
            User user = response.getBody();

            session.setAttribute("user", user);
            return ResponseEntity.ok(Collections.singletonMap("username", user.getUsername()));
        } catch (HttpClientErrorException e) {
            if (e.getStatusCode() == HttpStatus.UNAUTHORIZED) {
                return ResponseEntity.status(e.getStatusCode()).body("Nom d'utilisateur ou mot passe invalide.");
            } else {
                return ResponseEntity.status(e.getStatusCode()).body(e.getResponseBodyAsString());
            }
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
            return ResponseEntity.ok(response.getBody());
        } else {
            // Erreur (code de statut dans la plage des 4xx)
            return ResponseEntity.badRequest().body(response.getBody());
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
