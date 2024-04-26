package main.java.com.example.apigateway.services;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

@Service
public class ServiceApiRest1 {

    private final RestTemplate restTemplate;

    @Autowired
    public ServiceApiRest1(RestTemplate restTemplate) {
        this.restTemplate = restTemplate;
    }

    public boolean authenticateUser(String username, String password) {
        // Appel à votre API externe pour l'authentification
        String url = "http://api-externe.com/authenticate";
        // Préparer les données de la requête
        Map<String, String> credentials = new HashMap<>();
        credentials.put("username", username);
        credentials.put("password", password);
        // Effectuer la requête POST et obtenir la réponse
        ResponseEntity<String> response = restTemplate.postForEntity(url, credentials, String.class);
        // Vérifier si l'authentification a réussi
        return response.getStatusCode().is2xxSuccessful();
    }
}
