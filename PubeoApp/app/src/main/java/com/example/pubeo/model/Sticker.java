package com.example.pubeo.model;

public class Sticker {
    private String id;
    private String title;
    private String description;
    public int hauteur;
    public int largeur;
    public int nbUtilisationsRestantes;

    public Sticker(String id, String title, String description, int hauteur, int largeur, int nbUtilisationsRestantes) {
        this.id = id;
        this.title = title;
        this.description = description;
        this.hauteur = hauteur;
        this.largeur = largeur;
        this.nbUtilisationsRestantes = nbUtilisationsRestantes;
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
}
