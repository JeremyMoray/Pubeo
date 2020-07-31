package com.example.pubeo.model;

public class Localite {

    private String codePostal;
    private String ville;

    public Localite(String codePostal, String ville) {
        this.codePostal = codePostal;
        this.ville = ville;
    }

    public String getCodePostal() {
        return codePostal;
    }

    public String getVille() {
        return ville;
    }
}
