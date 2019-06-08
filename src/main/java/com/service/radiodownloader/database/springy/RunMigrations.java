package com.service.radiodownloader.database.springy;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class RunMigrations {

    @Autowired
    private NamedParameterJdbcTemplate namedParameterJdbcTemplate;

    public void run() {
        final var stationsSql = "CREATE TABLE IF NOT EXISTS stations (" +
                " station_name VARCHAR(80) UNIQUE NOT NULL," +
                " station_id INTEGER PRIMARY KEY" +
                ")";
        final var presenterSql = "CREATE TABLE IF NOT EXISTS presenters (" +
                " presenter_name VARCHAR(80) NOT NULL PRIMARY KEY," +
                " presenter_uuid VARCHAR(36) NOT NULL UNIQUE," +
                " presenter_station_id INTEGER,"  +
                " FOREIGN KEY (presenter_station_id) REFERENCES stations (station_id)" +
                ");";
        final var programmeSql = "CREATE TABLE IF NOT EXISTS programmes (   " +
                " programme_uuid VARCHAR(36) PRIMARY KEY NOT NULL," +
                " programme_presenter_uuid VARCHAR(36) NOT NULL," +
                " programme_date_broadcast timestamp NOT NULL," +
                " programme_description VARCHAR(1000)," +
                " FOREIGN KEY (programme_presenter_uuid) REFERENCES presenters (presenter_uuid)" +
                ");";
        final var trackSql = "CREATE TABLE IF NOT EXISTS tracks (" +
                " track_name VARCHAR(200) NOT NULL," +
                " track_artist VARCHAR(200) NOT NULL," +
                " track_programme_origin_uuid VARCHAR(36) NOT NULL," +
                " track_presenter_uuid VARCHAR(36) NOT NULL, " +
                " FOREIGN KEY (track_programme_origin_uuid) REFERENCES programmes (programme_uuid)," +
                " FOREIGN KEY (track_presenter_uuid) REFERENCES presenters (presenter_uuid)" +
                ");";

        for (var migration : List.of(stationsSql, presenterSql, programmeSql, trackSql)) {
            namedParameterJdbcTemplate.getJdbcTemplate().execute(migration);
        }
    }
}
