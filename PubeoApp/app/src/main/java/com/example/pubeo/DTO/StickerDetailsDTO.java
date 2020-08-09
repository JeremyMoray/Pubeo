package com.example.pubeo.DTO;

import com.example.pubeo.model.Advertiser;

import java.util.List;

public class StickerDetailsDTO {

    private String id;
    private String titre;
    private String description;
    private int hauteur;
    private int largeur;
    private int nbUtilisationsRestantes;
    private Advertiser professionnel;
    private List<ParticipationSimpleDTO> participations;

    public StickerDetailsDTO(String id, String titre, String description, int hauteur, int largeur, int nbUtilisationsRestantes, Advertiser professionnel, List<ParticipationSimpleDTO> participations) {
        this.id = id;
        this.titre = titre;
        this.description = description;
        this.hauteur = hauteur;
        this.largeur = largeur;
        this.nbUtilisationsRestantes = nbUtilisationsRestantes;
        this.professionnel = professionnel;
        this.participations = participations;
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

    public Advertiser getProfessionnel() {
        return professionnel;
    }

    public List<ParticipationSimpleDTO> getParticipations() {
        return participations;
    }
}
