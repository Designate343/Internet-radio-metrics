package com.service.radiodownloader.database.stations;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.concurrent.ThreadLocalRandom;

@Service
public class StationDao {

    @Autowired
    private JdbcTemplate jdbcTemplate;

    public int writeStationToDatabaseOrRetrieveExistingId(String stationName) {
        List<Integer> stationsWithSameName = jdbcTemplate.query("SELECT station_id" +
                        " FROM stations" +
                        " WHERE station_name = (?)",
                (rs, i) -> rs.getInt(1), stationName);

        if (stationsWithSameName.isEmpty()) {
            var query = "INSERT INTO stations" +
                    " (station_id, station_name)" +
                    " VALUES" +
                    " (?,?)";
            var stationId = ThreadLocalRandom.current().nextInt();
            jdbcTemplate.update(query, stationId, stationName);

            return stationId;
        } else {
            return stationsWithSameName.get(0);
        }
    }
}
