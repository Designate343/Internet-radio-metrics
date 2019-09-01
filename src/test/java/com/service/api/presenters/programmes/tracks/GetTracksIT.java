package com.service.api.presenters.programmes.tracks;

import com.service.api.BaseResource;
import com.service.api.presenters.CommonTestDaos;
import com.service.api.presenters.PresenterResource;
import com.service.api.presenters.programmes.ProgrammesResource;
import com.service.api.stations.Stations;
import org.junit.Assert;
import org.junit.Before;
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
import java.util.UUID;

import static javax.servlet.http.HttpServletResponse.SC_OK;

@RunWith(SpringRunner.class)
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
public class GetTracksIT {

    @LocalServerPort
    private int port;
    @Autowired
    private TestRestTemplate restTemplate;
    @Autowired
    private NamedParameterJdbcTemplate jdbcTemplate;

    private static final String PATH = TracksResource.TRACKS_PATH;
    private CommonTestDaos commonTestDaos;

    @Before
    public void setUp() throws Exception {
        commonTestDaos = new CommonTestDaos(jdbcTemplate);
    }

    @Test
    public void shouldGetTracksSuccessfully() {
        UUID presenterId = commonTestDaos.insertPresenter("presenter_name", Stations.BBC_6_MUSIC.getStationId());
        UUID programmeId = commonTestDaos.insertProgramme( presenterId, "/foo/bar", "a programme description");
        for (int i = 0; i < 10; i++) {
            commonTestDaos.insertTrack(presenterId, programmeId, "track" + i, "artist" + i);
        }

        String stationId = "{" + BaseResource.STATION_ID + "}";
        String presenterIdToken = "{" + PresenterResource.PRESENTER_ID + "}";
        String programmeIdToken = "{" + ProgrammesResource.PROGRAMME_ID + "}";
        String constructedPath = PATH.replace(stationId, String.valueOf(Stations.BBC_6_MUSIC.getStationId()))
                .replace(presenterIdToken, presenterId.toString())
                .replace(programmeIdToken, programmeId.toString());

        ResponseEntity<List> responseEntity = this.restTemplate.getForEntity("http://localhost:" + port + constructedPath, List.class);

        Assert.assertEquals(responseEntity.getStatusCode().value(), SC_OK);
        List<Object> tracks = responseEntity.getBody();

        Assert.assertNotNull(tracks);
        Assert.assertEquals(10, tracks.size());

        for (int i = 0; i < 10; i++) {
            Map<String, Object> response = (Map) tracks.get(i);
            Assert.assertEquals("artist" + i, response.get("artist"));
            Assert.assertEquals("track" + i, response.get("trackName"));
        }
    }
}
