package com.service.api.query.stations.post;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.stereotype.Repository;

import java.util.Map;

@Repository
public class AddStationDao {

    @Autowired
    private NamedParameterJdbcTemplate namedParameterJdbcTemplate;

    public void insert(String stationName, int id) {
        namedParameterJdbcTemplate.update("INSERT INTO STATIONS (station_name, station_id)" +
                " VALUES (:stationName, :stationId)", Map.of(
                "stationName", stationName,
                "stationId", id
        ));
    }

}
