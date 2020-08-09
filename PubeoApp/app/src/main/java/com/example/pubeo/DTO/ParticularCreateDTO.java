package com.example.pubeo.DTO;

import java.util.Date;

public class ParticularCreateDTO {

    private String prenom;
    private String nom;
    private String adresse;
    private String pseudo;
    private Date dateNaissance;
    private String numeroTel;
    private String mail;
    private String motDePasse;
    private String localiteCode;

    public ParticularCreateDTO(String prenom, String nom, String adresse, String pseudo, Date dateNaissance, String numeroTel, String mail, String motDePasse, String localiteCode) {
        this.prenom = prenom;
        this.nom = nom;
        this.adresse = adresse;
        this.pseudo = pseudo;
        this.dateNaissance = dateNaissance;
        this.numeroTel = numeroTel;
        this.mail = mail;
        this.motDePasse = motDePasse;
        this.localiteCode = localiteCode;
    }

    public String getPrenom() {
        return prenom;
    }

    public String getNom() {
        return nom;
    }

    public String getAdresse() {
        return adresse;
    }

    public String getPseudo() {
        return pseudo;
    }

    public Date getDateNaissance() {
        return dateNaissance;
    }

    public String getNumeroTel() {
        return numeroTel;
    }

    public String getMail() {
        return mail;
    }

    public String getMotDePasse() {
        return motDePasse;
    }

    public String getLocaliteCode() {
        return localiteCode;
    }
}
