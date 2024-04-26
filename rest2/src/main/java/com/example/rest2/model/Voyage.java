package com.example.rest2.model;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;

import java.time.LocalDate;
import java.util.Date;

import org.springframework.format.annotation.DateTimeFormat;


@Entity
@Table(name = "T_Voyage")
public class Voyage {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "PK_Voyage")
    private Integer id;


    @Column(name = "nom", length = 50)
    private String nom;

    @Column(name = "description", length = 200)
    private String description;

    @Column(name = "prix")
    private double prix;

    @Column(name = "fkPays", length = 50)
    private String fkPays;

    @Column(name = "version", columnDefinition = "int default 0")
    private int version;

    @Column(name = "date_depart")
    @DateTimeFormat(pattern = "dd-MM-yyyy")
    private LocalDate dateDepart;

    @Column(name = "date_retour")
    @DateTimeFormat(pattern = "dd-MM-yyyy")
    private LocalDate dateRetour;

    @Column(name = "image", nullable = true)
    private byte[] image;


    // Getters
    public int getPkVoyage() {
        return id;
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


    public String getFkPays() {
        return fkPays;
    }

    public int getVersion() {
        return version;
    }

    public LocalDate getDateDepart() {
        return dateDepart;
    }

    public LocalDate getDateRetour() {
        return dateRetour;
    }

    public byte[] getImage() {
        return this.image;
    }

    // Setters
    public void setPkVoyage(int pkVoyage) {
        this.id = pkVoyage;
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

    public void setFkPays(String fkPays) {
        this.fkPays = fkPays;
    }

    public void setVersion(int version) {
        this.version = version;
    }

    public void setDateDepart(LocalDate dateDepart) {
        this.dateDepart = dateDepart;
    }

    public void setDateRetour(LocalDate dateRetour) {
        this.dateRetour = dateRetour;
    }

    public void setImage(byte[] image) {
        this.image = image;
    }

}