package com.service.api.presenters.programmes;

import com.service.api.BaseResource;
import com.service.api.presenters.PresenterResource;
import com.service.api.stations.Stations;
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

import java.sql.Timestamp;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Map;
import java.util.UUID;

import static javax.servlet.http.HttpServletResponse.SC_NOT_FOUND;
import static javax.servlet.http.HttpServletResponse.SC_OK;

@RunWith(SpringRunner.class)
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
public class GetPresenterProgrammesIT {

    @LocalServerPort
    private int port;
    @Autowired
    private TestRestTemplate restTemplate;
    @Autowired
    private NamedParameterJdbcTemplate namedParameterJdbcTemplate;

    private static final String PATH = ProgrammesResource.PRESENTER_PROGRAMMES_PATH;

    @Test
    public void invalidStationIdReturnsNotFound() {
        String stationId = "{" + BaseResource.STATION_ID + "}";
        String presenterIdToken = "{" + PresenterResource.PRESENTER_ID + "}";

        UUID presenterId = UUID.randomUUID();

        String constructedPath = PATH.replace(stationId, "76").replace(presenterIdToken, presenterId.toString());
        ResponseEntity responseEntity = this.restTemplate.getForEntity("http://localhost:" + port + constructedPath, Object.class);

        Assert.assertEquals(responseEntity.getStatusCode().value(), SC_NOT_FOUND);
    }

    @Test
    public void shouldListPresenterProgrammes() {
        final UUID presenterId = UUID.randomUUID();
        namedParameterJdbcTemplate.update("INSERT INTO PRESENTERS " +
                        " (presenter_name, presenter_uuid, presenter_station_id)" +
                        " VALUES (:name, :presenterId, :stationId)",
                Map.of("name", "foo_bar",
                        "presenterId", presenterId,
                        "stationId", Stations.BBC_6_MUSIC.getStationId()));

        for (int i = 1; i <= 10; i++) {
            Map<String, Object> params = Map.of(
                    "programmeId", UUID.randomUUID().toString(),
                    "presenterId", presenterId.toString(),
                    "dateBroadcast", Timestamp.valueOf(LocalDateTime.now()),
                    "href", "foo/nar/fsg",
                    "description", "a programme description"
            );

            namedParameterJdbcTemplate.update("INSERT INTO programmes" +
                    " (programme_uuid, programme_presenter_uuid, programme_date_broadcast," +
                    " programme_ref, programme_description)" +
                    " VALUES (:programmeId, :presenterId, :dateBroadcast, :href, :description)",
                    params);
        }

        String stationId = "{" + BaseResource.STATION_ID + "}";
        String presenterIdToken = "{" + PresenterResource.PRESENTER_ID + "}";
        String constructedPath = PATH.replace(stationId, String.valueOf(Stations.BBC_6_MUSIC.getStationId()))
                .replace(presenterIdToken, presenterId.toString());

        ResponseEntity<List> responseEntity = this.restTemplate.getForEntity("http://localhost:" + port + constructedPath, List.class);

        Assert.assertEquals(responseEntity.getStatusCode().value(), SC_OK);
        List<Object> presenter = responseEntity.getBody();

        Assert.assertNotNull(presenter);
        Assert.assertEquals(presenter.size(), 10);
    }
}
