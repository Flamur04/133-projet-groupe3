package com.example.rest2.services;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.Date;
import com.example.rest2.model.Voyage;
import com.example.rest2.repository.VoyageRepository;

import jakarta.transaction.Transactional;

@Service
public class VoyageService {
    private final VoyageRepository voyageRepository;

    @Autowired
    public VoyageService(VoyageRepository voyageRepository) {
        this.voyageRepository = voyageRepository;
    }

    @Transactional
    public String addNewVoyage(String name, String description, double prix, int nombreJour, String fkPays, int version, LocalDate dateDepart, LocalDate dateRetour) {
        Voyage newVoyage = new Voyage();
        newVoyage.setNom(name);
        newVoyage.setDescription(description);
        newVoyage.setPrix(prix);
        newVoyage.setNombreJour(nombreJour);
        newVoyage.setFkPays(fkPays);
        newVoyage.setVersion(version);
        newVoyage.setDateDepart(dateDepart);
        newVoyage.setDateRetour(dateRetour);
        voyageRepository.save(newVoyage);
        return "saved";
    }
    
    
}
