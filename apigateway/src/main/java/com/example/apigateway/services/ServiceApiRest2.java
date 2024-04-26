package  main.java.com.example.apigateway.services;

import java.util.HashMap;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
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
}
