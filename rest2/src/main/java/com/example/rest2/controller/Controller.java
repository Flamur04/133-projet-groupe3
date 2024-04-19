package com.example.rest2.controller;


import com.example.rest2.model.Pays;
import com.example.rest2.model.Voyage;
import com.example.rest2.repository.PaysRepository;
import com.example.rest2.repository.VoyageRepository;
import com.example.rest2.services.PaysService;
import com.example.rest2.services.VoyageService;
import java.util.Date;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import jakarta.servlet.http.HttpSession;
import org.springframework.http.ResponseEntity;

@RestController
@RequestMapping("/user")
public class Controller {

    private final VoyageService voyageService;
    private final PaysService paysService;

    @Autowired
    public Controller(VoyageService voyageService, PaysService paysService) {
        this.voyageService = voyageService;
        this.paysService = paysService;
    }

    // Handler pour GET
    @GetMapping("/")
    public String getNothing() {
        return "";
    }

    @PostMapping(path = "/addPays")
    public @ResponseBody String addNewPays(@RequestParam String name) {
        return paysService.addNewPays(name);
    }
    
    @PostMapping(path = "/addVoyage")
    public @ResponseBody String addNewVoyage(@RequestParam String name, @RequestParam String description, @RequestParam double prix, @RequestParam int nombreJour, @RequestParam String fkPays, @RequestParam int version, @RequestParam Date dateDepart, @RequestParam Date dateRetour) {
        return voyageService.addNewVoyage(name, description, prix, nombreJour, fkPays, version, dateDepart, dateRetour);
    }
    

}