package com.example.pubeo.model;

import java.util.ArrayList;
import java.util.List;

public class Advertiser {

    private String id;
    private String nomEntreprise;
    private String adresse;
    private String numeroTel;
    private String mail;
    private String numeroTVA;
    private List<Sticker> stickers;

    public Advertiser(String id, String nomEntreprise, String adresse, String numeroTel, String mail, String numeroTVA) {
        this.id = id;
        this.nomEntreprise = nomEntreprise;
        this.adresse = adresse;
        this.numeroTel = numeroTel;
        this.mail = mail;
        this.numeroTVA = numeroTVA;
        stickers = new ArrayList<>();
    }

    public String getId() {
        return id;
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
}
