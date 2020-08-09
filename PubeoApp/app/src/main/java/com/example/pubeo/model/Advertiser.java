package com.example.pubeo.model;

import java.io.Serializable;
import java.util.List;

public class Advertiser implements Serializable {

    private String id;
    private String nomEntreprise;
    private String adresse;
    private String numeroTel;
    private String motDePasse;
    private String mail;
    private String numeroTVA;
    private String localiteCode;
    private Locality localite;
    private List<Sticker> stickers;

    public Advertiser(String id, String nomEntreprise, String adresse, String numeroTel, String motDePasse, String mail, String numeroTVA, String localiteCode, Locality localite, List<Sticker> stickers) {
        this.id = id;
        this.nomEntreprise = nomEntreprise;
        this.adresse = adresse;
        this.numeroTel = numeroTel;
        this.motDePasse = motDePasse;
        this.mail = mail;
        this.numeroTVA = numeroTVA;
        this.localiteCode = localiteCode;
        this.localite = localite;
        this.stickers = stickers;
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

    public String getMotDePasse() {
        return motDePasse;
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

    public Locality getLocalite(){
        return localite;
    }

    public List<Sticker> getStickers() {
        return stickers;
    }
}
