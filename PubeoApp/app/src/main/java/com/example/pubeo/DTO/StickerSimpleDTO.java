package com.example.pubeo.DTO;

public class StickerSimpleDTO {

    private String id;
    private String titre;
    private String description;
    private int hauteur;
    private int largeur;
    private int nbUtilisationsRestantes;
    private String professionnelId;

    public StickerSimpleDTO(String id, String titre, String description, int hauteur, int largeur, int nbUtilisationsRestantes, String professionnelId) {
        this.id = id;
        this.titre = titre;
        this.description = description;
        this.hauteur = hauteur;
        this.largeur = largeur;
        this.nbUtilisationsRestantes = nbUtilisationsRestantes;
        this.professionnelId = professionnelId;
    }

    public String getId() {
        return id;
    }

    public String getTitre() {
        return titre;
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
