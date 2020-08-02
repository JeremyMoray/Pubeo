package com.example.pubeo.model;

import com.example.pubeo.DTO.ParticipationSimpleDTO;

import java.io.Serializable;
import java.util.List;

public class Sticker implements Serializable {
    private String id;
    private String titre;
    private String description;
    private int hauteur;
    private int largeur;
    private int nbUtilisationsRestantes;
    private String professionnelId;
    private Advertiser professionnel;
    private List<ParticipationSimpleDTO> participations;

    public Sticker(String id, String titre, String description, int hauteur, int largeur, int nbUtilisationsRestantes, String professionnelId, Advertiser professionnel, List<ParticipationSimpleDTO> participations) {
        this.id = id;
        this.titre = titre;
        this.description = description;
        this.hauteur = hauteur;
        this.largeur = largeur;
        this.nbUtilisationsRestantes = nbUtilisationsRestantes;
        this.professionnelId = professionnelId;
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

    public String getProfessionnelId() {
        return professionnelId;
    }

    public Advertiser getProfessionnel() {
        return professionnel;
    }

    public List<ParticipationSimpleDTO> getParticipations() {
        return participations;
    }
}
