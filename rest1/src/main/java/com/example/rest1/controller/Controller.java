package com.example.rest1.controller;

import java.sql.Blob;
import java.time.LocalDate;
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

    @PostMapping(path = "addUser")
    public ResponseEntity<String> addUser(@RequestParam String username, @RequestParam String password,
            @RequestParam Boolean isAdmin) {
        try {
            // Hacher le mot de passe
            String hashedPassword = passwordEncoder.hashPassword(password);

            // Ajouter l'utilisateur en utilisant le service utilisateur avec le mot de
            // passe haché et le statut d'administrateur
            userService.addUser(username, hashedPassword, isAdmin);

            return ResponseEntity.ok("Utilisateur ajouté avec succès");

        } catch (IllegalArgumentException e) {
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }

    /**
     * @param username
     * @param password
     * @param session
     * @return
     */
    @PostMapping(path = "/login")
    public ResponseEntity<String> login(@RequestParam String username, @RequestParam String password,
            HttpSession session) {

        // Hacher le mot de passe fourni et le comparer avec le hachage stocké
        String hashedPassword = passwordEncoder.hashPassword(password);

        // Vérifiez les identifiants de l'utilisateur
        final boolean validCredentials = userService.checkCredentials(username, hashedPassword);

        if (!validCredentials) {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body("Identifiants incorrects");
        }

        // Stockez le nom d'utilisateur dans la session
        session.setAttribute("username", username);

        return ResponseEntity.ok("Logged in with " + username);
    }

    @PostMapping(path = "/logout")
    public ResponseEntity<String> logout(HttpSession session) {
        // Invalidates this session then unbinds any objects bound to it.
        session.invalidate();

        return ResponseEntity.ok("Déconnexion réussie");
    }

    // Handler pour GET
    @GetMapping("/")
    public ResponseEntity<String> getNothing(HttpSession session) {
        // Utilisez la session comme nécessaire
        return ResponseEntity.ok("Api ok");
    }

    @PostMapping(path = "/addReservation")
    public ResponseEntity<String> addReservation(@RequestParam Integer Fk_voyage, HttpSession session) {
        try {
            // Récupérer l'utilisateur connecté à partir de la session
            User user = (User) session.getAttribute("user");
            if (user == null) {
                return ResponseEntity.badRequest().body("Aucun utilisateur connecté");
            }

            // Récupérer le voyage correspondant à l'ID Fk_voyage
            Reservation voyage = (Reservation) reservationService.getVoyageById(Fk_voyage);
            if (voyage == null) {
                return ResponseEntity.badRequest().body("Le voyage avec l'ID " + Fk_voyage + " n'existe pas");
            }

            // Créer une nouvelle réservation
            Reservation reservation = new Reservation();
            reservation.setUser(user);
            reservation.setVoyage(Fk_voyage);

            // Ajouter la réservation en utilisant le service de réservation
            reservationService.addReservation(reservation);

            return ResponseEntity.ok("Réservation ajoutée avec succès");
        } catch (IllegalArgumentException e) {
            return ResponseEntity.badRequest().body(e.getMessage());
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
    public ResponseEntity<String> modifieReservation(@RequestParam Integer Fk_voyage,
            HttpSession session) {
        try {
            // Récupérer l'utilisateur connecté à partir de la session
            User user = (User) session.getAttribute("user");
            if (user == null) {
                return ResponseEntity.badRequest().body("Aucun utilisateur connecté");
            }

            // Récupérer la réservation existante
            Reservation reservation = (Reservation) reservationService.getUserReservations(user.getId());
            if (reservation == null) {
                return ResponseEntity.badRequest().body("La réservation avec l'ID " + user.getId() + " n'existe pas");
            }

            // Définir l'utilisateur pour la réservation
            reservation.setUser(user);

            // Modifier la réservation en utilisant le service de réservation
            reservationService.modifieReservation(reservation);

            return ResponseEntity.ok("Réservation modifiée avec succès");
        } catch (IllegalArgumentException e) {
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }

}