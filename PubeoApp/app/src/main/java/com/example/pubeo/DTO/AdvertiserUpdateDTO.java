package com.example.pubeo.DTO;

public class AdvertiserUpdateDTO {

    private String nomEntreprise;
    private String adresse;
    private String numeroTel;
    private String mail;
    private String numeroTVA;
    private String localiteCode;

    public AdvertiserUpdateDTO(String nomEntreprise, String adresse, String numeroTel, String mail, String numeroTVA, String localiteCode) {
        this.nomEntreprise = nomEntreprise;
        this.adresse = adresse;
        this.numeroTel = numeroTel;
        this.mail = mail;
        this.numeroTVA = numeroTVA;
        this.localiteCode = localiteCode;
    }

    public String getNomEntreprise() {
        return nomEntreprise;
    }

    public String getAdresse() {
        return adresse;
    }

    public String getNumeroTel() {
        return numeroTel;
    }

    public String getMail() {
        return mail;
    }

    public String getNumeroTVA() {
        return numeroTVA;
    }

    public String getLocaliteCode() {
        return localiteCode;
    }
}
