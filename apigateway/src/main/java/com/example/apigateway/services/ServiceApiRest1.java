package main.java.com.example.apigateway.services;

import org.springframework.beans.factory.annotation.Autowired;
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

    public boolean addUser(String username, String password) {
        String url = apiGatewayUrl + "/addUser";

        // Créer le corps de la requête avec les informations de l'utilisateur
        String requestBody = "{\"username\":\"" + username + "\", \"password\":\"" + password + "\"}";

        // Créer l'entité HttpEntity avec le corps de la requête
        HttpEntity<String> requestEntity = new HttpEntity<>(requestBody);

        // Effectuer la requête POST
        ResponseEntity<String> response = restTemplate.postForEntity(url, requestEntity, String.class);

        return response.getStatusCode().is2xxSuccessful();
    }

    public boolean login(String username, String password) {
        // Appel à votre API externe pour l'authentification
        String url = apiGatewayUrl + "/login";
        // Préparer la requête (ajustez selon l'API que vous appelez)
        Map<String, String> credentials = new HashMap<>();
        credentials.put("username", username);
        credentials.put("password", password);

        // Effectuer l'appel API et recevoir la réponse => postFor peut être un getFor !
        ResponseEntity<String> response = restTemplate.postForEntity(url, credentials, String.class);

        return response.getStatusCode().is2xxSuccessful();
    }

    public boolean logout() {
        // Appeler votre API Gateway pour gérer la déconnexion
        String url = apiGatewayUrl + "/logout";

        // Effectuer la requête GET ou POST selon l'API Gateway
        ResponseEntity<String> response = restTemplate.postForEntity(url, null, String.class);

        return response.getStatusCode().is2xxSuccessful();
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
