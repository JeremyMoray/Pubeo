package com.example.pubeo.model;

public class Locality {

    private String codePostal;
    private String ville;

    public Locality(String codePostal, String ville) {
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
