package com.service.api.query.stations.get;

import com.service.api.query.stations.dto.Station;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public class GetStationsDao {

    private static final String GET_STATIONS = "SELECT station_name, station_id" +
            " FROM stations";
    
    @Autowired
    private NamedParameterJdbcTemplate namedParameterJdbcTemplate;

    public List<Station> get() {
        return namedParameterJdbcTemplate.getJdbcTemplate().query(GET_STATIONS,
                (rs, rowNum) -> new Station(rs.getString("station_name"), rs.getInt("station_id")));
    }
}
