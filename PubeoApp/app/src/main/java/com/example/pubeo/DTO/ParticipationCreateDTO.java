package com.example.pubeo.DTO;

public class ParticipationCreateDTO {

    private String particulierId;
    private String stickerId;

    public ParticipationCreateDTO(String particulierId, String stickerId) {
        this.particulierId = particulierId;
        this.stickerId = stickerId;
    }

    public String getParticulierId() {
        return particulierId;
    }

    public String getStickerId() {
        return stickerId;
    }
}
