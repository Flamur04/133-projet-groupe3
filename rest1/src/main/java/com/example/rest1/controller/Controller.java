package com.example.rest1.controller;

import java.sql.Blob;
import java.time.LocalDate;
import java.util.Collections;
import java.util.Date;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import com.example.rest1.model.Reservation;
import com.example.rest1.model.User;
import com.example.rest1.service.ReservationService;
import com.example.rest1.service.UserService;
import com.example.rest1.controller.BCryptPasswordEncoder;
import com.example.rest1.dto.UserDTO;

import jakarta.servlet.http.HttpSession;

import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

@RestController
public class Controller {

    private final UserService userService;
    private final ReservationService reservationService;
    private final BCryptPasswordEncoder passwordEncoder;

    /**
     * @param userService
     * @param reservationService
     */
    @Autowired
    public Controller(UserService userService, ReservationService reservationService,
            BCryptPasswordEncoder passwordEncoder) {
        this.userService = userService;
        this.reservationService = reservationService;
        this.passwordEncoder = passwordEncoder;
    }

    // Handler pour GET
    @GetMapping("/")
    public ResponseEntity<String> getNothing(HttpSession session) {
        // Utilisez la session comme nécessaire
        return ResponseEntity.ok("Api ok");
    }

    @PostMapping(path = "addUser")
    public ResponseEntity<?> addUser(@RequestParam String username, @RequestParam String password) {
        try {
            // Hacher le mot de passe
            String hashedPassword = passwordEncoder.hashPassword(password);

            // Ajouter l'utilisateur en utilisant le service utilisateur avec le mot de
            // passe haché et le statut d'administrateur
            userService.addUser(username, hashedPassword);

            return ResponseEntity.ok(true);

        } catch (IllegalArgumentException e) {
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }

    // Handler pour GET
    @GetMapping(path = "/getUsers")
    public ResponseEntity<Iterable<User>> getUsers() {
        // Utilisez la session comme nécessaire
        Iterable<User> users = userService.findAllUsers();
        return ResponseEntity.ok(users);
    }

    /**
     * @param username
     * @param password
     * @param session
     * @return
     */
    @PostMapping(path = "/login")
    public ResponseEntity<?> login(@RequestParam String username, @RequestParam String password) {
        // Hacher le mot de passe fourni et le comparer avec le hachage stocké
        String hashedPassword = passwordEncoder.hashPassword(password);

        // Vérifiez les identifiants de l'utilisateur
        UserDTO user = userService.checkCredentials(username, hashedPassword);

        if (user != null) {
            return ResponseEntity.ok(user);
        } else {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED)
                    .body(Collections.singletonMap("error", "Nom d'utilisateur ou mot de passe invalide."));
        }
    }

       // Handler pour GET
       @GetMapping(path = "/getReservationUser")
       public ResponseEntity<Iterable<Reservation>> getReservationUser(Integer id) {
           // Utilisez la session comme nécessaire
           Iterable<Reservation> reservations = reservationService.getUserReservations(id);
           return ResponseEntity.ok(reservations);
       }

    @PostMapping(path = "/addReservation")
    public ResponseEntity<?> addReservation(@RequestParam Integer fk_voyage, @RequestParam Integer fk_user) {
        try {
            // Créer une nouvelle réservation
            Reservation reservation = new Reservation();

            reservation.setUser(userService.getUserById(fk_user));
            reservation.setVoyage(fk_voyage);
            if (reservation != null) {
                // Ajouter la réservation en utilisant le service de réservation
                reservationService.addReservation(reservation);
                return ResponseEntity.ok(true);
            } else {
                return ResponseEntity.ok(false);
            }
        } catch (IllegalArgumentException e) {
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }

    @PostMapping(path = "/searchUser")
    public ResponseEntity<User> searchUser(@RequestParam Integer id) {
        try {
            User user = userService.getUserById(id);

            return ResponseEntity.ok(user);
        } catch (IllegalArgumentException e) {
            return ResponseEntity.badRequest().body(null);
        }
    }

    @DeleteMapping(path = "/deleteReservation")
    public ResponseEntity<String> deleteReservation(@RequestParam Integer id) {
        try {
            // Supprimer la réservation en utilisant le service de réservation
            reservationService.deleteReservation(id);

            return ResponseEntity.ok("Réservation supprimée avec succès");
        } catch (IllegalArgumentException e) {
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }

    @PutMapping(path = "/modifieReservation")
    public ResponseEntity<String> modifieReservation(@RequestParam Integer fk_voyage, @RequestParam Integer fk_user) {
        try {

            // Récupérer la réservation existante
            Reservation reservation = (Reservation) reservationService.getUserReservations(fk_user);
            if (reservation == null) {
                return ResponseEntity.badRequest().body("La réservation avec l'ID " + fk_user + " n'existe pas");
            }

            // reservation.setUser(userService.getUserById(fk_user));
            reservation.setVoyage(fk_voyage);

            // Modifier la réservation en utilisant le service de réservation
            reservationService.modifieReservation(reservation);

            return ResponseEntity.ok("Réservation modifiée avec succès");
        } catch (IllegalArgumentException e) {
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }

}