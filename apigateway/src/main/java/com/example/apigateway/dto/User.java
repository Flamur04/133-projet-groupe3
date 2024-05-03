package com.example.apigateway.dto;

public class User {

    private Integer pkUser;
    private String username;

    public String getUsername() {
        return username;
    }

    public Integer getPKUser() {
        return pkUser;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public void setPKUser(Integer pk) {
        this.pkUser = pk;
    }
}