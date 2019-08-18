package com.service.api.presenters.programmes;

import java.time.LocalDateTime;
import java.util.UUID;

public class Programme {

    private final String programmeRef;
    private final UUID programmeId;
    private final String description;
    private final LocalDateTime whenBroadcast;
    private final UUID presenterId;

    public Programme(String programmeRef, UUID programmeId, String description, LocalDateTime whenBroadcast,
            UUID presenterId) {
        this.programmeRef = programmeRef;
        this.programmeId = programmeId;
        this.description = description;
        this.whenBroadcast = whenBroadcast;
        this.presenterId = presenterId;
    }

    public String getProgrammeRef() {
        return programmeRef;
    }

    public UUID getProgrammeId() {
        return programmeId;
    }

    public String getDescription() {
        return description;
    }

    public LocalDateTime getWhenBroadcast() {
        return whenBroadcast;
    }

    public UUID getPresenterId() {
        return presenterId;
    }

}