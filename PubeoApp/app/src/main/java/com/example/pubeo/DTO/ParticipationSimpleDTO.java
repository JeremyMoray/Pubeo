package com.example.pubeo.DTO;

public class ParticipationSimpleDTO {

    private String participationId;
    private String particulierId;

    public ParticipationSimpleDTO(String participationId, String particulierId) {
        this.participationId = participationId;
        this.particulierId = particulierId;
    }

    public String getParticipationId() {
        return participationId;
    }

    public String getParticulierId() {
        return particulierId;
    }
}
