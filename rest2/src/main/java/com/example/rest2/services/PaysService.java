package com.example.rest2.services;

import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.StreamSupport;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.example.rest2.model.Pays;
import com.example.rest2.repository.PaysRepository;

import jakarta.transaction.Transactional;

@Service
public class PaysService {
    private final PaysRepository paysRepository;

    @Autowired
    public PaysService(PaysRepository paysRepository) {
        this.paysRepository = paysRepository;
    }

    @Transactional
    public String addNewPays(String name) {
        Pays newPays = new Pays();
        newPays.setNom(name);
        paysRepository.save(newPays);
        return "saved";
    }

    @Transactional
    public String updatePays(Integer id, String name) {
        Pays existingPays = paysRepository.findById(id).orElse(null);
        if (existingPays != null) {
            existingPays.setNom(name);
            existingPays.setVersion(existingPays.getVersion() + 1);
            paysRepository.save(existingPays);
            return "updated";
        } else {
            return "Pays not found";
        }
    }
    

    @Transactional
    public String deletePays(Integer id) {
        if (paysRepository.existsById(id)) {
            paysRepository.deleteById(id);
            return "deleted";
        } else {
            return "Pays not found";
        }
    }

    public List<Pays> getAllPays() {
        Iterable<Pays> paysIterable = paysRepository.findAll();
        List<Pays> paysList = StreamSupport.stream(paysIterable.spliterator(), false).collect(Collectors.toList());
        return paysList;
    }
    
}
