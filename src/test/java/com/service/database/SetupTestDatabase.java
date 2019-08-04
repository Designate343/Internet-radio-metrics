package com.service.database;

import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.JdbcTest;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.List;
import java.util.Map;

@RunWith(SpringRunner.class)
@JdbcTest
public class SetupTestDatabase {

    //n.b. connects to h2 in memory database
    @Autowired
    private NamedParameterJdbcTemplate namedParameterJdbcTemplate;

    @Test
    public void databaseConnectionWorks() {
        namedParameterJdbcTemplate.update("INSERT INTO STATIONS (station_name, station_id)" +
                " VALUES (:stationName, :stationId)", Map.of(
                        "stationName", "fooBar",
                        "stationId", "1"
        ));
        List<String> result = namedParameterJdbcTemplate.getJdbcTemplate()
                .query("SELECT (station_name) FROM stations",
                        (rs, rowNum) -> rs.getString("station_name"));

        Assert.assertEquals("fooBar", result.get(0));
    }
}
