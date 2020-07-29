package com.example.pubeo.model;

import java.io.Serializable;

public class Sticker implements Serializable {
    private String id;
    private String title;
    private String description;
    public int hauteur;
    public int largeur;
    public int nbUtilisationsRestantes;
    public String professionnelId;

    public Sticker(String id, String title, String description, int hauteur, int largeur, int nbUtilisationsRestantes, String professionnelId) {
        this.id = id;
        this.title = title;
        this.description = description;
        this.hauteur = hauteur;
        this.largeur = largeur;
        this.nbUtilisationsRestantes = nbUtilisationsRestantes;
        this.professionnelId = professionnelId;
    }

    public String getId() {
        return id;
    }

    public String getTitle() {
        return title;
    }

    public String getDescription() {
        return description;
    }

    public int getHauteur() {
        return hauteur;
    }

    public int getLargeur() {
        return largeur;
    }

    public int getNbUtilisationsRestantes() {
        return nbUtilisationsRestantes;
    }

    public String getProfessionnelId() {
        return professionnelId;
    }
}
