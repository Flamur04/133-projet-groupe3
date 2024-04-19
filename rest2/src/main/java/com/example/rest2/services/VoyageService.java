package com.example.rest2.services;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.Date;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.StreamSupport;

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

    @Transactional
public String updateVoyage(Integer id, String name, String description, double prix, int nombreJour, String fkPays, int version, LocalDate dateDepart, LocalDate dateRetour) {
    Voyage existingVoyage = voyageRepository.findById(id).orElse(null);
    if (existingVoyage != null) {
        existingVoyage.setNom(name);
        existingVoyage.setDescription(description);
        existingVoyage.setPrix(prix);
        existingVoyage.setNombreJour(nombreJour);
        existingVoyage.setFkPays(fkPays);
        existingVoyage.setVersion(existingVoyage.getVersion() + 1);
        existingVoyage.setDateDepart(dateDepart);
        existingVoyage.setDateRetour(dateRetour);
        voyageRepository.save(existingVoyage);
        return "updated";
    } else {
        return "Voyage not found";
    }
}

@Transactional
public String deleteVoyage(Integer id) {
    if (voyageRepository.existsById(id)) {
        voyageRepository.deleteById(id);
        return "deleted";
    } else {
        return "Voyage not found";
    }
}

public List<Voyage> getAllVoyages() {
    Iterable<Voyage> voyageIterable = voyageRepository.findAll();
    List<Voyage> voyageList = StreamSupport.stream(voyageIterable.spliterator(), false).collect(Collectors.toList());
    return voyageList;
}



    
    
}
