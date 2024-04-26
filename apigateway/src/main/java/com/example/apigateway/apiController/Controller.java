package main.java.com.example.apigateway.ApiController;

import main.java.com.example.apigateway.services.ServiceApiRest1;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpSession;

@RestController
public class Controller {

    private final ServiceApiRest1 serviceApiRest1;

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

    @PostMapping("/login")
    public ResponseEntity<String> login(@RequestParam String username,
            @RequestParam String password,
            HttpSession session) {
        boolean authenticated = serviceApiRest1.authenticateUser(username, password);

        if (authenticated) {
            session.setAttribute("username", username);
            return ResponseEntity.ok("Logged in with " + username);
        } else {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body("Incorrect username or password");
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

}
