package com.example.rest2.repository;

import org.springframework.data.repository.CrudRepository;
import com.example.rest2.model.Voyage;

// CRUD refers Create, Read, Update, Delete

public interface VoyageRepository extends CrudRepository<Voyage, Integer> {

}
