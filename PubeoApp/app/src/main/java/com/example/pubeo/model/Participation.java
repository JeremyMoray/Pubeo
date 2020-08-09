package com.example.pubeo.model;

public class Participation {

    private String participationId;
    private String particulierId;
    private String stickerId;
    private Particular particulier;
    private Sticker sticker;

    public Participation(String participationId, String particulierId, String stickerId, Particular particulier, Sticker sticker) {
        this.participationId = participationId;
        this.particulierId = particulierId;
        this.stickerId = stickerId;
        this.particulier = particulier;
        this.sticker = sticker;
    }

    public String getParticipationId() {
        return participationId;
    }

    public String getParticulierId() {
        return particulierId;
    }

    public String getStickerId() {
        return stickerId;
    }

    public Particular getParticulier() {
        return particulier;
    }

    public Sticker getSticker() {
        return sticker;
    }
}
