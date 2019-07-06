package com.service.radiodownloader.database.tracks;

import com.service.radiodownloader.dataclasses.Track;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Map;
import java.util.UUID;

@Repository
public class GetTracks {

    @Autowired
    private NamedParameterJdbcTemplate namedParameterJdbcTemplate;

    private static final String BASE_SELECT_TRACKS = "SELECT track_name, track_artist" +
            " FROM TRACKS";

    public List<Track> getAllByPresenterId(UUID presenterId) {
        return namedParameterJdbcTemplate
                .query(BASE_SELECT_TRACKS + " WHERE track_presenter_uuid = :presenterId",
                        Map.of("presenerId", presenterId.toString()),
                        (rs, rowNum) -> new Track(rs.getString("track_name"), rs.getString("track_artist")));
    }

    public List<Track> getAllForProgramme(UUID programmeId) {
        return namedParameterJdbcTemplate
                .query(BASE_SELECT_TRACKS + " WHERE track_programme_origin_uuid = :programmeId",
                        Map.of("programmeId", programmeId.toString()),
                        (rs, rowNum) -> new Track(rs.getString("track_name"), rs.getString("track_artist")));
    }

}
