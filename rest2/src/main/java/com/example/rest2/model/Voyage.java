package com.example.rest2.model;

import java.util.Date;

public class Voyage {
    private int pkVoyage;
    private String nom;
    private String description;
    private double prix;
    private int nombreJour;
    private String fkPays;
    private int version;
    private Date dateDepart;
    private Date dateRetour;

    // Getters
    public int getPkVoyage() {
        return pkVoyage;
    }

    public String getNom() {
        return nom;
    }

    public String getDescription() {
        return description;
    }

    public double getPrix() {
        return prix;
    }

    public int getNombreJour() {
        return nombreJour;
    }

    public String getFkPays() {
        return fkPays;
    }

    public int getVersion() {
        return version;
    }

    public Date getDateDepart() {
        return dateDepart;
    }

    public Date getDateRetour() {
        return dateRetour;
    }

    // Setters
    public void setPkVoyage(int pkVoyage) {
        this.pkVoyage = pkVoyage;
    }

    public void setNom(String nom) {
        this.nom = nom;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public void setPrix(double prix) {
        this.prix = prix;
    }

    public void setNombreJour(int nombreJour) {
        this.nombreJour = nombreJour;
    }

    public void setFkPays(String fkPays) {
        this.fkPays = fkPays;
    }

    public void setVersion(int version) {
        this.version = version;
    }

    public void setDateDepart(Date dateDepart) {
        this.dateDepart = dateDepart;
    }

    public void setDateRetour(Date dateRetour) {
        this.dateRetour = dateRetour;
    }
}
