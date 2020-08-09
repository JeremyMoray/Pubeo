package com.example.pubeo.model;

public class Vehicule {

    private String id;
    private String marque;
    private String modele;
    private String particulierId;
    private Particular particulier;

    public Vehicule(String id, String marque, String modele, String particulierId, Particular particulier) {
        this.id = id;
        this.marque = marque;
        this.modele = modele;
        this.particulierId = particulierId;
        this.particulier = particulier;
    }

    public String getId() {
        return id;
    }

    public String getMarque() {
        return marque;
    }

    public String getModele() {
        return modele;
    }

    public String getParticulierId() {
        return particulierId;
    }

    public Particular getParticulier() {
        return particulier;
    }
}
