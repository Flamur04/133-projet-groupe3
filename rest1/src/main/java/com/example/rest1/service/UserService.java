package com.example.rest1.service;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.example.rest1.model.User;
import com.example.rest1.repository.UserRepository;

import jakarta.transaction.Transactional;

@Service
public class UserService {
    private final UserRepository userRepository;

    @Autowired
    public UserService(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    @Transactional
    public boolean checkCredentials(String username, String password) {
        // Récupérer l'utilisateur par nom d'utilisateur
        User user = userRepository.findByUsername(username);

        // Vérifier si l'utilisateur existe et si le mot de passe correspond
        return user != null && user.getPassword().equals(password);
    }

    public Iterable<User> findAllUsers() {
        Iterable<User> users = userRepository.findAll();
        List<User> usersList = new ArrayList<>();
        users.forEach(usersList::add);
        return usersList;
    }

    @Transactional
    public User addUser(String username, String password) {
        // Vérifier si l'utilisateur existe déjà
        if (userRepository.findByUsername(username) != null) {
            throw new IllegalArgumentException("L'utilisateur existe déjà");
        }

        // Créer un nouvel utilisateur avec le nom d'utilisateur et le mot de passe
        // fournis
        User newUser = new User();
        newUser.setUsername(username);
        newUser.setPassword(password);

        // Enregistrer le nouvel utilisateur dans la base de données
        return userRepository.save(newUser);
    }

    @Transactional
    public User getUserByUsername(String username) {
        // Récupérer l'utilisateur par nom d'utilisateur
        User user = null;
        // Vérifier si l'utilisateur existe
        if (user != null && username != null) {
            user = userRepository.findByUsername(username);
        }
        return user;
    }

}
