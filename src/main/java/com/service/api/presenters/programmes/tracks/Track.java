package com.service.api.presenters.programmes.tracks;

import java.util.UUID;

public class Track {

    private final UUID id;
    private final String artist;
    private final String trackName;

    public Track(UUID id, String artist, String trackName) {
        this.id = id;
        this.artist = artist;
        this.trackName = trackName;
    }

    public UUID getId() {
        return id;
    }

    public String getArtist() {
        return artist;
    }

    public String getTrackName() {
        return trackName;
    }
}
