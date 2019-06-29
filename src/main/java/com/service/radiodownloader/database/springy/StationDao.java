package com.service.radiodownloader.database.springy;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.support.GeneratedKeyHolder;
import org.springframework.jdbc.support.KeyHolder;
import org.springframework.stereotype.Service;

import java.util.List;

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
            KeyHolder keyHolder = new GeneratedKeyHolder();
            var query = "INSERT INTO stations" +
                    " (station_name)" +
                    " VALUES" +
                    " (?,?)";
            jdbcTemplate.update(query, stationName, keyHolder);

            var generatedKey = keyHolder.getKey();
            if (generatedKey != null) {
                return generatedKey.intValue();
            }
            throw new RuntimeException("Auto generated station id key failed");
        } else {
            return stationsWithSameName.get(0);
        }
    }
}
