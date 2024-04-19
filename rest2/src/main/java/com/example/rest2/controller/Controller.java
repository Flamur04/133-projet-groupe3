package com.example.rest2.controller;


import com.example.rest2.model.Pays;
import com.example.rest2.model.Voyage;
import com.example.rest2.repository.PaysRepository;
import com.example.rest2.repository.VoyageRepository;
import com.example.rest2.services.PaysService;
import com.example.rest2.services.VoyageService;

import java.time.LocalDate;
import java.util.Date;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
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

    @PutMapping(path = "/updatePays/{id}")
    public @ResponseBody String updatePays(@PathVariable Integer id, @RequestParam String name) {
        return paysService.updatePays(id, name);
    }

    @DeleteMapping(path = "/deletePays/{id}")
    public @ResponseBody String deletePays(@PathVariable Integer id) {
        return paysService.deletePays(id);
    }

    @GetMapping(path = "/getAllPays")
    public @ResponseBody List<Pays> getAllPays() {
        return paysService.getAllPays();
    }

    @PostMapping(path = "/addVoyage")
    public @ResponseBody String addNewVoyage(@RequestParam String name, @RequestParam String description, @RequestParam double prix, @RequestParam int nombreJour, @RequestParam String fkPays, @RequestParam int version, 
    @RequestParam @DateTimeFormat(pattern = "dd-MM-yyyy") LocalDate dateDepart, 
    @RequestParam @DateTimeFormat(pattern = "dd-MM-yyyy") LocalDate dateRetour) {
        return voyageService.addNewVoyage(name, description, prix, nombreJour, fkPays, version, dateDepart, dateRetour);
    }

    @PutMapping(path = "/updateVoyage/{id}")
    public @ResponseBody String updateVoyage(@PathVariable Integer id, @RequestParam String name, @RequestParam String description, @RequestParam double prix, @RequestParam int nombreJour, @RequestParam String fkPays, @RequestParam int version, 
    @RequestParam @DateTimeFormat(pattern = "dd-MM-yyyy") LocalDate dateDepart, 
    @RequestParam @DateTimeFormat(pattern = "dd-MM-yyyy") LocalDate dateRetour) {
        return voyageService.updateVoyage(id, name, description, prix, nombreJour, fkPays, version, dateDepart, dateRetour);
    }

    @DeleteMapping(path = "/deleteVoyage/{id}")
    public @ResponseBody String deleteVoyage(@PathVariable Integer id) {
        return voyageService.deleteVoyage(id);
    }

    @GetMapping(path = "/getAllVoyages")
    public @ResponseBody List<Voyage> getAllVoyages() {
        return voyageService.getAllVoyages();
    }
    

    

}