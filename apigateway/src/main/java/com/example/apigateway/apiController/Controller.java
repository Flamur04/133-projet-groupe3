package com.example.apigateway.apiController;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class Controller {

    private final ServiceApiRest1 serviceApiRest1;
    private final ServiceApiRest2 serviceApiRest2;

    @Autowired
    public Controller(ServiceApiRest1 serviceApiRest1) {
        this.serviceApiRest1 = serviceApiRest1;
    }

    @GetMapping("/getUsers")
    public ResponseEntity<String> getUsers(HttpSession session) {
        if (session.getAttribute("username") != null) {
            // L'utilisateur est connecté, procéder à la récupération des utilisateurs
            String users = serviceApiRest1.getUsers();
            return ResponseEntity.ok(users);
        } else {
            // L'utilisateur n'est pas connecté, renvoyer une erreur non autorisée
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body("User not logged in");
        }
    }

    @GetMapping("/getAllVoyages")
    public ResponseEntity<String> getUsers(HttpSession session) {
        if (session.getAttribute("username") != null) {
            // L'utilisateur est connecté, procéder à la récupération des utilisateurs
            String voyages = serviceApiRest2.getAllVoyages();
            return ResponseEntity.ok(voyages);
        } else {
            // L'utilisateur n'est pas connecté, renvoyer une erreur non autorisée
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body("User not logged in");
        }
    }

    @PostMapping("/login")
    public ResponseEntity<String> login(@RequestParam String username,
            @RequestParam String password,
            HttpSession session) {
        boolean authenticated = serviceApiRest1.login(username, password);
        if (authenticated) {
            session.setAttribute("username", username);
            return ResponseEntity.ok("Logged in with " + username);
        } else {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body("Incorrect username or password");
        }
    }

    @PostMapping("/logout")
    public ResponseEntity<String> logout(HttpSession session) {
        boolean logout = serviceApiRest1.logout();
        if (logout) {
            session.invalidate();
            return ResponseEntity.ok("Logged out");
        } else {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body("Error durant le logout !!");
        }
    }

    @PostMapping("/addUser")
    public ResponseEntity<String> addUser(@RequestParam String username,
            @RequestParam String password) {
        boolean added = serviceApiRest1.addUser(username, password);

        if (added) {
            return ResponseEntity.ok("User added: " + username);
        } else {
            return ResponseEntity.badRequest().body("Failed to add user");
        }
    }

    @PostMapping("/addReservation")
    public ResponseEntity<String> addReservation(@RequestParam Integer Fk_voyage, HttpSession session) {
        boolean added = serviceApiRest1.addReservation(Fk_voyage, Fk_voyage);

        if (added) {

        }
    }

}
