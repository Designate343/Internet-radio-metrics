package com.service.radiodownloader.database.tracks;

import com.service.radiodownloader.dataclasses.Track;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

@Service
public class WriteTracks {

    @Autowired
    private NamedParameterJdbcTemplate namedParameterJdbcTemplate;


    public void insertTracks(List<Track> trackInfo, UUID programmeID, UUID presenterId) {
        var query = "INSERT INTO TRACKS" +
                " (track_name, track_artist, track_programme_origin_uuid, track_presenter_uuid)" +
                " VALUES" +
                " (?, ?, ?, ?)";

        List<Object[]> tracks = new ArrayList<>(trackInfo.size());
        for (var track : trackInfo) {
            tracks.add(new Object[]{track.getSong_name(), track.getArtist(), programmeID, presenterId});
        }
        namedParameterJdbcTemplate.getJdbcTemplate().batchUpdate(query, tracks);
    }
}
