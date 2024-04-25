package com.example.rest1.repository;

import java.util.List;

import org.springframework.data.repository.CrudRepository;
import com.example.rest1.model.Reservation;

public interface ReservationRepository extends CrudRepository<Reservation, Integer> {
    List<Reservation> findByUserId(Integer userId);
    List<Reservation> findByVoyageId(Integer voyageId);
}
