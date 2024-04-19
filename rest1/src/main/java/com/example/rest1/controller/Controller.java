package com.example.rest1.controller;

import java.sql.Blob;
import java.time.LocalDate;
import java.util.Date;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
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

    // Handler pour GET
    @GetMapping("/")
    public ResponseEntity<String> getNothing(HttpSession session) {
        // Utilisez la session comme nécessaire
        return ResponseEntity.ok("");
    }

    @PostMapping(path = "/addReservation")
    public ResponseEntity<String> addReservation(@RequestParam String commentaire,
            @RequestParam @DateTimeFormat(pattern = "dd-MM-yyyy") LocalDate date_depart,
            @RequestParam @DateTimeFormat(pattern = "dd-MM-yyyy") LocalDate date_retour,
            @RequestParam String pays,
            @RequestParam Double prix,
            @RequestParam Integer fk_user,
            @RequestParam String img) {

        try {
            // Créer une nouvelle réservation avec les paramètres fournis
            Reservation newReservation = new Reservation();
            newReservation.setCommentaire(commentaire);
            newReservation.setDateDepart(date_depart);
            newReservation.setDateRetour(date_retour);
            newReservation.setPays(pays);
            newReservation.setPrix(prix);
            newReservation.setImg(img);

            // Récupérer l'utilisateur correspondant à l'ID fk_user
            User user = userService.getUserById(fk_user); // Assurez-vous d'implémenter cette méthode dans votre service
                                                          // UserService

            // Définir l'utilisateur pour la réservation
            newReservation.setUser(user);

            // Ajouter la réservation en utilisant le service de réservation
            reservationService.addReservation(newReservation);

            return ResponseEntity.ok("Réservation ajoutée avec succès");
        } catch (IllegalArgumentException e) {
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }

    @GetMapping(path = "getReservationByUser")
    public ResponseEntity<List<Reservation>> getUserReservations(@RequestParam Integer userId) {
        // Récupérer les réservations de l'utilisateur spécifié
        List<Reservation> userReservations = reservationService.getUserReservations(userId);

        if (userReservations.isEmpty()) {
            return ResponseEntity.notFound().build();
        }

        return ResponseEntity.ok(userReservations);
    }

    @PostMapping(path = "addUser")
    public ResponseEntity<String> addUser(@RequestParam String username, @RequestParam String password) {
        try {
            // Hacher le mot de passe
            String hashedPassword = passwordEncoder.hashPassword(password);

            // Ajouter l'utilisateur en utilisant le service utilisateur avec le mot de
            // passe haché
            userService.addUser(username, hashedPassword);

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

        // Vérifiez les identifiants de l'utilisateur
        final boolean validCredentials = userService.checkCredentials(username, password);

        if (!validCredentials) {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body("Identifiants incorrects");
        }

        // Stockez le nom d'utilisateur dans la session
        session.setAttribute("username", username);

        Integer visites = (Integer) session.getAttribute("visites");
        if (visites == null) {
            visites = 0;
        }
        session.setAttribute("visites", visites + 1);
        return ResponseEntity.ok("Logged in with " + username);
    }

    @PostMapping(path = "/logout")
    public ResponseEntity<String> logout(HttpSession session) {
        // Invalidates this session then unbinds any objects bound to it.
        session.invalidate();

        return ResponseEntity.ok("Déconnexion réussie");
    }

    @GetMapping(path = "/visites")
    public ResponseEntity<Integer> visites(HttpSession session) {
        // Récupère le compteur de visites de la session
        Integer visites = (Integer) session.getAttribute("visites");

        return ResponseEntity.ok(visites);
    }
}