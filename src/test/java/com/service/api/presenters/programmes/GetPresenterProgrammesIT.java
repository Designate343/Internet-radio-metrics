package com.service.api.presenters.programmes;

import com.service.api.query.BaseResource;
import com.service.api.query.presenters.PresenterResource;
import com.service.api.query.presenters.programmes.ProgrammesResource;
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

import java.util.UUID;

import static javax.servlet.http.HttpServletResponse.SC_NOT_FOUND;

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

}
