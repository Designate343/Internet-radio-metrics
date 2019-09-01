package com.service.api.presenters;

import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;

import java.sql.Timestamp;
import java.time.LocalDateTime;
import java.util.Map;
import java.util.UUID;

public class CommonTestDaos {

    private final NamedParameterJdbcTemplate jdbcTemplate;

    public CommonTestDaos(NamedParameterJdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }

    public UUID insertProgramme(UUID presenterId, String href, String description) {
        UUID programmeId = UUID.randomUUID();
        Map<String, Object> params = Map.of(
                "programmeId", programmeId.toString(),
                "presenterId", presenterId.toString(),
                "dateBroadcast", Timestamp.valueOf(LocalDateTime.now()),
                "href", href,
                "description", description
        );

        jdbcTemplate.update("INSERT INTO programmes" +
                        " (programme_uuid, programme_presenter_uuid, programme_date_broadcast," +
                        " programme_ref, programme_description)" +
                        " VALUES (:programmeId, :presenterId, :dateBroadcast, :href, :description)",
                params);
        return programmeId;
    }

    public UUID insertPresenter(String name, int stationId) {
        UUID presenterId = UUID.randomUUID();
        jdbcTemplate.update("INSERT INTO PRESENTERS " +
                        " (presenter_name, presenter_uuid, presenter_station_id)" +
                        " VALUES (:name, :presenterId, :stationId)",
                Map.of("name", name,
                        "presenterId", presenterId.toString(),
                        "stationId", stationId));
        return presenterId;
    }

    public UUID insertTrack(UUID presenterId, UUID programmeId, String trackName, String artistName) {
        UUID trackId = UUID.randomUUID();
        jdbcTemplate.update("INSERT INTO tracks" +
                " (track_uuid, track_name, track_artist, track_programme_origin_uuid, track_presenter_uuid)" +
                " VALUES " +
                " (:trackId, :trackName, :trackArtist, :trackOriginId, :trackPresenterId)",
                Map.of(
                        "trackId", trackId.toString(),
                        "trackName", trackName,
                        "trackArtist", artistName,
                        "trackOriginId", programmeId.toString(),
                        "trackPresenterId", presenterId.toString()
                ));
        return trackId;
    }

}
