package com.example.rest2.repository;

import org.springframework.data.repository.CrudRepository;
import com.example.rest2.model.Pays;

// CRUD refers Create, Read, Update, Delete

public interface PaysRepository extends CrudRepository<Pays, Integer> {

}
