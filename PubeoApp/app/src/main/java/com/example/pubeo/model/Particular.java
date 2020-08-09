package com.example.pubeo.model;

import java.util.Date;
import java.util.List;

public class Particular {

    private String id;
    private String prenom;
    private String nom;
    private String adresse;
    private String pseudo;
    private Date dateNaissance;
    private String numeroTel;
    private String mail;
    private String motDePasse;
    private String localiteCode;
    private Locality localite;
    private List<Participation> participations;
    private List<Vehicule> vehicules;

    public Particular(String id, String prenom, String nom, String adresse, String pseudo, Date dateNaissance, String numeroTel, String mail, String motDePasse, String localiteCode, Locality localite, List<Participation> participations, List<Vehicule> vehicules) {
        this.id = id;
        this.prenom = prenom;
        this.nom = nom;
        this.adresse = adresse;
        this.pseudo = pseudo;
        this.dateNaissance = dateNaissance;
        this.numeroTel = numeroTel;
        this.mail = mail;
        this.motDePasse = motDePasse;
        this.localiteCode = localiteCode;
        this.localite = localite;
        this.participations = participations;
        this.vehicules = vehicules;
    }

    public String getId() {
        return id;
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

    public Locality getLocalite() {
        return localite;
    }

    public List<Participation> getParticipations() {
        return participations;
    }

    public List<Vehicule> getVehicules() {
        return vehicules;
    }
}
