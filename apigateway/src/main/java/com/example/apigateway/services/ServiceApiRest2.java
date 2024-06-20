package com.example.apigateway.services;

import org.springframework.http.HttpHeaders;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.http.MediaType;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.web.client.RestTemplateBuilder;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.client.HttpClientErrorException;
import org.springframework.web.client.RestTemplate;

import org.springframework.core.env.Environment;

import org.springframework.http.HttpStatus;



@Service
public class ServiceApiRest2 {
    private final RestTemplate restTemplate;
    private final String Rest2Url = "http://rest2:8082";

    @Autowired
    public ServiceApiRest2(RestTemplateBuilder restTemplateBuilder) {
        this.restTemplate = restTemplateBuilder.build();
    }

    public ResponseEntity<String> getAllPays() {
        String url = Rest2Url + "/getAllPays";
    
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
            e.printStackTrace(); // Ajoutez cette ligne pour imprimer la trace de la pile d'exceptions
            return ResponseEntity.badRequest().body("Erreur : " + e.getMessage());
        }
    }
    

    public ResponseEntity<String> addNewPays(@RequestBody String name) {
        String url = Rest2Url + "/addPays";
    
        // Créer le corps de la requête avec les informations du pays
        LinkedMultiValueMap<String, String> params = new LinkedMultiValueMap<>();
        params.add("name", name);
    
        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.parseMediaType(MediaType.APPLICATION_FORM_URLENCODED_VALUE));
        HttpEntity<MultiValueMap<String, String>> requestEntity = new HttpEntity<>(params, headers);
    
        try {
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
        } catch (HttpClientErrorException e) {
            return ResponseEntity.status(e.getStatusCode()).body(e.getResponseBodyAsString());
        }
    }

    

    public ResponseEntity<String> updatePays(@PathVariable int id, @RequestBody String name) {
        String url = Rest2Url + "/updatePays/" + id;
    
        // Créer le corps de la requête avec les informations du pays
        LinkedMultiValueMap<String, String> params = new LinkedMultiValueMap<>();
        params.add("name", name);
    
        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.parseMediaType(MediaType.APPLICATION_FORM_URLENCODED_VALUE));
        HttpEntity<MultiValueMap<String, String>> requestEntity = new HttpEntity<>(params, headers);
        
    
        try {
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
        } catch (HttpClientErrorException e) {
            return ResponseEntity.status(e.getStatusCode()).body(e.getResponseBodyAsString());
        }
    }
    

    public ResponseEntity<String> deletePays(@PathVariable int id) {
        String url = Rest2Url + "/deletePays/" + id;
        try {
            // Effectuer la requête DELETE
            ResponseEntity<String> response = restTemplate.exchange(url, HttpMethod.DELETE, null, String.class);
    
            // Vérifier la réponse
            if (response.getStatusCode().is2xxSuccessful()) {
                // Traitement réussi
                return ResponseEntity.ok("Pays supprimé avec succès");
            } else {
                // Gérer les erreurs si nécessaire
                return ResponseEntity.badRequest().body("Échec de la suppression du pays");
            }
        } catch (Exception e) {
            // Gérer les exceptions (par exemple, erreurs réseau)
            e.printStackTrace(); // Ajoutez cette ligne pour imprimer la trace de la pile d'exceptions
            return ResponseEntity.badRequest().body("Erreur : " + e.getMessage());
        }
    }
    

public ResponseEntity<String> addNewVoyage(String name, String description, double prix, String fkPays, LocalDate dateDepart, LocalDate dateRetour) {
    String url = Rest2Url + "/addVoyage";

    // Convertir les objets LocalDate en chaînes de caractères
    DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd-MM-yyyy");
    String strDateDepart = dateDepart.format(formatter);
    String strDateRetour = dateRetour.format(formatter);

    // Créer le corps de la requête avec les informations du voyage
    LinkedMultiValueMap<String, String> params = new LinkedMultiValueMap<>();
    params.add("name", name);
    params.add("description", description);
    params.add("prix", String.valueOf(prix));
    params.add("fkPays", fkPays);
    params.add("dateDepart", strDateDepart);
    params.add("dateRetour", strDateRetour);

    HttpHeaders headers = new HttpHeaders();
    headers.setContentType(MediaType.APPLICATION_FORM_URLENCODED);
    HttpEntity<MultiValueMap<String, String>> requestEntity = new HttpEntity<>(params, headers);

    try {
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
    } catch (Exception e) {
        // Gérer les exceptions si nécessaire
        return ResponseEntity.badRequest().body("Erreur : " + e.getMessage());
    }
}

public ResponseEntity<String> updateVoyage(int id, String name, String description, int prix, String fkPays, int version, LocalDate dateDepart, LocalDate dateRetour) {
    String url = Rest2Url + "/updateVoyage/" + id;

    // Convertir les objets LocalDate en chaînes de caractères
    DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd-MM-yyyy");
    String strDateDepart = dateDepart.format(formatter);
    String strDateRetour = dateRetour.format(formatter);

    // Créer le corps de la requête avec les informations du voyage
    LinkedMultiValueMap<String, String> params = new LinkedMultiValueMap<>();
    params.add("name", name);
    params.add("description", description);
    params.add("prix", String.valueOf(prix));
    params.add("fkPays", fkPays);
    params.add("version", String.valueOf(version));
    params.add("dateDepart", strDateDepart);
    params.add("dateRetour", strDateRetour);

    HttpHeaders headers = new HttpHeaders();
    headers.setContentType(MediaType.APPLICATION_FORM_URLENCODED);
    HttpEntity<MultiValueMap<String, String>> requestEntity = new HttpEntity<>(params, headers);

    try {
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
    } catch (Exception e) {
        // Gérer les exceptions si nécessaire
        return ResponseEntity.badRequest().body("Erreur : " + e.getMessage());
    }
}



public ResponseEntity<String> deleteVoyage(int id) {
    String url = Rest2Url + "/deleteVoyage/" + id;

    try {
        // Effectuer la requête DELETE
        ResponseEntity<String> response = restTemplate.exchange(url, HttpMethod.DELETE, null, String.class);

        // Vérifier la réponse
        if (response.getStatusCode().is2xxSuccessful()) {
            // Traitement réussi
            return ResponseEntity.ok("Voyage supprimé avec succès");
        } else {
            // Gérer les erreurs si nécessaire
            return ResponseEntity.badRequest().body("Échec de la suppression du voyage");
        }
    } catch (Exception e) {
        // Gérer les exceptions si nécessaire
        return ResponseEntity.badRequest().body("Erreur : " + e.getMessage());
    }
}


public ResponseEntity<String> getAllVoyages() {
    String url = Rest2Url + "/getAllVoyages";

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
