package com.example.rest1.dto;

public class UserDTO {
    private Integer id;
    private String username;
    private Boolean isAdmin;

    // Constructeur par défaut
    public UserDTO() {
    }

    // Constructeur avec tous les champs
    public UserDTO(Integer id, String username, Boolean isAdmin) {
        this.id = id;
        this.username = username;
        this.isAdmin = isAdmin;
    }

    // Getters et setters
    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public Boolean getIsAdmin() {
        return isAdmin;
    }

    public void setIsAdmin(Boolean isAdmin) {
        this.isAdmin = isAdmin;
    }
}
