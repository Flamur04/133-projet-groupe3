package com.example.rest1.controller;

import java.util.Date;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RestController;

import com.example.rest1.model.Reservation;
import com.example.rest1.model.User;
import com.example.rest1.service.ReservationService;
import com.example.rest1.service.UserService;

import jakarta.servlet.http.HttpSession;

import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

@RestController
public class Controller {

    private final UserService userService;
    private final ReservationService reservationService;

    /**
     * @param userService
     * @param reservationService
     */
    @Autowired
    public Controller(UserService userService, ReservationService reservationService) {
        this.userService = userService;
        this.reservationService = reservationService;
    }

    // Handler pour GET
    @GetMapping("/")
    public ResponseEntity<String> getNothing(HttpSession session) {
        // Utilisez la session comme nécessaire
        return ResponseEntity.ok("");
    }

    @PostMapping(path = "/addReservation")
    public ResponseEntity<String> addNewReservation(
            @RequestParam String pays,
            @RequestParam Double prix,
            @RequestParam Date dateDepart,
            @RequestParam Date dateRetour,
            @RequestParam String commentaire,
            @RequestParam String img,
            HttpSession session) {

        // Vous devez d'abord récupérer l'ID de l'utilisateur à partir de la session
        Integer userId = (Integer) session.getAttribute("userId");
        if (userId == null) {
            return ResponseEntity.badRequest().body("L'utilisateur n'est pas connecté");
        }

        // Créez une nouvelle réservation avec les paramètres fournis
        Reservation newReservation = new Reservation();
        newReservation.setPays(pays);
        newReservation.setPrix(prix);
        newReservation.setDateDepart(dateDepart);
        newReservation.setDateRetour(dateRetour);
        newReservation.setCommentaire(commentaire);
        newReservation.setImg(img);

        // Ajoutez la réservation en utilisant le service de réservation
        try {
            reservationService.addReservation(newReservation, userId);
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
            // Créer un nouvel utilisateur avec les paramètres fournis
            /*
             * User newUser = new User();
             * newUser.setUsername(username);
             * newUser.setPassword(password);
             */

            // Ajouter l'utilisateur en utilisant le service utilisateur
            userService.addUser(username, password);

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