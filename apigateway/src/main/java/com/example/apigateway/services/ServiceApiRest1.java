package com.example.apigateway.services;

import org.springframework.http.MediaType;
import org.apache.catalina.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.web.client.RestTemplateBuilder;
import org.springframework.core.ParameterizedTypeReference;
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
import org.springframework.web.util.UriComponentsBuilder;

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

    public ResponseEntity<?> getUsers() {
        String url = apiGatewayUrl + "/getUsers";
        ResponseEntity<?> reponse = restTemplate.getForEntity(url, String.class);
        return ResponseEntity.ok(reponse.getBody());
    }

    public ResponseEntity<?> addUser(@RequestParam String username, @RequestParam String password) {
        String url = apiGatewayUrl + "/addUser";

        LinkedMultiValueMap<String, String> params = new LinkedMultiValueMap<>();
        params.add("username", username);
        params.add("password", password);

        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_FORM_URLENCODED);
        HttpEntity<MultiValueMap<String, String>> requestEntity = new HttpEntity<>(params, headers);

        try {
            ResponseEntity<?> response = restTemplate.postForEntity(url, requestEntity, String.class);
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

    public ResponseEntity<?> addReservation(@RequestParam Integer fk_voyage, @RequestParam Integer fk_user) {
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

    public ResponseEntity<?> getReservationUser(Integer id) {
        String url = apiGatewayUrl + "/getReservationUser";
        UriComponentsBuilder builder = UriComponentsBuilder.fromUriString(url)
                .queryParam("fk_user", id);

        try {
            ResponseEntity<Iterable<?>> response = restTemplate.exchange(
                    builder.toUriString(),
                    HttpMethod.GET,
                    null,
                    new ParameterizedTypeReference<Iterable<?>>() {
                    });

            // Vérifier la réponse
            if (response.getStatusCode().is2xxSuccessful()) {
                // Traitement réussi
                return ResponseEntity.ok(response.getBody());
            } else {
                // Gérer les erreurs si nécessaire
                return ResponseEntity.badRequest().body(response.getBody());
            }
        } catch (HttpClientErrorException.BadRequest ex) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(false);
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
