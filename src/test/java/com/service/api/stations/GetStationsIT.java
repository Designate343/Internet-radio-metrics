package com.service.api.stations;

import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.boot.web.server.LocalServerPort;
import org.springframework.http.ResponseEntity;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.List;
import java.util.Map;

@RunWith(SpringRunner.class)
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@SuppressWarnings("unchecked")
public class GetStationsIT {

    @LocalServerPort
    private int port;
    @Autowired
    private TestRestTemplate restTemplate;
    @Autowired
    private NamedParameterJdbcTemplate namedParameterJdbcTemplate;

    @Test
    public void canRetrieveStations() {
        namedParameterJdbcTemplate.update("INSERT INTO STATIONS (station_name, station_id)" +
                " VALUES (:stationName, :stationId)", Map.of(
                "stationName", "test",
                "stationId", 1
        ));

        ResponseEntity<List> responseEntity = this.restTemplate.getForEntity("http://localhost:" + port + "internet_radio/stations", List.class);

        List<Map<String, Object>> presenters = responseEntity.getBody();
        var presenter = presenters.get(0);
        Assert.assertEquals("test", presenter.get("name"));
        Assert.assertEquals(1, presenter.get("id"));
    }
}
