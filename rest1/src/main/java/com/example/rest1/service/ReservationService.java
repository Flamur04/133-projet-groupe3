package com.example.rest1.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.example.rest1.model.Reservation;
import com.example.rest1.model.User;
import com.example.rest1.repository.ReservationRepository;

import jakarta.transaction.Transactional;

@Service
public class ReservationService {
    private final ReservationRepository reservationRepository;

    @Autowired
    public ReservationService(ReservationRepository reservationRepository) {
        this.reservationRepository = reservationRepository;
    }

    @Transactional
    public Reservation addReservation(Reservation reservation) {
        // Ajouter la réservation en utilisant le ReservationRepository
        return reservationRepository.save(reservation);
    }

    @Transactional
    public List<Reservation> getUserReservations(Integer userId) {
        // Récupérer toutes les réservations associées à l'ID d'utilisateur spécifié
        List<Reservation> userReservations = reservationRepository.findByUserId(userId);
        return userReservations;
    }

    @Transactional
    public Reservation modifieReservation(Reservation reservation) {
        // Vérifier si la réservation existe avant de la modifier
        if (reservation.getId() == null || !reservationRepository.existsById(reservation.getId())) {
            throw new IllegalArgumentException("La réservation n'existe pas");
        }

        // Valider les données de la réservation
        if (!validateReservation(reservation)) {
            throw new IllegalArgumentException("Données de réservation invalides");
        }

        return reservationRepository.save(reservation);
    }

    @Transactional
    public void deleteReservation(Integer id) {
        // Vérifier si la réservation existe avant de la supprimer
        if (!reservationRepository.existsById(id)) {
            throw new IllegalArgumentException("La réservation n'existe pas");
        }

        reservationRepository.deleteById(id);
    }

    // Méthode privée pour valider les données de la réservation
    private boolean validateReservation(Reservation reservation) {
        // Vérifier que les champs obligatoires ne sont pas nuls ou vides
        if (reservation.getPays() == null || reservation.getPays().isEmpty()) {
            return false;
        }
        if (reservation.getPrix() == null || reservation.getPrix() <= 0) {
            return false;
        }
        if (reservation.getDateDepart() == null || reservation.getDateRetour() == null) {
            return false;
        }
        // D'autres validations peuvent être ajoutées ici selon vos besoins
        return true;
    }
}
